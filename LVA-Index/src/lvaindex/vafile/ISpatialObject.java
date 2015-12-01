/*
 * ISpatialObject.java
 *
 * Created on 14 maj 2008, 16:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package lvaindex.vafile;

import java.util.ArrayList;

/**
 *
 * @author Piotr Lasek
 */
public interface ISpatialObject {

    /**
     * 
     * @param coordinates
     */
    public void setCoordinates(double[] coordinates);
    
    /**
     * 
     * @return
     */
    public double[] getCoordinates();
    
    /**
     * 
     * @param coordinates
     */
    public void setParentCellCoordinates(int[] coordinates);
    
    /**
     * 
     * @return
     */
    public int[] getParentCellCoordinates();
    
    /**
     * 
     * @return
     */
    public Cell getParentCell();
    
    /**
     * 
     */    
    public void setParentCell(Cell c);

    /**
     * 
     * @param approximation
     */
    public void setApproximation(Long approximation);
    
    /**
     * 
     * @return
     */
    Long getApproximation();

    /**
     * 
     * @param value
     */
    public void setValue(int value);
    
    /**
     * 
     * @return
     */
    public int getValue();
    
    /**
     * 
     * @param k
     * @return
     */
    public ArrayList<ISpatialObject> getNeighbors(int k);
    
}
