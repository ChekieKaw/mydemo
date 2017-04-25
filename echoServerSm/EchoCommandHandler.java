// EchoCommandHandler.java
 package echoServerSm;

 import java.net.*;
 import java.io.*;
 import org.quickserver.net.server.ClientBinaryHandler;
 import org.quickserver.net.server.ClientHandler;

 public class EchoCommandHandler implements ClientBinaryHandler {

 public void gotConnected(ClientHandler handler)
 throws SocketTimeoutException, IOException {
 handler.sendClientMsg("+++++++++++++++++++++++++++++++");
 handler.sendClientMsg("| Welcome to EchoServer v 1.3 |");
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

 public void handleBinary(ClientHandler handler, byte command[])
			throws SocketTimeoutException, IOException {
		
		  handler.sendSystemMsg("data:come!",false);
		//data.sendData(command);
		}
 }