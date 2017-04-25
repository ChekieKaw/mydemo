 package echoserver;
 import org.quickserver.net.server.*;
 import java.io.*;

 public class EchoServerQuickAuthenticator extends QuickAuthenticator {

 public boolean askAuthorisation(ClientHandler clientHandler)
 throws IOException {
 String username = askStringInput(clientHandler, "User Name :");
  if (username != null && username.equalsIgnoreCase("QUIT")) 
 {
   sendString(clientHandler, "Logged out.");
   clientHandler.closeConnection();
   return false;
 }

 String password = askStringInput(clientHandler, "Password :");

 if(username==null || password ==null)
 return false;

 if(username.equals(password)) 
 {
 	sendString(clientHandler, "Auth OK");
	EchoServerData data = (EchoServerData)clientHandler.getClientData();
 data.setUsername(username);
 	return true;
 } 
 else
 {
 	sendString(clientHandler, "Auth Failed");
 	return false;
 }
 
 }



 
 }
