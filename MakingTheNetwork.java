import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MakingTheNetwork
{
	//Instance Variables
    private Map<Integer, Integer> indexesForStopIDsToVerticesInNetwork;
    private Map<Integer, String> indexForVerticesToStopNames;
    private int Vertices;
    private FindingTheShortestPathDijkstra network;
    
    //Constructor
	public MakingTheNetwork(String fileForStopInfo, String fileForStopTimes, String fileListingTransfers) throws IOException
    {
        indexesForStopIDsToVerticesInNetwork = new HashMap<Integer, Integer>();
        indexForVerticesToStopNames = new HashMap<Integer, String>();
        
        int vertex = 0;
        String stop_name = null;
        int fromVerticeIndex = 0;
        int toVerticeIndex = 0;
        int weight = 0;

        //Loading up the vertices and indexing the names and IDs
		try
		{
			File file = new File(fileForStopInfo);
			Scanner in;
			in = new Scanner(file);
			in.nextLine();
			in.useDelimiter(",|" + "/n");
			while(in.hasNext())
			{
				String trip_info = in.nextLine();
				trip_info = trip_info.replace("\n", "").replace("\r", "");
				String[] trip_info_split = trip_info.split(",");
				int stop_id = Integer.parseInt(trip_info_split[0]);
				stop_name = trip_info_split[2];
				indexesForStopIDsToVerticesInNetwork.put(stop_id, vertex);
				indexForVerticesToStopNames.put(vertex, stop_name);
				vertex++;
			}
			in.close();
		}
		catch (FileNotFoundException | NullPointerException e)
		{
		}
		
		Vertices=vertex;
		FindingTheShortestPathDijkstra network = new FindingTheShortestPathDijkstra(Vertices);
		this.setNetwork(network);

		// TRANSFERS
        
		File file2 = new File(fileListingTransfers);
		Scanner input2;
		input2 = new Scanner(file2);
		input2.nextLine();
		input2.useDelimiter(",|" + "/n");
		while(input2.hasNext())
		{
			String trip_info = input2.nextLine();
			trip_info = trip_info.replace("\n", "").replace("\r", "");
			String[] trip_info_split = trip_info.split(",");
			String from_stop_id = trip_info_split[0];
			String to_stop_id = trip_info_split[1];
			if(trip_info_split[2].equals("0"))
			{
				weight =2;
			}
			else if(trip_info_split.length==4)
			{
				weight = Integer. parseInt(trip_info_split[3])/100;
			}
			else
			{
				weight=1;
			}
			fromVerticeIndex = indexesForStopIDsToVerticesInNetwork.get(Integer. parseInt(from_stop_id));
			toVerticeIndex = indexesForStopIDsToVerticesInNetwork.get(Integer. parseInt(to_stop_id));
			this.getNetwork().addEdge(fromVerticeIndex, toVerticeIndex, weight);
		}
		input2.close();
        
		//stop times
        File file = new File(fileForStopTimes);
        Scanner input;
		input = new Scanner(file);
		String currentTrip = null;
		input.nextLine();
		input.useDelimiter(",|" + "/n");
		ArrayList<String> currentTripStops = new ArrayList<String>();
		while(input.hasNext())
		{
			String trip_info = input.nextLine();
			trip_info = trip_info.replace("\n", "").replace("\r", "");
			String[] trip_info_split = trip_info.split(",");
			String trip_id = trip_info_split[0];
			String stop_id = trip_info_split[3];
			Integer trip_id_int = Integer. parseInt(trip_id);
			Integer currentTrip_int =0;
			if(currentTrip!=null)
			{
				currentTrip_int = Integer. parseInt(currentTrip);
			}
			if(trip_id_int.equals(currentTrip_int) || currentTrip==null)
			{
				currentTripStops.add(stop_id);
				currentTrip=trip_id;
			}
			else
			{
				for (int i=0; i<currentTripStops.size()-2; i++)
				{
					int from = Integer. parseInt(currentTripStops.get(i));
					int to = Integer. parseInt(currentTripStops.get(i+1));
					weight = 1;
					fromVerticeIndex = indexesForStopIDsToVerticesInNetwork.get(from);
					toVerticeIndex = indexesForStopIDsToVerticesInNetwork.get(to);
					this.getNetwork().addEdge(fromVerticeIndex, toVerticeIndex, weight);
				}
				currentTripStops.clear();
				currentTripStops.add(stop_id);
				currentTrip=trip_id;
			}
		}
		input.close();
    }

	public FindingTheShortestPathDijkstra getNetwork()
	{
		return network;
	}

	public void setNetwork(FindingTheShortestPathDijkstra network)
	{
		this.network = network;
	}

	public int GetVertice(int stopID)
	{
		return indexesForStopIDsToVerticesInNetwork.get(stopID);
	}
	
	public String GetName(int vertice)
	{
		return indexForVerticesToStopNames.get(vertice);
	}
}