import java.io.*;
import java.util.*;

// just generates all the strings & prints them as they are generated

public class Boggle
{
	static String[][] board;
	static long startTime,endTime; // for timing
	static final long MILLISEC_PER_SEC = 1000;


	static int num = 0;


	static TreeSet<String> dictionary = new TreeSet<String>();
	static TreeSet<String> hits = new TreeSet<String>();
	// define your dictionary set and and your hits set UP HERE as TreeSets

	public static void main( String args[] ) throws Exception
	{
		startTime= System.currentTimeMillis();
		board = loadBoard( args[1] );

		// loading in dictionary from args 0
		Scanner Dinfile = new Scanner( new File( args[0] ) );
		while ( Dinfile.hasNext() )
      dictionary.add( Dinfile.nextLine() );


// INITIALIZE DICT AND HITS HERE

		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board[row].length; col++)
				dfs( row, col, ""  ); // FOR EACH [R][C] THE WORD STARTS EMPTY

		for ( String hit: hits )
		{
			System.out.println( hit );
			num++;
		}

		//System.out.println( ""+num );

		endTime =  System.currentTimeMillis(); // for timing
		//System.out.println("GENERATION COMPLETED: runtime=" + (endTime-startTime)/MILLISEC_PER_SEC );

	} // END MAIN ----------------------------------------------------------------------------

	static void dfs( int r, int c, String word  )
	{
		word += board[r][c];

		if ( dictionary.contains( word ) )
			if ( word.length() > 2 )
				hits.add(word);
		else
			for ( String dWord: dictionary )
				if (!(dWord.startsWith( word )) )
					break;



		if ( r-1 >= 0 && board[r-1][c] != null )   // THE r +/- and c+/- WILL CHANGE FOR EVEY BLOCK BELOW
		{
			String unMarked = board[r][c]; // SAVE TO RESTORE AFTER RET FROM RECURSION
			board[r][c] = null; // // null IS THE MARKER OF A VALUE AS IN USE ALREADY
			dfs( r-1, c, word ); // THE r-1,c WILL CHANGE WITH EVERY OTHER BLOCK BELOW
			board[r][c] = unMarked; // BACK. UNMARK IT
		}

		if ( r-1 >= 0 && c+1 < board[r].length && board[r-1][c+1] != null ) // NE r-1, c+ 1
		{
			String unMarked = board[r][c];
			board[r][c] = null;
			dfs(r-1, c+1, word);
			board[r][c] = unMarked;
		}

		if ( c+1 < board[r].length && board[r][c+1] != null ) // East --> c+1
		{
			String unMarked = board[r][c];
			board[r][c] = null;
			dfs(r, c+1, word);
			board[r][c] = unMarked;
		}
		if ( r+1 <board.length && c+1 < board[r].length && board[r+1][c+1] != null) // SE --> r+1, c+1
		{
			String unMarked = board[r][c];
			board[r][c] = null;
			dfs(r+1, c+1, word);
			board[r][c] = unMarked;
		}
		if ( r+1 <board.length && board[r+1][c] != null ) // South --> r+1
		{
			String unMarked = board[r][c];
			board[r][c] = null;
			dfs(r+1, c, word);
			board[r][c] = unMarked;
		}

		if ( r+1 <board.length && c-1 >=0 && board[r+1][c-1] != null ) // SW r+1 col-1
		{
			String unMarked = board[r][c];
			board[r][c] = null;
			dfs(r+1, c-1, word);
			board[r][c] = unMarked;
		}

		if ( c-1 >=0 && board[r][c-1] != null ) // West c-1
		{
			String unMarked = board[r][c];
			board[r][c] = null;
			dfs(r, c-1, word);
			board[r][c] = unMarked;
		}
		if ( r-1 >= 0 && c-1 >=0 && board[r-1][c-1] != null )// NW r-1 c-1
		{
			String unMarked = board[r][c];
			board[r][c] = null;
			dfs(r-1, c-1, word);
			board[r][c] = unMarked;
		}
		return;

	} // END DFS ----------------------------------------------------------------------------

	//=======================================================================================
	static String[][] loadBoard( String fileName ) throws Exception
	{	Scanner infile = new Scanner( new File(fileName) );
		int rows = infile.nextInt();
		int cols = rows;
		String[][] board = new String[rows][cols];
		for (int r=0; r<rows; r++)
			for (int c=0; c<cols; c++)
				board[r][c] = infile.next();
		infile.close();
		return board;
	} //END LOADBOARD

} // END BOGGLE CLASS
