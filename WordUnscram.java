import java.util.*; // input output
import java.io.*; // scanner class

public class WordUnscram // name of java file
{
  public static void main(String args[]) throws Exception
  {
    Scanner Dinfile = new Scanner( new File( args[0] ) );
    TreeMap<String,TreeSet<String>> dCanon2Dword = new TreeMap<String,TreeSet<String>>();
    while ( Dinfile.hasNext() )
    {
      String dWord = Dinfile.nextLine();
      String dCanonWord = Canonical(dWord);
      if ( !dCanon2Dword.containsKey(dCanonWord))
        dCanon2Dword.put(dCanonWord, new TreeSet<String>());
      dCanon2Dword.get(dCanonWord).add(dWord);
      dCanon2Dword.put(dCanonWord, dCanon2Dword.get(dCanonWord) );
    }


    Scanner kbd = new Scanner( System.in );
    System.out.print("Enter your scrammbled word: ");
    String jumble = kbd.nextLine();
    String jCanonWord = Canonical(jumble);

    System.out.println("This is your scrammbled word --> "+jumble);
    if (dCanon2Dword.containsKey(jCanonWord) ) // if the canonical form of eeh jumbled word is in keyset
      for ( String val : dCanon2Dword.get(jCanonWord) )
        System.out.print("Words that match the letters above: "+val);

    System.out.println();



  }// end of main
  static String Canonical ( String s )
    {
      char[] nonScram = s.toCharArray();
      Arrays.sort( nonScram );
      return new String ( nonScram );
    }


} // end of public class
