/*
 * IIndex.java
 *
 * Created on 14 May 2008, 16:51
 *
 */

package lvaindex.vafile;

/**
 * 
 * @author Piotr Lasek, plasek@ii.pw.edu.pl
 */
public interface IIndex {
    public void add(ISpatialObject object);

    public void getNeighbors(ISpatialObject neighbor, int neighborsCount);
}
