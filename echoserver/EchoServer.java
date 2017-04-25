package echoserver;

 import org.quickserver.net.*;
 import org.quickserver.net.server.*;

 import java.io.*;
 import java.util.logging.*;

 public class EchoServer {
 public static void main(String s[]) {

 String cmd = "echoserver.EchoCommandHandler";
 String auth = "echoserver.EchoServerQuickAuthenticator";
 String data = "echoserver.EchoServerPoolableData"; //Poolable

 QuickServer myServer = new QuickServer();

 //setup logger to log to file
 Logger logger = null;
 FileHandler xmlLog = null;
 FileHandler txtLog = null;
 File log = new File("./log/");
 if(!log.canRead())
 log.mkdir();
 try {
 logger = Logger.getLogger("org.quickserver.net"); //get QS logger
 logger.setLevel(Level.FINEST);
 xmlLog = new FileHandler("log/EchoServer.xml");
 logger.addHandler(xmlLog);

 logger = Logger.getLogger("echoserver"); //get App logger
 logger.setLevel(Level.FINEST);
 txtLog = new FileHandler("log/EchoServer.txt");
 txtLog.setFormatter(new SimpleFormatter());
 logger.addHandler(txtLog);
 myServer.setAppLogger(logger); //img : Sets logger to be used for app.
 } catch(IOException e){
 System.err.println("Could not create xmlLog FileHandler : "+e);
 }
 //set logging level to fine
 myServer.setConsoleLoggingLevel(Level.INFO);

 myServer.setClientCommandHandler(cmd);
 myServer.setAuthenticator(auth);
 myServer.setClientData(data);

 myServer.setPort(4123);
 myServer.setName("Echo Server v 1.0");

 //store data needed to be changed by QSAdminServer
 Object[] store = new Object[]{"12.00"};
 myServer.setStoreObjects(store);

 //config QSAdminServer
 myServer.setQSAdminServerPort(4124);
 myServer.getQSAdminServer().getServer().setName("EchoAdmin v 1.0");
 try {
 //add command plugin
 myServer.getQSAdminServer().setCommandPlugin(
 "echoserver.QSAdminCommandPlugin");
 myServer.startQSAdminServer();
 myServer.startServer();
 } catch(AppException e){
 System.out.println("Error in server : "+e);
 } catch(Exception e){
 System.out.println("Error : "+e);
 }
 }
 }