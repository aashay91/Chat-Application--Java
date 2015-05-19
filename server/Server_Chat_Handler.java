package chat.server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import chat.util.Client_Storage;
import chat.util.Server_Chat;

public class Server_Chat_Handler implements Runnable,Server_Chat {

	private Client_Storage store =new Client_Storage();
	
	private static Map<Socket,StringBuffer> bkcup=new HashMap<Socket,StringBuffer>();
	private volatile Socket sck=null;
	private volatile InputStreamReader ir;
	private volatile BufferedReader br;
	private volatile String  msg;
	private Scanner reader=null;
	private String name=null;
	private String th_name;
	private String threadchoice;
	private  Scanner sc=new Scanner(System.in);
	private BufferedReader stdin;
	private static Integer cnt=1;

	private Socket temp=null;
	private PrintStream ps;
	

	/**
	 * 
	 * @param s initialize socket and user name
	 * @param user
	 */
	public Server_Chat_Handler(Socket s,String user)
	{
		this.sck=s;
		this.name=user;
		StringBuffer use=new StringBuffer();
		bkcup.put(s,use);

	}

	public void run() 
	{
		Create_Menu();

	}

	/**
	 * 
	 * @param bound max option 
	 * @return returns the selected option 
	 */
	public int validatechoice(int bound)
	{
		int choice;
		while(true){
			while(!sc.hasNextInt())
			{
				System.out.println("please enter no");
				sc.next();
			}
			choice=sc.nextInt();
			if(choice<1 || choice >bound)
			{
				System.out.println("please enter valid option");
			}
			else
			{

				break;
			}	
		}
		return choice;
	}

	public void Receive_Message()
	{
		
		try {
			if(temp!=null)
				this.ir=new InputStreamReader(temp.getInputStream());
			else
			return;
		} catch (IOException e1) {
			System.err.println("Exception occured while reading client msg");
			System.exit(1);	
		}
		this.br=new BufferedReader(this.ir);
		try {
			this.msg=this.br.readLine();
		} catch (IOException e) {

			System.err.println("Exception occured while reading client msg");
			System.exit(1);
		}
		if(!msg.equals("BACKUP"))
		{
			System.out.println(Client_Storage.user_socket_list.get(temp)+">"+this.msg);
			String tem=Client_Storage.user_socket_list.get(temp)+">"+this.msg;
			bkcup.put(temp,bkcup.get(temp).append(tem+"\n"));
			
		}
		else
		{
			try {

				String[] tem2;
				ps=new PrintStream(temp.getOutputStream());
				ps.println(bkcup.get(temp).toString());

			} catch (IOException e) {

				System.err.println("Exception during sending backup");
			}
			finally{
				System.out.println("Backup sent");
			}
		}
	
		
	}
	
	/**
	 * sends the text to client
	 */
	public void Send_Message()
	{
		try {
			ps = new PrintStream(temp.getOutputStream());
			System.out.println("Enter the message u want to send");
			String a=stdin.readLine();

			String tem2="Server>"+a;
			bkcup.put(temp,bkcup.get(temp).append(tem2+"\n"));		
			ps.println(a);
		} catch (IOException e) {

			System.err.println("Exception occured while writting  client msg");
			System.exit(1);
		}

		
	}
	
	/**
	 * prints the menu and call recevie message and send message
	 */
	void Create_Menu()
	{
		int choice=0;
		int choose_client;
		stdin=new BufferedReader(new InputStreamReader(System.in));	

		while(true)
		{


			if(Thread.currentThread().getName().equals(Client_Storage.th_list.get(cnt)))
			{


				System.out.println("1.Set client Name");
				System.out.println("2.Accept text from client");
				System.out.println("3.Send to client");
				System.out.println("4.Quit");
				System.out.println(Thread.currentThread().getName());   
				System.out.println("Enter choice");

				choice=validatechoice(3);

				if(choice!=3)
				{
					temp=getchoice();

				}

				switch(choice)
				{
				case 1:
					try {
						if((ir=new InputStreamReader(temp.getInputStream()))!=null)
						{

							BufferedReader br=new BufferedReader(ir);
							String clnt=br.readLine();
							store.Set_Client_Name(Client_Storage.user_socket_list.get(temp),clnt);

						}
					} catch (IOException e2) {

						System.err.println("Exception during setting name of client");
						System.exit(0);
					}

					break;

				case 2:
	
					Receive_Message();
					
				
					break;


				case 3:
								
					Send_Message();				

					break;

				case 4:
					System.exit(0);
					break;
				}

				cnt++;
				if(Client_Storage.th_list.size()<cnt)
					cnt=1;
				

			}

		}
	}

	/**
	 * prinnts the current online users
	 * @return returns the user name
	 */
	public String current_thread()
	{
		int index;
		System.out.println("------------------Online are:-------------");
		for(int i=0;i<Client_Storage.thread_list.size();i++)
		{
			System.out.println(i+1+Client_Storage.thread_list.get(i));
		}
		System.out.println("----------------------------------");

		System.out.println("Enter client no");
		index=validatechoice(Client_Storage.thread_list.size());
		return Client_Storage.thread_list.get(index-1);
	} 

	/**
	 * ask user to choose one of the client
	 * @return returns the client socket
	 */

	public Socket getchoice()
	{
		String threadchoice=current_thread();
		return Client_Storage.thread_name_list.get(threadchoice);



	}






}


