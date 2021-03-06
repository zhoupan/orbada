package pl.mpak.orbada.localhistory.gui;

import pl.mpak.orbada.localhistory.OrbadaLocalHistoryPlugin;
import pl.mpak.orbada.localhistory.services.LocalHistorySettingsService;
import pl.mpak.orbada.plugins.IApplication;
import pl.mpak.orbada.plugins.ISettings;
import pl.mpak.orbada.plugins.ISettingsComponent;
import pl.mpak.usedb.core.Database;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class SchemaSettingsPanel extends javax.swing.JPanel implements ISettingsComponent {
  
  private StringManager stringManager = StringManagerFactory.getStringManager("local-history");

  private Database database;
  private IApplication application;
  private ISettings settings;
  
  public SchemaSettingsPanel(IApplication application, Database database) {
    this.application = application;
    this.database = database;
    initComponents();
    init();
  }
  
  private void init() {
    settings = application.getSettings(database.getUserProperties().getProperty("schemaId"), LocalHistorySettingsService.settingsName);
    restoreSettings();
  }
  
  public void restoreSettings() {
    checkGlobalSettings.setSelected(settings.getValue(LocalHistorySettingsService.setGlobalSettings, true));
    checkTurnedOn.setSelected(settings.getValue(LocalHistorySettingsService.setTurnedOn, false));
  }

  public void applySettings() {
    settings.setValue(LocalHistorySettingsService.setGlobalSettings, checkGlobalSettings.isSelected());
    settings.setValue(LocalHistorySettingsService.setTurnedOn, checkTurnedOn.isSelected());
    settings.store();
  }

  public void cancelSettings() {
    restoreSettings();
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    checkGlobalSettings = new javax.swing.JCheckBox();
    checkTurnedOn = new javax.swing.JCheckBox();
    jLabel1 = new javax.swing.JLabel();

    checkGlobalSettings.setText(stringManager.getString("SchemaSettingsPanel-checkGlobalSettings-text")); // NOI18N
    checkGlobalSettings.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        checkGlobalSettingsStateChanged(evt);
      }
    });

    checkTurnedOn.setText(stringManager.getString("SchemaSettingsPanel-checkTurnedOn-text")); // NOI18N

    jLabel1.setText(stringManager.getString("SchemaSettingsPanel-settings-info")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addGap(21, 21, 21)
            .addComponent(checkTurnedOn))
          .addComponent(checkGlobalSettings))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(checkGlobalSettings)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(checkTurnedOn)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
        .addComponent(jLabel1)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

private void checkGlobalSettingsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkGlobalSettingsStateChanged
  checkTurnedOn.setEnabled(!checkGlobalSettings.isSelected());
}//GEN-LAST:event_checkGlobalSettingsStateChanged
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox checkGlobalSettings;
  private javax.swing.JCheckBox checkTurnedOn;
  private javax.swing.JLabel jLabel1;
  // End of variables declaration//GEN-END:variables
  
}
