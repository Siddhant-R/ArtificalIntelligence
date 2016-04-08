package SAnnealing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

public class Anneal {
	
	static Vector<City> city_list = new Vector<City>();
	public static void Populate_city_list(String file_name)
	{
		/*
		 * READS INPUT FILE AND CREATES A LIST OF TYPE CITY CONTAINING ALL NAMES
		 */
		
		try {
			for (String line : Files.readAllLines(Paths.get(file_name))) 
			{
			String[] part = line.split("\\s+");
			city_list.addElement(new City(
										  part[0],
										  Double.parseDouble(part[1]),
										  Double.parseDouble(part[2])
										  ));	
			}
			
		}
		catch (Exception e ){
			e.printStackTrace();
			System.err.println("Exited : Input File does not exists or the format is incorrect. \n Please include data file in the root folder");
			System.exit(2);
		}
		
	}

	
	public static void main(String[] args)
	{
		
		Search search = new Search();
		
		
		Populate_city_list("texas-cities.dat");
		
		System.out.println("---------SIMULATED ANNEALING------------");
		System.out.println("Use all city ? \n Auto selected ALL");
		Node node =new Node(city_list);
		
		
		search._Anneal(node);
		
		
		
		
		
	}
}
