package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Application2 
{
	private JFrame frame;
	private int nVM,nCL;
	private JButton submit;
	private JLabel vmL[] = new JLabel[100], inval;
	private JTextField mipsL[] = new JTextField[100];
	private JLabel lblNewLabel;
	public void start(int v,int c) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				Application2 window = new Application2(v,c);
				window.frame.setVisible(true);
				
			}
		});
	}

	public Application2()
	{	
	}  
	
	public Application2(int v,int c) 
	{
		nVM = v;
		nCL = c;
		initialize();
	}

	private void initialize() 
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(90,90,500,600);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //frame.setSize(1000,1000);
	    frame.getContentPane().setLayout(null);
		//frame.setVisible(true);
	   
		
		
		JLabel vmReqLabel = new JLabel("Enter VM MIPS");
		vmReqLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		vmReqLabel.setHorizontalAlignment(SwingConstants.CENTER);
		vmReqLabel.setBounds(6,6,488,42);
		frame.getContentPane().add(vmReqLabel);
		
		for(int i=0;i<nVM;i++)
		{
			vmL[i] = new JLabel("VM "+(i+1)+": ");
			vmL[i].setBounds(147, 66+(i*30), 61, 16);
			frame.getContentPane().add(vmL[i]);
			
			mipsL[i] = new JTextField();
			mipsL[i].setBounds(220, 61+(i*30), 130, 26);
			frame.getContentPane().add(mipsL[i]);
			mipsL[i].setColumns(10);
			
			
		}
		//int cons=nVM*30;
		
		inval = new JLabel("Invalid Input");
		inval.setForeground(UIManager.getColor("CheckBox.select"));
		inval.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		inval.setHorizontalAlignment(SwingConstants.CENTER);
		inval.setBounds(6, 530, 488, 42);
		frame.getContentPane().add(inval);
		inval.setVisible(false);
		
		submit = new JButton("submit");
		submit.setBounds(193, 65+(nVM*30), 117, 29);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int min = Integer.MAX_VALUE;
				try
				{
					int vmList[] = new int[nVM];
					for(int i=0;i<nVM;i++)
					{
						 vmList[i] = Integer.parseInt(mipsL[i].getText());
						 if(vmList[i]<min)
							 min = vmList[i];
						 if(vmList[i]<=0)
							 throw new Exception();
					}
					
					if(min>=4000)
						throw new Exception();
			
					new Application3().start(nVM, nCL, vmList);
					frame.dispose();
				}
				catch(Exception exc)
				{
					lblNewLabel.setVisible(false);
					inval.setVisible(true);
					
					if(min>=4000)
					{
						inval.setVisible(false);
						lblNewLabel.setVisible(true);
					}
					
				}
			}
		});
		frame.getContentPane().add(submit);
		
		lblNewLabel = new JLabel("Invalid Input: Atleast one VM should have less than 4000 MIPS");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 548, 488, 16);
		lblNewLabel.setVisible(false);
		lblNewLabel.setForeground(Color.red);
		frame.getContentPane().add(lblNewLabel);
		
		
		
		
		
		
		
	}
}
