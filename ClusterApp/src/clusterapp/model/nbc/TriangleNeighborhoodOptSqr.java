package clusterapp.model.nbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import lvaindex.vafile.ISpatialObject;

public class TriangleNeighborhoodOptSqr {

    ArrayList<ISpatialObject> neighborhood = new ArrayList<ISpatialObject>();

    double epsMax = 0.0;    
    HashMap<Double, Integer> epsMap = new HashMap<Double, Integer>();
    
    /**
     * 
     * @return
     */
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
    }
    
    /**
     * 
     * @param dist
     * @param f
     */
    public void insertSorted(double dist, Point f) {

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
    }
    
    public void remove(double Eps) {
        boolean removed = false;
        Iterator<ISpatialObject> iter = neighborhood.iterator();
        while(iter.hasNext()) {
            Point p = (Point) iter.next();
            double d = p.tmpDist;
            if (d == Eps) {
                iter.remove();
                removed = true;
            }
        }
        
        Point p = (Point) neighborhood.get(neighborhood.size()-1);
        epsMax = p.tmpDist;
        
        if (epsMap.containsKey(Eps)) { 
            int count = epsMap.get(Eps);
            count--;
            epsMap.put(Eps, count);
        }
    }
    
    /**
     * 
     */
    public void clear() {
        epsMax = 0.0;
        epsMap.clear();
        neighborhood.clear();
    }
    
    /**
     * 
     * @return
     */
    public int size() {
        return neighborhood.size();
    }
}
