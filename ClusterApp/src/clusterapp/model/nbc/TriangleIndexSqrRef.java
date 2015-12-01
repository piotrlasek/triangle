package clusterapp.model.nbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.print.attribute.HashAttributeSet;

import lvaindex.vafile.ClusteringLogger;
import lvaindex.vafile.ISpatialObject;

import util.Distance;

public class TriangleIndexSqrRef extends LoggingIndex implements lvaindex.vafile.ISpatialIndex {

    int k = 0;
    // Point zero;

    double[] minCoordinates;
    double[] maxCoordinates;

    ArrayList<ISpatialObject> D;

    TriangleNeighborhoodOptSqr tn = new TriangleNeighborhoodOptSqr();
    ArrayList<Point> RefPoints = new ArrayList<Point>();

    /**
     * 
     * @param k
     */
    public TriangleIndexSqrRef(int k) {
        TI_k_Neighborhood_Index(D, k);
    }

    public void TI_k_Neighborhood_Index(ArrayList<ISpatialObject> D, int k) {
        this.k = k;
    }

    // function TI-k-Neighborhood(D, point p, k)
    public ArrayList<ISpatialObject> TI_k_Neighborhood(
            ArrayList<ISpatialObject> D, Point p, int k) {
        tn.clear();
        // b = p;
        Point[] b = { p };
        // f = p;
        Point[] f = { p };
        // backwardSearch = PrecedingPoint(D, b);
        Integer backwardSearch = PrecedingPoint(D, b);
        // forwardSearch = FollowingPoint(D, f);
        Integer forwardSearch = FollowingPoint(D, f);
        // k-Neighborhood = {}
        ArrayList<ISpatialObject> k_Neighborhood = new ArrayList<ISpatialObject>();

        // i = 0;
        int[] i = { 0 };
        // Find-First-k-Candidate-Neighbours-Forward&Backward(D, p, b, f,
        // backwardSearch, forwardSearch, k-Neighborhood);
        Find_First_k_Candidate_Neighbours_Forward_Backward(D, p, b, f,
                backwardSearch, forwardSearch, /* tn, */k, i);
        // Find-First-k-Candidate-Neighbours-Backward(D, p, b,
        // backwardSearch, k-Neighborhood);
        Find_First_k_Candidate_Neighbours_Backward(D, p, b, backwardSearch, /*
                                                                             * tn,
                                                                             */
                i);
        // Find-First-k-Candidate-Neighbours-Forward(D, p, f,
        // forwardSearch, k-Neighborhood);
        Find_First_k_Candidate_Neighbours_Forward(D, p, f, forwardSearch, /* tn, */
                i);
        // Eps2 = max({0} + {e.dist2| e in k-Neighborhood});
        double[] Eps = new double[1];
        tn.getEps(Eps);
        // Verify-k-Candidate-Neighbours-Backward(D, p, b, backwardSearch,
        // k-Neighborhood, Eps2);
        Verify_k_Candidate_Neighbours_Backward(D, p, b, backwardSearch, /* tn, */
                k, Eps);
        // Eps2[0] = tmpEps;
        // Verify-k-Candidate-Neighbours-Forward(D, p, f, forwardSearch,
        // k-Neighborhood, Eps2);
        Verify_k_Candidate_Neighbours_Forward(D, p, f, forwardSearch, /* tn, */
                Eps);
        // return k-Neighborhood // component dist2 may not be returned
        // return k_Neighborhood;
        return tn.k_Neighborhood();
    }

    // function PrecedingPoint(D, var point p)
    public Integer PrecedingPoint(ArrayList<ISpatialObject> D, Point[] p) {
        Integer backwardSearch = 0;
        // if there is a point in D preceding p then
        if (p[0].pos > 0) {
            // p = point immediately preceding p in D;
            p[0] = (Point) D.get(p[0].pos - 1);
            // backwardSearch = true
            backwardSearch = 1;
            // else
        } else {
            // backwardSearch = false
            backwardSearch = 0;
            // endif
        }
        // return backwardSearch
        return backwardSearch;
    }

    // function FollowingPoint(D, var point p)
    public Integer FollowingPoint(ArrayList<ISpatialObject> D, Point[] p) {
        Integer forwardSearch = 0;
        // if there is a point in D following p then
        if (p[0].pos + 1 < D.size()) {
            // p = point immediately following p in D;
            p[0] = (Point) D.get(p[0].pos + 1);
            // forwardSearch = true
            forwardSearch = 1;
            // else
        } else {
            // forwardSearch = false
            forwardSearch = 0;
            // endif
        }
        // return forwardSearch
        return forwardSearch;
    }

