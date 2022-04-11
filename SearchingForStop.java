import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.ArrayList;

public class SearchingForStop
{
	static TSTForStops createTST(String filename , String input) throws IOException
    {
    	
    	TSTForStops TST = new TSTForStops();
		int lineNo =2;
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
	    	int output = TST.get(input);
	        if(output >= 0)
	        {
	        	System.out.print("Here is list of stops that were found");
	        	System.out.println("stop_id,stop_code,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url,location_type,parent_station");
	            ArrayList<String> results = new ArrayList<>();
	            for (int i = 0; i < TSTForStops.matches.size(); i++)
	            {
	                String outputFromLine;
	                int lineNumber = TSTForStops.matches.get(i);
	                try (Stream<String> lines = Files.lines(Paths.get(filename)))
	                {
	                	outputFromLine = lines.skip(lineNumber - 1).findFirst().get();
	                    results.add(outputFromLine);
	                }
	            }
	            results.forEach(System.out::println);
	            TSTForStops.matches.clear();
	        }
	        else
	        {
	        	System.out.print("Not found");
	        }
		}
		catch (FileNotFoundException | NullPointerException e)
		{
			System.out.println("Error");
		}
		return TST;
    }
    
	public static void main(String[] args) throws IOException 
	{
		@SuppressWarnings("unused")
		TSTForStops tst = new TSTForStops();
		tst = createTST("stops.txt" , "DUN");
	}
}
