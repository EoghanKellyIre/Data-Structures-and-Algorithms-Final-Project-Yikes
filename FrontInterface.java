import java.io.IOException;
import java.util.Scanner;

public class FrontInterface {

	public static void main(String[] args) throws IOException {
		System.out.println("Which part do you wish to test?");
		System.out.println("Type number (1/2/3)");
		System.out.println("Type 4 if you dont want to test anything");
		Scanner input = new Scanner(System.in);
		String userInput = input.next();
		
		if (userInput.equals("1"))
		{
		    
		}
		
		if (userInput.equals("2"))
		{
			System.out.println("Part 2");
			System.out.println("Please give part of name of stop. EG DUN/Water");
			userInput = input.next();
		    System.out.println("Here is the list of stops");
		    System.out.println("stop_id,stop_code,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url,location_type,parent_station");
			TSTForStops tst = new TSTForStops();
			tst = SearchingForStop.createTST("stops.txt" , userInput);
		}
		
		if (userInput.equals("3"))
		{
			System.out.println("Part 3");
			System.out.println("Please give time in hh:mm:ss, Eg:5:25:50");
			userInput = input.next();
			String[] userInput_split = userInput.split(":");
			StringBuffer sb = new StringBuffer();
		    for(int i = 0; i < userInput_split.length; i++)
		    {
		    	sb.append(userInput_split[i]);
		    }
		    System.out.println("Here is the list of stops");
		    System.out.println("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled");
		    System.out.println(SearchingGivenTime.providetimes("stop_times.txt", sb.toString()));
		}
		input.close();
	}

}
