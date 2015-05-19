package chat.util;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class Logger {
/**
 * 
 * @param data takes overall chat and store it into local file
 */
	public static void dump(List data,String name)
	{
		FileWriter file=null;
		try {
			String filename="Backup"+name;
			file=new FileWriter(filename);
		} catch (IOException e) {

			System.out.println("Exception while creating the file");
			System.exit(0);
		}
		if(file!=null)
		{
			BufferedWriter bw=new BufferedWriter(file);
			try {
				for(int i=0;i<data.size();i++)
				bw.write(data.get(i).toString());
					
			} catch (IOException e) {

				System.err.println("Exception occured during writting the file");
				System.exit(0);
			}
			finally{
				try {
					bw.close();
				} catch (IOException e) {
				
					System.err.println("Exception occured while closing the file");	
				}
			}
		}



	}

}
