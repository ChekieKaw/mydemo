/*
 * This	file is	part of	the	QuickServer	library	
 * Copyright (C) 2003-2005 QuickServer.org
 *
 * Use, modification, copying and distribution of this software is subject to
 * the terms and conditions of the GNU Lesser General Public License. 
 * You should have received a copy of the GNU LGP License along with this 
 * library; if not, you can download a copy from <http://www.quickserver.org/>.
 *
 * For questions, suggestions, bug-reports,	enhancement-requests etc.
 * visit http://www.quickserver.org
 *
 */

package	forwardserver;

import org.quickserver.net.server.*;

import org.quickserver.util.pool.PoolableObject;
import org.apache.commons.pool.BasePoolableObjectFactory; 
import org.apache.commons.pool.PoolableObjectFactory;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.util.logging.*;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *extends Thread 
 * @author	Akshathkumar Shetty
 */
public class ForwardData extends Thread implements ClientData, ClientIdentifiable, PoolableObject	{
		private static final Logger logger = 
			Logger.getLogger(ForwardData.class.getName());
	
		private static Set usernameList = new HashSet();
	
		public String username = null;
		private String room = null;
		private String info = null;
		private String optusername = null;
	
		//for auth
		private String lastAsked = null;
		private byte password[] = null;
		private ClientHandler handler;
		private ClientHandler opthandler;
        private boolean init = false;	
	    private boolean closed = false;
		private ForwardData optdata;

        java.util.Iterator iterator;
		private LinkedList<byte[]> myList = new LinkedList<byte[]>();
    	private int MAX = 10;
    	private final Lock lock = new ReentrantLock();
    	private final Condition full = lock.newCondition();
    	private final Condition empty = lock.newCondition();

		public ForwardData()
		{
			super("DataThread");
			setDaemon(true);
			start();
	    }

		public void setInit(ClientHandler handler)
		{
           this.handler = handler;
		   init = true;
		   closed = false;
		}
	
		public void setLastAsked(String lastAsked) {
			this.lastAsked = lastAsked;
		}
		public String getLastAsked() {
			return lastAsked;
		}
	
		public void setPassword(byte[] password) {
			this.password = password;
		}
		public byte[] getPassword() {
			return password;
		}
	
		public boolean registerUsername(String username) {
			return usernameList.add(username);
		}
		public void deregisterUsername(String username) {
			usernameList.remove(username);
		}
		public void setUsername(String username,String optname) {
			this.username = username;
			this.optusername = optname;
		}
		public String getUsername() {
			return username;
		}
	
		public void setRoom(String room) {
			this.room = room;
		}
		public String getRoom() {
			return room;
		}
	
		public String getClientId() {
			return username;
		}
	
		public String getClientKey() {
			if(room==null)
				return username;
			else
				return username+"@"+room;
		}
	
		public void setClientInfo(String info) {
			this.info = info;
		}
		public String getClientInfo() {
			return getClientKey()+" - "+info;
		}
	
		public String toString() {
			return getClientInfo();
		}
	
		//-- PoolableObject
		public void clean() {
			usernameList.remove(username);
			username = null;
			room = null;
			info = null;
			lastAsked = null;
			handler = null;
			opthandler = null;
			if(myList != null){myList.clear();myList = null;};
			
		}
	
		public boolean isPoolable() {
			return true;
		}

		
		private final static char[] mChars = "0123456789ABCDEF".toCharArray(); 
		
			public static String str2HexStr(String str){	
					
					StringBuilder sb = new StringBuilder();  
					byte[] bs = str.getBytes();    
					  
					for (int i = 0; i < bs.length; i++){	
						sb.append(mChars[(bs[i] & 0xFF) >> 4]);    
						sb.append(mChars[bs[i] & 0x0F]);  
						sb.append(' ');  
					}	 
					return sb.toString().trim();	
				}  


public void sendData(byte data[]) throws IOException {
		if(init==false)throw new IOException("Data is not yet init!");
		try	
		{
             lock.lock();//加锁
             handler.sendSystemMsg(username,false);
             if(myList.size() == MAX)myList.clear();//如果10个元素没有发送出去，就清空整个空间                        
             if(myList.add(data))empty.signal();//解除empty.await()等待
            
              	
		} 
		catch(Exception e) 
		{
			if(closed==false) 
			{
				logger.warning("Error sending data in senddata: "+e);
				throw new IOException(e.getMessage());
			}
			else 
			{
				logger.fine("Error after connection was closed : sending data : "+e);
			}
		}	
		finally{lock.unlock();}//解锁
	}

public void run()
{

  while(true)
  {
      try{
		    if(init==false){sleep(50);continue;}
			
	  	    lock.lock();
            if(myList.size() == 0){empty.await();}//没有收到数据就停止等待
            byte data[] = myList.removeLast();
			lock.unlock();

		    iterator = handler.getServer().findAllClient(); 

			while(iterator.hasNext())
			{
				opthandler = (ClientHandler)iterator.next();
				optdata = (ForwardData)opthandler.getClientData();
			    String opclientname = optdata.getUsername();
			    if(!username.equals(opclientname))
				{
				  handler.sendSystemMsg("run is sendBinary",true);
				  //String str = new String(command);
				  opthandler.sendClientBinary(data);
				  //handler.sendSystemMsg(clientname + ":",false);
				  //handler.sendSystemMsg(str2HexStr(str),true);
				}
			}	
			
      	}
	 catch(Exception e)
      	{
			init = false;
			if(closed==false)
			{
				logger.warning("Error in data thread in run : "+e);
			}
			else 
			{
				logger.fine("Error after connection was closed in data thread : "+e);
			}
	 	}
  }

}

	
		public PoolableObjectFactory getPoolableObjectFactory() {
			return	new BasePoolableObjectFactory() {
				public Object makeObject() { 
					return new ForwardData();//创建一个实例
				} 
				public void passivateObject(Object obj) {
					ForwardData ed = (ForwardData)obj;
					ed.clean();
				} 
				public void destroyObject(Object obj) {
					if(obj==null) return;
					passivateObject(obj);
					obj = null;
				}
				public boolean validateObject(Object obj) {
					if(obj==null) //确定一个实例是否安全
						return false;
					else
						return true;
				}
			};
		}
	}
	
	
	



	
			



