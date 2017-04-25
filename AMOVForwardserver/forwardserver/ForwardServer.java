package forwardserver;

 import org.quickserver.net.*;
 import org.quickserver.net.server.*;

 import java.io.*;

 public class ForwardServer
 {
 public static void main(String s[])
 {
    QuickServer forwardServerapm = new QuickServer();
	
	//end of logger code
		String confFile = "conf"+
			File.separator+"comServerAPM.xml";
		Object config[] = new Object[] {confFile};
		if(forwardServerapm.initService(config)==true)
		{
			try	
			{
				forwardServerapm.startServer();
				forwardServerapm.startQSAdminServer();
			}
			catch(AppException e)
			{
				System.err.println("Error in server : "+e);
				e.printStackTrace();
			}
		}

 	} 
 	}