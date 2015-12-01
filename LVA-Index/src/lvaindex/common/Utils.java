/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lvaindex.common;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JFrame;

//import clusterapp.SpatialObject;
import lvaindex.vafile.BasicSpatialObject;
import lvaindex.vafile.ISpatialObject;
import lvaindex.vafile.VAFile;

/**
 *
 * @author Piotrek
 */
public class Utils {

    /**
     *
     *
     */
    public static ArrayList<ISpatialObject> readFile( String fileName )
    {
        ArrayList< ISpatialObject > sos = new ArrayList< ISpatialObject >();
        
        //DataView dv = (DataView) this.wnds.get(activeWnd);
        try {
            FileInputStream fin = new FileInputStream( fileName );
            // JDK1.1+
            BufferedReader myInput = new BufferedReader(new InputStreamReader(fin));
            // JDK1.02
            // DataInputStream myInput = new DataInputStream(fin);
            String thisLine;
            // ommiting 2 first lines
            String sNumOb = myInput.readLine();
            String[] sNumAttr = myInput.readLine().split(" ");
            int nAttr = new Integer(sNumAttr[1]).intValue();

            while ((thisLine = myInput.readLine()) != null) {
                //System.out.println(thisLine);
                String[] s = thisLine.split(";");
                if ((s.length - 1) != nAttr) {
                    for (int i = 0; i < s.length - 1; i++) {
                        System.out.println(s[i]);
                    }
                    throw new Exception("Incorrect number of attributes: " + (s.length - 1) + " != " + nAttr);
                }

                double[] coords = new double[nAttr];

                for (int i = 0; i < nAttr; i++)
                {
                    coords[i] = new Double(s[i]);
                }
                BasicSpatialObject bso = new BasicSpatialObject(coords);
                sos.add(bso);
                //MyPoint point = new MyPoint(coords, new Integer(s[nAttr]).intValue());
                //double []coords = new double[2];
                //coords[0] = (double) (r.nextInt(dv.getSize().width));
                //coords[1] = (double) (r.nextInt(dv.getSize().height));
                //MyPoint point = new MyPoint(coords, -1);
                //sos.add(point);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sos;

        //return fd.getFile();
    }

    /**
     *
     *
     */
    public static ArrayList<ISpatialObject> readFileByDialog()
    {
        // Creating new window...
        String title = "Open...";

        JFrame jf = new JFrame();
        FileDialog fd = new FileDialog( jf, title, FileDialog.LOAD);
        fd.setLocation(50, 50);
        fd.setVisible( true );

        return readFile( fd.getDirectory() + "" + fd.getFile() );
    }
}
