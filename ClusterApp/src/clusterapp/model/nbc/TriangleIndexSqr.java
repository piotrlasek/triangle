package clusterapp.model.nbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.print.attribute.HashAttributeSet;


import lvaindex.vafile.ClusteringLogger;
import lvaindex.vafile.ISpatialObject;

import util.Distance;

public class TriangleIndexSqr extends LoggingIndex implements lvaindex.vafile.ISpatialIndex {
	
	int k = 0;
	Point zero;
	
        double[] minCoordinates;
        double[] maxCoordinates; 
        
	ArrayList<ISpatialObject> D;
	
	//TriangleNeighborhood2 tn = new TriangleNeighborhood2();
	TriangleNeighborhoodOptSqr tn = new TriangleNeighborhoodOptSqr();
	//TriangleNeighborhoodOpt tn = new TriangleNeighborhoodOpt();
	//TriangleNeighborhood tn = new TriangleNeighborhood();

	/**
	 * 
	 * @param k
	 */
	public TriangleIndexSqr(int k) {
	    TI_k_Neighborhood_Index(D, k);
	}
	
	//Algorithm TI-k-Neighborhood-Index(set of points D, k);
	/* assert: 0 denotes a referential point,         */
	/* e.g. the point with all coordinates equal to 0 */
	public void TI_k_Neighborhood_Index(ArrayList<ISpatialObject> D, int k) {
		this.k = k;
	//for each point p in set D do
	//	for(Point p:D) {
	//    p.dist = Distance(p,0);
	//	    p.dist = Distance.Distance(p, zero);
	//endfor
	//	}
	//sort all points in D non-decreasingly wrt. attribute dist;
	//for each point p in the ordered set D starting from
	//    the first point until last point in D do
	//    insert (position of point p, TI-k-Neighborhood(D, p, k))
	//        to k-Neighborhood-Index
	//endfor
	}
	
	//function TI-k-Neighborhood(D, point p, k)
	public ArrayList<ISpatialObject> TI_k_Neighborhood(ArrayList<ISpatialObject> D, Point p, int k) {
	    tn.clear();
	//b = p;
           Point[] b = {p};
	//f = p;
           Point[] f = {p};
	//backwardSearch = PrecedingPoint(D, b);
           Integer backwardSearch = PrecedingPoint(D, b);
	//forwardSearch = FollowingPoint(D, f);
           Integer forwardSearch = FollowingPoint(D, f);
	//k-Neighborhood = {}
           ArrayList<ISpatialObject> k_Neighborhood = new ArrayList<ISpatialObject>();

	//i = 0;
	   int[] i = {0};
	//Find-First-k-Candidate-Neighbours-Forward&Backward(D, p, b, f, 
	//    backwardSearch, forwardSearch, k-Neighborhood);
           Find_First_k_Candidate_Neighbours_Forward_Backward(D, p, b, f, backwardSearch, forwardSearch, /*tn,*/ k, i);
	//Find-First-k-Candidate-Neighbours-Backward(D, p, b, 
	//    backwardSearch, k-Neighborhood);
	   Find_First_k_Candidate_Neighbours_Backward(D, p, b, backwardSearch, /*tn,*/ i);
	//Find-First-k-Candidate-Neighbours-Forward(D, p, f, 
	//    forwardSearch, k-Neighborhood);
	   Find_First_k_Candidate_Neighbours_Forward(D, p, f, forwardSearch, /*tn,*/ i);
	//Eps2 = max({0} + {e.dist2| e in k-Neighborhood});
	   double[] Eps = new double[1];
	   tn.getEps(Eps);
	//Verify-k-Candidate-Neighbours-Backward(D, p, b, backwardSearch,
	//    k-Neighborhood, Eps2);
            Verify_k_Candidate_Neighbours_Backward(D, p, b, backwardSearch, /*tn,*/ k, Eps);
            //Eps2[0] = tmpEps;
	//Verify-k-Candidate-Neighbours-Forward(D, p, f, forwardSearch,
	//    k-Neighborhood, Eps2);
            Verify_k_Candidate_Neighbours_Forward(D, p, f, forwardSearch, /*tn,*/ Eps);
	//return k-Neighborhood	     // component dist2 may not be returned
            //return k_Neighborhood;
            return tn.k_Neighborhood();
	}
	
	
	
