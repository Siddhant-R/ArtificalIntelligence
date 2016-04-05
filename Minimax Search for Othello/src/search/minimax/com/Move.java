package search.minimax.com;


public class Move
{
	private int cord_x;
	private int cord_y;
	Move()
	{
		
	}
	Move(int cord_x, int cord_y)
	{
		this.cord_x=cord_x;
		this.cord_y=cord_y;
	}
	
	public int get_cord_x()
	{
		return cord_x;
	}
	public int get_cord_y()
	{
		return cord_y;
	}
	public void display_cordinate()
	{
		System.out.println("("+cord_x+","+cord_y+")");
	}
	@Override
	public boolean equals(Object other) {
	    

	    Move that = (Move) other;

	    // Overridden equality check by comparing id's of object
	    if ((this.get_cord_x()==that.get_cord_x()) && (this.get_cord_y()==that.get_cord_y()))
	    	return true;
	    return false;

	}
	
}
