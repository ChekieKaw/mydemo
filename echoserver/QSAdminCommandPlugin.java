package echoserver;


 import java.io.*;
 import java.net.SocketTimeoutException;
 import org.quickserver.net.server.*;
 import org.quickserver.net.qsadmin.*;

 public class QSAdminCommandPlugin implements CommandPlugin {
 /**
11 * Echoserver commands
12 * ----------------------------------
13 * set interest value
14 * get interest
15 */
 public boolean handleCommand(ClientHandler handler, String command)
 throws SocketTimeoutException, IOException {

 QuickServer echoserver = (QuickServer)
 handler.getServer().getStoreObjects()[0];
 Object obj[] = echoserver.getStoreObjects();

 if(command.toLowerCase().startsWith("set interest ")) {
 String temp = "";
 temp = command.substring("set interest ".length());
 obj[0] = temp;
 echoserver.setStoreObjects(obj);
 handler.sendClientMsg("+OK interest changed");
 return true;
 } else if(command.toLowerCase().equals("get interest")) {
 handler.sendClientMsg("+OK " + (String)obj[0]);
 return true;
 }
 //ask QSAdminServer to process the command
 return false;
 }
 }

