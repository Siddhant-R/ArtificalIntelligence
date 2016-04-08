package SAnnealing;
import java.util.Random;
import java.util.Vector;

public class Node {

	private Vector<City> city_list;
	private String ID;
	private Vector<Double> distance;
	private double total_distance;
	
	public Vector<City> get_city_list()
	{
		
		return city_list;
	}
	
	public String get_id()
	{
		return ID;
	}
	
	public Vector<Double> get_distance()
	{
		return distance;
	}
	
	public double get_total_distance()
	{
		return total_distance;
	}
	
	public void disp_tour()
	{
		System.out.println();
	}
	
	
	@SuppressWarnings("unchecked")
	Node(Vector<City> city_list)
	{
		this.city_list = new Vector<City>();
		this.city_list = (Vector<City>) city_list.clone();
		generate_id();
		// System.out.println(this.city_list.size());
		calculate_distance_of_list();
		Calculate_Total_Distance();
	}
	

	
	@SuppressWarnings("unchecked")
	Node(Node copy)
	{
		
		this.city_list = (Vector<City>) copy.get_city_list().clone();
		this.ID = copy.get_id();
		this.distance = (Vector<Double>) copy.get_distance().clone();
		this.total_distance = copy.get_total_distance();
	}
	
	
	@SuppressWarnings("unchecked")
	public static Node generate_child(Node p)
	{
		
		Random rand = new Random();
		Vector<City> new_city_list = new Vector<City> ();
		new_city_list = (Vector<City>)p.get_city_list().clone();
		
		int rand1 = rand.nextInt(new_city_list.size()-1);
		int rand2= rand.nextInt(new_city_list.size()-1);
		while(rand1==rand2)
		{
			rand2= rand.nextInt(p.get_city_list().size()-1);
		}
		City x,y;
		x = new_city_list.elementAt(rand1);
		y = new_city_list.elementAt(rand2);
		new_city_list.set(rand1, y);
		new_city_list.set(rand2, x);
		
		Node child = new Node(new_city_list);
		return child;
	}
	
	//public Vector<Node> generate_all_children()
	{
		
		
	}
	
	private void generate_id()
	{
		ID="";
		for(City c : city_list)
		{
			ID += (c.name.charAt(0)+"") + (Double.toString(c.lat).charAt(1)+"") + (Double.toString(c.lon).charAt(1) + ""); 
		}
		
	}
	
	private void calculate_distance_of_list()
	{
		distance = new Vector<Double>();
		double lat1,lon1,lat2,lon2;
		for(int i=0;i<city_list.size();i++)
		{
		if(i < (city_list.size()-1))	
			{
			
			 lat1 = city_list.get(i).lat;
			 lon1 = city_list.get(i).lon;
			 lat2 = city_list.get(i+1).lat;
			 lon2 = city_list.get(i+1).lon;
			 distance.add(distance_between_two_cities(lat1,lon1,lat2,lon2));
			}
		
		else if(i==(city_list.size()-1) )
			{
			//System.out.println("Entered Else");
			 lat1 = city_list.get(i).lat;
			 lon1 = city_list.get(i).lon;
			 lat2 = city_list.get(1).lat;
			 lon2 = city_list.get(1).lon;
			 distance.add(distance_between_two_cities(lat1,lon1,lat2,lon2));
			 break;
			}
		
		
		}
		
	}
	
	
	
	private double distance_between_two_cities(double lat1, double lon1, double lat2, double lon2)
	{
		final double R = 3961;
		lat1=lat1*3.14159/180;
		lon1=lon1*3.14159/180;
		lat2=lat2*3.14159/180;
		lon2=lon2*3.14159/180;;
		
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		
		double a  = Math.pow(Math.sin(dlat/2),2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon/2),2);
		double c  = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a)); // great circle distance in radians
		double d = R * c;
		return d;
	}
	
	private void Calculate_Total_Distance()
	{
		for(Double d: distance)
		{
			total_distance += d; 
		}
	}
	
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Node)) {
	        return false;
	    }

	    Node that = (Node) other;

	    // Overridden equality check by comparing id's of object
	    return this.ID.equals(that.ID);

	}
	
}
