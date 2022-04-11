import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchingGivenTime
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String providetimes(String filename, String time)
	{
		try
		{
			ArrayList<String> stop_timesInfo = new ArrayList<String>();
			BST dataStored = new BST();
			File file = new File(filename);
			Scanner in;
			in = new Scanner(file);
			in.nextLine();
			in.useDelimiter(",|" + "/n");
			while(in.hasNext())
			{
				String trip_info = in.nextLine();
				trip_info = trip_info.replace("\n", "").replace("\r", "");
				String[] trip_info_split = trip_info.split(",");
				String[] arrival_time_split = trip_info_split[1].split(":");
				StringBuffer sb = new StringBuffer();
			    for(int i = 0; i < arrival_time_split.length; i++)
			    {
			    	sb.append(arrival_time_split[i]);
			    }
			    Double arrival_time_changed = Double.parseDouble(sb.toString().replace(" ", ""));
				if (Double.parseDouble(time)==arrival_time_changed)
				{
					dataStored.put(Double.parseDouble(trip_info_split[0]), trip_info);
				}
				stop_timesInfo.clear();
			}
			in.close();
			return dataStored.printKeysInOrder();
		}
		catch (FileNotFoundException | NullPointerException e)
		{
			filename = null;
		}
		return null;
	}
}
