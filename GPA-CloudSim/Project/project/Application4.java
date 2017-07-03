package project;

import java.util.*;
import java.util.List;
import java.text.DecimalFormat;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.*;
import org.jfree.ui.RefineryUtilities;

public class Application4 
{
	private JFrame frame;
	private JLabel cid[] = new JLabel[100];
	private JLabel fcfsN[] = new JLabel[100], fcfsG[] = new JLabel[100];
	private JLabel rrN[] = new JLabel[100];
	
	private static int vm,cl;
	private static int vmiL[] = new int[100], cmiL[] = new int[100];
	
	private static String fcNTime[] = new String[100], fcGTime[] = new String[100];
	private static String rrNTime[] = new String[100];
	
	private static double fcNTi[] = new double[100], fcGTi[] = new double[100];
	private static double rrNTi[] = new double[100];
	
	private static String fcNTA, fcGTA, rrNTA;
	
	private static List<Cloudlet> cloudletList;
	private static List<Vm> vmlist;
	
	private static List<Vm> createVM_TS(int userId, int vms) 
	{
		LinkedList<Vm> list = new LinkedList<Vm>();

		long size = 10000; 
		int ram = 512; 
		long bw = 1000;
		int pesNumber = 1; 
		String vmm = "Xen";

		Vm[] vm = new Vm[vms];
		
		for(int i=0;i<vms;i++)
		{
			vm[i] = new Vm(i, userId, vmiL[i], pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			list.add(vm[i]);
		}
		
		return list;
	}

	private static List<Vm> createVM_SS(int userId, int vms) 
	{
		LinkedList<Vm> list = new LinkedList<Vm>();

		long size = 10000; 
		int ram = 512; 
		long bw = 1000;
		int pesNumber = 1; 
		String vmm = "Xen";

		Vm[] vm = new Vm[vms];
		
		for(int i=0;i<vms;i++)
		{
			vm[i] = new Vm(i, userId, vmiL[i], pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
			list.add(vm[i]);
		}
		
		return list;
	}

	private static List<Cloudlet> createCloudlet(int userId, int cloudlets)
	{
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

		long fileSize = 300;
		long outputSize = 300;
		int pesNumber = 1;
		UtilizationModel utilizationModel = new UtilizationModelFull();

		Cloudlet[] cloudlet = new Cloudlet[cloudlets];

		for(int i = 0;i<cloudlets;i++)
		{
			cloudlet[i] = new Cloudlet(i, cmiL[i], pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
		}
		
		return list;
	}
	
	@SuppressWarnings("unused")
	public static void Normal_TS() throws Exception
	{
		Cloudlet cloudlet;
		int total = 0;
		
		int num_user = 1;   
		Calendar calendar = Calendar.getInstance();
		boolean trace_flag = false;  
	
		CloudSim.init(num_user, calendar, trace_flag);

		Datacenter datacenter0 = createDatacenter("Datacenter_0");
		Datacenter datacenter1 = createDatacenter("Datacenter_1");
		Datacenter datacenter2 = createDatacenter("Datacenter_2");
		Datacenter datacenter3 = createDatacenter("Datacenter_3");
			
		DatacenterBroker broker = createBroker();
		int brokerId = broker.getId();
		
		vmlist = createVM_TS(brokerId, vm); 
		cloudletList = createCloudlet(brokerId,cl);

		broker.submitVmList(vmlist);
		broker.submitCloudletList(cloudletList);

		CloudSim.startSimulation();

		List<Cloudlet> newList = broker.getCloudletReceivedList();

		CloudSim.stopSimulation();
		
		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < cl; i++) 
		{
			cloudlet = newList.get(i);
			int id = cloudlet.getCloudletId()+1;
			total += cloudlet.getActualCPUTime();
			rrNTime[id-1] = dft.format(rrNTi[id-1] = cloudlet.getActualCPUTime()); 
		}
		
		rrNTA = dft.format(total/cl);
	}
	
	@SuppressWarnings("unused")
	public static void Normal_SS() throws Exception
	{
		Cloudlet cloudlet;
		int total = 0;
		
		int num_user = 1;   
		Calendar calendar = Calendar.getInstance();
		boolean trace_flag = false;  
	
		CloudSim.init(num_user, calendar, trace_flag);

		Datacenter datacenter0 = createDatacenter("Datacenter_0");
		Datacenter datacenter1 = createDatacenter("Datacenter_1");
		Datacenter datacenter2 = createDatacenter("Datacenter_2");
		Datacenter datacenter3 = createDatacenter("Datacenter_3");
			
		DatacenterBroker broker = createBroker();
		int brokerId = broker.getId();
	
		vmlist = createVM_SS(brokerId, vm); 
		cloudletList = createCloudlet(brokerId,cl);

		broker.submitVmList(vmlist);
		broker.submitCloudletList(cloudletList);

		CloudSim.startSimulation();

		List<Cloudlet> newList = broker.getCloudletReceivedList();

		CloudSim.stopSimulation();
		
		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < cl; i++) 
		{
			cloudlet = newList.get(i);
			int id = cloudlet.getCloudletId()+1;
			total += cloudlet.getActualCPUTime();
			fcNTime[id-1] = dft.format(fcNTi[id-1] = cloudlet.getActualCPUTime()); 
		}

		fcNTA = dft.format(total/cl);
	}
	
	@SuppressWarnings("unused")
	public static void GPA_SS() throws Exception
	{
		Cloudlet cloudlet;
		int total = 0;
		
		int num_user = 1;   
		Calendar calendar = Calendar.getInstance();
		boolean trace_flag = false;  
	
		CloudSim.init(num_user, calendar, trace_flag);

		Datacenter datacenter0 = createDatacenter("Datacenter_0");
		Datacenter datacenter1 = createDatacenter("Datacenter_1");
		Datacenter datacenter2 = createDatacenter("Datacenter_2");
		Datacenter datacenter3 = createDatacenter("Datacenter_3");
			
		CustomDatacenterBroker broker = createBrokerCustom();
		int brokerId = broker.getId();

			
		vmlist = createVM_SS(brokerId, vm); 
		cloudletList = createCloudlet(brokerId,cl);

		broker.submitVmList(vmlist);
		broker.submitCloudletList(cloudletList);

		CloudSim.startSimulation();

		List<Cloudlet> newList = broker.getCloudletReceivedList();

		CloudSim.stopSimulation();
		
		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < cl; i++) 
		{
			cloudlet = newList.get(i);
			int id = cloudlet.getCloudletId()+1;
			total += cloudlet.getActualCPUTime();
			fcGTime[id-1] = dft.format(fcGTi[id-1] = cloudlet.getActualCPUTime()); 
		}

		fcGTA = dft.format(total/cl);
	}
	
