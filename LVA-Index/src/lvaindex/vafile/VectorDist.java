/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lvaindex.vafile;

/**
 *
 * @author Piotr Lasek
 */
public class VectorDist implements Comparable
{
    /**
     * 
     */
    public double dist;
    
    /**
     * Temporary id.
     */
    int id;
    
    /**
     * 
     */
    public ISpatialObject vector;

    /**
     * 
     * @param o
     * @return
     */
    public int compareTo(Object o)
    {
        VectorDist vd = (VectorDist) o;
        if ( vd.dist > dist )
            return -1;
        else if (vd.dist < dist)
            return 1;
        else
            return 0;
    }

    /**
     * 
     * @return
     */
    public String toString()
    {
        return vector.toString() + ", ";// + dist;
    }

}