    // function Find-First-k-Candidate-Neighbours-Forward&Backward(D,
    // var point p, var point b, var point f,
    // var backwardSearch, var forwardSearch, var k-Neighborhood,
    // k, var i)
    public void Find_First_k_Candidate_Neighbours_Forward_Backward(
            ArrayList<ISpatialObject> D, Point p, Point[] b, Point[] f,
            Integer backwardSearch, Integer forwardSearch, /*
                                                            * TriangleNeighborhood
                                                            * tn,
                                                            */
            int k, int[] i) {
        while (backwardSearch == 1 && forwardSearch == 1 && (i[0] < k)) {
            i[0] = i[0] + 1;
            if (p.Dists.get(0) - b[0].Dists.get(0) < f[0].Dists.get(0)
                    - p.Dists.get(0)) {
                double dist2 = Distance.Distance2(b[0], p);
                tn.insertSorted(dist2, b[0]);
                backwardSearch = PrecedingPoint(D, b);
            } else {
                double dist2 = Distance.Distance2(f[0], p);
                tn.insertSorted(dist2, f[0]);
                forwardSearch = FollowingPoint(D, f);
            }
        }
    }

    // public static void print(ArrayList<ISpatialObject> neigh, Point p) {
    // prn("" + p + " - ");
    // for (int j = 0; j < neigh.size(); j++) {
    // Point x = (Point) neigh.get(j);
    // prn(x + "-" + Distance.Distance(x, p) + " ");
    // }
    // prnl("");
    // }
    //
    // public static void print2(ArrayList<ISpatialObject> neigh) {
    // for (int j = 0; j < neigh.size(); j++) {
    // Point x = (Point) neigh.get(j);
    // prn(x + " ");
    // }
    // prnl("");
    // }
    //
    // function Find-First-k-Candidate-Neighbours-Backward(D,var point p,
    // var point b, var backwardSearch, var k-Neighborhood, k, var i)
    public void Find_First_k_Candidate_Neighbours_Backward(
            ArrayList<ISpatialObject> D, Point p, Point[] b,
            Integer backwardSearch, /* TriangleNeighborhood tn, */int[] i) {
        // while backwardSearch and (i < k) do // TODO i is not initialized
        while (backwardSearch == 1 && i[0] < k) {
            // i = i + 1;
            i[0] = i[0] + 1;
            // dist2 = Distance2(b, p);
            double dist2 = Distance.Distance2(b[0], p);
            // insert element e = (position of b, dist2) in k-Neighborhood
            // holding it sorted wrt. e.dist2;
            // insertSorted(k_Neighborhood, dist, b[0]);
            tn.insertSorted(dist2, b[0]);
            // backwardSearch = PrecedingPoint(D, b)
            backwardSearch = PrecedingPoint(D, b);
            // endwhile
        }
    }

    // function Find-First-k-Candidate-Neighbours-Backward(D,var point p,
    // var point b, var backwardSearch, var k-Neighborhood, k, var i)
    public void Find_First_k_Candidate_Neighbours_Forward(
            ArrayList<ISpatialObject> D, Point p, Point[] f,
            Integer forwardSearch, /* TriangleNeighborhood tn, */int[] i) {
        // while forwardSearch and (i < k) do // TODO i is not initialized
        while (forwardSearch == 1 && i[0] < k) {
            // i = i + 1;
            i[0] = i[0] + 1;
            // dist2 = Distance2(b, p);
            double dist2 = Distance.Distance2(f[0], p);
            // insert element e = (position of b, dist2) in k-Neighborhood
            // holding it sorted wrt. e.dist2;
            // insertSorted(k_Neighborhood, dist, f[0]);
            tn.insertSorted(dist2, f[0]);
            // backwardSearch = PrecedingPoint(D, b)
            forwardSearch = FollowingPoint(D, f);
            // endwhile
        }
    }

    //        
    // public static void prn(String s) {
    // // System.out.print(s);
    // }
    //        
    // public static void prnl(String s) {
    // // System.out.println(s);
    // }
    //

    // ---------------------------------------------------------------------
    public void Verify_k_Candidate_Neighbours_Backward(
            ArrayList<ISpatialObject> D, Point p, Point[] b,
            Integer backwardSearch, /* TriangleNeighborhood tn, */int k,
            double[] Eps2) {
        double Eps1 = Math.sqrt(Eps2[0]);
        boolean candidateNeighbor = false;

        while (backwardSearch == 1
                && (p.Dists.get(0) - b[0].Dists.get(0) <= Eps1)) {
            candidateNeighbor = true;
            int ii = 1;
            while (candidateNeighbor && ii < p.Dists.size()) {
                if (p.Dists.get(ii) - b[0].Dists.get(ii) > Eps1) {
                    candidateNeighbor = false;
                } else {
                    ii = ii + 1;
                }
            }
            if (candidateNeighbor) {
                double dist2 = Distance.Distance2(b[0], p);

                if (dist2 < Eps2[0]) {

                    int i = tn.getI(Eps2[0]);

                    if (tn.size() - i >= k - 1) {

                        tn.remove(Eps2[0]);
                        tn.insertSorted(dist2, b[0]);
                        tn.getEps(Eps2);
                        Eps1 = Math.sqrt(Eps2[0]);

                    } else {
                        tn.insertSorted(dist2, b[0]);
                    }
                } else if (dist2 == Eps2[0]) {

                    tn.insertSorted(dist2, b[0]);
                }
            }
            backwardSearch = PrecedingPoint(D, b);
        }
    }

