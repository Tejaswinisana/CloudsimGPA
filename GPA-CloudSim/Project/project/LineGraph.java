

package project;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class LineGraph extends JPanel 
{
	private static JFrame frame;
    private int padding = 25;
    private int labelPadding = 25;
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private static ArrayList<Double> fcN = new ArrayList<Double>(),fcG= new ArrayList<Double>();
    private static ArrayList<Double> rrN = new ArrayList<Double>();

    public LineGraph()
    {
    	setLayout(new BorderLayout(0, 0));
    	
    	JButton btnExit = new JButton("back");
    	btnExit.addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent e) 
    		{
    			frame.setVisible(false);
    		}
    	});
    	add(btnExit,BorderLayout.PAGE_END);
    	JLabel lblNewLabel = new JLabel("<html><br/><br/>X-Axis : Cloudlet<br/>Y-Axis : Exec Time&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><p color='#80FF00' align='center'>GPA</p>"+
    	"<p color='red' align='center'>FCFS</p><p color='blue' align='center'>RR</p></html>");
    	lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
    	lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
    	add(lblNewLabel, BorderLayout.EAST);
    }
    
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (fcN.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

        ArrayList<Point> graphPoints1 = new ArrayList<>();
        for (int i = 0; i < fcN.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxScore() - fcN.get(i)) * yScale + padding);
            graphPoints1.add(new Point(x1, y1));
        }
        
        ArrayList<Point> graphPoints2 = new ArrayList<>();
        for (int i = 0; i < fcN.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxScore() - fcG.get(i)) * yScale + padding);
            graphPoints2.add(new Point(x1, y1));
        }
        
        ArrayList<Point> graphPoints3 = new ArrayList<>();
        for (int i = 0; i < fcN.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxScore() - rrN.get(i)) * yScale + padding);
            graphPoints3.add(new Point(x1, y1));
        }

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (fcN.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        for (int i = 0; i < fcN.size(); i++) {
            if (fcN.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (fcN.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((fcN.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = (i+1) + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke1 = g2.getStroke();
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(6));
        for (int i = 0; i < graphPoints1.size() - 1; i++) {
            int x1 = graphPoints1.get(i).x;
            int y1 = graphPoints1.get(i).y;
            int x2 = graphPoints1.get(i + 1).x;
            int y2 = graphPoints1.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke1);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints1.size(); i++) {
            int x = graphPoints1.get(i).x - pointWidth / 2;
            int y = graphPoints1.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
        
        Stroke oldStroke2 = g2.getStroke();
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(6));
        for (int i = 0; i < graphPoints2.size() - 1; i++) {
            int x1 = graphPoints2.get(i).x;
            int y1 = graphPoints2.get(i).y;
            int x2 = graphPoints2.get(i + 1).x;
            int y2 = graphPoints2.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke2);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints2.size(); i++) {
            int x = graphPoints2.get(i).x - pointWidth / 2;
            int y = graphPoints2.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
        
        Stroke oldStroke3 = g2.getStroke();
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(6));
        for (int i = 0; i < graphPoints3.size() - 1; i++) {
            int x1 = graphPoints3.get(i).x;
            int y1 = graphPoints3.get(i).y;
            int x2 = graphPoints3.get(i + 1).x;
            int y2 = graphPoints3.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke3);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints3.size(); i++) {
            int x = graphPoints3.get(i).x - pointWidth / 2;
            int y = graphPoints3.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

    private double getMinScore() 
    {
        double minScore = Double.MAX_VALUE;
        
        for (Double score : fcN)
            minScore = Math.min(minScore, score);
        for (Double score : fcG)
            minScore = Math.min(minScore, score);
        for (Double score : rrN)
            minScore = Math.min(minScore, score);
        
        return minScore;
    }

    private double getMaxScore() 
    {
        double maxScore = Double.MIN_VALUE;
        
        for (Double score : fcN) 
            maxScore = Math.max(maxScore, score);
        for (Double score : fcG) 
            maxScore = Math.max(maxScore, score);
        for (Double score : rrN) 
            maxScore = Math.max(maxScore, score);
        
        return maxScore;
    }
   
    private static void createAndShowGui(double[] fcNTime, double[] fcGTime, double[] rrNTime,int cl) 
    {
    	for(int i=0; i<cl; i++)
    		fcN.add(fcNTime[i]);
    	for(int i=0; i<cl; i++)
    		fcG.add(fcGTime[i]);
    	for(int i=0; i<cl; i++)
    		rrN.add(rrNTime[i]);
    	
        LineGraph mainPanel = new LineGraph();
        mainPanel.setPreferredSize(new Dimension(800, 600));
        frame = new JFrame("Line Graph");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void start(double[] fcNTime, double[] fcGTime, double[] rrNTime,int cl) 
    {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() 
         {
            createAndShowGui(fcNTime,fcGTime,rrNTime,cl);
         }
      });
   }
}