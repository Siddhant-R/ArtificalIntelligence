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
package search.minimax.com;

import java.util.Vector;

public class Evaluator {
	
	private Board board;
	private char Player;
	private char Opponent;
	private int N;
	private Vector<Vector<Move>> BigBox = new Vector<Vector<Move>>();
	
	private int NumberOfBoxes;
	
	Evaluator(Board board, char P, int N)
	{
		this.board = new Board(board);
		this.Player=(P==Piece_Color.White.get_Color()?Piece_Color.White.get_Color():Piece_Color.Black.get_Color());
		this.Opponent=(P==Piece_Color.White.get_Color()?Piece_Color.Black.get_Color():Piece_Color.White.get_Color());
		this.N = N;
		create_BigBox_Skeleton();
		create_Elements_of_boxes();
		
	}
	
	private void create_BigBox_Skeleton()
	{
		/*
		 * Minimum size of a box is 4
		 * BigBox is a collection of boxes
		 * each small box has two parts
		 * 		i) corners
		 * 		ii) edges
		 * Size of box decreases as we go down in the Big Box index
		 */
		
		NumberOfBoxes = (N)/4;
		for (int i=0;i<NumberOfBoxes*2;i++)
		{
			BigBox.addElement(new Vector<Move>());
		}
		
	}
	
	private void create_Elements_of_boxes()
	{
		/*
		 * Start from inner box
		 */
		int reduction_factor = 0;
		//generate corners;
		for(int i=0; i<NumberOfBoxes; i++)
		{
			BigBox.elementAt(reduction_factor).addElement(new Move(reduction_factor,reduction_factor));
			BigBox.elementAt(reduction_factor).addElement(new Move(reduction_factor,N-reduction_factor-1));
			BigBox.elementAt(reduction_factor).addElement(new Move(N-reduction_factor-1,reduction_factor));
			BigBox.elementAt(reduction_factor).addElement(new Move(N-reduction_factor-1,N-reduction_factor-1));
			
			//System.out.println("corners"+NumberOfBoxes);
			dp(reduction_factor,reduction_factor);
			dp(reduction_factor,N-reduction_factor-1);
			dp(N-reduction_factor-1,reduction_factor);
			dp(N-reduction_factor-1,N-reduction_factor-1);
			reduction_factor+=2;
			
			
		}
		/*
		 * generate sides
		 */
		reduction_factor=0;
		//top
		//System.out.println("Top Edges ");
		for(int i=0;i<NumberOfBoxes;i++)
		{	
			//System.out.println("Top Edges "+i);
			for(int j=reduction_factor+1;j<N-reduction_factor-1;j++)
			{
			BigBox.elementAt(1+reduction_factor).addElement(new Move(reduction_factor,j));
			dp(reduction_factor,j);
			}
			reduction_factor+=2;
		}
		reduction_factor=0;
		//bottom
		//System.out.println("Bottom Edges ");
		for(int i=0;i<NumberOfBoxes;i++)
		{	
			//System.out.println("Bottom Edges "+i);
			for(int j=reduction_factor+1;j<N-reduction_factor-1;j++)
			{
			BigBox.elementAt(1+reduction_factor).addElement(new Move(N-reduction_factor-1,j));
			dp(N-reduction_factor-1,j);
			}
			reduction_factor+=2;
		}
		//Right edges
		reduction_factor=0;
		//System.out.println("Right Edges ");
		for(int i=0;i<NumberOfBoxes;i++)
		{	
			//System.out.println("Right Edges "+i);
			for(int j=reduction_factor+1;j<N-reduction_factor-1;j++)
			{
			BigBox.elementAt(1+reduction_factor).addElement(new Move(j,N-reduction_factor-1));
			dp(j,N-reduction_factor-1);
			}
			reduction_factor+=2;
		}
		//Left Edges
		reduction_factor=0;
		//System.out.println("Left Edges ");
		for(int i=0;i<NumberOfBoxes;i++)
		{	
			//System.out.println("Left Edges "+i);
			for(int j=reduction_factor+1;j<N-reduction_factor-1;j++)
			{
			BigBox.elementAt(1+reduction_factor).addElement(new Move(j,reduction_factor));
			dp(j,reduction_factor);
			}
			reduction_factor+=2;
		}
		
		
		
	}
	
	
	public int scorer()
	{
		int score =0;
		for(int i=0; i<N; i++)
		{			
			for(int j=0; j<N; j++)
			{
				if(board.get_board(i, j)==Player)
				{
					if(BigBox.elementAt(0).contains(new Move(i,j)))
					{
					score+=55;
					}
					else if(BigBox.elementAt(1).contains(new Move(i,j)))
					{
					score+=30;
					}
					if(BigBox.size()>2)
					{
						if(BigBox.elementAt(2).contains(new Move(i,j)))
						{
							score+=7;
						}
						else if(BigBox.elementAt(3).contains(new Move(i,j)))
						{
							score+=5;
						}
					}
				}
				if(board.get_board(i, j)==Opponent)
				{
					if(BigBox.elementAt(0).contains(new Move(i,j)))
					{
					score-=30;
					}
					else if(BigBox.elementAt(1).contains(new Move(i,j)))
					{
					score-=15;
					}
					if(BigBox.size()>2)
					{
						if(BigBox.elementAt(2).contains(new Move(i,j)))
						{
							score-=3;
						}
						else if(BigBox.elementAt(3).contains(new Move(i,j)))
						{
							score-=1;
						}
					}
					
				}
				
				
				
			}
		}
		if(Player==Piece_Color.White.get_Color())
		{
			score+= (board.get_score_WHITE() - board.get_score_BLACK());
		}
		else if(Player==Piece_Color.Black.get_Color())
		{
			score+= (board.get_score_BLACK() - board.get_score_WHITE());
		}
		
		
		return score;		
	}
	
	
	
	public void dump()
	{
		create_BigBox_Skeleton();
		create_Elements_of_boxes();
		
		for(Vector<Move> v : BigBox)
		{
			System.out.println("--"+v.size());
			
			for(Move m : v)
			{
				m.display_cordinate();
			}
			
			
		}
		
	}
	
	private void dp(int a, int b)
	{
		//System.out.println("["+a+","+b+"]");
	}

}
