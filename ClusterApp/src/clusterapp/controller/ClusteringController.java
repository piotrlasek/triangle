package clusterapp.controller;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

import clusterapp.model.api.BasicClusteringParameters;
import clusterapp.model.api.IClusteringAlgorithm;
import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringDataSource;
import clusterapp.view.IClusteringObserver;

/**
 * 
 * @author Piotr Lasek
 *
 */
public class ClusteringController implements IClusteringObserver
{	
	IClusteringAlgorithm algorithm;
	IClusteringData data;
	ArrayList<IClusteringObserver> observers;
	IClusteringDataSource dataSource;
	Graphics graphics;
	
	/**
	 * 
	 * @param dv
	 */
	public void setDataSource(IClusteringDataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	/**
	 * 
	 * @param algorithmName
	 */
	public void run(String algorithmName)
	{
		algorithm = AlgorithmFactory.createAlgorithm(algorithmName);
		algorithm.setObserver(this);
		algorithm.setGraphics(graphics);
		BasicClusteringParameters bcp = new BasicClusteringParameters();
		bcp.setValue("k", "22");	
		bcp.setValue("nDim", "2");
		algorithm.setParameters(bcp);
		algorithm.setData(dataSource.getData());
		algorithm.run();
		notifyObservers();
	}
	
	public void run(BasicClusteringParameters bcp)
	{
		String algorithmName = bcp.getValue("algorithm_name");
		bcp.remove("algorithm_name");
		algorithm = AlgorithmFactory.createAlgorithm(algorithmName);
		algorithm.setObserver(this);
		algorithm.setGraphics(graphics);
		algorithm.setParameters(bcp);
		algorithm.setData(dataSource.getData());
		algorithm.run();
		notifyObservers();		
	}
	
	/**
	 * 
	 */
	private void prepareData(String algorithmName)
	{
		data = dataSource.getData();
	}

	/**
	 * 
	 */
	public ClusteringController()
	{
		observers = new ArrayList<IClusteringObserver>();
	}
	
	/**
	 * 
	 * 
	 */
	public void addObserver(IClusteringObserver observer)
	{
		observers.add(observer);
	}
	
	/**
	 * 
	 */
	public void notifyObservers()
	{
		for(IClusteringObserver co:observers)
		{
			co.handleNotify(algorithm.getResult());
		}
	}
	
	/**
	 * 
	 * @param s
	 */
	public void notifyObservers(String s)
	{
		for(IClusteringObserver co:observers)
		{
			co.handleNotify(s);
		}
	}

	@Override
	public void handleNotify(IClusteringData data)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleNotify(String message)
	{
		notifyObservers(message); 		
	}
	
	public void setGraphics(Graphics g)
	{
		this.graphics = g;
	}

	@Override
	public void handleNotify(Object object)
	{
		// TODO Auto-generated method stub
		for(IClusteringObserver co:observers)
		{
			co.handleNotify(object);
		}
	}
	
	
}
