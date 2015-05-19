package chat.server;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

import chat.util.*;
public class Server {

	/**
	 * 
	 * @param args takes port no as input
 	 * @throws Exception
	 */
	
	public static void main(String args[])throws Exception{
		
		Server_Chat_Handler ch=null;	
		Thread t=null;
		int port;
		 port=Integer.parseInt(args[0].toString());
		ServerSocket ser=new ServerSocket(port);
		System.out.println("Waiting fr client");
		int i=0;
		while(true)
		{
		Socket s=ser.accept();
			i++;
			
			String clnt="Client"+i;
			
		
		
			
			  ch=new Server_Chat_Handler(s,clnt);
			 t=Thread_Pool.getInstance().getThread(ch);
			 String s1="Thread"+i;
			 Client_Storage.th_list.put(i, s1);
			 t.setName(s1);
			 if(t==null)
				 System.out.println("No more threads in the pool");
			 
			 Client_Storage.thread_name_list.put(clnt,s);
			System.out.println(clnt);
			
			Client_Storage.thread_list.add(clnt);
			Client_Storage.user_socket_list.put(s, clnt);
		//	System.out.println(user_socket_list.get(s));
			
			//threadinfo.put(t.getName(),s);
			//users.put(s, name);
			t.start();
			
		}
		
		/*
		if(msg!=null){
			PrintStream ps=new PrintStream(s.getOutputStream());
			ps.println("Msg received");
			
		}*/
	   //}
	}
	
	
}
