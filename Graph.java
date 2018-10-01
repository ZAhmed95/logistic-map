import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.BorderFactory;

public class Graph extends JPanel {
	
	protected Double[][] data; 
	//data points to plot, data[][0] contains the independent variable (x-axis)
	//and every other column contains dependent variables (y-axis)
	private int xOffset = 0; //horizontal offset value: distance from graph's bottom left corner to where plot starts
	private int yOffset = 0; //same as above, but vertical
	private int plotWidth; //width of plot
	private int plotHeight; //height of plot
	private boolean phase = false; //is this graph drawing a phase diagram
	private boolean line = false; //is this graph drawing a line graph
	
	public Graph()
	{		
		data = new Double[0][0];
		setPreferredSize(new Dimension(700,500));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.black));
		plotWidth = getPreferredSize().width - xOffset;
		plotHeight = getPreferredSize().height - yOffset;
	}
	
	public Graph(Double[][] data)
	{
		this.data = data;
		
		setPreferredSize(new Dimension(500,500));
		setBackground(Color.WHITE);
		plotWidth = getPreferredSize().width - xOffset;
		plotHeight = getPreferredSize().height - yOffset;
	}
	
	//sets phaseDiagram boolean
	public void setPhase(boolean phase)
	{
		this.phase = phase;
	}
	
	public void setLine(boolean line)
	{
		this.line = line;
	}
	
	public void setData(Double[][] data)
	{
		this.data = data;
		
		drawGraph();
	}
	
	void drawGraph()
	{
		//redraw plot
		repaint(xOffset, yOffset, plotWidth, plotHeight);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(phase) phaseDiagram(g);
		else Plot(g);
	}
	
	void Plot(Graphics g)
	{
		int bound = line ? 50 : data.length;
		int start = line ? 0 : 50;
		double upperBound = maximum(data, false, bound) * 1.1;
		double lowerBound = minimum(data, false, bound);
		lowerBound -= Math.abs(lowerBound) * 0.1;
		double range = upperBound - lowerBound;
		double domain = maximum(data, true, bound);
		
		int lastXPos = -1;
		int lastYPos = -1;
		
		int dataSets = line ? 2 : 1;
		for (int j = 1; j <= dataSets; j++)
		{
			if (j == 1) g.setColor(Color.BLUE); //pink for iteration 1
			else g.setColor(Color.MAGENTA); //cyan for iteration 2
			//plot points for both iterations
			for (int i = start; i < bound; i++)
			{
				//transform x-coordinate of point to graph's pixel coordinates
				int xPos = (int)(data[i][0] / domain * plotWidth) + xOffset;
				//do the same for y-coodinate
				int yPos = plotHeight - (int)((data[i][j] - lowerBound) / range * plotHeight);
				//plot point
				g.fillRect(xPos, yPos, 4, 4);
				if (line && i > 0)
				{
					g.drawLine(lastXPos, lastYPos, xPos, yPos);
				}
				lastXPos = xPos;
				lastYPos = yPos;
			}
		}
	}
	
	void phaseDiagram(Graphics g)
	{
		double upperBound = maximum(data, false, data.length) * 1.1;
		double lowerBound = minimum(data, false, data.length);
		double range = upperBound - lowerBound;
		double domain = maximum(data, true, data.length);
		
		for (int i = 0; i < data.length; i++)
		{
			//scale c-coordinate of point to graph's pixel coordinates
			int xPos = (int)(data[i][0] / domain * plotWidth) + xOffset;
			//do the same for y-coodinate
			int yPos = plotHeight - (int)((data[i][1] - lowerBound) / range * plotHeight) - yOffset;
			//plot point
			g.setColor(Color.CYAN);
			g.fillRect(xPos, yPos, 4, 4);
			g.setColor(Color.BLACK);
			g.drawRect(xPos, yPos, 4, 4);
		}	
	}
	
	/*
	 * arguments:
	 * data: array in which to find maximum
	 * domain: if true, function will only check first column (data[i][0])
	 * bound: up to how much of array should be checked
	 */
	Double maximum(Double[][] data, boolean domain, int bound)
	{
		Double max;
		int toPlot = (data[0].length > 3) ? 3 : data[0].length;
		if (domain) 
		{
			max = data[0][0];
			for (int i = 0; i < bound; i++)
			{
				if (data[i][0] > max) max = data[i][0];
			}
		}
		else 
		{
			max = data[0][1];
			
			for (int j = 1; j < toPlot; j++)
			{
				for (int i = 0; i < bound; i++)
				{
					if (data[i][j] > max) max = data[i][j];
				}
			}
		}
		return max;
	}
	
	Double minimum(Double[][] data, boolean domain, int bound)
	{
		Double min;
		int toPlot = (data[0].length > 3) ? 3 : data[0].length;
		if (domain) 
		{
			min = data[0][0];
			for (int i = 0; i < bound; i++)
			{
				if (data[i][0] < min) min = data[i][0];
			}
		}
		else 
		{
			min = data[0][1];
			
			for (int j = 1; j < toPlot; j++)
			{
				for (int i = 0; i < bound; i++)
				{
					if (data[i][j] < min) min = data[i][j];
				}
			}
		}
		return min;
	}
}
