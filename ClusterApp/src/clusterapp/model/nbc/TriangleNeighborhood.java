package clusterapp.model.nbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import lvaindex.vafile.ISpatialObject;

public class TriangleNeighborhood {

    ArrayList<ISpatialObject> neighborhood = new ArrayList<ISpatialObject>();

    
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
        int i = 0;
        for(int j = 0; j < neighborhood.size(); j++) {
            Point p = (Point) neighborhood.get(j);
            double dist = p.tmpDist;
            if (dist == Eps) {
                i++;
            }
        }

        return i;
    }
    
    /** 
     * Determine the greatest Eps (tmpDist in the neighborhood)
     * @param neighborhood
     * @param Eps
     */
    public void getEps(double[] Eps) {
        Eps[0] = 0.0;
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
        }
    }
    
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
    }
    
    public void clear() {
        neighborhood.clear();
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
        //if (!removed) {
        //    neighborhood.remove(neighborhood.size()-1);
        //}
        /*for (int i = 0; i < neighborhood.size(); i++) {
            Point p = (Point) neighborhood.get(i);
            double d = p.tmpDist;
            if (d == Eps) {
                neighborhood.remove(i);
            }
        }*/
    }
    
    public int size() {
        return neighborhood.size();
    }
    
    /*HashMap<Double, Integer> epsMap;
    double epsMax = 0.0;

    public TriangleNeighborhood() {
        n = new ArrayList<ISpatialObject>();
        epsMap = new HashMap<Double, Integer>();
    }

    public void clear() {
        n.clear();
        epsMap.clear();
        epsMax = 0.0;
    }


    public ArrayList<ISpatialObject> getNeighbours() {
        return n;
    }

    public double getEps() {
        return epsMax;
    }

    public int getI(double Eps) {
        Integer i = epsMap.get(Eps);
        return i;
    }

    public void insert(double dist, Point f) {

        boolean added = false;
        f.tmpDist = dist;

        if (dist > epsMax) {
            epsMax = dist;
        }

        for (int j = 0; j < n.size(); j++) {
            Point p = (Point) n.get(j);
            double d = p.tmpDist;
            if (dist < d) {
                n.add(j, f);
                added = true;
                break;
            }
        }
        // add at the end
        if (!added)
            n.add(f);

        if (epsMap.containsKey(dist)) {
            int i = epsMap.get(dist);
            epsMap.put(dist, ++i);
        } else {
            epsMap.put(dist, 1);
        }
    }

    public void remove(double Eps) {
        for (int i = 0; i < n.size(); i++) {
            Point p = (Point) n.get(i);
            double d = p.tmpDist;
            if (d == Eps)
                n.remove(i);
        }
    }

    public int size() {
        return n.size();
    }*/
}
