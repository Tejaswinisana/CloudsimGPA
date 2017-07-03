package project;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Application3 
{
	private JFrame frame;
	private int nVM,nCL;
	private JButton submit;
	private JLabel clL[] = new JLabel[100], inval;
	private JTextField mi[] = new JTextField[100];
	private int[] vmL = new int[100];

	public void start(int v, int c,int[] vmL) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				Application3 window = new Application3(v,c,vmL);
				window.frame.setVisible(true);
			}
		});
	}

	public Application3()
	{	
	}
	
	public Application3(int v,int c,int[] vL) 
	{
		nVM = v;
		nCL = c;
		for(int i=0;i<v;i++)
			vmL[i] = vL[i];
		
		initialize();
	}

	private void initialize() 
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(90,90,500,600);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel vmReqLabel = new JLabel("Enter Cloudlet MI");
		vmReqLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		vmReqLabel.setHorizontalAlignment(SwingConstants.CENTER);
		vmReqLabel.setBounds(6,6,488,42);
		frame.getContentPane().add(vmReqLabel);
		
		JPanel panel = new JPanel(); 
		panel.setBorder(null);
		GridLayout gl_panel = new GridLayout(0,2);
		gl_panel.setVgap(10);
		panel.setLayout(gl_panel);
		
		for(int i=0;i<nCL;i++)
		{
			clL[i] = new JLabel("Cloudlet "+(i+1)+": ");
			clL[i].setHorizontalAlignment(0);
			panel.add(clL[i]);
			
			mi[i] = new JTextField();
			//mi[i].setBounds(220, 61+(i*30), 130, 26);
			panel.add(mi[i]);
			mi[i].setColumns(10);
		}
		
		inval = new JLabel("Invalid Input");
		inval.setForeground(UIManager.getColor("CheckBox.select"));
		inval.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		inval.setHorizontalAlignment(SwingConstants.CENTER);
		inval.setBounds(6, 530, 488, 42);
		frame.getContentPane().add(inval);
		inval.setVisible(false);
		
		
		JScrollPane jsp = new JScrollPane(panel);
		jsp.setBounds(6, 50, 401, 431);
		frame.getContentPane().add(jsp);
		
		submit = new JButton("submit");
		submit.setBounds(193, 65+(nCL*30), 117, 29);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					int clList[] = new int[nCL];
					int vmList[] = new int[nVM];
					for(int i=0;i<nCL;i++)
						 clList[i] = Integer.parseInt(mi[i].getText());
					for(int i=0;i<nVM;i++)
						 vmList[i] = vmL[i];

					new Application4().start(nVM, nCL, vmList, clList);
					frame.dispose();
				}
				catch(Exception exc)
				{
					inval.setVisible(true);
				}
			}
		});
		submit.setBounds(146, 483, 117, 29);
		frame.getContentPane().add(submit);
	}
}