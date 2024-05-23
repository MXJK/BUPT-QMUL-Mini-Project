/** Timer Demo code provided in support of EBU4201 mini-project 2023-24
*
* @author Vindya Wijeratne
*
**/

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalTime;

public class TimerDemo implements ActionListener {
  JButton myButton;
  private Timer timer;                  //javax.swing.Timer
  private final int initialDelay = 1000;      //in milli seconds
  private ActionListener taskPerformer;
  private int timeAllowed = 10;           //in seconds as per the code below

  private LocalTime time = LocalTime.of(0, 0, 0);

  public static void main(String[] args) {
    TimerDemo myGui = new TimerDemo();
    myGui.go();
  }

  public void go() {
    JFrame myFrame = new JFrame();  

    taskPerformer = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
           time = time.minusSeconds(1);
           System.out.println(time);
           if(time.equals(LocalTime.MIN)){       //condition to stop the timer for this particular example
            timer.stop();
           myButton.setText("Ended");
          }
      }
  };

    timer = new Timer(initialDelay,taskPerformer);       //taskPerformer as define above; look up the Timer constructio in the javax.swing.Timer class

    myButton = new JButton("Start");
    myButton.addActionListener(this);
    myFrame.add(myButton);
    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    myFrame.setSize(400, 400);
    myFrame.setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    time = LocalTime.of(0,0,timeAllowed);      //counting only in seconds for this timer; look up overloaded of() methods
    timer.start();           //look up start() and restart() methods of the Timer class
    System.out.println("started");
  }
}