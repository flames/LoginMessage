package org.flames.bukkit.loginmessage;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;

public class LoginMessagePlayerListener extends PlayerListener {
  private final LoginMessage plugin;
  List<Player> OnlineList = new ArrayList();
  int str;

  	public LoginMessagePlayerListener(LoginMessage instance) {
  		plugin = instance;
  	}

  	public void onPlayerQuit(PlayerEvent event) {
	    Player player = event.getPlayer();
	    OnlineList.remove(player.getName());
	}
	public void onPlayerLogin(PlayerEvent event) {
	    Player player = event.getPlayer();
	    OnlineList.add(player);
	}

  public void onPlayerCommand(PlayerChatEvent event) {
    String[] split = event.getMessage().split(" ");
    Player player = event.getPlayer();
    if (split[0].equals("/lmsg")) {
      String welcomemsg = LoginMessage.welcomemessage;
      sendWelcomeMsg(player, welcomemsg);
    }
  }

  public void onPlayerJoin(PlayerEvent event) {
    Player player = event.getPlayer();
    String welcomemsg = LoginMessage.welcomemessage;
    String broadcastplrmsg = LoginMessage.broadcastplrmessage;
    String broadcastopmsg = LoginMessage.broadcastopmessage;
    String broadcastallmsg = LoginMessage.broadcastallmessage;
    /* this message goes only to the player who joins */
    sendWelcomeMsg(player, welcomemsg);
    if (player.isOp() == true) {
    	/* if operator joins send to this message to players who already in game */
    	sendBroadcastOpMsg(player, broadcastopmsg);
    } else {
    	/* if normal player joins send to this message to players who already in game */
    	sendBroadcastPlrMsg(player, broadcastplrmsg);
    }
    /* this message goes to all players, those who already in game and the one who joins */
    sendBroadcastAllMsg(player, broadcastallmsg);
  }

  public void sendWelcomeMsg(Player player, String welcomemsg) {
    Player[] online = plugin.getServer().getOnlinePlayers();
    String list = "";
    int length = online.length - 1;
    int on = 0;
    for (Player current : online) {
      if (current == null) { on++;
      } else {
        list = list + (on >= length ? current.getName() : new StringBuilder().append(current.getName()).append(", ").toString());
        on++; }  } int serverlist = online.length;
    String serverliststring = Integer.toString(serverlist);
    
    welcomemsg = welcomemsg.replaceAll("%name", player.getName());
    welcomemsg = welcomemsg.replaceAll("%number", serverliststring);
    welcomemsg = welcomemsg.replaceAll("%list", list);
    welcomemsg = welcomemsg.replaceAll("(�([a-z0-9]))", "�$2");
    String[] welcome = welcomemsg.split("&");
    sendMultiMessage(player, welcome);
  }
  
  public void sendBroadcastPlrMsg(Player player, String broadcastmsg) {
    Player[] online = plugin.getServer().getOnlinePlayers();
    String list = "";
    int length = online.length - 1;
    int on = 0;
    for (Player current : online) {
      if (current == null) { on++;
      } else {
        list = list + (on >= length ? current.getName() : new StringBuilder().append(current.getName()).append(", ").toString());
        on++;
      }
    }
    int serverlist = online.length;
    String serverliststring = Integer.toString(serverlist);
    
    broadcastmsg = broadcastmsg.replaceAll("%name", player.getName());
    broadcastmsg = broadcastmsg.replaceAll("%number", serverliststring);
    broadcastmsg = broadcastmsg.replaceAll("%list", list);
    broadcastmsg = broadcastmsg.replaceAll("(�([a-z0-9]))", "�$2");
    String[] broadcast = broadcastmsg.split("&");
    sendMultiBroadcastMessage(player, broadcast);
  }

  public void sendBroadcastOpMsg(Player player, String broadcastopmsg) {
	    Player[] online = plugin.getServer().getOnlinePlayers();
	    String list = "";
	    int length = online.length - 1;
	    int on = 0;
	    for (Player current : online) {
	      if (current == null) { on++;
	      } else {
	        list = list + (on >= length ? current.getName() : new StringBuilder().append(current.getName()).append(", ").toString());
	        on++;
	      }
	    }
	    int serverlist = online.length;
	    String serverliststring = Integer.toString(serverlist);
	    
	    broadcastopmsg = broadcastopmsg.replaceAll("%name", player.getName());
	    broadcastopmsg = broadcastopmsg.replaceAll("%number", serverliststring);
	    broadcastopmsg = broadcastopmsg.replaceAll("%list", list);
	    broadcastopmsg = broadcastopmsg.replaceAll("(�([a-z0-9]))", "�$2");
	    String[] broadcast = broadcastopmsg.split("&");
	    sendMultiBroadcastMessage(player, broadcast);
  }

  public void sendBroadcastAllMsg(Player player, String broadcastallmsg) {
	    Player[] online = plugin.getServer().getOnlinePlayers();
	    String list = "";
	    int length = online.length - 1;
	    int on = 0;
	    for (Player current : online) {
	      if (current == null) { on++;
	      } else {
	        list = list + (on >= length ? current.getName() : new StringBuilder().append(current.getName()).append(", ").toString());
	        on++;
	      }
	    }
	    int serverlist = online.length;
	    String serverliststring = Integer.toString(serverlist);
	    
	    broadcastallmsg = broadcastallmsg.replaceAll("%name", player.getName());
	    broadcastallmsg = broadcastallmsg.replaceAll("%number", serverliststring);
	    broadcastallmsg = broadcastallmsg.replaceAll("%list", list);
	    broadcastallmsg = broadcastallmsg.replaceAll("(�([a-z0-9]))", "�$2");
	    String[] broadcast = broadcastallmsg.split("&");
	    sendMultiBroadcastAllMessage(player, broadcast);
  }

  /* send message to the user who joins the game */
  public void sendMultiMessage(Player player, String[] message) {
    for (String str : message) player.sendMessage(str); 
  }

  /* send message to users that are already in game */
  public void sendMultiBroadcastMessage(Player player, String[] message) {
      if (player != null) {
        OnlineList.remove(player.getName());
        for (Player p : OnlineList) {
          for (String str : message) {
            p.sendMessage(str);
          }
        }
      }
      OnlineList.add(player);
  } 
  
  /* send message to all users */
  public void sendMultiBroadcastAllMessage(Player player, String[] message) {
      Player[] arrayOfPlayer;
      str = (arrayOfPlayer = plugin.getServer().getOnlinePlayers()).length;
      for (int str1 = 0; str1 < str; str1++) {
    	  Player p = arrayOfPlayer[str1];
          for (String str : message) {
            p.sendMessage(str);
          }
      }
  }
 
}