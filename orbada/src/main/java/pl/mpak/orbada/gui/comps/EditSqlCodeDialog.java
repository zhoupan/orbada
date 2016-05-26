/*
 * EditSqlCodeDialog.java
 *
 * Created on 4 grudzie� 2007, 19:22
 */

package pl.mpak.orbada.gui.comps;

import javax.swing.JComponent;
import pl.mpak.orbada.Consts;
import pl.mpak.orbada.core.Application;
import pl.mpak.orbada.plugins.ISettings;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.SwingUtil;
import pl.mpak.usedb.core.Database;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;
import pl.mpak.util.variant.Variant;

/**
 *
 * @author  akaluza
 */
public class EditSqlCodeDialog extends javax.swing.JDialog {
  
  private final static StringManager stringManager = StringManagerFactory.getStringManager("orbada");

  private int modalResult = ModalResult.NONE;
  private String sqlCode;
  private Database database;
  private Exception exception;
  private ISettings settings;
  
  /** Creates new form EditSqlCodeDialog */
  public EditSqlCodeDialog(String sqlCode, Database database, Exception exception) {
    super();
    this.sqlCode = sqlCode;
    this.database = database;
    this.exception = exception;
    initComponents();
    init();
  }
  
  public static String show(String sqlCode, Database database, Exception exception) {
    EditSqlCodeDialog dialog = new EditSqlCodeDialog(sqlCode, database, exception);
    dialog.setVisible(true);
    if (dialog.modalResult == ModalResult.OK) {
      return dialog.textSqlCode.getText();
    }
    return null;
  }
  
  public static String show(String sqlCode, Database database) {
    return show(sqlCode, database, null);
  }
  
  private void init() {
    settings = Application.get().getSettings("orbada-edit-sql-code");
    try {
      setBounds(0, 0, settings.getValue("dialog-width", new Variant(getWidth())).getInteger(), settings.getValue("dialog-height", new Variant(getHeight())).getInteger());
    } catch (Exception ex) {
    }
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(cmCancel.getShortCut(), "cmCancel");
    getRootPane().getActionMap().put("cmCancel", cmCancel);
    getRootPane().setDefaultButton(buttonOk);

    if (database != null) {
      textSqlCode.setDatabase(database);
    }
    textSqlCode.setText(sqlCode);
    if (exception != null) {
      textError.setText(exception.getMessage());
      textError.setCaretPosition(0);
    }
    else {
      textError.setVisible(false);
    }

    SwingUtil.centerWithinScreen(this);
  }
  
  @Override
  public void dispose() {
    settings.setValue("dialog-width", new Variant(getWidth()));
    settings.setValue("dialog-height", new Variant(getHeight()));
    settings.store();
    super.dispose();
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cmCancel = new pl.mpak.sky.gui.swing.Action();
    cmOk = new pl.mpak.sky.gui.swing.Action();
    textSqlCode = new OrbadaSyntaxTextArea();
    buttonOk = new javax.swing.JButton();
    buttonCancel = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    textError = new javax.swing.JTextArea();

    cmCancel.setActionCommandKey("cmCancel");
    cmCancel.setShortCut(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
    cmCancel.setText(stringManager.getString("cmCancel-text")); // NOI18N
    cmCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmCancelActionPerformed(evt);
      }
    });

    cmOk.setActionCommandKey("cmOk");
    cmOk.setText(stringManager.getString("cmOk-text")); // NOI18N
    cmOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmOkActionPerformed(evt);
      }
    });

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(stringManager.getString("EditSqlCodeDialog-title")); // NOI18N
    setModal(true);

    buttonOk.setAction(cmOk);
    buttonOk.setPreferredSize(new java.awt.Dimension(75, 23));

    buttonCancel.setAction(cmCancel);
    buttonCancel.setPreferredSize(new java.awt.Dimension(75, 23));

    textError.setColumns(20);
    textError.setEditable(false);
    textError.setFont(new java.awt.Font("Courier New", 0, 12));
    textError.setRows(5);
    jScrollPane1.setViewportView(textError);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(textSqlCode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(textSqlCode, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(buttonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void cmCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmCancelActionPerformed
    modalResult = ModalResult.CANCEL;      
    dispose();
  }//GEN-LAST:event_cmCancelActionPerformed

  private void cmOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmOkActionPerformed
    modalResult = ModalResult.OK;
    dispose();
  }//GEN-LAST:event_cmOkActionPerformed
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonCancel;
  private javax.swing.JButton buttonOk;
  private pl.mpak.sky.gui.swing.Action cmCancel;
  private pl.mpak.sky.gui.swing.Action cmOk;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextArea textError;
  private OrbadaSyntaxTextArea textSqlCode;
  // End of variables declaration//GEN-END:variables
  
}
