package lvaindex.vafile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TestUtil {
    public static ArrayList<ISpatialObject> readFile(String path, int max) throws Exception
    {
            ArrayList<ISpatialObject> dataset = new ArrayList<ISpatialObject>();
            
            //System.out.println(path);
            FileInputStream fin = new FileInputStream(path);
            BufferedReader myInput = new BufferedReader(new InputStreamReader(
                            fin));
            String thisLine;
            int nAttr = -1;// new Integer(sNumAttr[1]).intValue();
            //dv.nDim = nAttr; // trochê z³e nazewnictwo :(
            int linesCount = 0;
            while ((thisLine = myInput.readLine()) != null && linesCount++ <= max ) {
                    // System.out.println(thisLine);
                    String s[] = thisLine.split(";");
                    if (s.length <= 1) {
                            s = thisLine.split(":");
                    }

                    if (nAttr == -1) {
                            nAttr = s.length - 1;
                    }

                    if ((s.length - 1) != nAttr) {
                            throw new Exception("Incorrect number of attributes: "
                                            + (s.length - 1) + " != " + nAttr);
                    }

                    double[] coords = new double[nAttr];

                    for (int i = 0; i < nAttr; i++)
                            coords[i] = new Double(s[i]).doubleValue();
                    
                    //System.out.println(coords);
                    
                    BasicSpatialObject bso= new BasicSpatialObject(coords);
                    dataset.add(bso);
            }
            return dataset;
    }
}
