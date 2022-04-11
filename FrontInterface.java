import java.io.IOException;
import java.util.Scanner;

public class FrontInterface {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		boolean finished = false;
		Scanner input = new Scanner( System.in );
		Boolean correctlyDone = false;
		int inputFromUser =0;
		while (!(finished||(correctlyDone)))
		{
			System.out.println("Which part do you wish to test? (or enter 'quit'):  ");
			System.out.println("Type number (1/2/3)");
			if (input.hasNextInt())
			{
				inputFromUser = input.nextInt();
				if ((inputFromUser==1 ||inputFromUser==2 ||inputFromUser==3))
				{
					correctlyDone = true;
				}
				else
				{
					System.out.println("Type number (1/2/3)");
				}
			}
			else if (input.hasNext("exit")||input.hasNext("quit"))
			{
				finished = true;
				System.out.println("Exited the program");
			}
			else
			{
				System.out.println("Error: No integers were entered");
				input.next();
			}
		}
		if (correctlyDone)
		{
			if (inputFromUser==1)
			{
				FindingShortestPath idk = new FindingShortestPath();
				System.out.println("Part 1");
				finished=false;
				correctlyDone=false;
				String stop1 = null;
				String stop2 = null;
				while (!(finished||(correctlyDone)))
				{
					System.out.println("Please give ID of stop you are starting from. EG 1888");
					System.out.println("or enter 'quit'");
					stop1 = input.next();
					try {
						if(stop1.equals("exit")||stop1.equals("quit"))
						{
							finished=true;
						}
						else if (Integer.parseInt(stop1.replace(" ", ""))>0 && Integer.parseInt(stop1.replace(" ", ""))<15000 && !finished)
						{
							if(CheckIfContainsStop.findingStopId("stops.txt", Integer.parseInt(stop1))) //stops is contained
							{
								correctlyDone=true;
								System.out.println("Stop found");
							}
							else
							{
								System.out.println("Stop not found");
							}
						}
						else
						{
							System.out.println("Not valid input");
						}
					}
					catch(Exception e)
					{
						System.out.println("Not valid input");
					}
					if(correctlyDone==true && !finished)
					{
						correctlyDone=false;
						System.out.println("Please give ID of stop you are going to. EG 1888");
						stop2 = input.next();
						if (Integer.parseInt(stop2.replace(" ", ""))>0 && Integer.parseInt(stop2.replace(" ", ""))<15000)
						{
							if(CheckIfContainsStop.findingStopId("stops.txt", Integer.parseInt(stop1)))
							{
								correctlyDone=true;
								System.out.println("Stop found");
							}
							else
							{
								System.out.println("Stop not found");
							}
						}
						else
						{
							System.out.println("Not valid input");
						}
					}
					if(correctlyDone==true && !finished)
					{
						System.out.println("");
						idk.FindingShortestPathMethod("stops.txt", "stop_times.txt", "transfers.txt", stop1, stop2);
					}
				}
			}
			
			if (inputFromUser==2)
			{
				System.out.println("Part 2");
				System.out.println("Please give part of name of stop. EG DUN/Water");
				input.nextLine();
				String userInput = input.nextLine();
			    System.out.println("Here is the list of stops");
			    System.out.println("stop_id,stop_code,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url,location_type,parent_station");
				@SuppressWarnings("unused")
				TSTForStops tst = new TSTForStops();
				tst = SearchingForStop.createTST("stops.txt" , userInput);
			}
			
			if (inputFromUser==3)
			{
				System.out.println("Part 3");
				try {
				System.out.println("Please give time in hh:mm:ss, Eg:5:25:50");
				String userInput = input.next();
				String[] userInput_split = userInput.split(":");
				StringBuffer sb = new StringBuffer();
			    for(int i = 0; i < userInput_split.length; i++)
			    {
			    	sb.append(userInput_split[i]);
			    }
			    if (Integer.parseInt(userInput_split[0])>23 | Integer.parseInt(userInput_split[1])>59 | Integer.parseInt(userInput_split[2])>59)
			    {
			    	throw new ArithmeticException("Invalid Time");
			    }
			    System.out.println("Here is the list of stops");
			    System.out.println("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled");
			    System.out.println(SearchingGivenTime.providetimes("stop_times.txt", sb.toString()));
				}
				catch(Exception e)
				{
					System.out.println("Invalid input, please try another time.");
				}
			}
		}
		input.close();
		System.out.println("");
		System.out.println("Application Closing");
	}

}