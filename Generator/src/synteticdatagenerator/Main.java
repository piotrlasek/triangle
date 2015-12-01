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
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //args = new String[]{"0", "500", "0", "500", "0", "500", "100"};
        //args = new String[]{"0", "500", "0", "500", "100"};
        
        if ((args.length - 1) % 2 == 0)
        {
            int dims = (args.length - 1) / 2;
            
            int[] start = new int[dims];
            int[] end = new int[dims];
            
            int len = new Integer(args[args.length - 1]);
            
            for (int i = 0; i < dims; i++)
            {
                start[i] = new Integer(args[2*i]);
                end[i] = new Integer(args[2*i + 1]);
            }

            System.out.println("NumberOfObjects " + len );
            System.out.println("NumberOfAttributes " + dims );
                    
            for (int l = 0; l < len; l++ )
            {
                for (int i = 0; i < dims; i++ )
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
        }
        else
        {
            System.out.println("Incorrect arguments.");
        }
        
        
    }
    
}
