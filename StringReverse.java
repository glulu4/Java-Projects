import java.util.*;
import java.io.*;

public class StringReverse
{
  public static void main(String[] args)
  {
    String input = promptUserForString();
    System.out.println("This is the original: " + input);
    System.out.print("This is the sentence reversed: ");
    reverseString(input);

  }// end of main


  public static String promptUserForString()
  {
    Scanner kbd = new Scanner(System.in);
    System.out.print("Enter a sentence to be reversed: ");
    String input = kbd.nextLine();
    return input;
  }


  static void reverseString( String input )
  {

    String reversed = "";
    String[] inputTokens = input.split(" "); // ["I"]["want"]["to"]["go"]["to"]["florida"]
    for ( int i = inputTokens.length -1; i >= 0; i--)
      System.out.print(inputTokens[i] + " ");
    


  }
  // lets go to the mall

} // end of class
