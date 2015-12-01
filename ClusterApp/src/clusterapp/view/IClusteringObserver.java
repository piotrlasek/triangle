package clusterapp.view;

import java.util.Collection;

import clusterapp.model.api.IClusteringData;

/**
 * 
 * @author Piotr Lasek
 *
 */
public interface IClusteringObserver
{
	
	/**
	 * 
	 */
	public void handleNotify(Object object);
		
	/**
	 * 
	 */
	public void handleNotify(IClusteringData data);
	
	/**
	 * 
	 * @param message
	 */
	public void handleNotify(String message);
}
