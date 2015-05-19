package chat.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import chat.util.Client_Chat;
import chat.util.*;




public class Client_Chat_Handler implements Client_Chat {
	private Socket s=null;
	private List<String> backup_list=new ArrayList<String>();
	/*----------------------------------------------------------*/


	private	Socket temp=null;
	private	String a="";
	private	PrintStream ps=null;
	private	Scanner reader;
	private	BufferedReader br=null;
	private	BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
	private	InputStreamReader ir=null;
	private	String msg=null;
	private	Scanner sc=new Scanner(System.in);
	private	String myname="client";
	private	int backup_flag=0;




/**
 * 
 * @param s input socket for client
 */
	/*--------------------------------------------------------*/


	public Client_Chat_Handler(Socket s) {

		this.s = s;
	}



/**
 * it receives the message and print it on screen
 */

	@Override
	public void Receive_Message() {

		try {
			ir=new InputStreamReader(s.getInputStream());
		} catch (IOException e) {
			System.err.println("Exception occured while reading input from the server");
			System.exit(0);
		}
		if(ir!=null)
			br=new BufferedReader(ir);
		

			if(backup_flag==1)
			{
				backup_flag=0;
				try {
					while(br.ready())
					{
						msg=br.readLine();
						backup_list.add(msg);

					}
					Logger.dump(backup_list,myname);

				} catch (IOException e) {

					System.err.println("Exception while reading from server to client");
					System.exit(0);
				}
				
			}	
			
			else
			{
				try {
					while(br.ready())
					{
						msg=br.readLine();
						System.out.println(msg);
					}
				} catch (IOException e) {

					System.err.println("Exception while reading from server to client");;
					System.exit(0);
				}
			}
			

		
		
		
		

	}

/**
 * It sends the message to client if backup then send whole chat
 */


	@Override
	public void Send_Message() {

		try {
			ps=new PrintStream(s.getOutputStream());
		} catch (IOException e) {

			System.out.println("Exception occured during opening the connection");
			System.exit(0);
		}
		System.out.println("Enter message");
		System.out.println(myname+">");
		try {
			a=stdin.readLine();
			if(a.equals("BACKUP"))
			{
				backup_flag=1;
			}
		} catch (IOException e1) {

			System.err.println("Exception occured while taking data from client");
			System.exit(0);
		}
		ps.println(a);		

	}

/**
 * 
 * @param bound =max option
 * @param sc= scanner object to take input frm screen
 * @return
 */

	public int validatechoice(int bound,Scanner sc)
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


	/**
	 * Prints the menu and call sendmessage and recevie message
	 */
	public void chat()
	{
		int choice;
		int choose_client;
		System.out.println("Chat is started:");
		while(true)
		{


			System.out.println("1.Get name ");
			System.out.println("2.Send to Server ");
			System.out.println("3.Accept text from Server");
			System.out.println("4.Quit");

			//Scanner sc=new Scanner(System.in);
			choice=validatechoice(4,sc);
			//choice=sc.nextInt();

			switch(choice)
			{
			case 1:
				System.out.println("Enter your name");
				try {
					ps=new PrintStream(this.s.getOutputStream());
				} catch (IOException e) {

					System.out.println("Exception occured during opening the connection");
					System.exit(0);
				}


				try {
					myname=stdin.readLine();
				} catch (IOException e1) {

					System.err.println("Exception during accepting name");
					System.exit(0);
				}
				ps.println(myname);		

				break;
			case 2:
				Send_Message();

				break;


			case 3:

				Receive_Message();
				break;
			case 4:
				try {
					s.close();
					System.exit(0);
				} catch (IOException e) {

					System.err.println("Exception while closing socket");
					//System.exit(0);
				}

				break;
			}

		}
	}







}
