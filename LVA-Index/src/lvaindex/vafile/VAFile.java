/*******************************************************************************
 *
 *
 *
 *
 *
 ******************************************************************************/

package lvaindex.vafile;

import lvaindex.my.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * 
 * 
 * @author Piotr Lasek
 */
public class VAFile extends BasicVAFile
{
    public static final String NAME = "VAFile";
    /**
     * 
     * @param aNumberOfDimensions
     * @param bitsPerDimensions
     */
    public VAFile(int aNumberOfDimensions, int[] bitsPerDimensions)
    {
        super(aNumberOfDimensions, bitsPerDimensions);
    }
    
    public String getName() {
        return VAFile.NAME;
    }
    
    /**
     * 
     * @param k
     * @param m
     * @param q
     * @param id
     */
    public void SSA(int k, double m, ISpatialObject q)
    {
        //System.out.println("ssa");    
    	int k2 = 2*k;
        initCandidate(k2, m);
        double dist = 0;
        double max = m;
        for (int i = 0; i < objects.size(); i++)
        {
            ISpatialObject p = objects.get(i);

            /*first phase: filtering step*/
            double lB = lowerBound(p, q);
            if (lB < max)
            {
                /*second phase: refinement step*/
                dist = getDist(p, q);
                max = candidate(k2, p, dist);
            }
        }
    }
    
    /**
     * 
     * @param so
     */
    void insert(ISpatialObject so)
    {
        //System.out.println("abc");      
        Long approx = approximate(so.getCoordinates());
        so.setApproximation(approx);
        int[] approxcoord = approximationToCoordinates(approx);
        so.setParentCellCoordinates(approxcoord) ;
        
        for (int i = 0; i < dimensionsCount; i++ )
        {
            Double li = (double) sizes[i] * approxcoord[i];
            Double ui = (double) sizes[i] * (approxcoord[i] + 1);

            // sets values of the lower bounds table
            if (l.get(i) == null)
            {
                ArrayList al = new ArrayList<Double>();
                l.set(i, al);
            }
            ArrayList<Double> al = (ArrayList<Double>) l.get(i);
            Double d = al.get( approxcoord[i] );
            al.set( approxcoord[i], li );
            
            // sets values of the upper bounds table
            if (u.get(i) == null)
            {
                ArrayList au = new ArrayList<Double>();//l.get(i);
                u.set(i, au);
            }
            ArrayList<Double> au = (ArrayList<Double>) u.get(i);
            au.set( approxcoord[i], ui );
        }
        
        if (!nonEmptyCells.contains(approx))
                nonEmptyCells.add(approx);
        
        Cell c = getCell(so.getParentCellCoordinates());
        c.addObject(so);
        so.setParentCell(c);
    }
    
    /**
     * 
     * @param object
     * @param count
     * @return
     */
    public ArrayList<ISpatialObject> getNeighbors(ISpatialObject object,
            int k, double max)
    {
        //System.out.println("getNeighbors");    
        ArrayList<ISpatialObject> neighbors = new ArrayList<ISpatialObject>();

        this.SSA(k, max, object);
        
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

    public ArrayList<ISpatialObject> getLayer(ISpatialObject object,
            int l)
    {
    	ArrayList<ISpatialObject> layerObjects = new ArrayList<ISpatialObject>();
    	ArrayList<Cell> layer = scan(object.getApproximation(), l);
        
    	for(Cell c:layer)
    	{
    		layerObjects.addAll(c.getObjects());
    	}
    	
    	/*ArrayList<ISpatialObject> neighbors = new ArrayList<ISpatialObject>();
        int objectsCount = 0;
        int iter = 0;

        while((objectsCount < count)&&(iter < Cell.maxLayersCount))
        {
            ArrayList<Cell> nc = scan(object.getApproximation(), iter);
            for(Cell c:nc)
                neighbors.addAll(c.getObjects());
            
            if (neighbors.size() >= count)
                break;

            if (iter == Cell.maxLayersCount - 1)
            {
                System.out.println("VAFile - maxLayersCount exceeded");
                System.out.println("i = " + iter);
                //System.exit( -1);
            }
            iter++;
        }*/
        return layerObjects;
    }


    
    /**
     * 
     * @param object
     * @param count
     * @return
     */
    /*public ArrayList<ISpatialObject> getNeighbors(ISpatialObject object,
            int count)
    {
        ArrayList<ISpatialObject> neighbors = new ArrayList<ISpatialObject>();
        int objectsCount = 0;
        int iter = 0;

        while((objectsCount < count)&&(iter < Cell.maxLayersCount))
        {
            ArrayList<Cell> nc = scan(object.getApproximation(), iter);
            for(Cell c:nc)
                neighbors.addAll(c.getObjects());
            
            if (neighbors.size() >= count)
                break;

            if (iter == Cell.maxLayersCount - 1)
            {
                System.out.println("VAFile - maxLayersCount exceeded");
                System.out.println("i = " + iter);
                //System.exit( -1);
            }
            iter++;
        }
        return neighbors;
    }*/
}