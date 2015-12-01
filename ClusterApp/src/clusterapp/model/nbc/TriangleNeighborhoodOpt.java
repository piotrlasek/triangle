package clusterapp.model.nbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import lvaindex.vafile.ISpatialObject;

public class TriangleNeighborhoodOpt {

    ArrayList<ISpatialObject> neighborhood = new ArrayList<ISpatialObject>();

    double epsMax = 0.0;
    
    HashMap<Double, Integer> epsMap = new HashMap<Double, Integer>();
    
    public ArrayList<ISpatialObject> k_Neighborhood() {
        return neighborhood;
    }

    /**
     * Determines number of elements having tmpDist equal to Eps.
     * @param neighborhood
     * @param Eps
     * @return
     */
    public int getI(double Eps) {
        /*int i = 0;
        for(int j = 0; j < neighborhood.size(); j++) {
            Point p = (Point) neighborhood.get(j);
            double dist = p.tmpDist;
            if (dist == Eps) {
                i++;
            }
        }
        return i;*/
        if (epsMap.containsKey(Eps)) {
            return epsMap.get(Eps);
        } else {
            return 0;
        }
    }
    
    /** 
     * Determine the greatest Eps (tmpDist in the neighborhood)
     * @param neighborhood
     * @param Eps
     */
    public void getEps(double[] Eps) {
        Eps[0] = epsMax;
      /*Eps[0] = 0.0;
        int i = 0;
        for(ISpatialObject o:neighborhood) {
            Point p = (Point) o;
            double d = p.tmpDist;
            if (i == 0) {
                Eps[0] = d;
            } else {
                if (p.dist > Eps[0]) {
                    Eps[0] = d;
                }
            }
            i++;
        }*/
    }
    
    public void insertSorted(double dist, Point f) {
//        TriangleIndex.prn("\t\tinsert " + f + " into " );
//        TriangleIndex.print2(neighborhood);

        boolean added = false;
        f.tmpDist = dist;
        for (int j = 0; j < neighborhood.size(); j++) {
            Point p = (Point) neighborhood.get(j);
            double d = p.tmpDist;
            if (dist < d) {
                neighborhood.add(j, f);
                added = true;
                break;
            }
        }

        // add at the end
        if (!added)
            neighborhood.add(f);
        
        if (epsMax < f.tmpDist) {
            epsMax = f.tmpDist;
        }
        double eps = f.tmpDist;
        int count = 0;
        if (epsMap.containsKey(eps)) {
            count = epsMap.get(eps).intValue();
        }
        count++;
        epsMap.put(eps, count);
        
//        TriangleIndex.prn("\t\tk-Neighborhood=");
//        TriangleIndex.print2(neighborhood);
    }
    
    public void remove(double Eps) {
        boolean removed = false;
        Iterator<ISpatialObject> iter = neighborhood.iterator();
        while(iter.hasNext()) {
            Point p = (Point) iter.next();
            double d = p.tmpDist;
            if (d == Eps) {
//                TriangleIndex.prn("\t\tdelete " + p + " having dist2=" + d + " from ");
//                TriangleIndex.print2(neighborhood);
                iter.remove();
                removed = true;
//                TriangleIndex.prn("\t\tk-Neighborhood = ");
//                TriangleIndex.print2(neighborhood);
            }
        }
        
        Point p = (Point) neighborhood.get(neighborhood.size()-1);
        epsMax = p.tmpDist;
        
        int count = epsMap.get(Eps);
        count--;
        epsMap.put(Eps, count);
    }
    
    public void clear() {
        epsMax = 0.0;
        epsMap.clear();
        neighborhood.clear();
    }
    
    public int size() {
        return neighborhood.size();
    }
}
