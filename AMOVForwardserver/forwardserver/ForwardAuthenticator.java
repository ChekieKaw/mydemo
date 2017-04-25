

package forwardserver;

import org.quickserver.net.server.*;
import java.io.*;
import java.util.*;
import org.quickserver.net.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;     

/**
 *
 * @author  Akshathkumar Shetty
 */
public class ForwardAuthenticator extends QuickAuthenticationHandler {
	public AuthStatus askAuthentication(ClientHandler handler) 
			throws IOException, AppException {
	    ForwardData opdata;
		ForwardData data = (ForwardData) handler.getClientData();
		
		long clientcount = handler.getServer().getClientCount();
		java.util.Iterator iterator = handler.getServer().findAllClient();
		ClientHandler  Opclienthandler;
		//handler.sendSystemMsg("askAuthentication!",true);
		handler.sendSystemMsg(Long.toString(clientcount),true);

        Date date= new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);

		if(clientcount <= 3)
	   {
		data.setUsername(time,"APM1_2");
		data.registerUsername(time);
		data.setInit(handler);
		return AuthStatus.SUCCESS;
	   }
		else
	   {
	    handler.sendSystemMsg("Client number is more than 3!!",true);
	    return AuthStatus.FAILURE;
	   }
		


		}
		 
		}	
