package search.minimax.com;


import java.util.Vector;

enum Piece_Color{
	White('W'), Black('B');
	private char col;
	Piece_Color(char c) { col = c; }
	char get_Color() { return col; }
}


public class Board {
	private char[][] board;
	private int length;
	private int breadth;
	private int black_score;
	private int white_score;
	Piece_Color piece;
	private char Player,Opponent;
	/*
	 *  Default Constructor
	 */
			
	
	Board(int length, int breadth, char P)
	{
		/*
		 * Minimum allowed board size is 3 by 3
		 */
		if (length < 3 || breadth < 3)
		{
			throw new IllegalArgumentException("Board Size cannot be less than 3 by 3");
		}
		this.length=length;
		this.breadth=breadth;
		board = new char[length][breadth];
		reset_board();
		this.black_score = 0;
		this.white_score = 0;
		Player=(P==piece.White.get_Color()?piece.White.get_Color():piece.Black.get_Color());
		Opponent=(P==piece.White.get_Color()?piece.Black.get_Color():piece.White.get_Color());
		System.out.println("# ------- R E V E R S I ----------");
		System.out.println("# Player is playing " + Player + " and Opponent is Playing "+ Opponent );
		System.out.flush();
	}
	
	Board(Board copy)
	{
		this.length = copy.length;
		this.breadth = copy.breadth;
		board = new char[length][breadth];
		for(int i=0;i<length;i++)
			for(int j=0;j<breadth;j++)
				this.board[i][j]=copy.board[i][j];
		this.black_score=copy.black_score;
		this.white_score=copy.white_score;
		this.Player=copy.Player;
		this.Opponent=copy.Opponent;
	}
	
	
	
	/*
	 * Internal Board Functions
	 * ACCESS : Private
	 */
	private void reset_board()
	{
		for(int i=0; i<length; i++)
			for(int j=0; j<breadth; j++)
				board[i][j] = '.';
	}
	
	
	private void refresh_score()
	{
		white_score=black_score=0;
		for(int i=0; i<length; i++)
			for(int j=0; j<breadth; j++)
			{
				if(board[i][j] == piece.White.get_Color())
					white_score++;
				if(board[i][j] == piece.Black.get_Color())
					black_score++;
			}
	}
	
	
	/*
	 *  Public Getters and Setters 
	 *  ACCESS : Public
	 */
	public int get_score_BLACK()
	{
		refresh_score();
		return black_score;
	}
	public int get_score_WHITE()
	{
		refresh_score();
		return white_score;
	}
	public char get_board(int i, int j)
	{
		return board[i][j];
	}
	
	
	/*
	 * Board Display Utils
	 * ACCESS : Public
	 * !NOTE! Do not forget to flush() standard display when adding any functions to this block
	 */
	public void display_board()
	{
	for(int i=0; i<length; i++)
		{	
		System.out.print("# ");		
		for(int j=0; j<breadth; j++)
			{
			//System.out.print(" ("+i+","+j+")");
			System.out.print(" "+board[i][j]);
			}
		System.out.println("");
		}
	System.out.flush();
	}

	public void display_score()
	{
		refresh_score();
		System.out.println("# Score : WHITE = " + white_score + " BLACK = " + black_score);
		System.out.flush();
	}
	
	
	/*
	 * Board Computational functions 
	 * ACCESS : Public
	 */
	public void init()
	{
		board[length/2-1][breadth/2-1]=piece.White.get_Color();
		board[length/2-1][breadth/2]=piece.Black.get_Color();
		board[length/2][breadth/2]=piece.White.get_Color(); 
		board[length/2][breadth/2-1]=piece.Black.get_Color(); 			
	}
	
