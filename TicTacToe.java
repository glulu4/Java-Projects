import java.io.*;
import java.util.*;

public class TicTacToe
{
  static String[][] board = new String[3][3];

  public static void main(String[] args)
  {
    resetGame();
    playGame();
  }

  public static void playGame()
  {
    System.out.println("Hi, this is Tic Tac Toe. Player1 places X's while Player2 places O's" );
    printBoard();

    do {
      boolean xTurn;
      System.out.print("Player1( x ), ");
      int[] cords = promptUserForCordinates(); // returns valid cordinates for game to use

      placeMark( cords, xTurn = true );
      printBoard();

      ifGameOver();

      System.out.print("Player2( O ), ");
      cords = promptUserForCordinates();
      placeMark( cords, xTurn = false );
      printBoard();

      ifGameOver();

    } while ( true );
  }

  public static int[] promptUserForCordinates()
  {
    int[] cords;
    boolean isValidSpot;
    do
    {
      isValidSpot = true;
      Scanner kbd = new Scanner(System.in);
      System.out.println("Enter the cordinates of your move. Example:\n2 2");
      System.out.println("- -");
      String StringCords = kbd.nextLine();
      System.out.println();

      String[] StringCordsArr = StringCords.split(" ", 2);
      cords = new int[2];
      for ( int i = 0; i < 2; i++)
      {
        try {
          cords[i] = Integer.parseInt(StringCordsArr[i]);
        }
        catch(NumberFormatException nfe)
        {
          System.out.println("Ok something happened... only numbers");
          isValidSpot = false;
        }
        catch ( ArrayIndexOutOfBoundsException aioobe )
        {
          System.out.print(""); // ignoring this
        }
        catch( Exception e){
          System.out.print(e);
        }
      }

      if ( !cordinatesAreValid(cords) )
        isValidSpot = false;

    } while (!isValidSpot);

    return cords;
  }
  public static void resetGame()
  {
    for ( int row = 0; row < board.length; row++)
      for ( int col = 0; col < board[row].length; col++ )
        board[row][col] = "___";
  }
  public static void printBoard()
  {
    for ( int row = 0; row < board.length; row++){
      for ( int col = 0; col < board[row].length; col++ ){
        if (col == 2)
          System.out.print(board[row][col]);
        else
          System.out.print(board[row][col] + "|");
      }
      System.out.println();
    }
    System.out.println("   |   |");


  }
  private static boolean cordinatesAreValid(int[] cords) // if cordinates are out of bounds, return false
  {
    int x = cords[0];
    int y = cords[1];

    if ( x > 2 || y > 2 || x < 0 || y < 0 )
    {
      System.out.println("Cordinates are out of bounds");
      return false;
    }

    // checks to see if cordinates are in bounds
    // then to see if the space is taken or not

    for ( int row = 0; row < board.length; row++ )
      for ( int col = 0; col < board[row].length; col++)
        if ( board[cords[0]][cords[1]] != "___" )
        {
          System.out.println("Spot taken");
          return false;
        }

    return true;
  }
  private static void placeMark( int[] cords, boolean xTurn )// if xTurn is true it places an X
  {
    int x = cords[0];
    int y = cords[1];
    board[x][y] = xTurn ? " X ": " O ";
  }
  public static void checkDiagnols()
  {
    if ( board[0][0] == board[1][1] && board[2][2] == board[1][1] && board[0][0] != "___" )
      endGame();

    if ( board[0][2] == board[1][1] && board[1][1] == board[0][2] && board[0][2] != "___")
      endGame();
  }

  public static void checkColumns()
  {
    for ( int row = 0; row < board.length; row++)
      if ( board[0][row] == board[1][row] && board[1][row] == board[2][row] && board[0][row] != "___")
        endGame();
  }

  public static void checkRows()
  {
    for ( int row = 0; row < board.length; row++)
      if ( board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][0] != "___" )
        endGame();
  }
  private static void ifGameOver()
  {
    checkDiagnols();
    checkRows();
    checkColumns();
  }

  public static void endGame()
  {
    System.out.println("GAME OVER");
    System.exit(0);

  }
} // end of File
