package stuff; // to create a package, eneter this command prompt: 'javac -d . fileName.java'
import java.util.*;
import java.io.*;


public class Die
{
  private int sides;

  public Die()
  {
    sides = 6;
  }
  public Die( int sides )
  {
    this.sides = sides;
  }



  public int roll()
  {
    Random rnd = new Random();
    int roll = rnd.nextInt(sides + 1);
    while ( roll == 0 )
    {
      roll = rnd.nextInt(sides + 1);
    }
    return roll;
  }
}
