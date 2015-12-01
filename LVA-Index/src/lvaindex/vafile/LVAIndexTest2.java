package lvaindex.vafile;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.rtf.RTFEditorKit;

import rtree.CustomRTree;

import junit.framework.TestCase;


public class LVAIndexTest2 /*extends TestCase*/ {
    
    public class Point
    {
        public int x;
        public int y;
        public Color c;
        
        public Point(int x, int y, Color c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }

    public class MyFrame extends JFrame
    {
        ArrayList<Point> list = new ArrayList<Point>();
        
        public void add(Point p) {
            list.add(p);
        }
        
        @Override
        public void paint(Graphics g)
        {
            for (Point p:list) {
                g.setColor(p.c);
                g.drawRect(p.x, p.y, 2, 2);
            }
        }
    }

    String b = "C:\\Documents and Settings\\piotr.lasek\\workspace\\Data\\";
    
    public ISpatialIndex createIndex(String name, int nDim, int[] bits, int nLayers, int k) {
        
        ISpatialIndex isp = null;
        
        if (name.equals(LVAIndex.NAME)) {
            isp = new LVAIndex(nDim, bits, nLayers);
        } else if (name.equals(LVAIndexDynamic.NAME)) {
            isp = new LVAIndexDynamic(nDim, bits, k);
        } else if (name.equals(VAFile.NAME)) {
            isp = new VAFile(nDim, bits);
        } else if (name.equals(CustomRTree.NAME)) {
            isp = new CustomRTree();
        }
        
        return isp;
        
    }
    
    public void singleTest(String name, int nDim, int bit, int nLayers, int k, String p, int limit) throws Exception
    {
            int[] bits = new int[nDim];
            for(int i = 0; i < nDim; i++) bits[i] = bit;
            
            ArrayList<ISpatialObject> dataset = TestUtil.readFile(p, limit);
            
            ISpatialIndex isp = createIndex(name, nDim, bits, nLayers, k);
            long indexCreationTime = System.currentTimeMillis();
            isp.add(dataset);
            indexCreationTime = System.currentTimeMillis() - indexCreationTime;
            
            // MyFrame mf = new MyFrame();
            
            int sum = 0;
            int min = 0;
            int max = 0;
            double avg = 0;
            
            long totalSearchTime = 0;
            // measurements
            for(int i = 0; i < dataset.size(); i++) {
                ISpatialObject o = dataset.get(i);
                
                long searchTime = System.currentTimeMillis();
                ArrayList<ISpatialObject> n = (ArrayList<ISpatialObject>) isp.getNeighbors(o, k);
                searchTime = System.currentTimeMillis() - searchTime;
                
                totalSearchTime += searchTime;
                
                //ArrayList<ISpatialObject> n = (ArrayList<ISpatialObject>) isp.getNeighbors(o, (double) 200);
                int size = n.size();                
                if (size > max) max = size;
                if (size < min || min == 0) min = size;
                sum += size;
                
                /*if ( n.size() < k ) {
                    mf.add(new Point((int)o.getCoordinates()[0], (int)o.getCoordinates()[1], Color.RED));
                } else {
                    mf.add(new Point((int)o.getCoordinates()[0], (int)o.getCoordinates()[1], Color.BLACK));
                }*/
            }

            avg = sum / dataset.size();

            String bitsPerDim = "-";
            if (!isp.getName().equals(CustomRTree.NAME)) {
                bitsPerDim = "" + bit;
            }
            
            //double avgSearchTime = (double) totalSearchTime / (double) dataset.size();
                
            System.out.println(isp.getName() + "\t" + limit + "\t" + nDim + "\t" + k + "\t" + bitsPerDim + "\t" + indexCreationTime + "\t" + min + "\t" + max + "\t" + avg + "\t" + totalSearchTime);
            
            //mf.setSize(600, 600);
            //mf.setVisible(true);
            
            //System.out.println( p + "" + "\t" + sum / dataset.size() );
    }
    
    public static void main(String[] args) {
        LVAIndexTest2 t2 = new LVAIndexTest2();
        try {
            CustomRTree r;
            //int dim = 2, bit = 5, n = 6, k = 20, limit = 500; String file = t2.b + "syntetic\\ND\\d-05-500.txt";
            
            boolean d = false;
            
            if (d)
            {
            int dim = 2, bit = 5, n = 7, k = 20, limit = 500; String file = t2.b + "syntetic\\2D\\syn_2d_1000.txt";
            t2.singleTest(CustomRTree.NAME,        dim, bit, n, k, file, limit);
            t2.singleTest(VAFile.NAME,             dim, bit, n, k, file, limit);
            t2.singleTest(LVAIndex.NAME,           dim, bit, n, k, file, limit);
            t2.singleTest(LVAIndexDynamic.NAME,    dim, bit, n, k, file, limit);
            }
            
            if (d)
            {
            int dim = 3, bit = 4, n = 4, k = 20, limit = 500; String file = t2.b + "syntetic\\3D\\syn_3d_1000.txt";
            t2.singleTest(CustomRTree.NAME,        dim, bit, n, k, file, limit);
            t2.singleTest(VAFile.NAME,             dim, bit, n, k, file, limit);
            t2.singleTest(LVAIndex.NAME,           dim, bit, n, k, file, limit);
            t2.singleTest(LVAIndexDynamic.NAME,    dim, bit, n, k, file, limit);
            }

            if (d)
            {
            int dim = 5, bit = 3, n = 4, k = 20, limit = 500; String file = t2.b + "syntetic\\ND\\d-05-500.txt";
            t2.singleTest(CustomRTree.NAME,        dim, bit, n, k, file, limit);
            t2.singleTest(VAFile.NAME,             dim, bit, n, k, file, limit);
            t2.singleTest(LVAIndex.NAME,           dim, bit, n, k, file, limit);
            t2.singleTest(LVAIndexDynamic.NAME,    dim, bit, n, k, file, limit);
            }
            
            //if (d)
            {
            int dim = 10, bit = 2, n = 3, k = 20, limit = 2; String file = t2.b + "syntetic\\ND\\d-10-500.txt";
            t2.singleTest(CustomRTree.NAME,        dim, bit, n, k, file, limit);
            t2.singleTest(VAFile.NAME,             dim, bit, n, k, file, limit);
            t2.singleTest(LVAIndex.NAME,           dim, bit, n, k, file, limit);
            t2.singleTest(LVAIndexDynamic.NAME,    dim, bit, n, k, file, limit);
            }
            
            
            
            //t2.singleTest(2, 4, 5, t2.b + "syntetic\\2D\\syn_2d_1000.txt", 500);
            //t2.singleTest(3, new int[]{5, 5, 5}, 5, t2.b + "syntetic\\3D\\syn_3d_1000.txt", 1000);
            //t2.singleTest(3, 4, 6, t2.b + "syntetic\\3D\\syn_3d_1000.txt", 500);
            //t2.singleTest(10, 5, 4, t2.b + "syntetic\\ND\\d-10-500.txt", 500);
            //t2.singleTest(15, 5, 4, t2.b + "syntetic\\ND\\d-15-500.txt", 500);
            //t2.singleTest(20, 5, 4, t2.b + "syntetic\\ND\\d-20-500.txt", 500);*/
            
            //t2.singleTest(2, new int[]{5, 5}, 3, t2.b + "syntetic\\2D\\syn_2d_1000.txt", 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

