package pl.mpak.orbada.universal.gui.procedures;

import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pl.mpak.orbada.gui.comps.table.ViewTable;
import pl.mpak.orbada.gui.util.SimpleSelectDialog;
import pl.mpak.orbada.plugins.IViewAccesibilities;
import pl.mpak.orbada.universal.OrbadaUniversalPlugin;
import pl.mpak.orbada.gui.ITabObjectInfo;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.sky.gui.swing.TabCloseComponent;
import pl.mpak.usedb.UseDBException;
import pl.mpak.usedb.core.Database;
import pl.mpak.usedb.core.Query;
import pl.mpak.usedb.gui.swing.QueryTableCellRenderer;
import pl.mpak.usedb.gui.swing.QueryTableCellRendererFilter;
import pl.mpak.usedb.gui.swing.QueryTableColumn;
import pl.mpak.usedb.util.QueryUtil;
import pl.mpak.util.ExceptionUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.StringUtil;
import pl.mpak.util.Titleable;
import pl.mpak.util.timer.Timer;
import pl.mpak.util.variant.Variant;
import pl.mpak.util.variant.VariantException;

/**
 *
 * @author  akaluza
 */
public class ProceduresPanelView extends javax.swing.JPanel implements Closeable {
  
  private final StringManager stringManager = StringManagerFactory.getStringManager("universal");

  private IViewAccesibilities accesibilities;
  private String currentCatalogName;
  private String currentSchemaName;
  private String tabTitle;
  private boolean viewClosing = false;
  private boolean refreshing = false;
  private Timer timer;
  private boolean catalogPresents;
  private boolean schemaPresents;
  
  public ProceduresPanelView(IViewAccesibilities accesibilities) {
    this.accesibilities = accesibilities;
    tabTitle = this.accesibilities.getTabTitle();
    initComponents();
    init();
  }
  
