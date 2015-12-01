/*
 * NBC.java
 *
 * Created on 14 maj 2005, 09:07
 *
 */

package clusterapp.model.api;

import lvaindex.common.*;
import lvaindex.my.*;
import lvaindex.vafile.*;
import lvaindex.view.*;

import java.awt.Graphics;
import java.awt.Dialog.ModalityType;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

import java.util.*;

import spatialindex.rtree.RTree;
import spatialindex.spatialindex.IData;
import spatialindex.spatialindex.INode;
import spatialindex.spatialindex.ISpatialIndex;
import spatialindex.spatialindex.IVisitor;
import spatialindex.storagemanager.IBuffer;
import spatialindex.storagemanager.MemoryStorageManager;
import spatialindex.storagemanager.PropertySet;
import spatialindex.storagemanager.RandomEvictionsBuffer;

import clusterapp.model.nbc.NbcSpatialObject;
import clusterapp.model.nbc.NBCRTreePoint;
import clusterapp.model.nbc.NBCRTree;

import clusterapp.view.IClusteringObserver;
import clusterapp.view.LvaNbcParametersFrame;
import clusterapp.view.MainWindow;
import clusterapp.view.ParametersFrame;

/**
 * 
 * @author pl
 */
public class IndicesTest implements IClusteringAlgorithm {
	
	public static final String NAME = "IndicesTest";
	
        //String desc = "IndicesTest";
	
	Graphics graphics = null;

	int nDim = 0;
	int id = 0;
	int k = 0;

	LVAIndex lva;
	VAFile vaf;
	ISpatialIndex tree;
	ArrayList Dataset;
	ArrayList<ISpatialObject> dataset;
	
	//static double max = 600;
	static double max = 1;

	/** Creates a new instance of NBC */
	public IndicesTest()
	{
		
	}

	public void run()
	{
		run2();
	}
	
	public void run2()
	{
		ListIterator li = Dataset.listIterator();
		for (int i = 0; i < dataset.size(); i++)
		{
			NbcSpatialObject p = (NbcSpatialObject) dataset.get(i);
			ArrayList<ISpatialObject> knbL = (ArrayList<ISpatialObject>)lva.getNeighbors(p, k, IndicesTest.max);
			ArrayList<ISpatialObject> knbV = (ArrayList<ISpatialObject>)vaf.getNeighbors(p, k, IndicesTest.max);
			
			NBCRTreePoint p2 = (NBCRTreePoint) li.next();
			MyVisitor kNN = new MyVisitor();
			tree.nearestNeighborQuery(k, p2, kNN);
			
			System.out.println("----------------");
			System.out.println(knbL);
			System.out.println(knbV);
			System.out.println(kNN.toString());
		}

	}
	
	public void run1()
	{
		double timeLVA = 0;
		double timeRTree = 0;
		
		long begin = 0;
		long end = 0;
		
		begin = System.currentTimeMillis();
		for (int i = 0; i < dataset.size(); i++)
		{
			NbcSpatialObject p = (NbcSpatialObject) dataset.get(i);
			ArrayList<ISpatialObject> kNBp = (ArrayList<ISpatialObject>)lva.getNeighbors(p, k, IndicesTest.max);
		}
		end = System.currentTimeMillis();
		timeLVA = end - begin;
		
		begin = end = 0;
		
		begin = System.currentTimeMillis();
		ListIterator li = Dataset.listIterator();
		while (li.hasNext())
		{
			NBCRTreePoint p = (NBCRTreePoint) li.next();
			MyVisitor kNN = new MyVisitor();
			tree.nearestNeighborQuery(k, p, kNN);
		}
		end = System.currentTimeMillis();
		timeRTree = end - begin;
		
		System.out.println( "LVA-Index: " + timeLVA + ", RTree: " + timeRTree );
	}


	@Override
	public IClusteringData getResult()
	{	
		BasicClusteringData bcd = new BasicClusteringData();
		return bcd;
	}
	
