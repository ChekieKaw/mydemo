

package forwardserver;

import org.quickserver.net.server.*;
import java.io.*;
import java.util.*;
import org.quickserver.net.*;

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
		//只是按照顺序，没有按照对于关系
		 if(clientcount % 2 == 1)
		 {
		    handler.sendSystemMsg("count" + Long.toString(clientcount),true);
		    String name = ReadClientDataXml.readClientCount((int)clientcount,1);
		 	handler.sendSystemMsg(name,true);
		 	data.setUsername(name,"APM1_2");//简化另一端的名字设置
		 	data.registerUsername(name);
		 	data.setInit(handler);
		 	return AuthStatus.SUCCESS;
		 }
		 else
		 {
		    handler.sendSystemMsg("count" + Long.toString(clientcount),true);
            String name = ReadClientDataXml.readClientCount((int)clientcount - 1,2);
		 	handler.sendSystemMsg(name,true);
		 	data.setUsername(name,"APM1_1");//简化另一端设置
		 	data.registerUsername(name);
		 	data.setInit(handler);
		 	return AuthStatus.SUCCESS;
		 }
		}	
}

