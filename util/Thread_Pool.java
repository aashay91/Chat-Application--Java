package chat.util;

import chat.server.Server_Chat_Handler;


public class Thread_Pool {
	private Thread[] Thrd_pool;
	private static Thread_Pool pool=null;
	private int thread_cnt;
	/**
	 * Initialize thread and thread count
	 */
private Thread_Pool()
{
	Thrd_pool=new Thread[5];
	thread_cnt=0;
}
/**
 * 
 * @return object of pool
 */
public static Thread_Pool getInstance()
{
	if(pool==null)
		synchronized (Thread_Pool.class) {
			if(pool==null)
				pool=new Thread_Pool();
		}
	return pool;
}

/**
 * 
 * @param take object for which thread is be created 
 * @return
 */
public Thread getThread(Server_Chat_Handler t)
{
	if(thread_cnt<5)
	{
		Thrd_pool[thread_cnt]=new Thread(t);
		Thread temp=Thrd_pool[thread_cnt];
		thread_cnt++;
		return temp;
		
	}
	return null;
	
}

}
