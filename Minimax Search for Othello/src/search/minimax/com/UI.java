
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

/**
 * 
 */
package search.minimax.com;

import java.util.Scanner;
import java.util.Vector;

/**
 * @author Sid
 *
 */
public class UI {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		boolean loop = true;
		//int forfeit = 0;
		try 
		{	
			int N = Integer.parseInt(args[0]);
			char P = args[1].charAt(0);
			int Depth = Integer.parseInt(args[2]);
			Board board=new Board(N,N,P);
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String cmd=null;;
		
		while(loop)
		{
			cmd = input.nextLine();
			cmd =cmd.toUpperCase();
			
			
			if(cmd.contains("INIT") || cmd.contains("RESET"))
			{
				board = new Board(N,N,P);
				board.init();
				board.display_score();
				board.display_board();
			}
			
			if(cmd.contains("PUT"))
			{
				char[] split = cmd.toCharArray();
				boolean V = board.make_move(split[4], Character.getNumericValue(split[6]), Character.getNumericValue(split[8]));
				System.out.println("#"+V);
				board.display_score();
				board.display_board();
			}
			
			if(cmd.contains("MOVE"))
			{
				char[] split = cmd.toCharArray();
				System.out.print("# Making move for "+ split[5] + " ...\n");
				Vector<Move> legal = new Vector<Move>(board.legal_moves(split[5]));
				if(legal.size()!=0)
				{
				
					for(Move h : legal)
					{
						System.out.print("# Considering : ");
						h.display_cordinate();
					}
					
					Minimax search = new Minimax(board, split[5]);
					Move make_this = search.MINIMAX(legal, Depth);
					
					make_this.display_cordinate();
					board.make_move(split[5], make_this.get_cord_x(),make_this.get_cord_y());
					board.display_score();
					board.display_board();
				}
				else 
				{
					//forfeit++;
					System.out.println("\nforfeit");
					System.out.flush();
					
				}
			}
			
			if(cmd.contains("QUIT"))
			{
				System.out.println("#Bye!");
				System.out.flush();
				loop=false;
			}
			
			
			if(cmd.contains("DUMP"))
			{
				//Evaluator evaluate = new Evaluator(board,'W',N);
				//evaluate.dump();
			}
			
		}
		
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}

}
