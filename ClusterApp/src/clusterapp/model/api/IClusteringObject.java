package clusterapp.model.api;

import lvaindex.vafile.ISpatialObject;

public interface IClusteringObject
{
	public ISpatialObject getSpatialObject();
	
	public IClusterInfo getClusterInfo();
	
	public void setSpatialObject(ISpatialObject spatialObject);
	
	public void setClusterInfo(IClusterInfo clusterInfo);
}
