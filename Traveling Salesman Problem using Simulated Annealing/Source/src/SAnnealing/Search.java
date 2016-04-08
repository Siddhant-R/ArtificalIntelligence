/*##########################################################################################
 ###########################################################################################
 ###########################################################################################
 								S I D D H A N T    R A T H
 								U I N : 124007171
 								sid@tamu.edu
 ###########################################################################################
 ###########################################################################################
 ###########################################################################################

*/
package SAnnealing;

import java.io.IOException;

import java.util.Random;

public class Search {

	int iteration = -1;
	double temperature = 50;
	double deltaDistance = 0;
	double coolingRate = 0.999999;
	double absoluteTemperature = 1;
	Node best_node;
	GraphingData _graph = new GraphingData();
	//GraphingData _temperature_graph = new GraphingData();
	double prevtemp = temperature; 

	public void _Anneal(Node current) {
		int iteration = 0;
		double best = 999999999;
		best_node = new Node(current); 
		while (temperature > absoluteTemperature) 
		{
			
			Node child = new Node (Node.generate_child(current));
			
			
			deltaDistance = child.get_total_distance() - current.get_total_distance();
			Random random = new Random();
			
			if(deltaDistance < 0)
			{
				current =	new Node(child);
				_graph.add_point((child.get_total_distance()));
			}
			else if (Math.exp(-deltaDistance / temperature) > random.nextDouble()) 
			{
				current = 	new Node(child);
				_graph.add_point((child.get_total_distance()));
			}
			
			best_node = current.get_total_distance() < best_node.get_total_distance()  ? current: best_node;
			
			
			temperature *= coolingRate;
			
			
				
				//_temperature_graph.add_point(temperature);
			iteration++;
			System.out.println("ITER =" + iteration + " BEST LENGTH =" +best_node.get_total_distance() +" PRESENT LENGTH = "+current.get_total_distance()  + " NEW LENGTH = "+
					+ child.get_total_distance() + " TEMP = " + temperature
					+ "\n");

		}
		for(int q=0;q<best_node.get_city_list().size();q++)
		{
		System.out.println(best_node.get_city_list().elementAt(q).getCityName());
		}
		System.out.println("Total Miles = "+best_node.get_total_distance());
		_graph.draw_graph();
		//_temperature_graph.draw_graph();
	}

}
