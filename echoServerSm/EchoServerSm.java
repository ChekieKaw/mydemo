package echoServerSm;

 import org.quickserver.net.*;
 import org.quickserver.net.server.*;

 import java.io.*;

 public class EchoServerSm {
 public static void main(String s[]) {
 QuickServer myServer =
 new QuickServer("echoserver.EchoCommandHandler");
 myServer.setPort(5000);
 myServer.setName("EchoServer v 1.0");
 try {
 myServer.startServer();
 } catch(AppException e){
 System.err.println("Error in server : "+e);
 }
 }
 }
