package clusterapp.model.nbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.print.attribute.HashAttributeSet;

import lvaindex.vafile.ClusteringLogger;
import lvaindex.vafile.ISpatialObject;

import util.Distance;

public class TriangleIndexSqrEpsEstimRef extends LoggingIndex implements lvaindex.vafile.ISpatialIndex {

    int k = 0;
    //Point zero;

    double[] minCoordinates;
    double[] maxCoordinates;

    ArrayList<ISpatialObject> D;

    TriangleNeighborhoodOptSqr tn = new TriangleNeighborhoodOptSqr();

    ArrayList<Point> RefPoints = new ArrayList<Point>();
    
    public TriangleIndexSqrEpsEstimRef(int k) {
        TI_k_Neighborhood_Index(D, k);
    }

    public void TI_k_Neighborhood_Index(ArrayList<ISpatialObject> D, int k) {
        this.k = k;
    }

    public double getEps2(Point p, double[] Eps2) {
        tn.getEps(Eps2);
        return Math.min(p.Eps2, Eps2[0]);
    }

    public ArrayList<ISpatialObject> TI_k_Neighborhood(
            ArrayList<ISpatialObject> D, Point p, int k) {
        tn.clear();
        Point[] b = { p };
        Point[] f = { p };

        Integer backwardSearch = PrecedingPoint(D, b);
        Integer forwardSearch = FollowingPoint(D, f);

        int[] i = { 0 };

        Find_First_k_Candidate_Neighbours_Forward_Backward(D, p, b, f,
                backwardSearch, forwardSearch, k, i);
        Find_First_k_Candidate_Neighbours_Backward(D, p, b, backwardSearch, i);
        Find_First_k_Candidate_Neighbours_Forward(D, p, f, forwardSearch, i);

        double[] Eps2 = new double[1];
        tn.getEps(Eps2);

        p.Eps2 = Math.max(0.0, Eps2[0]);

        Verify_k_Candidate_Neighbours_Backward(D, p, b, backwardSearch, k);
        Verify_k_Candidate_Neighbours_Forward(D, p, f, forwardSearch, k);

        EstimateNeighborhoodRadius(p, tn.k_Neighborhood());
        
        return tn.k_Neighborhood();
    }

    public Integer PrecedingPoint(ArrayList<ISpatialObject> D, Point[] p) {
        Integer backwardSearch = 0;
        if (p[0].pos > 0) {
            p[0] = (Point) D.get(p[0].pos - 1);
            backwardSearch = 1;
        } else {
            backwardSearch = 0;
        }
        return backwardSearch;
    }

    public Integer FollowingPoint(ArrayList<ISpatialObject> D, Point[] p) {
        Integer forwardSearch = 0;
        if (p[0].pos + 1 < D.size()) {
            p[0] = (Point) D.get(p[0].pos + 1);
            forwardSearch = 1;
        } else {
            forwardSearch = 0;
        }
        return forwardSearch;
    }

    public void Find_First_k_Candidate_Neighbours_Forward_Backward(
            ArrayList<ISpatialObject> D, Point p, Point[] b, Point[] f,
            Integer backwardSearch, Integer forwardSearch, int k, int[] i) {

        while (backwardSearch == 1 && forwardSearch == 1 && (i[0] < k)) {
            if (p.dist - b[0].dist < f[0].dist - p.dist) {
                double dist2 = Distance.Distance2(b[0], p);
                if (dist2 <= p.Eps2) {
                    i[0] = i[0] + 1;
                    tn.insertSorted(dist2, b[0]);
                }
                backwardSearch = PrecedingPoint(D, b);
            } else {
                double dist2 = Distance.Distance2(f[0], p);
                if (dist2 <= p.Eps2) {
                    i[0] = i[0] + 1;
                    tn.insertSorted(dist2, f[0]);
                }
                forwardSearch = FollowingPoint(D, f);
            }
        }
    }

    public void Find_First_k_Candidate_Neighbours_Backward(
            ArrayList<ISpatialObject> D, Point p, Point[] b,
            Integer backwardSearch, int[] i) {
        while (backwardSearch == 1 && i[0] < k) {
            double dist2 = Distance.Distance2(b[0], p);
            if (dist2 <= p.Eps2) {
                i[0] = i[0] + 1;
                tn.insertSorted(dist2, b[0]);
            }
            backwardSearch = PrecedingPoint(D, b);
        }
    }