  private void init() {
    try {
      catalogPresents = !StringUtil.isEmpty(getDatabase().getMetaData().getCatalogTerm());
      schemaPresents = !StringUtil.isEmpty(getDatabase().getMetaData().getSchemaTerm());
    } catch (SQLException ex) {
      catalogPresents = false;
      schemaPresents = true;
    }

    timer = new Timer(250) {
      {
        setEnabled(false);
      }
      public void run() {
        setEnabled(false);
        refreshTabbedPanes();
      }
    };
    OrbadaUniversalPlugin.refreshQueue.add(timer);

    textSchema.addKeyListener(new KeyListener() {
      public void keyTyped(KeyEvent e) {
      }
      public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
          setCurrentSchemaName(textSchema.getText());
          refreshTableList();
        }
      }
      public void keyReleased(KeyEvent e) {
      }
    });

    currentSchemaName = getDatabase().getUserName().toUpperCase();
    try {
      if (!StringUtil.equalAnyOfString(currentSchemaName, getDatabase().getSchemaArray())) {
        currentSchemaName = null;
      }
    } catch (Exception ex) {
      ExceptionUtil.processException(ex);
      currentSchemaName = null;
    }
    
    tableTables.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (!refreshing) {
          timer.restart();
        }
      }
    });
    addInfoPanel(new ProcedureColumnsPanel(accesibilities), -1);
    
    tableTables.getQuery().setDatabase(getDatabase());
    try {
      tableTables.getQuery().setFlushMode(Query.FlushMode.fmSynch);
    } catch (UseDBException ex) {
    }
    tableTables.addColumn(new QueryTableColumn("PROCEDURE_NAME", stringManager.getString("procedure-name"), 150, new QueryTableCellRenderer(java.awt.Font.BOLD)));
    tableTables.addColumn(new QueryTableColumn("PROCEDURE_TYPE", stringManager.getString("procedure-type"), 120, new QueryTableCellRenderer(new QueryTableCellRendererFilter() {
      public void cellRendererPerformed(JTable table, Component renderer, Object value, boolean isSelected, boolean hasFocus) {
        if (value != null) {
          try {
            if (new Variant(value).getInteger() == 1) {
              ((JLabel)renderer).setHorizontalAlignment(JLabel.LEFT);
              ((JLabel)renderer).setText("PROCEDURE");
            } else if (new Variant(value).getInteger() == 2) {
              ((JLabel)renderer).setHorizontalAlignment(JLabel.LEFT);
              ((JLabel)renderer).setText("FUNCTION");
            }
          } catch (VariantException ex) {
          }
          ((JLabel)renderer).setForeground(SwingUtil.Color.GREEN);
        }
      }
    })));
    tableTables.addColumn(new QueryTableColumn("REMARKS", stringManager.getString("comment"), 300));
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        tableTables.requestFocusInWindow();
      }
    });
  }
  
  private void setCurrentSchemaName(String schemaName) {
    if (schemaPresents) {
      if (!StringUtil.equals(schemaName, currentSchemaName)) {
        currentSchemaName = schemaName;
        if (!currentSchemaName.equalsIgnoreCase(getDatabase().getUserName())) {
          accesibilities.setTabTitle(tabTitle +" (" +currentSchemaName +")");
        }
        else {
          accesibilities.setTabTitle(tabTitle);
        }
      }
    }
    else if (catalogPresents) {
      if (!StringUtil.equals(schemaName, currentCatalogName)) {
        currentCatalogName = schemaName;
        if (!currentCatalogName.equalsIgnoreCase(getDatabase().getUserName())) {
          accesibilities.setTabTitle(tabTitle +" (" +currentCatalogName +")");
        }
        else {
          accesibilities.setTabTitle(tabTitle);
        }
      }
    }
  }
  
  private void refreshTabbedPanes() {
    String procedureName = "";
    String procedureCat = null;
    int rowIndex = tableTables.getSelectedRow();
    if (rowIndex >= 0 && tableTables.getQuery().isActive()) {
      try {
        tableTables.getQuery().getRecord(rowIndex);
        procedureName = tableTables.getQuery().fieldByName("PROCEDURE_NAME").getString();
        procedureCat = tableTables.getQuery().fieldByName("PROCEDURE_CAT").getString();
      } catch (Exception ex) {
        ExceptionUtil.processException(ex);
      }
    }
    for (int i=0; i<tabbedTableInfo.getTabCount(); i++) {
      Component c = tabbedTableInfo.getComponentAt(i);
      if (c instanceof ITabObjectInfo) {
        ((ITabObjectInfo)c).refresh(procedureCat, currentSchemaName, procedureName);
      }
    }
  }
  
  private void refreshTableList() {
    if (!isVisible() || viewClosing) {
      return;
    }
    try {
      refreshing = true;
      String procedureName = null;
      if (tableTables.getQuery().isActive() && tableTables.getSelectedRow() >= 0) {
        tableTables.getQuery().getRecord(tableTables.getSelectedRow());
        procedureName = tableTables.getQuery().fieldByName("PROCEDURE_NAME").getString();
      }
      tableTables.getQuery().close();
      tableTables.getQuery().setResultSet(getDatabase().getMetaData().getProcedures(currentCatalogName, currentSchemaName, null));
      if (procedureName != null && tableTables.getQuery().locate("PROCEDURE_NAME", new Variant(procedureName))) {
        tableTables.changeSelection(tableTables.getQuery().getCurrentRecord().getIndex(), tableTables.getQuery().getCurrentRecord().getIndex());
      } else if (!tableTables.getQuery().isEmpty()) {
        tableTables.changeSelection(0, 0);
      }
    } catch (Exception ex) {
      ExceptionUtil.processException(ex);
    }
    finally {
      refreshing = false;
      refreshTabbedPanes();
    }
  }
  
  private void addInfoPanel(JPanel panel, int index) {
    String title = "???";
    if (panel instanceof Titleable) {
      title = ((Titleable)panel).getTitle();
    }
    if (index >= 0) {
      tabbedTableInfo.insertTab(title, null, panel, null, index);
    }
    else {
      tabbedTableInfo.addTab(title, panel);
      index = tabbedTableInfo.indexOfComponent(panel);
    }
    tabbedTableInfo.setTabComponentAt(index, new TabCloseComponent(title));
  }
  
  public void close() throws IOException {
    timer.cancel();
    viewClosing = true;
    int i = 0;
    while (i<tabbedTableInfo.getTabCount()) {
      Component c = tabbedTableInfo.getComponentAt(i);
      if (c instanceof Closeable) {
        try {
          ((Closeable)c).close();
        } catch (IOException ex) {
        }
      } else {
        i++;
      }
      tabbedTableInfo.remove(c);
    }
    tableTables.getQuery().close();
    accesibilities = null;
  }
  
  public Database getDatabase() {
    return accesibilities.getDatabase();
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmRefresh = new pl.mpak.sky.gui.swing.Action();
    cmSelectSchema = new pl.mpak.sky.gui.swing.Action();
    splinPane = new javax.swing.JSplitPane();
    panelTables = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    toolBarTables = new javax.swing.JToolBar();
    buttonRefresh = new pl.mpak.sky.gui.swing.comp.ToolButton();
    jLabel1 = new javax.swing.JLabel();
    buttonSelectSchema = new pl.mpak.sky.gui.swing.comp.ToolButton();
    textSchema = new pl.mpak.sky.gui.swing.comp.TextField();
    jScrollPane1 = new javax.swing.JScrollPane();
    tableTables = new ViewTable();
    statusBarTables = new pl.mpak.usedb.gui.swing.QueryTableStatusBar();
    tabbedTableInfo = new javax.swing.JTabbedPane();

    cmRefresh.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/res/icons/refresh16.gif")); // NOI18N
    cmRefresh.setText(stringManager.getString("cmRefresh-text")); // NOI18N
    cmRefresh.setTooltip(stringManager.getString("cmRefresh-hint")); // NOI18N
    cmRefresh.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmRefreshActionPerformed(evt);
      }
    });

    cmSelectSchema.setSmallIcon(pl.mpak.sky.gui.swing.ImageManager.getImage("/res/icons/users16.gif")); // NOI18N
    cmSelectSchema.setText(stringManager.getString("cmSelectSchema-text")); // NOI18N
    cmSelectSchema.setTooltip(stringManager.getString("cmSelectSchema-hint")); // NOI18N
    cmSelectSchema.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmSelectSchemaActionPerformed(evt);
      }
    });

    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {
        formComponentShown(evt);
      }
    });
    setLayout(new java.awt.BorderLayout());

    splinPane.setBorder(null);
    splinPane.setDividerLocation(250);
    splinPane.setContinuousLayout(true);
    splinPane.setOneTouchExpandable(true);

    panelTables.setPreferredSize(new java.awt.Dimension(250, 100));
    panelTables.setLayout(new java.awt.BorderLayout());

    jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

    toolBarTables.setFloatable(false);
    toolBarTables.setRollover(true);

    buttonRefresh.setAction(cmRefresh);
    buttonRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    buttonRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    toolBarTables.add(buttonRefresh);

    jLabel1.setText(stringManager.getString("schema-dd")); // NOI18N
    toolBarTables.add(jLabel1);

    buttonSelectSchema.setAction(cmSelectSchema);
    buttonSelectSchema.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    buttonSelectSchema.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    toolBarTables.add(buttonSelectSchema);

    textSchema.setPreferredSize(new java.awt.Dimension(90, 20));
    toolBarTables.add(textSchema);

    jPanel1.add(toolBarTables);

    panelTables.add(jPanel1, java.awt.BorderLayout.NORTH);

    jScrollPane1.setViewportView(tableTables);

    panelTables.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    statusBarTables.setShowFieldType(false);
    statusBarTables.setShowFieldValue(false);
    statusBarTables.setShowOpenTime(false);
    statusBarTables.setTable(tableTables);
    panelTables.add(statusBarTables, java.awt.BorderLayout.SOUTH);

    splinPane.setLeftComponent(panelTables);
    splinPane.setRightComponent(tabbedTableInfo);

    add(splinPane, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
  if (!tableTables.getQuery().isActive()) {
    refreshTableList();
  }
}//GEN-LAST:event_formComponentShown
  
