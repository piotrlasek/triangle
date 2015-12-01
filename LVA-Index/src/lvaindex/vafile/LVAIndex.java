/*
 * LVAFile (implements ISpatialIndex
 * 
 * 
 */

package lvaindex.vafile;

import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * @author Piotr Lasek
 */
public class LVAIndex extends BasicVAFile // which implements ISpatialIndex
{    
    
    public static final String NAME = "LVA-Index";
    /**
     * The LVAIndex constructor. 
     * 
     * @param numberOfDimensions
     * @param bitsPerDimensions
     * @param maxLayersCount
     */
    public LVAIndex(int numberOfDimensions, int[] bitsPerDimensions, int maxLayersCount)
    {
        super(numberOfDimensions, bitsPerDimensions);
        Cell.maxLayersCount = maxLayersCount;
    }
    
    public String getName() {
        return LVAIndex.NAME;
    }
    
    /**
     * 
     */
    void insert(ISpatialObject so)
    {
        Long approx = approximate(so.getCoordinates());
        so.setApproximation(approx);
        int[] vacoord = approximationToCoordinates(approx);
        so.setParentCellCoordinates(vacoord);
        
        if (!nonEmptyCells.contains(approx))
                nonEmptyCells.add(approx);
        
        Cell thisCell = getCell(so.getParentCellCoordinates());
        so.setParentCell(thisCell);
        thisCell.addObject(so);
        
        if (Cell.maxLayersCount > 0) {
            CubeCellsFinder ccf = new CubeCellsFinder();
            
            for (int lc = 0; lc < Cell.maxLayersCount; lc++)
            {
                ccf.reset(lc, dimensionsCount);
                
                //ArrayList<int[]> layerCellCoordinates = ccf.getLayerCells2(lc, dimensionsCount);
                int[] cellcoords = so.getParentCellCoordinates();
                int[] layercellcoords = null;
                //for(int i = 0; i < layerCellCoordinates.size(); i++)
                while((layercellcoords = ccf.getLayerCellsNext(lc, dimensionsCount)) != null)
                {
                    //int[] layercellcoords = layerCellCoordinates.get(i);
                    boolean breakfor = false;
                    Cell layerCell = null;

                    for (int ii = 0; ii < dimensionsCount; ii++) {
                        layercellcoords[ii] += cellcoords[ii];
                        if (layercellcoords[ii] < 0
                                || layercellcoords[ii] >= getNumberOfCellsPerDimension(ii)) {
                            breakfor = true;
                        }
                    }

                    /*
                     * if (breakfor || (layerCell =
                     * this.getCell2(layercellcoords)) == null) continue;
                     */

                    if (breakfor)
                        continue;

                    layerCell = this.getCell2(layercellcoords);
                    if (layerCell == null)
                        continue;

                    ArrayList<ISpatialObject> objects = layerCell.getObjects();
                    if (objects == null || objects.size() == 0)
                        continue;

                    ArrayList<Cell> layerCells2 = layerCell
                            .getNeighborCells(lc);

                    if (!layerCells2.contains(thisCell))
                        layerCells2.add(thisCell);

                    if (!thisCell.getNeighborCells(lc).contains(layerCell))
                        thisCell.getNeighborCells(lc).add(layerCell);
                }
            }
        }
        
        // setting upper and lower bounds
        int[] approxcoord = vacoord;
        
        for (int i = 0; i < dimensionsCount; i++ )
        {
            Double li = (double) sizes[i] * approxcoord[i];
            Double ui = (double) sizes[i] * (approxcoord[i] + 1);

            // sets values of the lower bounds table
            if (l.get(i) == null)
            {
                ArrayList<Double> al = new ArrayList<Double>();
                l.set(i, al);
            }
            ArrayList<Double> al = (ArrayList<Double>) l.get(i);
            al.set( approxcoord[i], li );
            
            // sets values of the upper bounds table
            if (u.get(i) == null)
            {
                ArrayList<Double> au = new ArrayList<Double>();
                u.set(i, au);
            }
            ArrayList<Double> au = (ArrayList<Double>) u.get(i);
            au.set( approxcoord[i], ui );
        }
    }
    
