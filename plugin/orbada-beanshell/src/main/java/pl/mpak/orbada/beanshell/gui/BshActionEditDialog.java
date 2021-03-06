/*
 * OgQueryInfoDialog.java
 *
 * Created on 15 październik 2008, 18:37
 */

package pl.mpak.orbada.beanshell.gui;

import java.awt.event.KeyEvent;
import java.beans.IntrospectionException;
import javax.swing.JComponent;

import pl.mpak.orbada.gui.comps.OrbadaJavaSyntaxTextArea;
import pl.mpak.orbada.beanshell.OrbadaBeanshellPlugin;
import pl.mpak.orbada.beanshell.db.BshActionRecord;
import pl.mpak.orbada.core.Application;
import pl.mpak.orbada.plugins.IApplication;
import pl.mpak.orbada.plugins.ISettings;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.usedb.UseDBException;
import pl.mpak.usedb.gui.RecordLink;
import pl.mpak.usedb.gui.linkreq.FieldRequeiredNotNull;
import pl.mpak.util.ExceptionUtil;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.StringUtil;
import pl.mpak.util.variant.Variant;

/**
 *
 * @author  akaluza
 */
public class BshActionEditDialog extends javax.swing.JDialog {

  private final StringManager stringManager = StringManagerFactory.getStringManager("beanshell");

  private IApplication application;
  private int modalResult = ModalResult.NONE;
  private BshActionRecord action;
  private RecordLink dataLink;
  private String bsha_id;
  private ISettings settings;
  
  /** Creates new form OgQueryInfoDialog */
  public BshActionEditDialog(IApplication application, String bsha_id, BshActionRecord action) throws IntrospectionException, UseDBException {
    super(SwingUtil.getRootFrame(), true);
    this.application = application;
    this.bsha_id = bsha_id;
    this.action = action;
    initComponents();
    init();
  }
  
  public static String show(IApplication application, String bsha_id) throws IntrospectionException, UseDBException {
    BshActionEditDialog dialog = new BshActionEditDialog(application, bsha_id, null);
    dialog.setVisible(true);
    return (dialog.modalResult == ModalResult.OK ? dialog.bsha_id : null);
  }
  
  public static String show(IApplication application, String bsha_id, BshActionRecord action) throws IntrospectionException, UseDBException {
    BshActionEditDialog dialog = new BshActionEditDialog(application, bsha_id, action);
    dialog.setVisible(true);
    return (dialog.modalResult == ModalResult.OK ? dialog.bsha_id : null);
  }
  
  private void init() throws IntrospectionException, UseDBException {
    try {
      queryDriverTypes.open();
    } catch (Exception ex) {
      ExceptionUtil.processException(ex);
    }

    settings = Application.get().getSettings("orbada-beanshell=action-edit-dialog");
    try {
      setBounds(0, 0, settings.getValue("width", new Variant(getWidth())).getInteger(), settings.getValue("height", new Variant(getHeight())).getInteger());
    } catch (Exception ex) {
    }
    
    dataLink = new RecordLink();
    dataLink.add("BSHA_TITLE", editName, new FieldRequeiredNotNull(stringManager.getString("action-title")));
    dataLink.add("BSHA_KEY", editKey, new FieldRequeiredNotNull(stringManager.getString("action-key")));
    dataLink.add("BSHA_TOOLTIP", editTooltip);
    dataLink.add("BSHA_DTP_ID", comboDriverType, "selectedItem");
    dataLink.add("BSHA_SCRIPT", editScript, new FieldRequeiredNotNull(stringManager.getString("beanshell-script")));
    
    if (action == null) {
      if (bsha_id != null) {
        action = new BshActionRecord(application.getOrbadaDatabase(), bsha_id);
      } else {
        action = new BshActionRecord(application.getOrbadaDatabase());
      }
    }
    dataLink.updateComponents(action);
    checkAllUsers.setSelected(action.getUsrId() == null);
    if (!application.isUserAdmin()) {
      checkAllUsers.setEnabled(false);
      checkAllUsers.setSelected(true);
    }
    textShortcut.setText(SwingUtil.shortcutText(action.getShortcutCode() != null ? action.getShortcutCode() : 0, action.getShortcutModifiers() != null ? action.getShortcutModifiers() : 0));
    
    getRootPane().setDefaultButton(buttonOk);
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(cmCancel.getShortCut(), "cmCancel");
    getRootPane().getActionMap().put("cmCancel", cmCancel);
    
    SwingUtil.centerWithinScreen(this);
  }
  
  @Override
  public void dispose() {
    settings.setValue("width", new Variant(getWidth()));
    settings.setValue("height", new Variant(getHeight()));
    settings.store();
    
    queryDriverTypes.close();
    super.dispose();
  }
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmOk = new pl.mpak.sky.gui.swing.Action();
    cmCancel = new pl.mpak.sky.gui.swing.Action();
    queryDriverTypes = new pl.mpak.usedb.core.Query();
    buttonOk = new javax.swing.JButton();
    buttonCancel = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    editName = new pl.mpak.sky.gui.swing.comp.TextField();
    jLabel2 = new javax.swing.JLabel();
    comboDriverType = new pl.mpak.usedb.gui.swing.QueryComboBox();
    jLabel3 = new javax.swing.JLabel();
    checkAllUsers = new javax.swing.JCheckBox();
    jLabel4 = new javax.swing.JLabel();
    editKey = new pl.mpak.sky.gui.swing.comp.TextField();
    jLabel5 = new javax.swing.JLabel();
    editTooltip = new pl.mpak.sky.gui.swing.comp.TextField();
    jLabel6 = new javax.swing.JLabel();
    editScript = new OrbadaJavaSyntaxTextArea();
    textShortcut = new pl.mpak.sky.gui.swing.comp.TextField();

