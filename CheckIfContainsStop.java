import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CheckIfContainsStop
{
	//Instance Variables
    private static Map<Integer, Integer> indexesForStopIDs;

	public static Boolean findingStopId(String filename, Integer stopID)
	{
		indexesForStopIDs = new HashMap<Integer, Integer>();
        int index = 0;

        //Indexing the names and IDs
		try
		{
			File file = new File(filename);
			Scanner in;
			in = new Scanner(file);
			in.nextLine();
			in.useDelimiter(",|" + "/n");
			String trip_info = null;
			System.out.println("Checking if stop id is valid");
			while(in.hasNext())
			{
				trip_info = in.nextLine();
				trip_info = trip_info.replace("\n", "").replace("\r", "");
				String[] trip_info_split = trip_info.split(",");
				int stop_id = Integer.parseInt(trip_info_split[0]);
				indexesForStopIDs.put(index, stop_id);
				index++;
				if(index==1000)
				{
					System.out.println("1000 checked");
				}
				if(index==5000)
				{
					System.out.println("5000 checked");
				}
				if(index==8000)
				{
					System.out.println("8000 checked");
				}
			}
			in.close();
			return indexesForStopIDs.containsValue(stopID);
		}
		catch (FileNotFoundException | NullPointerException e)
		{
		}
		return null;
	}
}