	//function PrecedingPoint(D, var point p)
	public Integer PrecedingPoint(ArrayList<ISpatialObject> D, Point[] p) {
		Integer backwardSearch = 0;
	//if there is a point in D preceding p then
		if (p[0].pos > 0) {
	//    p = point immediately preceding p in D;
			p[0] = (Point) D.get(p[0].pos - 1);
	//    backwardSearch = true
			backwardSearch = 1;
	//else
		} else {
	//    backwardSearch = false
			backwardSearch = 0;
	//endif
		}
	//return backwardSearch
		return backwardSearch;
	}

	//function FollowingPoint(D, var point p)
	public Integer FollowingPoint(ArrayList<ISpatialObject> D, Point[] p) {
		Integer forwardSearch = 0;
	//if there is a point in D following p then
		if (p[0].pos + 1 < D.size()) {
	//    p = point immediately following p in D;
			p[0] = (Point) D.get(p[0].pos + 1);
	//    forwardSearch = true
			forwardSearch = 1;
	//else
		} else {
	//    forwardSearch = false
			forwardSearch = 0;
	//endif
		}
	//return forwardSearch
		return forwardSearch;
	}

	//function Find-First-k-Candidate-Neighbours-Forward&Backward(D, 
	//	    var point p, var point b, var point f, 
	//	    var backwardSearch, var forwardSearch, var k-Neighborhood,
	//          k, var i)
	public void Find_First_k_Candidate_Neighbours_Forward_Backward(ArrayList<ISpatialObject> D,
	        Point p, Point[] b, Point[] f, Integer backwardSearch, Integer forwardSearch, /*TriangleNeighborhood tn,*/
	        int k, int[] i) {    
	//while backwardSearch and forwardSearch and (i < k) do
	    while (backwardSearch == 1 && forwardSearch == 1 && (i[0] < k)) {
	//    i = i + 1;
	        i[0] = i[0] + 1;
	//    if p.dist - b.dist < f.dist - p.dist then
	        if (p.dist - b[0].dist < f[0].dist - p.dist) {
	//        dist2 = Distance2(b, p);
	            double dist2 = Distance.Distance2(b[0], p);
	//        insert element e = (position of b, dist2)
	//            in k-Neighborhood holding it sorted wrt. e.dist2;
	            //insertSorted(k_Neighborhood, dist, b[0]);
	            tn.insertSorted(dist2, b[0]);
	//        backwardSearch = PrecedingPoint(D, b)
	            backwardSearch = PrecedingPoint(D, b);
	//    else
	        } else {
	//        dist2 = Distance2(f, p);
	            double dist2 = Distance.Distance2(f[0], p);
	//        insert element e = (position of f, dist2) in 
	//            k-Neighborhood holding it sorted wrt. e.dist2;
	            //insertSorted(tn, dist, f[0]);
	            tn.insertSorted(dist2, f[0]);
	//        forwardSearch = FollowingPoint(D, f);
	            forwardSearch = FollowingPoint(D, f);
	//    endif
	        }
	//endwhile
	    }
	}
	
//	public static void print(ArrayList<ISpatialObject> neigh, Point p) {
//	    prn("" + p + " - ");
//            for (int j = 0; j < neigh.size(); j++) {
//                Point x = (Point) neigh.get(j);
//                prn(x + "-" + Distance.Distance(x, p) + " ");
//            }
//            prnl("");
//	}
//
//        public static void print2(ArrayList<ISpatialObject> neigh) {
//            for (int j = 0; j < neigh.size(); j++) {
//                Point x = (Point) neigh.get(j);
//                prn(x + " ");
//            }
//            prnl("");
//        }
//
        //function Find-First-k-Candidate-Neighbours-Backward(D,var point p,
	//	    var point b, var backwardSearch, var k-Neighborhood, k, var i)
	public void Find_First_k_Candidate_Neighbours_Backward(ArrayList<ISpatialObject> D,
			Point p, Point[] b, Integer backwardSearch, /*TriangleNeighborhood tn,*/ int[] i) {
	//	while backwardSearch and (i < k) do // TODO i is not initialized
	    while(backwardSearch == 1 && i[0] < k) {
	//    i = i + 1;
                i[0] = i[0] + 1;
	//    dist2 = Distance2(b, p);
                double dist2 = Distance.Distance2(b[0], p);
	//    insert element e = (position of b, dist2) in k-Neighborhood
	//        holding it sorted wrt. e.dist2;
                //insertSorted(k_Neighborhood, dist, b[0]);
                tn.insertSorted(dist2, b[0]);
	//    backwardSearch = PrecedingPoint(D, b)
                backwardSearch = PrecedingPoint(D, b);
	//	endwhile
	    }
	}

