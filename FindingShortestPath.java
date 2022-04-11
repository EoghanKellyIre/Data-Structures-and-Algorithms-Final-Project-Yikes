import java.io.IOException;
import java.util.List;

public class FindingShortestPath
{
	public static void FindingShortestPathMethod(String stopsFile, String stopTimesFile, String transfersFile, String fromID, String toID)
	{
		try
		{
			MakingTheNetwork network = new MakingTheNetwork(stopsFile, stopTimesFile, transfersFile);
			int vertexfrom =network.GetVertice(Integer.parseInt(fromID));
			int vertexto =network.GetVertice(Integer.parseInt(toID));
			double cost = network.getNetwork().dijkstra(vertexfrom,vertexto);
			System.out.println(cost);
			List<Integer> path=network.getNetwork().reconstructPath(vertexfrom, vertexto);
			String[] pathString = path.toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
			for(int i =0; i<pathString.length;i++)
			{
				System.out.println(network.GetName(Integer.parseInt(pathString[i])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		FindingShortestPath idk = new FindingShortestPath();
		FindingShortestPathMethod("stops.txt", "stop_times.txt", "transfers.txt", "1132", "125");
	}
}
