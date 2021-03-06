/*
 * AlterTableNullWizardPanel.java
 *
 * Created on 27 listopad 2007, 19:39
 */

package pl.mpak.orbada.oracle.gui.wizards;

import pl.mpak.orbada.oracle.OrbadaOraclePlugin;
import pl.mpak.orbada.oracle.services.OracleDbInfoProvider;
import pl.mpak.orbada.universal.gui.wizards.SqlCodeWizardPanel;
import pl.mpak.sky.gui.mr.ModalResult;
import pl.mpak.sky.gui.swing.MessageBox;
import pl.mpak.usedb.core.Database;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class PurgeRecyclebinWizard extends SqlCodeWizardPanel {
  
  private final StringManager stringManager = StringManagerFactory.getStringManager("oracle");

  private Database database;
  private String schemaName;
  
  /** Creates new form FlashbackObjectWizardPanel
   * @param database
   */
  public PurgeRecyclebinWizard(Database database) {
    this.database = database;
    initComponents();
    init();
  }
  
  private void init() {
    schemaName = OracleDbInfoProvider.getCurrentSchema(database);
  }
  
  public void wizardShow() {
  }
  
  public String getDialogTitle() {
    return stringManager.getString("PurgeRecyclebinWizard-dialog-title");
  }
  
  public String getTabTitle() {
    return stringManager.getString("PurgeRecyclebinWizard-tab-title");
  }
  
  public String getSqlCode() {
    if (checkPurge.isSelected()) {
      return "PURGE RECYCLEBIN";
    }
    else {
      return "";
    }
  }
  
  public boolean execute() {
    try {
      database.executeCommand(getSqlCode());
      return true;
    } catch (Exception ex) {
      MessageBox.show(this, stringManager.getString("error"), ex.getMessage(), ModalResult.OK, MessageBox.ERROR);
      return false;
    }
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    checkPurge = new javax.swing.JCheckBox();

    checkPurge.setText(stringManager.getString("purge-recyclebin")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(checkPurge)
        .addContainerGap(252, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(checkPurge)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox checkPurge;
  // End of variables declaration//GEN-END:variables
  
}
