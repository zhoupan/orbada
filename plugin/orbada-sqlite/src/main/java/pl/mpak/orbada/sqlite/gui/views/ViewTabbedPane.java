package pl.mpak.orbada.sqlite.gui.views;

import java.awt.Component;
import pl.mpak.orbada.gui.OrbadaTabbedPane;
import pl.mpak.orbada.plugins.IViewAccesibilities;
import pl.mpak.orbada.gui.ContentPanel;

/**
 *
 * @author akaluza
 */
public class ViewTabbedPane extends OrbadaTabbedPane {
  
  public ViewTabbedPane(IViewAccesibilities accesibilities) {
    super("VIEW",
      new Component[] {
        new ViewColumnsPanel(accesibilities),
        new ViewTriggersPanel(accesibilities),
        new ContentPanel(accesibilities),
        new ViewSourcePanel(accesibilities)
    });
  }
  
}
