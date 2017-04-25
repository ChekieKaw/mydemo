// EchoCommandHandler.java
 package echoserver;

 import java.net.*;
 import java.io.*;
 import org.quickserver.net.server.ClientCommandHandler;
 import org.quickserver.net.server.ClientHandler;
 import java.util.logging.*;
 public class EchoCommandHandler implements ClientCommandHandler {

 public void gotConnected(ClientHandler handler)
 throws SocketTimeoutException, IOException {
  handler.sendSystemMsg("New Client : "+
 handler.getSocket().getInetAddress().getHostAddress(),
 Level.INFO);
 handler.sendClientMsg("+++++++++++++++++++++++++++++++");
 handler.sendClientMsg("| Welcome to EchoServer v 1.0 |");
 handler.sendClientMsg("| Note: Password = Username |");
 handler.sendClientMsg("| Send 'Quit' to exit |");
 handler.sendClientMsg("+++++++++++++++++++++++++++++++");
 }
 public void lostConnection(ClientHandler handler)
 throws IOException {
 handler.sendSystemMsg("Connection lost : " +
 handler.getSocket().getInetAddress());
 }
 public void closingConnection(ClientHandler handler)
 throws IOException {
 handler.sendSystemMsg("Closing connection : " +
 handler.getSocket().getInetAddress());
 }

 public void handleCommand(ClientHandler handler, String command)
 throws SocketTimeoutException, IOException {
 if(command.equals("Quit")) 
 {
    handler.sendClientMsg("Bye ;-)");
    handler.closeConnection();
	return;
 } 

  if(command.equals("What's interest?")) 
 {
    handler.sendClientMsg("Interest is : "+
    (String)handler.getServer().getStoreObjects()[0]+
    "%");
 }
 else if(command.equalsIgnoreCase("hello")) 
 {
 	EchoServerData data = (EchoServerData) handler.getClientData();
 	data.setHelloCount(data.getHelloCount()+1);
    if(data.getHelloCount()==1) 
	{
       handler.sendClientMsg("Hello "+data.getUsername());
    } else {
       handler.sendClientMsg("You told Hello "+data.getHelloCount()+
      " times. ");
    }	
 } 
 else
 {
       handler.sendClientMsg("Echo : "+command);
 }
 }
 }