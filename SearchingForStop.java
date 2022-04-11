import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SearchingForStop
{
    @SuppressWarnings({ "rawtypes" })
	static
	TST createTST(String filename , String input)
    {
    	
		TST TST = new TST();
		int lineNo =0;
		try
		{
			File file = new File(filename);
			Scanner in;
			in = new Scanner(file);
			in.nextLine();
			in.useDelimiter(",|" + "/n");
			while(in.hasNext())
			{
				in.next();
				in.next();
				String stop_name = in.next();
				String[] stop_name_split = stop_name.split(" ");
				if (stop_name_split[0].equals("WB") || stop_name_split[0].equals("NB") || stop_name_split[0].equals("SB") || stop_name_split[0].equals("EB"))
				{
					StringBuffer sb = new StringBuffer();
				      for(int i = 1; i < stop_name_split.length; i++) {
				         sb.append(stop_name_split[i]);
				         sb.append(" ");
				      }
				      sb.append(stop_name_split[0]);
				      stop_name = sb.toString();
				}
				in.nextLine();
				TST.put(stop_name, lineNo);
				lineNo++;
			}
			in.close();
	    	LinkedList<String> String = TST.keysWithPrefix(input);
	    	System.out.println(String);
		}
		catch (FileNotFoundException | NullPointerException e)
		{
			filename = null;
		}
		return TST;
    }
    
	public static void main(String[] args) throws IOException 
	{
		TST tst = new TST();
		tst = createTST("stops.txt" , "DUN");
	}
}
