package lvaindex.vafile;

import java.util.ArrayList;

public class CellManager
{
	Cell[] cells;
	int[] bitsPerDimension;
	int numberOfDimensions;
	int index;
	
	/**
	 * 
	 */
	CellManager(int[] bitsPerDimension, int numberOfDimensions)
	{
		this.bitsPerDimension = bitsPerDimension;
		this.numberOfDimensions = numberOfDimensions;
		int total = getNumberOfCellsPerDimension(0);
		for(int i = 1; i < numberOfDimensions; i++)
		{
			total *= getNumberOfCellsPerDimension(i);
		}
		System.out.println("total: " + total);
		cells = new Cell[total];
	}
	
    /**
     * 
     * @param aDimension
     * @return
     */
    protected int getNumberOfCellsPerDimension(int dimension)
    {
        int n = (int)(long) Math.pow(2, bitsPerDimension[dimension]);
        return n;
    }
	
	/**
	 * 
	 * @param coordinates
	 * @return
	 */
	public Cell getCell(int[] coordinates)
	{
		Cell c = null;
		int index = 0;
		
		for(int i = 0; i < numberOfDimensions-1; i++)
		{
			index += getNumberOfCellsPerDimension(numberOfDimensions - i - 1) * coordinates[i];
		}
		
		index += coordinates[numberOfDimensions - 1];
		
		c = cells[index];
		
		return c;
	}
	
	public void setCell(Cell c)
	{
		cells[index] = c;
	}
	
	public Cell getCell2(int[] coordinates)
	{
		Cell c = getCell(coordinates);
		if (c == null)
		{
			c = new Cell();
			setCell(c);
		}
		return c;
	}
	
	
	
	public static void main(String[] args)
	{
		CellManager cm = new CellManager(new int[]{5,3,4,5,3}, 5);
		
		Cell c0 = cm.getCell2(new int[]{1,1,1,1,1});
		Cell c1 = cm.getCell2(new int[]{1,1,2,1,1});
		Cell c2 = cm.getCell2(new int[]{1,1,3,1,1});
		Cell c3 = cm.getCell2(new int[]{2,2,3,1,1});
		Cell c4 = cm.getCell2(new int[]{2,2,4,1,1});
		
	}
}
