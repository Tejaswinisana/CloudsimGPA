package project;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Application
{
	private JFrame frame;
	private JTextField noVM, noCL;
	private JButton submit;
	private JLabel inpVM, inpCL, inval;
	
	private int nofVM,nofCL;

	public static void main(String[] args) 
	{
		start();
	}

	public static void start() 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				Application window = new Application();
				window.frame.setVisible(true);
			}
		});
	}
	
	public Application() 
	{
		initialize();
	}
	
	private void initialize() 
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.getContentPane().setBackground(UIManager.getColor("Button.background"));
		frame.setBounds(90, 90, 350, 200);
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		inpVM = new JLabel("Enter Number of VM(s).");
		inpVM.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		inpVM.setForeground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		inpVM.setHorizontalAlignment(SwingConstants.CENTER);
		inpVM.setBounds(6, 144, 338, 28);
		frame.getContentPane().add(inpVM);
		inpVM.setVisible(false);
		
		inpCL = new JLabel("Enter Number of Cloudlet(s).");
		inpCL.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		inpCL.setForeground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		inpCL.setHorizontalAlignment(SwingConstants.CENTER);
		inpCL.setBounds(6, 144, 338, 28);
		frame.getContentPane().add(inpCL);
		inpCL.setVisible(false);
		
		inval = new JLabel("Invalid Input.");
		inval.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		inval.setForeground(UIManager.getColor("CheckBox.select"));
		inval.setHorizontalAlignment(SwingConstants.CENTER);
		inval.setBounds(6, 144, 338, 28);
		frame.getContentPane().add(inval);
		inval.setVisible(false);
		
		JLabel vmsLabel = new JLabel("Number Of VM(s) : ");
		vmsLabel.setBounds(53, 37, 129, 16);
		frame.getContentPane().add(vmsLabel);
		
		noVM = new JTextField();
		noVM.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) 
			{
				inpVM.setVisible(true);
				inval.setVisible(false);
				inpCL.setVisible(false);
			}
			
			public void focusLost(FocusEvent e) 
			{
				inpVM.setVisible(false);
				inval.setVisible(false);
				inpCL.setVisible(false);
			}
		});
		noVM.setBounds(180, 32, 130, 26);
		frame.getContentPane().add(noVM);
		noVM.setColumns(10);
		
		JLabel cloLabel = new JLabel("Number of Cloudlet(s) :  ");
		cloLabel.setBounds(18, 75, 164, 16);
		frame.getContentPane().add(cloLabel);
		
		noCL = new JTextField();
		noCL.addFocusListener(new FocusAdapter() {
			
			
			
			
			public void focusGained(FocusEvent e) 
			{
				inpCL.setVisible(true);
				inpVM.setVisible(false);
				inval.setVisible(false);
			}
			
			public void focusLost(FocusEvent e) 
			{
				inpCL.setVisible(false);
				inpVM.setVisible(false);
				inval.setVisible(false);
				
			}
		});
		noCL.setBounds(180, 70, 130, 26);
		frame.getContentPane().add(noCL);
		noCL.setColumns(10);
		
		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					nofVM = Integer.parseInt(noVM.getText());
					nofCL = Integer.parseInt(noCL.getText());
					new Application2().start(nofVM, nofCL);
					frame.dispose();
				}
				catch(Exception exc)
				{
					inval.setVisible(true);
				}
			}
		});
		submit.setBounds(119, 103, 117, 29);
		frame.getContentPane().add(submit);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(346, 48, 22, 48);
		frame.getContentPane().add(scrollBar);
	}
}