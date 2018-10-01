import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Display extends JFrame 
implements KeyListener, ActionListener {

	//used for inputting coefficient A
	private JLabel labelA;
	private JTextField textA;
	//used for inputting first initial value
	private JLabel labelInit1;
	private JTextField textInit1;
	//used for inputting second initial value
	private JLabel labelInit2;
	private JTextField textInit2;
	//panel that holds all input text fields
	private JPanel header;
	//table to show data
	private JTable table;
	//Column names for table
	String[] columnNames = {"Time", "Iteration 1", "Iteration 2", "Difference"};
	//Scrollpane to scroll through table
	private JScrollPane tableScroll;
	//Scrollpane to scroll through graphs
	private JScrollPane graphScroll;
	//panel on which graphs are drawn
	private JPanel drawPanel;
	//graph on which data values for both iterations are plotted for up to t=50
	private Graph graph1;
	//graph on which data values for both iterations are plotted
	private Graph graph2;
	//graph on which phase diagram for iteration 1 is plotted
	private Graph graph3;
	
	public static void main(String[] args)
	{
		//start GUI on event dispatcher thread
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new Display();
			}
		});
	}
	
	public Display()
	{
		//initialize properties of JFrame
		setTitle("Text");
		setMinimumSize(new Dimension(800, 800));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
		//create header panel and add to top of frame
		header = new JPanel(new FlowLayout());
		add(header, BorderLayout.NORTH);
		
		//create and add all input labels and fields into header
		labelA = new JLabel("Parameter A: ");
		header.add(labelA);
		
		textA = new JTextField(6);
		textA.setText("2"); //default value for parameter A
		header.add(textA);
		//add actionListener to this and other text input fields
		textA.addActionListener(this);
		
		labelInit1 = new JLabel("1st Initial value: ");
		header.add(labelInit1);
		
		textInit1 = new JTextField(6);
		textInit1.setText("0.7"); //default value for initial value 1
		header.add(textInit1);
		textInit1.addActionListener(this);
		
		labelInit2 = new JLabel("2nd Initial value: ");
		header.add(labelInit2);
		
		textInit2 = new JTextField(6);
		textInit2.setText("0.6999999"); //default value for initial value 2
		header.add(textInit2);
		textInit2.addActionListener(this);

		//initialize table scrollPane
		tableScroll = new JScrollPane();
		add(tableScroll, BorderLayout.CENTER);
		
		//initialize graph scrollPane
		graphScroll = new JScrollPane();
		add(graphScroll, BorderLayout.EAST);
		
		//initialize drawing panel
		drawPanel = new JPanel(new FlowLayout());
		drawPanel.setPreferredSize(new Dimension(800, 1700));
		drawPanel.setBackground(Color.GRAY);
		graphScroll.setViewportView(drawPanel);
		
		//label for graph 1
		JPanel label1Panel = new JPanel(new FlowLayout());
		JLabel label1 = new JLabel("Time Series: X(n) vs. n, starting at initial value #1 and initial value #2");
		label1Panel.add(label1);
		label1Panel.setBackground(Color.WHITE);
		drawPanel.add(label1Panel);
		//create graph 1
		graph1 = new Graph();
		drawPanel.add(graph1);
		//label for graph 2
		JPanel label2Panel = new JPanel(new FlowLayout());
		JLabel label2 = new JLabel("Time Series: X(n) vs. n, long time for ic = #1");
		label2Panel.add(label2);
		label2Panel.setBackground(Color.WHITE);
		drawPanel.add(label2Panel);		
		//create graph 2
		graph2 = new Graph();
		drawPanel.add(graph2);
		//label for graph 3
		JPanel label3Panel = new JPanel(new FlowLayout());
		JLabel label3 = new JLabel("PHASE SPACE: X(n+1) vs. X(n)");
		label3Panel.add(label3);
		label3Panel.setBackground(Color.WHITE);
		drawPanel.add(label3Panel);
		//create graph 3
		graph3 = new Graph();
		drawPanel.add(graph3);
		
		//call calculate once initially to set up the table and graphs
		calculate();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//recalculates table values and displays them
	void calculate()
	{
		double a = Double.parseDouble(textA.getText());
		double initial1 = Double.parseDouble(textInit1.getText());
		double initial2 = Double.parseDouble(textInit2.getText());
		//get results of equation for both initial values
		Double[][] nums = RandomNumbers.psuedoRandomNumbers(a, initial1, initial2);
		
		//create an array to hold the values of iteration 1
		Double[] iter1 = new Double[nums.length];
		
		for (int i = 0; i < nums.length; i++)
		{
			iter1[i] = nums[i][1];
		}
		
		table = new JTable(nums, columnNames);
		tableScroll.setViewportView(table);
		
		//update graph1's data values
		graph1.setLine(true);
		graph1.setData(nums);
		//update graph2's data values
		graph2.setData(nums);
		//update graph3's data values, and set it as phase diagram
		graph3.setPhase(true);
		graph3.setData(RandomNumbers.phaseDiagram(iter1));
	}
	//ActionListener Event Handler
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//if all text fields have valid double inputs, proceed to calculate equation
		if (RandomNumbers.checkDouble(textA.getText()) 
				&& RandomNumbers.checkDouble(textInit1.getText())
				&& RandomNumbers.checkDouble(textInit2.getText()))
		{
			calculate();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}

