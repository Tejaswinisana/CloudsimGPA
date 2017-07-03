package project;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

public class Sample2 {
   private static final int PREF_W = 600;
   private static final int PREF_H = PREF_W;
   private static final int MAX_ROWS = 500;
   private static final String TEXT_BODY = "Sample Message To Test Scrolling";;

   private static void createAndShowGui() {
      JPanel panel = new JPanel(new GridLayout(0, 1));
      panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      for (int i = 0; i < MAX_ROWS; i++) {
         String text = String.format("%03d %s", i, TEXT_BODY);
         JLabel label = new JLabel(text);
         panel.add(label);
      }

      JScrollPane scrollPane = new JScrollPane(panel);
      scrollPane.setPreferredSize(new Dimension(PREF_W, PREF_H));

      JFrame frame = new JFrame("Sample2");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(scrollPane);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}