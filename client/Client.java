package chat.client;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Client  {

	
	
	


	public static void main(String args[])throws Exception{

		if(args.length==2)
		{
			
		
	Socket s=null;
		
		try {
			String hostaddress=args[0].toString();
			int port=Integer.parseInt(args[1].toString());
			s = new Socket(hostaddress,port);
				} catch (IOException e) {

			System.out.println("Exception occured during opening the connection");
		}
	
		Client_Chat_Handler chat=new Client_Chat_Handler(s);
		chat.chat();
		
	
		}
		else
			System.out.println("Wrong input");

	}


}
