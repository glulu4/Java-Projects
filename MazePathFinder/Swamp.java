import java.io.*;
import java.util.*;

// DO NOT!! IMPORT JAVA.LANG

public class Swamp
{
	static int[][] swamp;  // NOW YOU DON'T HAVE PASS THE REF IN/OUT METHODS

 	public static void main(String[] args) throws Exception
	{
		int[] dropInPt = new int[2]; // row and col will be on the 2nd line of input file;
		swamp = loadSwamp( args[0], dropInPt );
		int row=dropInPt[0], col = dropInPt[1];
		String path = ""; // with each step grows to => "[2,3][3,4][3,5][4,6]" etc
		dfs( row, col, path );
		printSwamp("MAZE",swamp );l
	} // END MAIN

 	// JUST FOR YOUR DEBUGGING - DELETE THIS METHOD AND ITS CALL BEFORE HANDIN
	// ----------------------------------------------------------------
	private static void printSwamp(String label, int[][] swamp )
	{
		System.out.println( label );
		System.out.print("   ");
		for(int c = 0; c < swamp.length; c++)
			System.out.print( c + " " ) ;
		System.out.print( "\n   ");
		for(int c = 0; c < swamp.length; c++)
			System.out.print("- ");
		System.out.print( "\n");

		for(int r = 0; r < swamp.length; r++)
		{	System.out.print( r + "| ");
			for(int c = 0; c < swamp[r].length; c++)
				System.out.print( swamp[r][c] + " ");
			System.out.println("|");
		}
		System.out.print( "   ");
		for(int c = 0; c < swamp.length; c++)
			System.out.print("- ");
		System.out.print( "\n");
	}
	// --YOU-- WRITE THIS METHOD (LOOK AT PRINTSWAMP FOR CLUES)
   	// ----------------------------------------------------------------
	private static int[][] loadSwamp( String infileName, int[] dropInPt  ) throws Exception
	{
		File file = new File(infileName);
		Scanner layOut = new Scanner( file );

		while ( layOut.hasNextInt() )
		{
			int size = layOut.nextInt();
			swamp = new int[size][size];
			dropInPt[0] = layOut.nextInt();
			dropInPt[1] = layOut.nextInt();

			for ( int row = 0; row < swamp.length; row++ )
				for ( int col = 0; col < swamp[row].length; col++ )
					swamp[row][col] = layOut.nextInt();
		// OPEN UP A SCANNER USING THE INCOMING FILENAME
		// THE FIRST NUMBER ON THE FIRST LINE WILL BE THE NUMBER OF ROWS & COLS
		// THE SECOND & THIRD NUMBER ON 1st LINE WILL BE THE DROP IN POINT X,Y
		// STORE SEOND NUMBER INTO dropInPt[0] THIRD # INTO dropInPt[1]
		// USING ROW, COL DEFINE A 2D ARRAY OF INT
		// USE A NESTED LOOP. OUTER LOOP ROWS, INNER LOOP COLS
		// READ IN THE GRID OF VALUES FROM THE INPUT FILE
		// CLOSE THE SCANNER
		// RETURN THE 2D ARRAY WITH VALUES LOADED INTO IT
		  // JUST TO MAKE IT COMPILE
	}
	return swamp;
}
	static void dfs( int row, int col, String path ) // dfs = DEPTH FIRST SEARCH
	{

		//printSwamp(path, swamp);

		path += "[" + row+"," + col+"]";
		//System.out.println(path);
		//printSwamp(path, swamp);

		if ( row == 0 || col == 0 || row == swamp.length - 1 || col == swamp[row].length -1	)
		{
			System.out.println(path);
			return;
		}

		if ( swamp[row-1][col] == 1) // North
		{
			swamp[row][col] = -1;
			dfs(row-1, col, path );
			swamp[row][col] = 1;
		}

		if ( swamp[row-1][col +1] ==1 ) // NE
		{
			swamp[row][col] = -1;
			dfs(row-1, col+1, path );
			swamp[row][col] = 1;
		}

		if ( swamp[row][col +1] == 1) // East
		{
			swamp[row][col] = -1;
			dfs(row, col+1, path );
			swamp[row][col] = 1;
		}

		if ( swamp[row +1][col+1] ==1) // south east
		{
			swamp[row][col] = -1;
			dfs(row+1, col+1, path );
			swamp[row][col] = 1;
		}

		if ( swamp[row+1][col] == 1) // south
		{
			swamp[row][col] = -1;
			dfs(row+1, col, path );
			swamp[row][col] = 1;
		}

		if (swamp[row+1][col-1] ==1 ) // south west
		{
			swamp[row][col] = -1;
			dfs(row+1, col-1, path );
			swamp[row][col] = 1;
		}

		if ( swamp[row][col-1] ==1) // west
		{
			swamp[row][col] = -1;
			dfs(row, col-1, path );
			swamp[row][col] = 1;
		}

		if ( swamp[row-1][col-1] ==1) // north west
		{
			swamp[row][col] = -1;
			dfs(row-1, col-1, path );
			swamp[row][col] = 1;
		}
		return;

	}
}
