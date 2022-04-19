import java.util.*;
import java.io.*;

public class FindSubArray
{
  public static void main(String[] args)
  {
    int size = promptUserForSize();
    int[] arr = new int[size];
    fillArray(arr);
    System.out.print("Original Array: ");
    printArr(arr);
    Scanner obj = new Scanner ( System.in );
    System.out.print("Press enter to continue");
    obj.nextLine();

    int pSum = promptUser();
    findSubArraySum( arr, pSum);



  } // end of main


  static int promptUser()
  {
    int pSum = 0;
    boolean go = true;
    do
    {
    try
    {
      Scanner in = new Scanner ( System.in );
      System.out.print("Enter the partial sum: ");
      pSum = in.nextInt();
      go = true;
    }
    catch ( Exception e)
    {
      //System.out.println("Enter a number: ");
      Scanner in = new Scanner ( System.in );
      go = false;
    }

  } while ( !go );
  return pSum;

  } // End of promptUser
  static void printArr( int[] arr)
  {
    for ( int i = 0; i < arr.length; i++)
      System.out.print(arr[i] + " ");
    System.out.println();
  }
  static void fillArray( int[] arr )
  {
    Random rnd = new Random();
    for ( int i = 0; i < arr.length; i++)
      arr[i] = rnd.nextInt(11); // generates numbers from 0-100
  }

  static void findSubArraySum( int[] arr, int pSum )
  {
    int localSum = 0, i = 0, j =0;
    boolean go = false;
    for ( i = 0; i < arr.length; i++)
    {
      localSum = 0;
      for ( j = i; j < arr.length; j++)
      {
        localSum += arr[j];
        if ( localSum > pSum  )
          break;
        if ( localSum == pSum )
          System.out.println("From index " + i + " to index " + j + " sums to " + localSum) ;
      }
    }
  } // end of method
  static int promptUserForSize()
  {
    int size = 0;
    boolean go = true;
    do
    {
    try
    {
      Scanner in = new Scanner ( System.in );
      System.out.print("Enter array size: ");
      size = in.nextInt();
      go = true;
    }
    catch ( Exception e)
    {
      //System.out.println("Enter a number: ");
      Scanner in = new Scanner ( System.in );
      go = false;
    }

  } while ( !go );
  return size;

  } // End of promptUser
} // end of class
