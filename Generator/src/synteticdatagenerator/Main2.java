/*
 * Main.java
 *
 * Created on 7 maj 2008, 16:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package synteticdatagenerator;

/**
 *
 * @author Piotrek
 */
public class Main2 {
    
    /** Creates a new instance of Main */
    public Main2() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //args = new String[]{"0", "500", "0", "500", "0", "500", "100"};
        //args = new String[]{"0", "500", "0", "500", "100"};

        try {
            int dim = new Integer(args[0]);
            int len = new Integer(args[1]);
            int min = new Integer(args[2]);
            int max = new Integer(args[3]);
            
            int[] start = new int[dim];
            int[] end = new int[dim];
            
            for (int i = 0; i < dim; i++)
            {
                start[i] = new Integer(min);
                end[i] = new Integer(max);
            }
    
            //System.out.println("NumberOfObjects " + len );
            //System.out.println("NumberOfAttributes " + dim );
                    
            for (int l = 0; l < len; l++ )
            {
                for (int i = 0; i < dim; i++ )
                {
                    double r = Math.random();
                    int dif = end[i] - start[i];
                    double r2 = r * dif;
                    int r3 = (int) (r2 + start[i]);
                    if (i == 0)
                        System.out.print( (float) r3 );
                    else
                        System.out.print( ";" + (float) r3 );
                }
                System.out.print(";-1;");
                System.out.println();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error! Try this:");
            System.out.println("C:\\Documents and Settings\\piotr.lasek\\workspace\\Generator\\bin>java synteticdatagenerator.Main2 100 2000 0 500 > \"C:\\Documents and Settings\\piotr.lasek\\workspace\\Data\\syntetic\\ND\\d-100-2000.txt\"");
        }

        
        
    }
    
}
