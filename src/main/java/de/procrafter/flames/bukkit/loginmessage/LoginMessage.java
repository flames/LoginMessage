
package de.procrafter.flames.bukkit.loginmessage;

import java.io.File;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

public class LoginMessage extends JavaPlugin {
	
  private final LoginMessagePlayerListener playerListener = new LoginMessagePlayerListener(this);
  
  public static String whomessage;
  public static String welcomemessage;
  public static String broadcastplrmessage;
  public static String broadcastopmessage;
  public static String broadcastallplrmessage;
  public static String broadcastallopmessage;
  
  /*public LoginMessage(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader)
  {
    super(pluginLoader, instance, desc, folder, plugin, cLoader);
  }*/
  
  public void onDisable() {
	  //log.info( pdfFile.getName() + " " + pdfFile.getVersion() + " unloaded" );
  }

  public void onEnable() {
	  PluginDescriptionFile pdfFile = this.getDescription();
	  System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
    pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
    //pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
    pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
    
    Configuration configuration = new Configuration(new File(this.getDataFolder(), "configuration.yml"));
	configuration.load();
	whomessage = configuration.getString("Who-Message", "¤eOnline list (%number): ¤f%list");
	welcomemessage = configuration.getString("Welcome-Message", "¤bWelcome, %name! && ¤eOnline list (%number): ¤f%list");
	broadcastplrmessage = configuration.getString("Broadcast-Player", "¤bSay hello to ¤2%name ¤beveryone!");
	broadcastopmessage = configuration.getString("Broadcast-OP", "¤bUhoh! ¤2%name ¤bis here, stop griefing!");
	broadcastallplrmessage = configuration.getString("Broadcast-All-Player", "¤2%name: ¤bhello everyone! I promise not to grief ¤e;)");
	broadcastallopmessage = configuration.getString("Broadcast-All-OP", "¤2%name: ¤bhello everyone! I just check the situation ¤e:P");
  }
  
  	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
      String[] trimmedArgs = args;
      String commandName = command.getName().toLowerCase();
      
      if (commandName.equals("lmsg")) {
      	return performLmsg(sender, trimmedArgs);
      } else if (commandName.equals("who")) {
      	return performWho(sender, trimmedArgs);
      }
      return false;
  	}

	private boolean performLmsg(CommandSender sender, String[] split) {
	      Player player = (Player)sender;
	      String welcomemsg = LoginMessage.welcomemessage;
	      
	      playerListener.sendWelcomeMsg(player, welcomemsg);
	      return true;
		}
	private boolean performWho(CommandSender sender, String[] split) {
	      Player player = (Player)sender;
	      String whomsg = LoginMessage.whomessage;
	      
	      playerListener.sendWhoMsg(player, whomsg);
	      return true;
		}
}