	public Vector<Move> legal_moves(char C) 
	{
		/*
		 * This will temporarily put a stone for Player P with coordinates (x,y)
		 * This makes a list of all possible legal moves.
		 */
		//System.out.println("# MAKE MOVE "+C+ " > "+ x+ " "+y);
		
		Vector<Move> legal = new Vector<Move>();
		for(int i=0;i<length;i++)
		{
			for(int j=0;j<breadth;j++)
			{
				Board temp_board =  new Board(this);
				if(temp_board.make_move(C, i, j))
				{
					legal.addElement(new Move(i,j));
					//System.out.println("FROM INSIDE legal"+i+j);
				}
			}
		}
		
	return legal;
	}
	
	public boolean make_move (char C, int x, int y)
	{
		/*
		 * This will put a stone manually for Player P with coordinates (x,y)
		 * This also flips all of the oponent's pieces that are possible
		 * Returns 1 for success.
		 * Returns 0 if the move is illegal. 		 
		 */
		//System.out.println("# MAKE MOVE "+C+ " > "+ x+ " "+y);
		
		if (board[x][y]!='.')
			return false;
		
		Vector<Move> diagonal;
		Vector<Move> horizontal;
		Vector<Move> vertical;
		int flag=0;
		
		for(int i=0; i<length; i++)
		{
			for(int j=0;j<breadth;j++)
			{
				//System.out.println("Looping for " +i+ " " +j);
				
				diagonal=new Vector<Move>(generate_diagonal(i, j, x, y));
				horizontal=new Vector<Move>(generate_horizontal(i, j, x, y));
				vertical=new Vector<Move>(generate_vertical(i, j, x, y));
				
				if( (diagonal.size()>2 || vertical.size()>2 || horizontal.size()>2) && (board[i][j] == C))
				{
					if (C == Player)
					{    
						int flip=0;
						//Horizontal
						for(Move m : horizontal)
						{
							if(board[m.get_cord_x()][m.get_cord_y()] == Opponent)
							{
								flip++;
							}
						}
						if((horizontal.size()-2)==flip && flip!=0)
						{
							for(Move m : horizontal)
							{
								board[m.get_cord_x()][m.get_cord_y()] = Player;
								//System.out.println("SETTING HORIZONTAL :" + Player + " " +m.get_cord_x() +" " +m.get_cord_y() );
							}
							flag=1;
						}
						// Vertical
						flip = 0;
						for(Move m : vertical)
						{
							if(board[m.get_cord_x()][m.get_cord_y()] == Opponent)
							{
								flip++;
							}
						}
						if((vertical.size()-2)==flip && flip!=0)
						{
							for(Move m : vertical)
							{
								board[m.get_cord_x()][m.get_cord_y()] = Player;
								//System.out.println("SETTING VERTICAL :" + Player + " " +m.get_cord_x() +" " +m.get_cord_y() );
							}
							flag=1;
						}
						// Diagonal
						flip = 0;
						for(Move m : diagonal)
						{
							if(board[m.get_cord_x()][m.get_cord_y()] == Opponent)
							{
								flip++;
							}
						}
						if((diagonal.size()-2)==flip && flip!=0)
						{
							for(Move m : diagonal)
							{
								board[m.get_cord_x()][m.get_cord_y()] = Player;
								//System.out.println("SETTING DIAGONAL :" + Player + " " +m.get_cord_x() +" " +m.get_cord_y() );
							}
							flag=1;
						}
						
					}  // END if C = Player
					
					else if (C == Opponent)
					{
						int flip=0;
						//Horizontal
						for(Move m : horizontal)
						{
							if(board[m.get_cord_x()][m.get_cord_y()] == Player)
							{
								flip++;
							}
						}
						if((horizontal.size()-2)==flip && flip!=0)
						{
							for(Move m : horizontal)
							{
								board[m.get_cord_x()][m.get_cord_y()] = Opponent;
							}
							flag=1;
						}
						
						// Vertical
						flip = 0;
						for(Move m : vertical)
						{
							if(board[m.get_cord_x()][m.get_cord_y()] == Player)
							{
								flip++;
							}
						}
						if((vertical.size()-2)==flip && flip!=0)
						{
							for(Move m : vertical)
							{
								board[m.get_cord_x()][m.get_cord_y()] = Opponent;
							}
							flag=1;
						}
						
						// Diagonal
						flip = 0;
						for(Move m : diagonal)
						{
							if(board[m.get_cord_x()][m.get_cord_y()] == Player)
							{
								flip++;
							}
						}
						if((diagonal.size()-2)==flip && flip!=0)
						{
							for(Move m : diagonal)
							{
								board[m.get_cord_x()][m.get_cord_y()] = Opponent;
							}
							flag=1;
						}  
							
					}// END if C == Opponent
				}
				diagonal.removeAllElements();
				vertical.removeAllElements();
				horizontal.removeAllElements();
			}
		}
		
		if(flag==1)
			return true;
		return false;
	}
	
