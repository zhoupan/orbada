/*
 * SystemSettingsPanel.java
 *
 * Created on 6 grudzie� 2007, 21:13
 */

package pl.mpak.orbada.system.gui;

import pl.mpak.orbada.plugins.IApplication;
import pl.mpak.orbada.plugins.ISettings;
import pl.mpak.orbada.plugins.ISettingsComponent;
import pl.mpak.orbada.system.OrbadaSystemPlugin;
import pl.mpak.orbada.system.serives.SystemSettingsProvider;
import pl.mpak.orbada.system.serives.SystemStatusBarProvider;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author  akaluza
 */
public class SystemSettingsPanel extends javax.swing.JPanel implements ISettingsComponent {
  
  private final static StringManager stringManager = StringManagerFactory.getStringManager("system");
  private IApplication application;
  private ISettings settings;
  
  /** Creates new form SystemSettingsPanel */
  public SystemSettingsPanel(IApplication application) {
    initComponents();
    this.application = application;
    init();
  }
  
  private void init() {
    settings = application.getSettings(SystemSettingsProvider.settingsName);
    restoreSettings();
  }
  
  public void restoreSettings() {
    checkHideTime.setSelected(settings.getValue(SystemSettingsProvider.hideTime, false));
    checkHideRunTime.setSelected(settings.getValue(SystemSettingsProvider.hideRunTime, false));
  }

  public void applySettings() {
    settings.setValue(SystemSettingsProvider.hideTime, checkHideTime.isSelected());
    settings.setValue(SystemSettingsProvider.hideRunTime, checkHideRunTime.isSelected());
    settings.store();
    if (SystemStatusBarProvider.instance != null) {
      SystemStatusBarProvider.instance.applySettings();
    }
  }

  public void cancelSettings() {
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    checkHideTime = new javax.swing.JCheckBox();
    checkHideRunTime = new javax.swing.JCheckBox();

    checkHideTime.setText(stringManager.getString("hide-os-time")); // NOI18N

    checkHideRunTime.setText(stringManager.getString("hide-run-time")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(checkHideTime)
          .addComponent(checkHideRunTime))
        .addContainerGap(191, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(checkHideTime)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(checkHideRunTime)
        .addContainerGap(247, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox checkHideRunTime;
  private javax.swing.JCheckBox checkHideTime;
  // End of variables declaration//GEN-END:variables

}