/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mpak.orbada.laf.substance.services;

import pl.mpak.orbada.Consts;
import pl.mpak.orbada.laf.substance.OrbadaLafSubstancePlugin;
import pl.mpak.orbada.laf.substance.starters.SubstanceMistAquaLookAndFeelStarter;
import pl.mpak.orbada.plugins.providers.ILookAndFeelStarter;
import pl.mpak.orbada.plugins.providers.LookAndFeelProvider;
import pl.mpak.util.StringManager;
import pl.mpak.util.StringManagerFactory;

/**
 *
 * @author akaluza
 */
public class SubstanceMistAquaLookAndFeelService extends LookAndFeelProvider {

  private StringManager stringManager = StringManagerFactory.getStringManager("laf-substance");

  public final static String lookAndFeelId = "substance-mist-aqua-look-and-feel-service";

  @Override
  public String getLookAndFeelId() {
    return lookAndFeelId;
  }

  @Override
  public Class<? extends ILookAndFeelStarter> getLookAndFeelClass() {
    return SubstanceMistAquaLookAndFeelStarter.class;
  }

  @Override
  public String getDescription() {
    return "Mist Aqua (Substance)";
  }

  @Override
  public String getGroupName() {
    return Consts.orbadaLookAndFeelGroupName;
  }

}
