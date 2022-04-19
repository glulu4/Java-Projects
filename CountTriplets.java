import java.io.*;
import java.util.*;

public class CountTriplets
{
  public static void main(String[] args)
  {
    int size = promptUserForSize();
    int[] arr = new int[size];

    fillArray( arr );
    System.out.print("Unsorted Array: ");
    printArr(arr);
    int[] sortedArr = InsertionSort( arr );
    System.out.print("Sorted Array:   ");
    printArr(sortedArr);
    countTriplets( sortedArr );

  } // end of main
  static void countTriplets(int[] arr )
  {
    int sum = 0;

    for ( int i = 0; i < arr.length; i++ )
    {
      sum = arr[i];
      for ( int j = 0; j < arr.length-1; j++ )
      {
        if ( sum == (arr[j] + arr[j+1]) )
          System.out.println("Found a sum: " + arr[j] + " + " + arr[j+1] + " = " + sum );


      }
    }

  } // end of count triplet
  public static int[] InsertionSort( int[] arr ) // compare arr[i] to all values before and move the min to the back
  {
    // iterate trhough sorrted portion, starts at j and go down
    for ( int j = 0; j < arr.length-1; j++)
    {
      for ( int i = j; i >= 0 ; i--) // this loops backwards from wherever j is. so lets say j=5, then the inner loop
      {                              // will iterate from 5 to zero backwards
        if ( arr[i+1] < arr[i])
        {
          int temp = arr[i+1];
          arr[i+1] = arr[i];
          arr[i] = temp;
        }
      }
    }
    return arr;
  }

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
  static void fillArray( int[] arr )
  {
    Random rnd = new Random();
    for ( int i = 0; i < arr.length; i++ )
      arr[i] = rnd.nextInt(11); // generates numbers from 0-10
  }
  static void printArr( int[] arr )
  {
    for ( int i = 0; i < arr.length; i++ )
      System.out.print(arr[i]+" ");
    System.out.println();
  }
} // end of class
