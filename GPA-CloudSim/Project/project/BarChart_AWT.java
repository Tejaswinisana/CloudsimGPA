


package project;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class BarChart_AWT extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	   public BarChart_AWT( String applicationTitle , String chartTitle ,int f,int r,int g )
	   {
		   super( applicationTitle );        
		      JFreeChart barChart = ChartFactory.createBarChart(
		         chartTitle,           
		         "Category",            
		         "Score",            
		         createDataset(f,r,g),          
		         PlotOrientation.VERTICAL,           
		         true, true, false);
		         
		      ChartPanel chartPanel = new ChartPanel( barChart );        
		      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
		      setContentPane( chartPanel ); 
		      
		      Button button = new Button("Back");
		      button.addActionListener(new ActionListener() {
		      	public void actionPerformed(ActionEvent e) {
		      		chartPanel .setVisible(false);
		                //setContentPane();
		                getContentPane().setLayout(null);    
		                barChart.setBorderVisible(false);
		                //.dispose();

		      	}
		      });
		      chartPanel.add(button);
		   }
	   private CategoryDataset createDataset(int f,int r,int g )
	   {
	      final String fcfs = "FCFS";        
	      final String rr="RR" ;
	      final String gpa="GPA";
	      final String avgf = "AVERAGE FCFs";        
	      final String avgr = "AVERAGE RR";
	      final String avgg = "AVERAGE GPA";
	      final DefaultCategoryDataset dataset = 
	      new DefaultCategoryDataset( );  

	      dataset.setValue( f , avgf , fcfs ); 
	      dataset.setValue(r,avgr,rr);
	      dataset.setValue(g,avgg,gpa);
	      
	      return dataset;
	      	      
	      	      
	   }
	   public static void main( String[ ] args )
	   {
	      
	   }
}























