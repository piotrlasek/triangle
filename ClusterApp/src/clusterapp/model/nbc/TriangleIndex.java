package clusterapp.model.nbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.print.attribute.HashAttributeSet;


import lvaindex.vafile.ClusteringLogger;
import lvaindex.vafile.ISpatialObject;

import util.Distance;

public class TriangleIndex extends LoggingIndex implements lvaindex.vafile.ISpatialIndex  {
	
	int k = 0;
	Point zero;
	
        double[] minCoordinates;
        double[] maxCoordinates; 
        
	ArrayList<ISpatialObject> D;
	
	//TriangleNeighborhood2 tn = new TriangleNeighborhood2();
	//TriangleNeighborhood tn = new TriangleNeighborhood();
	TriangleNeighborhoodOpt tn = new TriangleNeighborhoodOpt();

	/**
	 * 
	 * @param k
	 */
	public TriangleIndex(int k) {
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
	   double[] Eps2 = new double[1];
	   //getEps(k_Neighborhood, Eps2);
	   
	   tn.getEps(Eps2);
	   double tmpEps = Eps2[0];
	//Verify-k-Candidate-Neighbours-Backward(D, p, b, backwardSearch,
	//    k-Neighborhood, Eps2);
            Verify_k_Candidate_Neighbours_Backward(D, p, b, backwardSearch, /*tn,*/ k, Eps2);
            //Eps2[0] = tmpEps;
	//Verify-k-Candidate-Neighbours-Forward(D, p, f, forwardSearch,
	//    k-Neighborhood, Eps2);
            Verify_k_Candidate_Neighbours_Forward(D, p, f, forwardSearch, /*tn,*/ Eps2);
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
	            double dist = Distance.Distance(b[0], p);
	//        insert element e = (position of b, dist2)
	//            in k-Neighborhood holding it sorted wrt. e.dist2;
	            //insertSorted(k_Neighborhood, dist, b[0]);
	            tn.insertSorted(dist, b[0]);
	//        backwardSearch = PrecedingPoint(D, b)
	            backwardSearch = PrecedingPoint(D, b);
	//    else
	        } else {
	//        dist2 = Distance2(f, p);
	            double dist = Distance.Distance(f[0], p);
	//        insert element e = (position of f, dist2) in 
	//            k-Neighborhood holding it sorted wrt. e.dist2;
	            //insertSorted(tn, dist, f[0]);
	            tn.insertSorted(dist, f[0]);
	//        forwardSearch = FollowingPoint(D, f);
	            forwardSearch = FollowingPoint(D, f);
	//    endif
	        }
	//endwhile
	    }
	}
	
	//function Find-First-k-Candidate-Neighbours-Backward(D,var point p,
	//	    var point b, var backwardSearch, var k-Neighborhood, k, var i)
	public void Find_First_k_Candidate_Neighbours_Backward(ArrayList<ISpatialObject> D,
			Point p, Point[] b, Integer backwardSearch, /*TriangleNeighborhood tn,*/ int[] i) {
	//	while backwardSearch and (i < k) do // TODO i is not initialized
	    while(backwardSearch == 1 && i[0] < k) {
	//    i = i + 1;
                i[0] = i[0] + 1;
	//    dist2 = Distance2(b, p);
                double dist = Distance.Distance(b[0], p);
	//    insert element e = (position of b, dist2) in k-Neighborhood
	//        holding it sorted wrt. e.dist2;
                //insertSorted(k_Neighborhood, dist, b[0]);
                tn.insertSorted(dist, b[0]);
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
                        double dist = Distance.Distance(f[0], p);
        //          insert element e = (position of b, dist2) in k-Neighborhood
        //              holding it sorted wrt. e.dist2;
                        //insertSorted(k_Neighborhood, dist, f[0]);
                        tn.insertSorted(dist, f[0]);
        //          backwardSearch = PrecedingPoint(D, b)
                        forwardSearch = FollowingPoint(D, f);
        //      endwhile
                }
        }
        
        //function Verify-k-Candidate-Neighbours-Backward(D, var point p, 
	//	  var point b, var backwardSearch, var k-Neighborhood, k, var Eps2k)
        public void Verify_k_Candidate_Neighbours_Backward(ArrayList<ISpatialObject> D, Point p,
                Point[] b, Integer backwardSearch, /*TriangleNeighborhood tn,*/ int k, double[] Eps) {
//	// Eps = (Eps2)1/2;
//        //    double Eps = Math.sqrt(Eps[0]);
//	// while backwardSearch and (p.dist – b.dist <= Eps) do
//            prnl("----------------------------------------------------------------------------------------");
//            prnl("Verify_k_Candidate_Neighbours_Backward");
//            prnl("----------------------------------------------------------------------------------------");
//            prnl("\tbackwardSearch=" + backwardSearch);
//            prnl("\tb=" + b[0]);
//            prnl("\tEps=" + Eps[0]);
            //prn("p.dist-b.dist <= Eps " + (x < Eps[0]?"TRUE":"FALSE"));
//            int xx = 1;
            while (backwardSearch == 1 && (p.dist - b[0].dist <= Eps[0])) {
//                prnl("\t------ backward " + xx++ + "---------");
//                double x = p.dist-b[0].dist;
//                prnl("\t\tp.dist - b.dist = " + p.dist + "-" + b[0].dist + "=" + x );
//                prnl("\t\tEps=" + Eps[0]);
//                prnl("\t\tp.dist-b.dist <= Eps " + (x < Eps[0]?"TRUE":"FALSE"));
//                double[] xxx = p.getCoordinates();
//                if (xxx[0] == 40.0 && xxx[1] == 165.0) {
//                    int a = 100;
//                }
//                prn("Backward: " + Eps[0] + " " + (p.dist - b[0].dist));
	//    dist2 = Distance2(b, p);
                double dist = Distance.Distance(b[0], p);
//                prnl("\t\tdist2 = Distance2(b,p) = " + dist);
	//    if dist2 < Eps2 then
                
                if (dist < Eps[0]) {
//                    prnl("\t\tdist2 < Eps2 " + "TRUE");
	//        i = |{e in k-Neighborhood| e.dist2 = Eps2}};
                    //int i = getI(k_Neighborhood, Eps[0]);
                    int i = tn.getI(Eps[0]);
//                    prnl("\t\ti=" + i);
	//        if |k-Neighborhood| - i >=  k - 1 then
//                    prnl("\t\tk-Neighborhood|-i > k-1 = " + tn.size() + "-" + i + " >= " + k + "-1 " + ((tn.size()-i>=k-1)?"TRUE":"FALSE"));
                    if (tn.size() - i >= k - 1) {
	//            delete each element e with e.dist2 = Eps2
	//                from k-Neighborhood;
                        //remove(k_Neighborhood, Eps[0]);
                        tn.remove(Eps[0]);
        //              Eps2 = max({e.dist^2| e in k-Neighborhood});
                        //getEps(k_Neighborhood, Eps);
        //              Eps = (Eps2)1/2;
                        //Eps = Math.sqrt(Eps[0]); 
        //	        endif
                    
                        //insertSorted(k_Neighborhood, dist, b[0]);
                        tn.insertSorted(dist, b[0]);
                        
                        //Eps[0] = dist;
                        //getEps(k_Neighborhood, Eps);
                        tn.getEps(Eps);
//                        prnl("\t\tEps=" + Eps[0]);
                    } else {
	//        insert element e = (position of b, dist2) in 
	//            k-Neighborhood holding it sorted wrt. e.dist2;
                        //insertSorted(k_Neighborhood, dist, b[0]);
                        tn.insertSorted(dist, b[0]);
                    }
	//	    else if dist2 = Eps2
                } else if (dist == Eps[0]) {
//                    prnl("\t\tdist2 == Eps2 " + "TRUE");
    	//	    insert element e = (position of b, dist2) in 
    	//	        k-Neighborhood holding it sorted wrt. e.dist2
                    //insertSorted(k_Neighborhood, dist, b[0]);
                    tn.insertSorted(dist, b[0]);
    	//	endif
                }
	//	    backwardSearch = PrecedingPoint(D, b)
                backwardSearch = PrecedingPoint(D, b);
//                prnl("\t\tbackwardSearch=" + backwardSearch);
//                prnl("\t\tb = " + b[0]);
//                double x2 = p.dist - b[0].dist;
//                prnl("\t\tp.dist - b.dist = " + p.dist + "-" + b[0].dist + "=" + x2 );
//                prnl("\t\t" + ((backwardSearch == 1 && x2 <= Eps[0])?"next iteration":"stop"));
	//	endwhile
            }
        }

	//function Verify-k-Candidate-Neighbours-Forward(D, var point p, 
	//  var point f, var forwardSearch, var k-Neighborhood, var Eps2)
        public void Verify_k_Candidate_Neighbours_Forward(ArrayList<ISpatialObject> D, Point p, Point[] f, Integer forwardSearch,
                /*TriangleNeighborhood tn,*/ double[] Eps) {
	//Eps = (Eps2)1/2;
         //   double Eps = Math.sqrt(Eps2[0]);
	//while forwardSearch and (f.dist – p.dist <= Eps) do
//            prnl("----------------------------------------------------------------------------------------");
//            prnl("Verify_k_Candidate_Neighbours_Forward");
//            prnl("----------------------------------------------------------------------------------------");
//            prnl("\tforwardSearch=" + forwardSearch);
//            prnl("\tb=" + f[0]);
//            prnl("\tEps=" + Eps[0]);
//            int xx =0 ;
            while (forwardSearch == 1 && (f[0].dist - p.dist <= Eps[0])) {
//                prnl("\t------ forward " + xx++ + "---------");
//                double x = f[0].dist - p.dist;
//                prnl("\t\tf.dist - p.dist = " + f[0].dist + "-" + p.dist + "=" + x );
//                prnl("\t\tEps=" + Eps[0]);
//                prnl("\t\tf.dist - p.dist <= Eps " + (x < Eps[0]?"TRUE":"FALSE"));
	//    dist2 = Distance2(f, p);
        //        prn("Forward: " + Eps[0] + " " + (f[0].dist - p.dist));

                double dist = Distance.Distance(f[0], p);
//                prnl("\t\tdist2 = Distance2(b,p) = " + dist);
	//    if dist2 < Eps2 then
                //int i = getI(k_Neighborhood, Eps[0]);
                int i = tn.getI(Eps[0]);
//                prnl("\t\ti=" + i);
                
                if (dist < Eps[0]) {
//                    prnl("\t\tdist2 < Eps2 " + "TRUE");
	//        i = |{e  k-Neighborhood| e.dist2 = Eps2}};
	//        if |k-Neighborhood| - i >= k - 1 then
//                    prnl("\t\tk-Neighborhood|-i > k-1 = " + tn.size() + "-" + i + " >= " + k + "-1 " + ((tn.size()-i>=k-1)?"TRUE":"FALSE"));
                    if (tn.size() - i >= k - 1) {
                        //remove(k_Neighborhood, Eps[0]);
                        tn.remove(Eps[0]);
                        //insertSorted(k_Neighborhood, dist, f[0]);
                        tn.insertSorted(dist, f[0]);
                        //Eps[0] = dist;
                        //getEps(k_Neighborhood, Eps);
                        tn.getEps(Eps);
//                        prnl("\t\tEps=" + Eps[0]);
                    } else {
        //        Eps2 = dist2;
        //                 getEps(k_Neighborhood, Eps);
        //        Eps = (Eps2)1/2;
                  //      Eps = Math.sqrt(Eps[0]);

	//        endif
                    
	//        insert element e = (position of f, dist2) in 
	//            k-Neighborhood holding it sorted wrt. e.dist2;
                    //insertSorted(k_Neighborhood, dist, f[0]);
                    //Eps[0] = dist;
                        tn.insertSorted(dist, f[0]);
                    }                
	//    else if dist2 = Eps2
                } else if (dist == Eps[0]) {
	//        insert element e = (position of f, dist2) in 
	//            k-Neighborhood holding it sorted wrt. e.dist2
                    //insertSorted(k_Neighborhood, dist, f[0]);
//                    prnl("\t\tdist2 == Eps2 " + "TRUE");
                    tn.insertSorted(dist, f[0]);
	//    endif
                }
	//    forwardSearch = FollowingPoint(D, f)
                forwardSearch = FollowingPoint(D, f);
//                prnl("\t\tforwardSearch=" + forwardSearch);
//                prnl("\t\tf = " + f[0]);
//                double x2 = f[0].dist - p.dist;
//                prnl("\t\tf.dist - p.dist = " + f[0].dist + "-" + p.dist + "=" + x2 );
//                prnl("\t\t" + ((forwardSearch == 1 && x2 <= Eps[0])?"next iteration":"stop"));
	//endwhile
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

                double distance = Distance.Distance(refPoint, point);

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