	private Vector<Move> generate_horizontal(int i, int j, int x, int y)
	{
		Vector<Move> horizontal = new Vector<Move>();
		
		if(i!=x)
			return horizontal;
		
		if(j>y)
		{
			while(j>=y)
			{
				horizontal.addElement(new Move(i,j));
				j--;
			}
		}
		else if(j<y)
		{
			while(j<=y)
			{
				horizontal.addElement(new Move(i,j));
				j++;
			}
		}
		//for(Move m : horizontal)
		//m.display_cordinate();

		
		return horizontal;
	}
	
	private Vector<Move> generate_vertical(int i, int j, int x, int y)
	{
		Vector<Move> vertical = new Vector<Move>();
		if(j!=y)
			return vertical;
		
		if(i>x)
		{
			while(i>=x)
			{
				vertical.addElement(new Move(i,j));
				i--;
			}
		}
		else if(i<x)
		{
			while(i<=x)
			{
				vertical.addElement(new Move(i,j));
				i++;
			}
		}
		//for(Move m : vertical)
		//m.display_cordinate();

		return vertical;
	}
	
	private Vector<Move> generate_diagonal(int i, int j, int x, int y)
	{
		Vector<Move> diagonals = new Vector<Move>();
		
		/*
		 * Two points (x,y) and (i,j) can only lie diagonally if | x-i | = | y-j | 
		 */
		if(Math.abs(x-i)!=Math.abs(y-j))
			return diagonals;
		
		if(i<x && j<y)
		{
			while (i<=x && j<=y)
			{
				diagonals.addElement(new Move(i,j));
				i++; j++;
			}
		}
		
		else if(i>x && j>y)
		{
			while (i>=x && j>=y)
			{
				diagonals.addElement(new Move(i,j));
				i--; j--;
			}
		}
		
		else if(i>x && j<y)
		{
			while (i>=x && j<=y)
			{
				diagonals.addElement(new Move(i,j));
				i--; j++;
			}
		}
		
		else if(i<x && j>y)
		{
			while (i<=x && j>=y)
			{
				diagonals.addElement(new Move(i,j));
				i++; j--;
			}
		}
		/*for(Move m : diagonals)
			m.display_cordinate();
		*/
		return diagonals;
	}
	
	/*
	 * BOARD EVALUATION FUNCTION
	 * ACCESS : PUBLIC
	 */
	public int board_evaluation(char P)
	{
		int score = 0;
		
		if(Player==piece.White.get_Color())
		{
			Evaluator evaluate = new Evaluator(this,'W',length);
			score = evaluate.scorer();
			//score = get_score_WHITE() - get_score_BLACK();
		}
		else if(Player==piece.Black.get_Color())
		{
			Evaluator evaluate = new Evaluator(this,'B',length);
			score = evaluate.scorer();
			//score = get_score_BLACK() - get_score_WHITE();
		}
		
		//score = get_score_WHITE() - get_score_BLACK();
		return score;
	}
	
	
	
	

}
