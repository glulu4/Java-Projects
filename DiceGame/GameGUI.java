import java.util.*;
import java.io.*;
import stuff.Die;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.BorderFactory.*;

public class GameGUI
{
  static JFrame frame;
  static JPanel buttonPanel;
  static JPanel textPanel;
  static JButton button1;
  static JLabel label1;
  static JLabel label2;

  static int playerScore = 0;
  static int computerScore = 0;

  static Die d1 = new Die(6);
  static Die d2 = new Die(6);

  public static void main(String[] args)
  {
    // creating the frame
    frame = new JFrame("This is the game");
    frame.setSize(600,400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(null);
    // creating the button panel
    buttonPanel = new JPanel();
    buttonPanel.setBounds(100,0, 400,100 );
    buttonPanel.setLayout( new BorderLayout());

    button1 = new JButton("Player 1");


    //creating the textPanel
    textPanel = new JPanel();
    textPanel.setBounds(100,200,400,100);
    textPanel.setLayout( new BorderLayout());
    // creating the text labels
    label1 = new JLabel("Roll: ");
    label1.setFont(new Font("Serif", Font.BOLD, 14));
    label1.setForeground(Color.blue);
    label2 = new JLabel(displayScore());
    label2.setFont(new Font("Serif", Font.BOLD, 14));
    label2.setForeground(Color.red);
    // creating the buttons
    button1 = new JButton("Player 1");

    button1.addActionListener( new ActionListener()
    {
      public void actionPerformed( ActionEvent e )
      {
        int playerRoll = d1.roll();
        rollComparison( playerRoll );

      }
    });

    button1.setSize(50, 40);
    buttonPanel.add(button1, BorderLayout.LINE_START);
    buttonPanel.add( label1, BorderLayout.LINE_END );

    textPanel.add(label2 );

    Border border = BorderFactory.createLineBorder( Color.green, 4 );
    Border border2 = BorderFactory.createLineBorder( Color.blue, 4 );

    buttonPanel.setBorder ( border );
    textPanel.setBorder ( border2 );

    frame.getContentPane().add(buttonPanel);
    frame.add( textPanel );

    frame.setVisible(true);


  } // end of main
  public static void rollComparison( int playerRoll )
  {
    int computerRoll = d2.roll();
    label1.setText("Player 1 rolled a " + playerRoll + ". Computer rolled a " + computerRoll );


    if ( playerRoll > computerRoll)
    {
      playerScore++;
      label2.setText("Player won this round! " + displayScore() );
    }
    else if ( playerRoll == computerRoll)
    {
      label2.setText("Computer and Player both rolled a " + computerRoll);
    }
    else
    {
      computerScore++;
      label2.setText("Computer won this round! " + displayScore());
    }


    if ( playerScore == 10 || computerScore == 10 )
    {
      if (playerScore > computerScore)
        label2.setText("GAME OVER. Player won " + displayScore());
      else
        label2.setText("GAME OVER. Computer won " + displayScore());


      button1.setEnabled(false);
    }






  }
  public static String displayScore()
  {
    String score = "";
    if ( playerScore > computerScore )
      score += playerScore + " - " + computerScore;
    else
      score += computerScore + " - " + playerScore;
    return score;
  }




} // end of class