	@Override
	public void setData(IClusteringData data) {
		
		nDim = data.get().iterator().next().getSpatialObject().getCoordinates().length;
		k = 22;
		
		int[] bits = new int[nDim];
		for(int i = 0; i < nDim; i++)
			bits[i] = 4;
		
		lva = new LVAIndex(nDim, bits, 3);        //VAFile vaf2d = new VAFile( dim, new int[]{1,1,2,2,2,1,2,2} );
		vaf = new VAFile(nDim, bits);        //VAFile vaf2d = new VAFile( dim, new int[]{1,1,2,2,2,1,2,2} );

		Collection<IClusteringObject> input = data.get();
		dataset = new ArrayList<ISpatialObject>();
		
		for(IClusteringObject co:input)
		{
			dataset.add(new NbcSpatialObject(co.getSpatialObject()));
		}
		
		lva.add(dataset);
		vaf.add(dataset);
		
		ArrayList<IClusteringObject> tmp = (ArrayList<IClusteringObject>)data.get();
		Dataset = new ArrayList();
		
		try {
			initRTree();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		}

		// building R-Tree
		for (IClusteringObject ico:tmp)
		{
			NBCRTreePoint mp = new NBCRTreePoint( ico.getSpatialObject().getCoordinates(), -1 );
			Dataset.add(mp);
			tree.insertData(null, mp, id++);
		}

	}
	
	// -------------
	
	public void initRTree() throws IOException
	{
		PropertySet ps = new PropertySet();
		Boolean b = new Boolean(true);
		ps.setProperty("Overwrite", b);
		// overwrite the file if it exists.
		ps.setProperty("FileName", "nbc-rtree");
		Integer i = new Integer(128);
		ps.setProperty("PageSize", i);

		// IStorageManager diskfile = new DiskStorageManager(ps);
		// IBuffer file = new RandomEvictionsBuffer(diskfile, 10, false);
		// applies a main memory random buffer on top of the persistent storage
		// manager (LRU buffer, etc can be created the same way).

		// Create a new, empty, RTree with dimensionality 2, minimum load 70%,
		// using "file" as the StorageManager and the RSTAR splitting policy.
		PropertySet ps2 = new PropertySet();

		Double f = new Double(0.1);

		ps2.setProperty("FillFactor", f);

		i = new Integer(32);

		ps2.setProperty("IndexCapacity", i);
		ps2.setProperty("LeafCapacity", i);
		// Index capacity and leaf capacity may be different.

		i = new Integer(nDim);
		ps2.setProperty("Dimension", i);

		MemoryStorageManager memmanag = new MemoryStorageManager();
		IBuffer mem = new RandomEvictionsBuffer(memmanag, 40000, false);

		tree = new RTree(ps2, mem);
	}

	// example of a Visitor pattern.
	// findes the index and leaf IO for answering the query and prints
	// the resulting data IDs to stdout.
	class MyVisitor implements IVisitor
	{
		public int m_indexIO = 0;

		public int m_leafIO = 0;

		public int kNB = 0;

		ArrayList neighbours = new ArrayList();

		ArrayList n = new ArrayList();

		public void reset() {
			kNB = 0;
			neighbours.clear();
		}

		public void visitNode(final INode n) {
			if (n.isLeaf())
				m_leafIO++;
			else
				m_indexIO++;
		}

		public void visitData(final IData d) {
			kNB++;
			NBCRTreePoint np = new NBCRTreePoint(d.getShape().getCenter(), -1);
			neighbours.add(Dataset.get(Dataset.indexOf(np)));
			n.add(d);
		}
	}
	
	// -------------

	@Override
	public IClusteringParameters getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParameters(IClusteringParameters parameters)
	{

	}

	@Override
	public void setObserver(IClusteringObserver observer)
	{
		
	}
	
	/**
	 * 
	 */
	public void setGraphics(Graphics g)
	{
		this.graphics = g;
	}

	@Override
	public String getName() {
	    return IndicesTest.NAME;
	}

    @Override
    public void addDescription(String description) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

	
}