    /**
     * 
     * @param so
     */
    void insertNew(ISpatialObject so)
    {}
    
    /**
     * 
     * @param so
     */
    void insertOld(ISpatialObject so)
    {}

//    
//    /**
//     * 
//     * @param object
//     * @param k
//     * @return
//     */
//    public Collection<ISpatialObject> getNeighbors(ISpatialObject q, int k)
//    {
//        return null;
//    }
    

    /**
     * 
     * @param q
     * @param l
     * @return
     */
    public ArrayList<ISpatialObject> getLayer( ISpatialObject q, int l )
    {
        ArrayList<ISpatialObject> neighbors = new ArrayList<ISpatialObject>();

        //Cell c = getCell(this.approximationToCoordinates(q.getApproximation()));
        Cell c = q.getParentCell();
        
        ArrayList<Cell> nn = c.getNeighborCells(l);
        
        for(Cell cc:nn)
        {
            if(cc.getObjectsCount() > 0)
            {
                neighbors.addAll(cc.getObjects());
            }
        }
        
        return neighbors;
    }

    /**
     * 
     * @param q
     * @param k
     * @param max
     * @return
     */
    public ArrayList<ISpatialObject> getNeighbors(ISpatialObject q, int k,
            double max)
    {
    	int k2 = 2*k;
        ArrayList<ISpatialObject> neighbors = new ArrayList<ISpatialObject>();
        //Cell c = getCell(this.approximationToCoordinates(q.getApproximation()));
        //Cell c = getCell(q.getParentCellCoordinates());
        Cell c = q.getParentCell();
        
        double dist = 0;
        
        initCandidate(k2, max);

        int layersCount = c.getNeighborLayersCount();
        assert(layersCount != 0);
        
        for(int i = 0; i < layersCount; i++)
        {
            //System.out.println("Searching in layer: " + i);
            ArrayList<Cell> nn = c.getNeighborCells(i);
            
            for(Cell cc:nn)
            {
                // TODO -- remove the line below
                if(cc.getObjectsCount() > 0) // probably there is no need to check this statement
                {
                    // TODO -- do not remember neighbors in this was
                    // neighbors.addAll(cc.getObjects());
                    // PREVIOUS VERSION -
                    // set dst table
                    // FILTERING
                    ISpatialObject p0 = cc.getObjects().get(0);
                    double  lB = this.lowerBound( p0, q);
                    
                    if (lB < max)
                    {
                        for(ISpatialObject p:cc.getObjects())
                        {
                            /*second phase: refinement step*/
                            dist = getDist(p, q);
                            max = candidate(k2, p, dist);
                        }
                    }

                }
            }
            
            // Refine results.
            
            // This is an issue to be investigated!!!

            /*if (neighbors.size() >= k)
                break;*/
            
//            if ( i == Cell.maxLayersCount - 1)
//            {
//                System.out.println("LVAFile - maxLayersCount exceeded");
//                System.out.println("i = " + i);
//                return neighbors;
//            }
        }
        
        double prevDist = 0;
        double nextDist = 0;
        int count = 0;
        
        /*for (VectorDist vd:dst)
        {
        	if (vd.vector != null)
        		neighbors.add((ISpatialObject)vd.vector);
        }*/
        
        for (int i = 1; (i < dst.length) && (count <= k); i++ )
        {
        	nextDist = dst[i].dist;
        	ISpatialObject o = dst[i].vector;
        	if ( o != null )
        	{
        		neighbors.add( o );
        		if ( i > 1 )
        		{
        			if ( nextDist != prevDist )
        			{
        				count++;
        			}
        			else
        			{
        				//System.out.println("the same");
        			}
        			prevDist = dst[i].dist;
        		}
        	}
        	else
        		break;
        }
        
        return neighbors;
    }


    
}
