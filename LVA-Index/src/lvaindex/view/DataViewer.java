/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lvaindex.view;

/*
 * DataViewer.java
 *
 * Created on 13 maj 2005, 11:19
 */


import javax.swing.*;
import java.awt.*;

import lvaindex.vafile.BasicVAFile;
import lvaindex.vafile.ISpatialObject;
import lvaindex.view.DataViewer2;
import lvaindex.vafile.VAFile;

/**
 * 
 * @author pl
 */
public class DataViewer extends JFrame {

	//ArrayList< SpatialObject > objects = null;
        VAFile vaf;

	int pointsBefore = 0;

	int nDim = 2;

	static int a = 0;
        
        JPanel jp;
        
       // DataViewer2 njf = new DataViewer2();

	/** Creates new form DataViewer */
	public DataViewer( VAFile vaf ) {
		//mw = new JFrame();
                super();
		this.setSize(400, 300);
		this.setBackground(Color.WHITE);
                this.getContentPane().setBackground( Color.WHITE );

                //this.objects = objects;
                this.vaf = vaf;
                
                jp = new JPanel();
                
                JScrollPane jsp = new JScrollPane(  );
                jsp.setAutoscrolls( true );
                jsp.setEnabled( true );
                
                jsp.setPreferredSize(new Dimension(300, 250));
                jsp.setViewportBorder( BorderFactory.createLineBorder(Color.black));
                //njf.setVisible( true );
                
                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //jsp.setColumnHeaderView(columnView);
        //jsp.setRowHeaderView(rowView);
                
                jsp.setViewportView( jp );
                //jsp.add( jp );
                
                this.getContentPane().add( jsp );
                
                pack();
	}
        
        public Component getPane()
        {
            return jp; 
        }

	/**
	 * @param args
	 *            the command line arguments
	 */
	/*
	 * public static void main(String args[]) {
	 * java.awt.EventQueue.invokeLater(new Runnable() { public void run() { new
	 * DataViewer().setVisible(true); } }); }
	 */


	// End of variables declaration                   

	public void paint(Graphics g) {
		// System.out.println(a++);
		super.paint(g);
                
                Graphics jpg = getPane().getGraphics();
                
               //Graphics jpg2 = njf.getPanel().getGraphics();
                
		paintData(jpg);
                paintCellBorers(jpg);
                
		//paintData(jpg2);
                //paintCellBorers(jpg2);
                
	}

	public void repaint() {
		super.repaint();
	}

	public void paintData(Graphics g) {
		for (int i = 0; i < vaf.getSpatialObjects().size(); i++) {
			paintPoint(g, i);
		}
	}

	public void paintPoint(Graphics g, int i) {
//		ISpatialObject point = ((ArrayList<BasicVAFile>) vaf.getSpatialObjects()).get(i);
//		int yoff = 25;
//		int xoff = 3;
//
//                System.out.println("DataViewer.paintPoint(Graphics g, int i)");
//                System.exit( -1 );
		/*g.setColor(getColor(point.getValue()));
                
                Integer[] m_pCoords = point.getCoordinates();
		if (point.getValue() != 0) {
			g.drawLine((int) m_pCoords[0] - 1,
					(int) m_pCoords[1] - 1, (int) m_pCoords[0] + 1,
					(int) m_pCoords[1] + 1);
			g.drawLine((int) m_pCoords[0] + 1,
					(int) m_pCoords[1] - 1, (int) m_pCoords[0] - 1,
					(int) m_pCoords[1] + 1);

		} else {
			g.drawLine((int) m_pCoords[0] - 1,
					(int) m_pCoords[1] - 1, (int) m_pCoords[0] + 1,
					(int) m_pCoords[1] + 1);
			g.drawLine((int) m_pCoords[0] + 1,
					(int) m_pCoords[1] - 1, (int) m_pCoords[0] - 1,
					(int) m_pCoords[1] + 1);
		}*/
	}

        /**
         * 
         * @param g
         */
        public void paintCellBorers( Graphics g)
        {
            // we assume that the number of dimensions equals 2
               
            {
                // dimension Y
                // number of bits per dimension i
                int dim = 1;
                int bits = vaf.bits[ dim ];
                // number of cells per dimension i
                long cellCount = ( long ) Math.pow( 2, bits );
                // size of each cell
                long cellSize = (long) ( vaf.maxValues[ dim ] - vaf.minValues[ dim ] ) / cellCount; 

                int x =  (int) cellSize;
                int y = 0;

                for ( int j = 0; j < cellCount + 1; j++ )
                {
                    g.drawLine(
                            0, x,
                            (int) ( ( cellCount + 1) * cellSize), x );
                    x += cellSize;
                }
            }

            {  
                // dimension X
                int dim = 0;
                // number of bits per dimension i
                int bits = vaf.bits[ dim ];
                // number of cells per dimension i
                long cellCount = ( long ) Math.pow( 2, bits );
                // size of each cell
                long cellSize = (long) ( vaf.maxValues[ dim ] - vaf.minValues[ dim ] ) / cellCount; 

                int x = 0;
                int y = (int )cellSize;

                for ( int j = 0; j < cellCount + 1; j++ )
                {
                    g.drawLine(  x, 0, x, (int) ((cellCount +1 )* cellSize) );
                    x += cellSize;
                }
            }

        }

	public Color getColor(int c) {

		switch (c) {
		case -1:
			return Color.lightGray; // unclassified
		case 0:
			return Color.lightGray; // noise
		}

		switch (c % 10) {
		case 0:
			return Color.pink;
		// return new Color(Color.pink.getRGB(), Color.pink.getGreen(),
		// Color.pink.getBlue(), a);
		case 1:
			return Color.red;
		// return new Color(Color.red.getRGB(), Color.red.getGreen(),
		// Color.red.getBlue(), a);
		case 2:
			return Color.cyan;
		// return new Color(Color.cyan.getRGB(), Color.cyan.getGreen(),
		// Color.cyan.getBlue(), a);
		case 3:
			return Color.blue;
		// return new Color(Color.blue.getRGB(), Color.blue.getGreen(),
		// Color.blue.getBlue(), a);
		case 4:
			return Color.darkGray;
		// return new Color(Color.darkGray.getRGB(), Color.darkGray.getGreen(),
		// Color.darkGray.getBlue(), a);
		case 5:
			return Color.green;
		// return new Color(Color.green.getRGB(), Color.green.getGreen(),
		// Color.green.getBlue(), a);
		case 6:
			return Color.yellow;
		// return new Color(Color.yellow.getRGB(), Color.yellow.getGreen(),
		// Color.yellow.getBlue(), a);
		case 7:
			return Color.magenta;
		// return new Color(Color.magenta.getRGB(), Color.magenta.getGreen(),
		// Color.magenta.getBlue(), a);
		case 8:
			return Color.orange;
		// return new Color(Color.orange.getRGB(), Color.orange.getGreen(),
		// Color.orange.getBlue(), a);
		case 9:
			return Color.pink;
		// return new Color(Color.pink.getRGB(), Color.pink.getGreen(),
		// Color.pink.getBlue(), a);
		// case 10:
		// return Color.yellow;

		default:
			return Color.black;
		}
	}
        

}