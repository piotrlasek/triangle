package lvaindex.vafile;

import java.util.ArrayList;

public class LVAIndexOldInsert extends LVAIndex
{
	/**
	 * 
	 * @param numberOfDimensions
	 * @param bitsPerDimensions
	 * @param maxLayersCount
	 */
	LVAIndexOldInsert(int numberOfDimensions, int[] bitsPerDimensions, int maxLayersCount)
	{
		super(numberOfDimensions, bitsPerDimensions, maxLayersCount);
	}
	
	public long vafileTime = 0;
	
	/**
	 * 
	 */
	public void insert(ISpatialObject so)
	{
        Long approx = approximate(so.getCoordinates());
        so.setApproximation(approx);
        int[] vacoord = approximationToCoordinates(approx);
        so.setParentCellCoordinates(vacoord);
        
        long t = System.currentTimeMillis();
        
        if (!nonEmptyCells.contains(approx))
                nonEmptyCells.add(approx);
        
        long r = System.currentTimeMillis();
        vafileTime += (r - t);
        
        Cell thisCell = getCell(so.getParentCellCoordinates());
        thisCell.addObject(so);
        
        
        // TODO: try to replace by scanFast!
        for (int lc = 0; lc < Cell.maxLayersCount; lc++)
        {
            ArrayList<Cell> layerCells = this.scan(approx, lc);
            for(int i = 0; i < layerCells.size(); i++)
            {
                Cell layerCell = layerCells.get(i);
                
                ArrayList<Cell> layerCells2 = layerCell.getNeighborCells(lc);
                
                if (!layerCells2.contains(thisCell))
                    layerCells2.add(thisCell);
                    
                if (!thisCell.getNeighborCells(lc).contains(layerCell))
                    thisCell.getNeighborCells(lc).add(layerCell);
            }
        }
        
        int[] approxcoord = vacoord;
        
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
    }
}
