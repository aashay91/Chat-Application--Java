package chat.util; 
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Client_Storage {
	public static Map<String,Socket> thread_name_list =new HashMap<String,Socket>();
	public static Map<Socket,String> user_socket_list =new HashMap<Socket,String>();
	public static Map<Integer,String> th_list =new HashMap<Integer,String>();
	public static List<String>thread_list=new ArrayList<String>();
	/**
	 * 
	 * @param oldname old name of client
	 * @param newname  new name to be inserted for client
	 */

	public void Set_Client_Name(String oldname,String newname)
	{
		int i=0;
		Socket s1=Client_Storage.thread_name_list.remove(oldname);
		Client_Storage.thread_name_list.put(newname, s1);
		Client_Storage.user_socket_list.remove(s1);
		Client_Storage.user_socket_list.put(s1, newname);

		int size=Client_Storage.thread_list.size();
		
		for( i=0;i<size;i++){
			if(Client_Storage.thread_list.get(i).equals(oldname))
			{

				Client_Storage.thread_list.remove(i);
				Client_Storage.thread_list.add(i, newname);
				break;
			}

		}

	}

	

}
