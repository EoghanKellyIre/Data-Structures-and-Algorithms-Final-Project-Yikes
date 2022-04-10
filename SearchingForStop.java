import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

public class SearchingForStop
{
	//public String filename;
	//@SuppressWarnings("rawtypes")
	//public ArrayList stopInfo;
	//@SuppressWarnings("rawtypes")
	
    @SuppressWarnings({ "rawtypes" })
	static TST createTST(String filename)
    {
		TST TST = new TST();
		try
		{
			ArrayList<String> stopInfo = new ArrayList<String>();
			File file = new File(filename);
			Scanner in;
			in = new Scanner(file);
			in.nextLine();
			in.useDelimiter(",|" + "/n");
			while(in.hasNext())
			{
				String stop_id = in.next();
				stop_id = stop_id.replace("\n", "").replace("\r", "");
				stopInfo.add(stop_id);
				String stop_code = in.next();
				stopInfo.add(stop_code);
				String stop_name = in.next();
				String[] stop_name_split = stop_name.split(" ");
				if (stop_name_split[0]=="WB" || stop_name_split[0]=="NB" || stop_name_split[0]=="SB" || stop_name_split[0]=="EB" )
				{
					StringBuffer sb = new StringBuffer();
				      for(int i = 1; i < stop_name_split.length; i++) {
				         sb.append(stop_name_split[i]);
				         sb.append(" ");
				      }
				      sb.append(stop_name_split[0]);
				      stop_name = sb.toString();
				}
				stopInfo.add(stop_name);
				String stop_lat = in.next();
				stopInfo.add(stop_lat);
				String stop_lon = in.next();
				stopInfo.add(stop_lon);
				String zone_id = in.next();
				stopInfo.add(zone_id);
				String stop_url = in.next();
				stopInfo.add(stop_url);
				String location_type = in.next();
				stopInfo.add(location_type);
				String parent_station = in.next();
				stopInfo.add(parent_station);
				TST.put(stop_name, stopInfo);
				stopInfo.clear();
			}
			in.close();
		}
		catch (FileNotFoundException | NullPointerException e)
		{
			filename = null;
		}
		return TST;
    }
    
    static void searching(String filename, String input)
    {
    	TST<ArrayList> TST = new TST<>();
    	TST = SearchingForStop.createTST(filename);
    	LinkedList<String> String = TST.keysWithPrefix(input);
    	System.out.println(String);
    }
   
}
