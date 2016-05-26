package pl.mpak.orbada.oracle.gui.wizards.parameters;

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
public class PlSqlCodeTypeWizard extends SqlCodeWizardPanel {
  
  private final StringManager stringManager = StringManagerFactory.getStringManager(OrbadaOraclePlugin.class);

  private Database database;
  
  public PlSqlCodeTypeWizard(Database database) {
    this.database = database;
    initComponents();
    init();
  }
  
  private void init() {
    checkSystem.setEnabled(OracleDbInfoProvider.instance.getDatabaseInfo(database).getObjectInfo("/SESSION PRIVILEGES/ALTER SYSTEM") != null);
  }
  
  public void wizardShow() {
  }
  
  public String getDialogTitle() {
    return stringManager.getString("PlSqlCodeTypeWizard-dialog-title");
  }
  
  public String getTabTitle() {
    return stringManager.getString("PlSqlCodeTypeWizard-tab-title");
  }
  
  public String getSqlCode() {
    return
      "ALTER " +(checkSystem.isSelected() ? "SYSTEM" : "SESSION") +" SET PLSQL_CODE_TYPE = " +
      "'" +comboState.getSelectedItem().toString() +"'";
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

    jLabel3 = new javax.swing.JLabel();
    comboState = new javax.swing.JComboBox();
    checkSystem = new javax.swing.JCheckBox();

    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel3.setText(stringManager.getString("plsql-code-type-dd")); // NOI18N

    comboState.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "INTERPRETED", "NATIVE" }));

    checkSystem.setText(stringManager.getString("system")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(checkSystem)
          .addComponent(comboState, 0, 196, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(comboState, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(checkSystem)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox checkSystem;
  private javax.swing.JComboBox comboState;
  private javax.swing.JLabel jLabel3;
  // End of variables declaration//GEN-END:variables
  
}