	//function Find-First-k-Candidate-Neighbours-Backward(D,var point p,
        //          var point b, var backwardSearch, var k-Neighborhood, k, var i)
        public void Find_First_k_Candidate_Neighbours_Forward(ArrayList<ISpatialObject> D,
                        Point p, Point[] f, Integer forwardSearch, /*TriangleNeighborhood tn,*/ int[] i) {
        //      while forwardSearch and (i < k) do // TODO i is not initialized
                while(forwardSearch == 1 && i[0] < k) {
        //          i = i + 1;
                        i[0] = i[0] + 1;
        //          dist2 = Distance2(b, p);
                        double dist2 = Distance.Distance2(f[0], p);
        //          insert element e = (position of b, dist2) in k-Neighborhood
        //              holding it sorted wrt. e.dist2;
                        //insertSorted(k_Neighborhood, dist, f[0]);
                        tn.insertSorted(dist2, f[0]);
        //          backwardSearch = PrecedingPoint(D, b)
                        forwardSearch = FollowingPoint(D, f);
        //      endwhile
                }
        }
//        
//        public static void prn(String s) {
//           // System.out.print(s);
//        }
//        
//        public static void prnl(String s) {
//           // System.out.println(s);
//        }
//


        // ---------------------------------------------------------------------
        public void Verify_k_Candidate_Neighbours_Backward(ArrayList<ISpatialObject> D, Point p,
                Point[] b, Integer backwardSearch, /*TriangleNeighborhood tn,*/ int k, double[] Eps2) {
            
            double Eps1 = Math.sqrt(Eps2[0]);
            
            while (backwardSearch == 1 && (p.dist - b[0].dist <= Eps1)) {
            
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

                backwardSearch = PrecedingPoint(D, b);

            }
        }

	//function Verify-k-Candidate-Neighbours-Forward(D, var point p, 
	//  var point f, var forwardSearch, var k-Neighborhood, var Eps2)
        public void Verify_k_Candidate_Neighbours_Forward(ArrayList<ISpatialObject> D, Point p, Point[] f, Integer forwardSearch,
                /*TriangleNeighborhood tn,*/ double[] Eps2) {

            double Eps1 = Math.sqrt(Eps2[0]);
            
            while (forwardSearch == 1 && (f[0].dist - p.dist <= Eps1)) {

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

                forwardSearch = FollowingPoint(D, f);

            }
        }
        
        protected void determineBorderCoordinates(Collection<ISpatialObject> objectsList) {
            minCoordinates = objectsList.iterator().next().getCoordinates().clone();
            maxCoordinates = minCoordinates.clone(); 

            for (ISpatialObject o : objectsList) {
                double[] coordinates = o.getCoordinates();
                for(int i = 0; i < coordinates.length; i++) {
                    
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
            zero = new Point(minCoordinates);
            createSortedTableD(objectsList, zero);
            logger.sortingEnd();
        }
        
        public void createSortedTableD(Collection<ISpatialObject> input,
                Point refPoint) {
            D = new ArrayList<ISpatialObject>();
            for (ISpatialObject o : input) {

                Point point = (Point) o;

                double distance2 = Distance.Distance2(refPoint, point);
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
//                prnl(p + " " + p.dist);
                p.pos = i;
            }
            
//            prn("x");

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