    public void Find_First_k_Candidate_Neighbours_Forward(
            ArrayList<ISpatialObject> D, Point p, Point[] f,
            Integer forwardSearch, int[] i) {

        while (forwardSearch == 1 && i[0] < k) {
            double dist2 = Distance.Distance2(f[0], p);
            if (dist2 <= p.Eps2) {
                i[0] = i[0] + 1;
                tn.insertSorted(dist2, f[0]);
            }
            forwardSearch = FollowingPoint(D, f);
        }
    }

    public void Verify_k_Candidate_Neighbours_Backward(
            ArrayList<ISpatialObject> D, Point p, Point[] b,
            Integer backwardSearch, int k) {

        double Eps1 = Math.sqrt(p.Eps2);
        while (backwardSearch == 1 && (p.Dists.get(0) - b[0].Dists.get(0) <= Eps1)) {
            boolean candidateNeighbor = true;
            int ii = 1;
            while (candidateNeighbor && (ii < p.Dists.size())) {
                if (p.Dists.get(ii) - b[0].Dists.get(ii) > Eps1) {
                    candidateNeighbor = false;
                } else {
                    ii = ii + 1;
                }
            }
            if (candidateNeighbor) {
                double dist2 = Distance.Distance2(b[0], p);
                if (dist2 < p.Eps2) {
                    int i = tn.getI(p.Eps2);
                    if (tn.size() - i >= k - 1) {
                        tn.remove(p.Eps2);
                        tn.insertSorted(dist2, b[0]);
                        double[] eps = new double[1];
                        tn.getEps(eps);
                        p.Eps2 = eps[0];
                        Eps1 = Math.sqrt(p.Eps2);
                    } else {
                        tn.insertSorted(dist2, b[0]);
                    }
                } else if (dist2 == p.Eps2) {
                    tn.insertSorted(dist2, b[0]);
                }
            }
            backwardSearch = PrecedingPoint(D, b);
        }
    }

    public void Verify_k_Candidate_Neighbours_Forward(
            ArrayList<ISpatialObject> D, Point p, Point[] f,
            Integer forwardSearch, int k) {
        double Eps1 = Math.sqrt(p.Eps2);
        while (forwardSearch == 1 && (f[0].dist - p.dist <= Eps1)) {
            boolean candidateNeighbor = true;
            int ii = 1;
            while (candidateNeighbor && (ii < p.Dists.size())) {
                if (f[0].Dists.get(ii) - p.Dists.get(ii) > Eps1) {
                    candidateNeighbor = false;
                } else {
                    ii = ii + 1;
                }
            }
            if (candidateNeighbor) {
                double dist2 = Distance.Distance2(f[0], p);
                if (dist2 < p.Eps2) {
                    int i = tn.getI(p.Eps2);
                    if (tn.size() - i >= k - 1) {
                        tn.remove(p.Eps2);
                        tn.insertSorted(dist2, f[0]);
                        double[] eps = new double[1];
                        tn.getEps(eps);
                        p.Eps2 = eps[0];
                        Eps1 = Math.sqrt(p.Eps2);
                    } else {
                        tn.insertSorted(dist2, f[0]);
                    }
                } else if (dist2 == p.Eps2) {
                    tn.insertSorted(dist2, f[0]);
                }
            }
            forwardSearch = FollowingPoint(D, f);
        }
    }
    
    public void EstimateNeighborhoodRadius(Point p,
            ArrayList<ISpatialObject> k_Neighborhood) {
        double qDist2 = p.dist2;
        double pEps = Math.sqrt(p.Eps2);
        double qDist = pEps;
        for (int i = k_Neighborhood.size() - 1; i >= 0; i--) {
            Point e = (Point) k_Neighborhood.get(i);
            if (e.dist2 != qDist2) {
                qDist2 = e.dist2;
                qDist = Math.sqrt(qDist2);
            }
            e.Eps2 = Math.min(e.Eps2, Math.pow(pEps + qDist, 2));
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
      //-------------------------------------------------------------
//      double minCoord = 0;
//      for (int i = 0; i < nDim; i++) {
//          int j = 0;
//          Point minPoint = null;
//          for (ISpatialObject co : objectsList) {
//              Point point = new Point(co.getCoordinates().clone());
//              if (j == 0) {
//                  minCoord = point.getCoordinates()[i];
//              }
//              if (point.getCoordinates()[i] <= minCoord) {
//                  minCoord = point.getCoordinates()[i];
//                  minPoint = point;
//              }
//              j++;
//          }
//          RefPoints.add(minPoint);
//      }

        
        //zero = new Point(minCoordinates);
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
            point.Eps2 = Double.MAX_VALUE;

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
//            prnl(p + " " + p.dist);
            p.pos = i;
        }
        
//        prn("x");

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