    cmOk.setActionCommandKey("cmOk");
    cmOk.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0));
    cmOk.setText(stringManager.getString("cmOk-text")); // NOI18N
    cmOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmOkActionPerformed(evt);
      }
    });

    cmCancel.setActionCommandKey("cmCancel");
    cmCancel.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
    cmCancel.setText(stringManager.getString("cmCancel-text")); // NOI18N
    cmCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmCancelActionPerformed(evt);
      }
    });

    queryDriverTypes.setDatabase(application.getOrbadaDatabase());
    try {
      queryDriverTypes.setSqlText("select cast(null as varchar(40)) dtp_id, '<dla wszystkich>' dtp_name, 1 sort from dual\nunion all \nselect dtp_id, dtp_name, 2 sort from driver_types\norder by sort, dtp_name\n");
    } catch (pl.mpak.usedb.UseDBException e1) {
      e1.printStackTrace();
    }

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(stringManager.getString("BshActionEditDialog-title")); // NOI18N

    buttonOk.setAction(cmOk);
    buttonOk.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonOk.setPreferredSize(new java.awt.Dimension(85, 25));

    buttonCancel.setAction(cmCancel);
    buttonCancel.setMargin(new java.awt.Insets(2, 2, 2, 2));
    buttonCancel.setPreferredSize(new java.awt.Dimension(85, 25));

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel1.setText(stringManager.getString("action-title-dd")); // NOI18N

    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel2.setText(stringManager.getString("for-driver-dd")); // NOI18N

    comboDriverType.setDisplayField("DTP_NAME");
    comboDriverType.setKeyField("DTP_ID");
    comboDriverType.setQuery(queryDriverTypes);

    jLabel3.setText(stringManager.getString("beanshell-script-dd")); // NOI18N

    checkAllUsers.setText(stringManager.getString("for-all-users")); // NOI18N

    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel4.setText(stringManager.getString("action-key-dd")); // NOI18N

    jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel5.setText(stringManager.getString("tooltip-dd")); // NOI18N

    jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel6.setText(stringManager.getString("shortcut-key-dd")); // NOI18N

    textShortcut.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        textShortcutKeyPressed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(editScript, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(checkAllUsers)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
            .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(editName, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(editKey, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(editTooltip, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(comboDriverType, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textShortcut, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(jLabel3))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(editName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel4)
          .addComponent(editKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel5)
          .addComponent(editTooltip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(comboDriverType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel6)
          .addComponent(textShortcut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel3)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(editScript, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(checkAllUsers))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

private void cmOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmOkActionPerformed
  try {
    dataLink.updateRecord(action);
    if (checkAllUsers.isSelected()) {
      action.setUsrId(null);
    }
    else {
      action.setUsrId(application.getUserId());
    }
    if (action.isChanged()) {
      if (bsha_id == null) {
        action.applyInsert();
        bsha_id = action.getId();
      } else {
        action.applyUpdate();
      }
    }
    modalResult = ModalResult.OK;
    dispose();
  } catch (Exception ex) {
    MessageBox.show(this, stringManager.getString("error"), ex.getMessage(), new int[] {ModalResult.OK});
  }
}//GEN-LAST:event_cmOkActionPerformed

private void cmCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmCancelActionPerformed
  modalResult = ModalResult.CANCEL;
  dispose();
}//GEN-LAST:event_cmCancelActionPerformed

private void textShortcutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textShortcutKeyPressed
  if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE && !StringUtil.isEmpty(textShortcut.getText())) {
    textShortcut.setText("");
    action.setShortcutCode(null);
    action.setShortcutModifiers(null);
  }
  else {
    textShortcut.setText(SwingUtil.shortcutText(evt.getKeyCode(), evt.getModifiers()));
    action.setShortcutCode(evt.getKeyCode());
    action.setShortcutModifiers(evt.getModifiers());
  }
  evt.consume();
}//GEN-LAST:event_textShortcutKeyPressed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonCancel;
  private javax.swing.JButton buttonOk;
  private javax.swing.JCheckBox checkAllUsers;
  private pl.mpak.sky.gui.swing.Action cmCancel;
  private pl.mpak.sky.gui.swing.Action cmOk;
  private pl.mpak.usedb.gui.swing.QueryComboBox comboDriverType;
  private pl.mpak.sky.gui.swing.comp.TextField editKey;
  private pl.mpak.sky.gui.swing.comp.TextField editName;
  private OrbadaJavaSyntaxTextArea editScript;
  private pl.mpak.sky.gui.swing.comp.TextField editTooltip;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private pl.mpak.usedb.core.Query queryDriverTypes;
  private pl.mpak.sky.gui.swing.comp.TextField textShortcut;
  // End of variables declaration//GEN-END:variables

}
