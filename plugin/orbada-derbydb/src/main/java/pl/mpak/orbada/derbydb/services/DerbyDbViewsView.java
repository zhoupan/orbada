/*
 * DerbyDbViewsView.java
 *
 * Created on 2007-11-02, 20:09:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pl.mpak.orbada.derbydb.services;

import pl.mpak.orbada.derbydb.*;
import java.awt.Component;
import javax.swing.Icon;
import pl.mpak.orbada.derbydb.views.ViewsPanelView;
import pl.mpak.orbada.plugins.IViewAccesibilities;
import pl.mpak.orbada.plugins.providers.ViewProvider;
import pl.mpak.usedb.core.Database;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author akaluza
 */
public class DerbyDbViewsView extends ViewProvider {
  
  private final static StringManager stringManager = StringManagerFactory.getStringManager("derbydb");

  public Component createView(IViewAccesibilities accesibilities) {
    return new ViewsPanelView(accesibilities);
  }
  
  public String getPublicName() {
    return stringManager.getString("DerbyDbViewsView-public-name");
  }
  
  public String getViewId() {
    return "orbada-derbydb-views-view";
  }
  
  public Icon getIcon() {
    return null;
  }

  public boolean isForDatabase(Database database) {
    if (database == null) {
      return false;
    }
    return OrbadaDerbyDbPlugin.apacheDerbyDriverType.equals(database.getDriverType());
  }

  public String getDescription() {
    return String.format(stringManager.getString("DerbyDbViewsView-description"), new Object[] {OrbadaDerbyDbPlugin.apacheDerbyDriverType});
  }

  public String getGroupName() {
    return OrbadaDerbyDbPlugin.apacheDerbyDriverType;
  }
  
}
