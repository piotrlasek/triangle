package lvaindex.my;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JPanel;
import lvaindex.vafile.ISpatialObject;
import lvaindex.common.Utils;
import lvaindex.vafile.VAFile;
import lvaindex.vafile.LVAIndex;
import lvaindex.view.DataViewer2;

/**
 *
 * @author pls
 */
public class Main
{
    private static double max = 70;

	/**
     * Returns the position of the data point accordingly to the number of the
     * dimensions and its coordinates.
     * 
     * @param coordinates
     * @return the position of the n-dimensional object in the one dimensional 
     * cells vector
     */
    long getPosition(Integer[] coordinates)
    {
        long pos = 0;
        return pos;
    }

    /**
     *
     *
     * @param
     */
    static public void showGUI(final VAFile vaf2d) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                DataViewer2 dv = new DataViewer2(vaf2d) {

                    public JPanel getPanel() {
                        return jPanel1;
                    }
                };
                dv.setVisible(true);
            }
        });
    }

    /**
     *
     *
     * @param
     */
    static public void subtest1(String filePath, boolean print) throws Exception
    {
        int knn = 30;
        int dim = 2;

             // 2D
             VAFile vaf2d = new VAFile(dim, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
             LVAIndex lvaf2d = new LVAIndex(dim, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, 3);
             //VAFile vaf2d = new VAFile( dim, new int[]{1,1,2,2,2,1,2,2} );

             ArrayList< ISpatialObject > sos = Utils.readFile( filePath );     

             vaf2d.add( sos );

             int i = 0;
             int j = sos.size() / 500;

             Collection<ISpatialObject> nn2 = null;
             Collection<ISpatialObject> nn  = null;
             long nn2_s = System.currentTimeMillis();
             ISpatialObject tmp = null;
             for (ISpatialObject so:sos)
             {
                if (i % j == 0)
                {
                    nn2 = lvaf2d.getNeighbors(so, knn, max );
                    if (print)
                    {
                        System.out.println("LVA");
                        System.out.println(so + " - " + nn2.size() );
                        System.out.println(nn2);
                    }
                }
                i++;
             }
             long nn2_e = System.currentTimeMillis();
   
             i = 0;

             long nn_s = System.currentTimeMillis();
             for (ISpatialObject so:sos)
             {
                if (i % j == 0)
                {
                    nn  = vaf2d.getNeighbors(so, knn, max);
                    if (print)
                    {
                        System.out.println("VA");
                        System.out.println(so + " - " + nn.size() );
                        System.out.println(nn);
                    }
                }
                i++;
            }
             
             long nn_e = System.currentTimeMillis();
             
             System.out.print(
                sos.size() + "\t" + nn.size() + "\t" + nn2.size() + "\t" + 
                    (nn2_e - nn2_s) + "\t" + (nn_e - nn_s)  + "\n"
                );

            if (nn.size() != nn2.size())
                throw new Exception("Different sizes.");
    }

    /**
     *
     *
     * @param
     */
    static public void subtest2(VAFile vaf2d, LVAIndex lvaf2d, int count, boolean print) throws Exception
    {
        int knn = 5;

        Collection< ISpatialObject> sos = vaf2d.getSpatialObjects();
        int i = sos.size() / 10;

        Collection<ISpatialObject> nn2 = null;
        Collection<ISpatialObject> nn  = null;
        
        ISpatialObject tmp = null;

        long nn2_s = System.currentTimeMillis();
        
        for (int j = i; j < count; j++)
        {
            ISpatialObject so = ((ArrayList<ISpatialObject>)sos).get( j );
            nn2 = lvaf2d.getNeighbors(so, knn, max);
            if (print)
            {
                System.out.println("LVA");
                System.out.println(so + " - " + nn2.size() );
                System.out.println(nn2);
            }
        }
        long nn2_e = System.currentTimeMillis();

        i = 0;

        long nn_s = System.currentTimeMillis();

        for (int j = i; j < count; j++)
        {
            ISpatialObject so = ((ArrayList<ISpatialObject>) sos).get( j );
            nn  = vaf2d.getNeighbors(so, knn, max);
            if (print)
            {
                System.out.println("VA");
                System.out.println(so + " - " + nn.size() );
                System.out.println(nn);
            }
            i++;
        }

         long nn_e = System.currentTimeMillis();

         System.out.print(
            sos.size() + "\t" + /*nn.size()*/0 + "\t" + /*nn2.size()*/0 + "\t" + 
                (nn2_e - nn2_s) + "\t" + (nn_e - nn_s) + "\n"
            );

            /*if (nn.size() != nn2.size())
                throw new Exception("Different sizes.");*/
    }
    
    /**
     *
     *
     * @param
     */
    static public void subtest3(VAFile vaf2d, LVAIndex lvaf2d, int count, 
            int knn, double max, boolean print) throws Exception
    {
        ArrayList< ISpatialObject> sos = (ArrayList< ISpatialObject>)vaf2d.getSpatialObjects();

        ArrayList<ISpatialObject> nn2 = null;
        ArrayList<ISpatialObject> nn  = null;
        
        ISpatialObject tmp = null;
        
        long nn_s = System.currentTimeMillis();

        for (int j = 0; j < count; j++)
        {
            ISpatialObject so = sos.get( j );
            
            //vaf2d.SSA(knn, max, so);
            
            nn  = vaf2d.getNeighbors(so, knn, max);
            
            if (print)
            {
                System.out.println("VA");
                //System.out.println(so + " - " + nn.size() );
                //System.out.println(nn);
                vaf2d.printDist();
                System.out.println();
            }
        }
         long nn_e = System.currentTimeMillis();

        long nn2_s = System.currentTimeMillis();
        
        for (int j = 0; j < count; j++)
        {
            ISpatialObject so = sos.get( j );
            nn2 = lvaf2d.getNeighbors(so, knn, max);
            if (print)
            {
                System.out.println("LVA");
                //System.out.println(so + " - " + nn2.size() );
                //System.out.println(nn2);
                lvaf2d.printDist();
                System.out.println();
            }
        }
        long nn2_e = System.currentTimeMillis();
        
        System.out.print(
            sos.size() + "\t" + /*nn.size()*/0 + "\t" + /*nn2.size()*/0 + "\t" + 
                (nn2_e - nn2_s) + "\t" + (nn_e - nn_s) + "\n"
            );

            /*if (nn.size() != nn2.size())
                throw new Exception("Different sizes.");*/
    }
    
    /**
     *
     *
     *
     */
    static public void test2()
    {
        String rootDir =
            //"data\\ex01\\";
            "data\\syntetic\\2D\\";

        int dim = 2;
        // 2D
        System.out.println("Creating VAFile.");
        VAFile vaf2d1 = new VAFile(dim, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
        VAFile vaf2d2 = new VAFile(dim, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
        VAFile vaf2d3 = new VAFile(dim, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});

        LVAIndex lvaf2d1 = new LVAIndex(dim, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, 3);
        LVAIndex lvaf2d2 = new LVAIndex(dim, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, 3);
        LVAIndex lvaf2d3 = new LVAIndex(dim, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, 3);

        
        ArrayList<ISpatialObject> sos = Utils.readFile( rootDir + "syn_2d_1000.txt" );
        vaf2d1.add( sos );
        lvaf2d1.add( sos );
       
        ArrayList<ISpatialObject> sosb = Utils.readFile( rootDir + "syn_2d_2000.txt" );
        vaf2d2.add( sosb );
        lvaf2d2.add( sosb );

        ArrayList<ISpatialObject> sosc = Utils.readFile( rootDir + "syn_2d_5000.txt" );
        vaf2d3.add( sosc );
        lvaf2d3.add( sosc );

        System.out.println("VAFile created.");

        try
        {
            subtest2( vaf2d1,  lvaf2d1, 250, false );
            subtest2( vaf2d1,  lvaf2d1, 450, false );
            subtest2( vaf2d1,  lvaf2d1, 550, false );
            subtest2( vaf2d1,  lvaf2d1, 650, false );
            subtest2( vaf2d1,  lvaf2d1, 750, false );
            subtest2( vaf2d1,  lvaf2d1, 850, false );

            subtest2( vaf2d2, lvaf2d2, 250, false );
            subtest2( vaf2d2, lvaf2d2, 450, false );
            subtest2( vaf2d2, lvaf2d2, 550, false );
            subtest2( vaf2d2, lvaf2d2, 650, false );
            subtest2( vaf2d2, lvaf2d2, 750, false );
            subtest2( vaf2d2, lvaf2d2, 850, false );

            subtest2( vaf2d3, lvaf2d3, 250, false );
            subtest2( vaf2d3, lvaf2d3, 450, false );
            subtest2( vaf2d3, lvaf2d3, 550, false );
            subtest2( vaf2d3, lvaf2d3, 650, false );
            subtest2( vaf2d3, lvaf2d3, 750, false );
            subtest2( vaf2d3, lvaf2d3, 850, false );
            
            /*
            subtest2( vaf2d,  1000, false );
            subtest2( vaf2d,  2000, false );
            subtest2( vaf2d,  3000, false );
            subtest2( vaf2d,  4000, false );
            subtest2( vaf2d,  5000, false );
            subtest2( vaf2d,  6000, false );
            subtest2( vaf2d,  7000, false );
            subtest2( vaf2d,  8000, false );
            subtest2( vaf2d,  9000, false );
            subtest2( vaf2d, 10000, false );
            subtest2( vaf2d, 11000, false );
            subtest2( vaf2d, 12000, false );
            subtest2( vaf2d, 13000, false );
            subtest2( vaf2d, 14000, false );
            subtest2( vaf2d, 15000, false );
            */
            
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        
    }

    /**
     *
     *
     *
     */
    static public void test3()
    {
        String rootDir =
            "..\\data\\syntetic\\";
            
        int dim = 2;
        // 2D

        //VAFile vaf2d = new VAFile( dim, new int[]{1,1,2,2,2,1,2,2} );

        HashMap<Integer, String> data = new HashMap<Integer, String>();
        /*data.put( 2, "2D\\syn_2d_2000.txt");
        data.put( 3, "3D\\syn_3d_2000.txt");
        data.put( 4, "4D\\syn_4d_2000.txt");*/
        
        /*data.put( 2,  "2D\\syn_2d_10000.txt");
        data.put( 3,  "2D\\syn_2d_20000.txt");
        data.put( 4,  "2D\\syn_2d_50000.txt");
        data.put( 5, "2D\\syn_2d_100000.txt");*/
        
        data.put( 3,  "3D\\syn_3d_10000.txt");
        /*data.put( 2,  "3D\\syn_3d_10000.txt");
        data.put( 3,  "3D\\syn_3d_20000.txt");
        data.put( 4,  "3D\\syn_3d_50000.txt");
        data.put( 5, "3D\\syn_3d_100000.txt");*/

        //int[] knns={5, 10, 20, 50, 100};
        //int[] knns={10,20,30,40,50};
        int[] knns={5,10,15,20,25};
        int repeat = 1;
        int tests = 100;
        //int tests = 1;
        int b = 4;
        boolean print = false;

        for(int dimension=3; dimension <= 5; dimension++ )
        {
            String input = rootDir + data.get(dimension);
            System.out.println("Creating VAFile and LVAFile from: " + input);
            VAFile vaf =
                    new VAFile(
                    	dimension, new int[]{b, b, b, b, b, b, b, b, b, b, b}
                    );
            LVAIndex lvaf =
                    new LVAIndex(
                    	dimension, new int[]{b, b, b, b, b, b, b, b, b, b, b}, 3
                    );
    
            ArrayList<ISpatialObject> sos = Utils.readFile(input);

            vaf.add( sos );
            lvaf.add( sos);

            System.out.println("Done.");

            try
            {
                for(int knn:knns)
                {
                    System.out.print("knn = " + knn + "\n");
                    for (int i = 0; i < repeat; i++)
                    {
                        subtest3(vaf, lvaf, tests, knn, max, print);
                    }
                    System.out.println("--");
                }
                /*subtest2( vaf3d, lvaf3d, 500, false );
                subtest2( vaf4d, lvaf4d, 500, false );            */
                
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }
            
            if (true)
            	return;
        } // end for dimensions
    }

    /**
     *
     *
     *
     */
    static public void test1()
    {
        String rootDir =
            //"data\\ex01\\";
              "data\\syntetic\\2D\\";

        System.out.println("S\ts1\ts2\tt1\tt2");

        try
        {
            boolean print = false;
            
            subtest1( rootDir + "object-ex01-500.txt", print );
            subtest1( rootDir + "object-ex01-2500.txt", print );
            /*subtest1( rootDir + "object-ex01-3000.txt", print );
            subtest1( rootDir + "object-ex01-3500.txt", print );
            subtest1( rootDir + "object-ex01-4000.txt", print );
            subtest1( rootDir + "object-ex01-4500.txt", print );
            subtest1( rootDir + "object-ex01-5000.txt", print );
            subtest1( rootDir + "object-ex01-500.txt", print );
            subtest1( rootDir + "object-ex01-1000.txt", print );
            subtest1( rootDir + "object-ex01-1500.txt", print );
            subtest1( rootDir + "object-ex01-2000.txt", print );
            subtest1( rootDir + "syn_2d_10000.txt", print );
            subtest1( rootDir + "syn_2d_20000.txt", print );
            subtest1( rootDir + "syn_2d_50000.txt", print );
            subtest1( rootDir + "syn_2d_20000.txt", print );*/
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }        
    }

    /**
     *
     * @param args the command line arguments
     */
    public static void main ( String[] args )
    {

        // do something
        // test1();
        
        test3();

        System.exit( 0 );
    }

}
             /*int dim = 2;
             
             // 2D
             VAFile vaf2d = new VAFile( dim, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5} );
             //VAFile vaf2d = new VAFile( dim, new int[]{1,1,2,2,2,1,2,2} );
             String rootDir =
                "C:\\Documents and Settings\\Piotrek\\Moje dokumenty\\WORKING_DIR\\PHD\\SpatialIndex\\java\\SpatialIndexer\\data\\ex01\\";
             String file1Path = rootDir + "object-ex01-500.txt";

             ArrayList< SpatialObject > sos = Utils.readFile( file1Path );     

             vaf2d.insert2( sos );
             
             int i = 0;
             int j = sos.size() / 500;

             long nn2_s = System.currentTimeMillis();

             for ( SpatialObject so:sos )
             {
                //
                //if ( i % j == 0 )
                    //vaf2d.getNN2( so.getApproximation(), 5 );
                    vaf2d.getNN( so.getApproximation(), 5 );
                i++;
                //vaf.getNN( so.getApproximation(), 5 );
             }
            long nn2_e = System.currentTimeMillis();
            
            System.out.println( ">>>>" + (nn2_e - nn2_s) );
            
            System.out.println("checked " + i);
            System.exit( 0 );

             // 3D
             /*VAFile vaf3d = new VAFile( 3, new int[]{2, 2, 2} );
             ArrayList< SpatialObject > sos2d = new ArrayList<SpatialObject>();
             SpatialObject s3d00 = new SpatialObject( new Integer[]{ 5, 2, 1}); sos2d.add( s3d00 );
             SpatialObject s3d01 = new SpatialObject( new Integer[]{ 3, 8, 9}); sos2d.add( s3d01 );
             SpatialObject s3d02 = new SpatialObject( new Integer[]{ 2, 9, 3}); sos2d.add( s3d02 );
             SpatialObject s3d03 = new SpatialObject( new Integer[]{ 4, 2, 1}); sos2d.add( s3d03 );
             SpatialObject s3d04 = new SpatialObject( new Integer[]{ 7, 5, 7}); sos2d.add( s3d04 );
             SpatialObject s3d05 = new SpatialObject( new Integer[]{ 6, 5, 6}); sos2d.add( s3d05 );
             SpatialObject s3d06 = new SpatialObject( new Integer[]{ 6, 5, 7}); sos2d.add( s3d06 );
             SpatialObject s3d07 = new SpatialObject( new Integer[]{ 6, 6, 7}); sos2d.add( s3d07 );
             vaf3d.insert2( sos2d );
             
             ArrayList<Cell> cells3dNN = vaf3d.getNN( s3d02.getApproximation(), 2 );
             ArrayList<Cell> cells3dNN2 = vaf3d.getNN2( s3d02.getApproximation(), 2 );*/
             
             // 4D
             
             // 5D


             /*
             int i = 0;
             int j = sos.size() / 500;

             long nn2_s = System.currentTimeMillis();

             for ( SpatialObject so:sos )
             {
                //
                if ( i % j == 0 )
                    vaf.getNN2( so.getApproximation(), 5 );
                    //vaf.getNN( so.getApproximation(), 5 );
                i++;
                //vaf.getNN( so.getApproximation(), 5 );
             }
             //System.out.println( System.currentTimeMillis() );

             long nn2_e = System.currentTimeMillis();

             long nn_s = System.currentTimeMillis();

             for ( SpatialObject so:sos )
             {
                if ( i % j == 0 )
                    vaf.getNN( so.getApproximation(), 5 );
                i++;
             }
             long nn_e = System.currentTimeMillis();

             System.out.println(sos.size() + "\t" + (nn_e-nn_s) + "\t" + (nn2_e - nn2_s) + "\n");
*/


