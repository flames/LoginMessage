
package org.flames.bukkit.loginmessage;

import java.io.File;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

public class LoginMessage extends JavaPlugin
{
  PluginDescriptionFile pdfFile = this.getDescription();
  protected static final Logger log = Logger.getLogger("Minecraft");
  private final LoginMessagePlayerListener playerListener = new LoginMessagePlayerListener(this);
  public static String welcomemessage;
  public static String broadcastplrmessage;
  public static String broadcastopmessage;
  public static String broadcastallmessage;
  
  public LoginMessage(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader)
  {
    super(pluginLoader, instance, desc, folder, plugin, cLoader);
  }
  
  public void onDisable() {
	  log.info( pdfFile.getName() + " " + pdfFile.getVersion() + " unloaded" );
  }

  public void onEnable() {
    log.info( pdfFile.getName() + " " + pdfFile.getVersion() + " initialized." );
    
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
    pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
    pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
    
    Configuration configuration = new Configuration(new File(this.getDataFolder(), "configuration.yml"));
	configuration.load();
	welcomemessage = configuration.getString("Welcome-Message", "¤bWelcome, %name! && ¤eOnline list (%number): ¤4%list");
	broadcastplrmessage = configuration.getString("Broadcast-Player", "¤bSay hello to ¤2%name ¤beveryone!");
	broadcastopmessage = configuration.getString("Broadcast-OP", "¤bUhoh! ¤2%name ¤bis here, stop griefing!");
	broadcastallmessage = configuration.getString("Broadcast-All", "¤bhello everyone! I promise not to grief!");
  }
}