    // function Verify-k-Candidate-Neighbours-Forward(D, var point p,
    // var point f, var forwardSearch, var k-Neighborhood, var Eps2)
    public void Verify_k_Candidate_Neighbours_Forward(
            ArrayList<ISpatialObject> D, Point p, Point[] f,
            Integer forwardSearch,
            /* TriangleNeighborhood tn, */double[] Eps2) {
        double Eps1 = Math.sqrt(Eps2[0]);
        boolean candidateNeighbor = false;

        while (forwardSearch == 1
                && (f[0].Dists.get(0) - p.Dists.get(0) <= Eps1)) {
            candidateNeighbor = true;
            int ii = 1;
            while (candidateNeighbor && ii < p.Dists.size()) {
                if (f[0].Dists.get(ii) - p.Dists.get(ii) > Eps1) {
                    candidateNeighbor = false;
                } else {
                    ii = ii + 1;
                }
            }
            if (candidateNeighbor) {
                double dist2 = Distance.Distance2(f[0], p);

                int i = tn.getI(Eps2[0]);

                if (dist2 < Eps2[0]) {

                    if (tn.size() - i >= k - 1) {

                        tn.remove(Eps2[0]);
                        tn.insertSorted(dist2, f[0]);
                        tn.getEps(Eps2);
                        Eps1 = Math.sqrt(Eps2[0]);

                    } else {

                        tn.insertSorted(dist2, f[0]);
                    }

                } else if (dist2 == Eps2[0]) {

                    tn.insertSorted(dist2, f[0]);

                }
            }
            forwardSearch = FollowingPoint(D, f);

        }
    }

    protected void determineBorderCoordinates(
            Collection<ISpatialObject> objectsList) {
        minCoordinates = objectsList.iterator().next().getCoordinates().clone();
        maxCoordinates = minCoordinates.clone();

        for (ISpatialObject o : objectsList) {
            double[] coordinates = o.getCoordinates();
            for (int i = 0; i < coordinates.length; i++) {

                if (minCoordinates[i] > coordinates[i]) {
                    minCoordinates[i] = coordinates[i];
                }

                if (maxCoordinates[i] < coordinates[i]) {
                    maxCoordinates[i] = coordinates[i];
                }
            }
        }
    }

    @Override
    public void add(Collection<ISpatialObject> objectsList) {
        logger.sortingStart();
        determineBorderCoordinates(objectsList);
        RefPoints.add(new Point(minCoordinates));
        double[] tmp = minCoordinates.clone();
        tmp[0] = maxCoordinates[0];
        RefPoints.add(new Point(tmp));
        int nDim = objectsList.iterator().next().getCoordinates().length;
        // -------------------------------------------------------------
        // double minCoord = 0;
        // for (int i = 0; i < nDim; i++) {
        // int j = 0;
        // Point minPoint = null;
        // for (ISpatialObject co : objectsList) {
        // Point point = new Point(co.getCoordinates().clone());
        // if (j == 0) {
        // minCoord = point.getCoordinates()[i];
        // }
        // if (point.getCoordinates()[i] <= minCoord) {
        // minCoord = point.getCoordinates()[i];
        // minPoint = point;
        // }
        // j++;
        // }
        // RefPoints.add(minPoint);
        // }

        // zero = new Point(minCoordinates);
        createSortedTableD(objectsList);
        logger.sortingEnd();
    }

    public void createSortedTableD(Collection<ISpatialObject> input) {

        D = new ArrayList<ISpatialObject>();
        for (ISpatialObject o : input) {

            Point point = (Point) o;

            for (int i = 0; i < RefPoints.size(); i++) {
                point.Dists.add(Distance.Distance(point, RefPoints.get(i)));
            }
            double distance2 = Distance.Distance2(RefPoints.get(0), point);
            double distance = Distance.sqrt(distance2);

            point.dist2 = distance2;
            point.dist = distance;

            if (D.size() == 0) {
                D.add(point);
            } else {
                for (int i = 0; i < D.size(); i++) {
                    Point tip = (Point) D.get(i);

                    if (distance < tip.dist) {
                        // insert before the current point
                        D.add(i, point);
                        if (i > 0) {
                            Point pr = (Point) D.get(i - 1);
                        }
                        break;
                    } else if (i == D.size() - 1) {
                        // insert at the end
                        D.add(point);
                        break;
                    } else {
                        // compare to the next point
                        ;
                    }
                }
            }
        }
        for (int i = 0; i < D.size(); i++) {
            Point p = (Point) D.get(i);
            // prnl(p + " " + p.dist);
            p.pos = i;
        }

        // prn("x");

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "TE Index";
    }

    @Override
    public Collection<ISpatialObject> getNeighbors(ISpatialObject object,
            int count, double max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<ISpatialObject> getNeighbors(ISpatialObject object,
            double max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<ISpatialObject> getNeighbors(ISpatialObject object,
            int count) {
        return TI_k_Neighborhood(D, (Point) object, count);
    }


}