private void cmSelectSchemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmSelectSchemaActionPerformed
  Query query = getDatabase().createQuery();
  try {
    Vector<String> vl = null;
    if (schemaPresents) {
      query.setResultSet(getDatabase().getMetaData().getSchemas());
      vl = QueryUtil.staticData("{table_schem}", query);
    }
    else if (catalogPresents) {
      query.setResultSet(getDatabase().getMetaData().getCatalogs());
      vl = QueryUtil.staticData("{table_cat}", query);
    }
    if (vl != null) {
      Point point = buttonSelectSchema.getLocationOnScreen();
      point.y+= buttonSelectSchema.getHeight();
      SimpleSelectDialog.select((Window)SwingUtil.getWindowComponent(this), point.x, point.y, vl, vl.indexOf(currentSchemaName), new SimpleSelectDialog.CallBack() {
        public void selected(Object o) {
          setCurrentSchemaName(o.toString().trim());
          textSchema.setText(o.toString().trim());
          refreshTableList();
        }
      });
    }
  }
  catch (Exception ex) {
    ExceptionUtil.processException(ex);
  }
  finally {
    query.close();
  }
}//GEN-LAST:event_cmSelectSchemaActionPerformed

private void cmRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmRefreshActionPerformed
  refreshTableList();
}//GEN-LAST:event_cmRefreshActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonRefresh;
  private pl.mpak.sky.gui.swing.comp.ToolButton buttonSelectSchema;
  private pl.mpak.sky.gui.swing.Action cmRefresh;
  private pl.mpak.sky.gui.swing.Action cmSelectSchema;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JPanel panelTables;
  private javax.swing.JSplitPane splinPane;
  private pl.mpak.usedb.gui.swing.QueryTableStatusBar statusBarTables;
  private javax.swing.JTabbedPane tabbedTableInfo;
  private ViewTable tableTables;
  private pl.mpak.sky.gui.swing.comp.TextField textSchema;
  private javax.swing.JToolBar toolBarTables;
  // End of variables declaration//GEN-END:variables
  
}
