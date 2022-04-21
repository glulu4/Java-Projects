import java.util.*; // input output
import java.io.*; // scanner class

public class Jumbles // name of java file
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

      Scanner Jinfile = new Scanner( new File( args[1] ) );
      TreeSet<String> jWords = new TreeSet<String>();
      while (Jinfile.hasNext())
        jWords.add( Jinfile.nextLine());

      for ( String jumble: jWords )
      {
        String jCanonWord = Canonical(jumble);
        System.out.print(jumble);
        if (dCanon2Dword.containsKey(jCanonWord) ) // if teh canonical form of teh jumbled word is in keyset
        {
          for ( String val : dCanon2Dword.get(jCanonWord) )
            System.out.print(" "+val);
        }
        System.out.println();
      }


  }// end of main
  static String Canonical ( String s )
    {
      char[] nonScram = s.toCharArray();
      Arrays.sort( nonScram );
      return new String ( nonScram );
    }


} // end of public class
