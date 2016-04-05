package search.minimax.com;

import java.util.Collections;
import java.util.Vector;

public class Minimax {
	
	private Board B;
	private char Player;
	private char Opponent;
	Minimax(Board B, char P)
	{
		this.B = new Board(B);
		this.Player=(P==Piece_Color.White.get_Color()?Piece_Color.White.get_Color():Piece_Color.Black.get_Color());
		this.Opponent=(P==Piece_Color.White.get_Color()?Piece_Color.Black.get_Color():Piece_Color.White.get_Color());
	}
	
	
	
	
	public Move MINIMAX(Vector<Move> legal_moves, int depth)
	{
		Vector<Integer> score_list = new Vector<Integer>();
		for(Move m : legal_moves)
		{
			//int depth=5;
			score_list.add(MINVAL(m,B,depth));			
		}
		for(Move k: legal_moves)
		{
			System.out.println(("# MM for ("+k.get_cord_x()+","+k.get_cord_y()+") : "+score_list.get(legal_moves.indexOf(k))));
		}
		return legal_moves.get(score_list.indexOf(Collections.max(score_list)));
		
	}
	
	public int MINVAL(Move m, Board BL, int depth)
	{
		depth--;
		Board tmp_Board = new Board(BL);
		//make move
		tmp_Board.make_move(Player, m.get_cord_x(), m.get_cord_y());
		Vector<Move> Next_Legal = new Vector<Move>(tmp_Board.legal_moves(Opponent));
		
		if((depth > 0) && Next_Legal.size()!=0)
		{
			Vector<Integer> score_list = new Vector<Integer>();
			for(Move m_o : Next_Legal)
			{
				score_list.add(MAXVAL(m_o, tmp_Board, depth));			
			}
			
			return Collections.min(score_list);
		}
		else
		{
			return tmp_Board.board_evaluation(Player);
		}		
		
	}
	
	public int MAXVAL(Move m, Board BL, int depth)
	{
		depth--;
		Board tmp_Board = new Board(BL);
		//make move
		tmp_Board.make_move(Opponent, m.get_cord_x(), m.get_cord_y());
		Vector<Move> Next_Legal = new Vector<Move>(tmp_Board.legal_moves(Player));

		
		if((depth > 0) && Next_Legal.size()!=0)
		{	
			Vector<Integer> score_list = new Vector<Integer>();
			for(Move m_o : Next_Legal)
			{
				score_list.add(MINVAL(m_o, tmp_Board, depth));			
			}
			
			return Collections.max(score_list);
		}
		else
		{	
			return tmp_Board.board_evaluation(Player);
		}		
				
	}
	
	
	
}
