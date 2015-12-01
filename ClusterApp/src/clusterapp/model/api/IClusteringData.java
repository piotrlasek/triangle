package clusterapp.model.api;

import java.util.Collection;

import lvaindex.vafile.ISpatialObject;

public interface IClusteringData {
	
	/**
	 * 
	 */
	public void reset();
	
	/**
	 * 
	 */
	public void set(Collection<IClusteringObject> collection);
	
	/**
	 * 
	 * @return
	 */
	public Collection<IClusteringObject> get();
	
}