	private static CustomDatacenterBroker createBrokerCustom() throws Exception
	{
		return new CustomDatacenterBroker("Broker");	
	}

	private static DatacenterBroker createBroker() throws Exception
	{
		return new DatacenterBroker("Broker");
	}

	private static Datacenter createDatacenter(String name) throws Exception 
	{
		List<Host> hostList = new ArrayList<Host>();
		
		int mips = 4000;
		
		List<Pe> peList1 = new ArrayList<Pe>();
		peList1.add(new Pe(0, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(2, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(3, new PeProvisionerSimple(mips)));

		List<Pe> peList2 = new ArrayList<Pe>();
		peList2.add(new Pe(0, new PeProvisionerSimple(mips)));
		peList2.add(new Pe(1, new PeProvisionerSimple(mips)));

		int hostId=0;
		int ram = 2048; 
		long storage = 1000000;
		int bw = 10000;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList1,
    				new VmSchedulerTimeShared(peList1)
    			)
    		);
		
		hostId++;
		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList2,
    				new VmSchedulerTimeShared(peList2)
    			)
    		); 

		String arch = "x86";      
		String os = "Linux";      
		String vmm = "Xen";
		double time_zone = 10.0;  
		double cost = 3.0;        
		double costPerMem = 0.05;
		double costPerStorage = 0.1;
		double costPerBw = 0.1;		
		LinkedList<Storage> storageList = new LinkedList<Storage>();

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);

		return new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
	}
	
	public void start(int v, int c, int[] vL, int[] cL)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try 
				{
					Application4 window = new Application4(v,c,vL,cL);
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

public Application4() 
	{
	} 
	
	public Application4(int v, int c, int[] vL, int[] cL) throws Exception 
	{
		vm = v;
		cl = c;
		
		for(int i=0;i<v;i++)
			vmiL[i] = vL[i];
		
		for(int i=0;i<c;i++)
			cmiL[i] = cL[i];
		
		Normal_SS();
		GPA_SS();
		Normal_TS();
		
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(300, 300, 950, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel title = new JLabel("Output");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		title.setForeground(UIManager.getColor("RadioButton.darkShadow"));
		title.setBounds(6, 17, 938, 36);
		frame.getContentPane().add(title);
		
		JLabel cidLabel = new JLabel("Cloudlet ID");
		cidLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cidLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		cidLabel.setBounds(78, 96, 156, 36);
		frame.getContentPane().add(cidLabel);
		
		JLabel fcfsLabel = new JLabel("First Come First Serve");
		fcfsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fcfsLabel.setForeground(UIManager.getColor("RadioButton.darkShadow"));
		fcfsLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 22));
		fcfsLabel.setBounds(268, 96, 268, 36);
		frame.getContentPane().add(fcfsLabel);
		
		JLabel rrLabel = new JLabel("Round Robin");
		rrLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rrLabel.setForeground(UIManager.getColor("RadioButton.darkShadow"));
		rrLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 22));
		rrLabel.setBounds(532, 96, 268, 36);
		frame.getContentPane().add(rrLabel);
		
		JLabel gpaLabel = new JLabel("GPA");
		gpaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gpaLabel.setForeground(UIManager.getColor("RadioButton.darkShadow"));
		gpaLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 22));
		gpaLabel.setBounds(707, 96, 268, 36);
		frame.getContentPane().add(gpaLabel);
		
		for(int i=0;i<cl;i++)
		{
			cid[i] = new JLabel(Integer.toString(i+1));
			cid[i].setHorizontalAlignment(SwingConstants.CENTER);
			cid[i].setBounds(123, 144+(i*30), 61, 16);
			frame.getContentPane().add(cid[i]);
		
			fcfsN[i] = new JLabel(fcNTime[i]);
			fcfsN[i].setHorizontalAlignment(SwingConstants.CENTER);
			fcfsN[i].setBounds(369, 144+(i*30), 61, 16);
			frame.getContentPane().add(fcfsN[i]);
		
			rrN[i] = new JLabel(rrNTime[i]);
			rrN[i].setHorizontalAlignment(SwingConstants.CENTER);
			rrN[i].setBounds(634, 144+(i*30), 61, 16);
			frame.getContentPane().add(rrN[i]);
			
			fcfsG[i] = new JLabel(fcGTime[i]);
			fcfsG[i].setHorizontalAlignment(SwingConstants.CENTER);
			fcfsG[i].setBounds(818, 144+(i*30), 61, 16);
			frame.getContentPane().add(fcfsG[i]);
		}
		
		JLabel avg = new JLabel("Average");
		avg.setForeground(UIManager.getColor("InternalFrame.borderShadow"));
		avg.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
		avg.setHorizontalAlignment(SwingConstants.CENTER);
		avg.setBounds(104, 144+(30*cl), 102, 30);
		frame.getContentPane().add(avg);
	
		JLabel fcNAVG = new JLabel(fcNTA);
		fcNAVG.setForeground(UIManager.getColor("InternalFrame.borderShadow"));
		fcNAVG.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
		fcNAVG.setHorizontalAlignment(SwingConstants.CENTER);
		fcNAVG.setBounds(369, 144+(30*cl), 61, 30);
		frame.getContentPane().add(fcNAVG);
	
		JLabel rrNAVG = new JLabel(rrNTA);
		rrNAVG.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
		rrNAVG.setForeground(UIManager.getColor("InternalFrame.borderShadow"));
		rrNAVG.setHorizontalAlignment(SwingConstants.CENTER);
		rrNAVG.setBounds(634, 144+(30*cl), 61, 30);
		frame.getContentPane().add(rrNAVG);
		
		JLabel fcGAVG = new JLabel(fcGTA);
		fcGAVG.setForeground(UIManager.getColor("InternalFrame.borderShadow"));
		fcGAVG.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
		fcGAVG.setHorizontalAlignment(SwingConstants.CENTER);
		fcGAVG.setBounds(818, 144+(30*cl), 61, 30);
		frame.getContentPane().add(fcGAVG);
		JLabel inval = new JLabel("Invalid Input");
		inval.setForeground(UIManager.getColor("CheckBox.select"));
		inval.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		inval.setHorizontalAlignment(SwingConstants.CENTER);
		inval.setBounds(6, 530, 488, 42);
		frame.getContentPane().add(inval);
		inval.setVisible(false);
		
	
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		exit.setBounds(377, 300+20*cl, 123, 29);
		frame.getContentPane().add(exit);
		
		JButton restart = new JButton("Restart");
		restart.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) 
			{
				new Application().start();
				frame.dispose();
			}
		});
		restart.setBounds(550, 300+20*cl, 133, 29);
		frame.getContentPane().add(restart);
		
		JButton btnGenerateGraph = new JButton("Line Graph");
		btnGenerateGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LineGraph().start(fcNTi,fcGTi,rrNTi,cl);	
			}
		});
		btnGenerateGraph.setBounds(66, 300+20*cl, 133, 29);
		frame.getContentPane().add(btnGenerateGraph);
		JButton Bargraph = new JButton("BARGRAPH");
		Bargraph.setBounds(250, 300+20*cl, 117, 29);
		Bargraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					BarChart_AWT chart = new BarChart_AWT("ALGORITHM", "",Integer.parseInt(fcNAVG.getText()),Integer.parseInt(rrNAVG.getText()),Integer.parseInt(fcGAVG.getText()) );
				      chart.pack( );        
				      RefineryUtilities.centerFrameOnScreen( chart );        
				      chart.setVisible( true );
					

					
				}
				catch(Exception exc)
				{
					inval.setVisible(true);
				}
			}
		});
		frame.getContentPane().add(Bargraph);
	}
}