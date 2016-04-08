package SAnnealing;



import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;

import javax.swing.*;
 
public class GraphingData extends JPanel {
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Vector<Integer> data =new Vector<Integer>();
	    final int PAD = 20;
	    
	    public void add_point(double x)
	    {
	    	Double d  = new Double(x);
	    	data.addElement(d.intValue());
	    
	    }
	    
	    public void draw_graph()
	    {
	  
	    	System.out.println("GRAPH = " + data.size());
	    	JFrame f = new JFrame();
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.add(new GraphingData());
	        f.setSize(800,400);
	        f.setLocation(200,200);
	        f.setVisible(true);
	    }
	 
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        int w = getWidth();
	        int h = getHeight();
	        // Draw ordinate.
	        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
	        // Draw abcissa.
	        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
	        double xInc = (double)(w - 2*PAD)/(data.size()-1);
	        double scale = (double)(h - 2*PAD)/getMax();
	        // Mark data points.
	        g2.setPaint(Color.red);
	        for(int i = 0; i < data.size(); i++) {
	            double x = PAD + i*xInc;
	            double y = h - PAD - scale*data.get(i);
	            g2.fill(new Ellipse2D.Double(x+5, y+5, 1, 1));
	        }
	    }
	 
	    private int getMax() {
	        int max = -Integer.MAX_VALUE;
	        for(int i = 0; i < data.size(); i++) {
	            if(data.get(i) > max)
	                max = data.get(i);
	        }
	        return max;
	    }
}