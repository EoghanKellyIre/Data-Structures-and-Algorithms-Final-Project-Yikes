import java.io.IOException;
import java.util.List;

public class FindingShortestPath
{
	public void FindingShortestPathMethod(String stopsFile, String stopTimesFile, String transfersFile, String fromID, String toID)
	{
		try
		{
			MakingTheNetwork network = new MakingTheNetwork(stopsFile, stopTimesFile, transfersFile);
			int vertexfrom =network.GetVertice(Integer.parseInt(fromID));
			int vertexto =network.GetVertice(Integer.parseInt(toID));
			double cost = network.getNetwork().dijkstra(vertexfrom,vertexto);
			System.out.println("The cost is " + cost);
			if(cost>-1)
			{
				if(cost<9999999)
				{
					System.out.println("The stops along the way are;");
					List<Integer> path=network.getNetwork().reconstructPath(vertexfrom, vertexto);
					String[] pathString = path.toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
					for(int i =0; i<pathString.length;i++)
					{
						System.out.println(network.GetName(Integer.parseInt(pathString[i])));
					}
				}
				else
				{
					System.out.println("No path found");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
