package lvaindex.vafile;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;

import junit.framework.TestCase;

public class LVAFileTest extends TestCase {

	LVAIndex lva; 
	int nDim = 2;
	

	
	public void testLVABuildingBenchmark()
	{
		String b = "C:\\Documents and Settings\\piotr.lasek\\workspace\\Data\\";
		
		singleTest(2, new int[]{5, 5}, 3, b + "syntetic\\2D\\syn_2d_1000.txt", 1000);
		singleTest(2, new int[]{5, 5}, 3, b + "syntetic\\2D\\syn_2d_2000.txt", 1500);
		singleTest(2, new int[]{5, 5}, 3, b + "syntetic\\2D\\syn_2d_2000.txt", 2000);
		singleTest(2, new int[]{5, 5}, 3, b + "syntetic\\2D\\syn_2d_5000.txt", 2500);
		singleTest(2, new int[]{5, 5}, 3, b + "syntetic\\2D\\syn_2d_5000.txt", 3000);
		singleTest(2, new int[]{5, 5}, 3, b + "syntetic\\2D\\syn_2d_5000.txt", 3500);
		singleTest(2, new int[]{5, 5}, 3, b + "syntetic\\2D\\syn_2d_5000.txt", 4000);
		singleTest(2, new int[]{5, 5}, 3, b + "syntetic\\2D\\syn_2d_5000.txt", 4500);
		singleTest(2, new int[]{5, 5}, 3, b + "syntetic\\2D\\syn_2d_5000.txt", 5000);
		
		singleTest(3, new int[]{5, 5, 5}, 3, b + "syntetic\\3D\\syn_3d_1000.txt", 1000);
		singleTest(3, new int[]{5, 5, 5}, 3, b + "syntetic\\3D\\syn_3d_2000.txt", 1500);
		singleTest(3, new int[]{5, 5, 5}, 3, b + "syntetic\\3D\\syn_3d_2000.txt", 2000);
		singleTest(3, new int[]{5, 5, 5}, 3, b + "syntetic\\3D\\syn_3d_5000.txt", 2500);
		singleTest(3, new int[]{5, 5, 5}, 3, b + "syntetic\\3D\\syn_3d_5000.txt", 3000);
		singleTest(3, new int[]{5, 5, 5}, 3, b + "syntetic\\3D\\syn_3d_5000.txt", 3500);
		singleTest(3, new int[]{5, 5, 5}, 3, b + "syntetic\\3D\\syn_3d_5000.txt", 4000);
		singleTest(3, new int[]{5, 5, 5}, 3, b + "syntetic\\3D\\syn_3d_5000.txt", 4500);
		singleTest(3, new int[]{5, 5, 5}, 3, b + "syntetic\\3D\\syn_3d_5000.txt", 5000);
		
//		singleTest(4, new int[]{5, 5, 5, 5}, 3, b + "syntetic\\4D\\syn_4d_1000.txt", 1000);
//		singleTest(4, new int[]{5, 5, 5, 5}, 3, b + "syntetic\\4D\\syn_4d_2000.txt", 2000);
//		singleTest(4, new int[]{5, 5, 5, 5}, 3, b + "syntetic\\4D\\syn_4d_5000.txt", 3000);
//		singleTest(4, new int[]{5, 5, 5, 5}, 3, b + "syntetic\\4D\\syn_4d_5000.txt", 4000);
//		singleTest(4, new int[]{5, 5, 5, 5}, 3, b + "syntetic\\4D\\syn_4d_5000.txt", 5000);
	}

	/**
	 * 
	 * @param nDim
	 * @param bits
	 * @param nLayers
	 * @param p
	 */	
	public void singleTest(int nDim, int[] bits, int nLayers, String p, int max)
	{
		LVAIndex lvanew = new LVAIndex(nDim, bits, nLayers);
		LVAIndexDynamic lvadyn = new LVAIndexDynamic(nDim, bits, nLayers);
		LVAIndexOldInsert lvaold = new LVAIndexOldInsert(nDim, bits, nLayers);

		try
		{
			ArrayList<ISpatialObject> dataset = TestUtil.readFile(p, max);
			long timeNew1 = System.currentTimeMillis();
			lvanew.add(dataset);
			long timeNew2 = System.currentTimeMillis();
			
			long timeOld1 = System.currentTimeMillis();
			lvaold.add(dataset);
			long timeOld2 = System.currentTimeMillis();
			
	                long timeDyn1 = System.currentTimeMillis();
	                lvadyn.add(dataset);
	                long timeDyn2 = System.currentTimeMillis();
			
			long o = timeOld2 - timeOld1;
			long n = timeNew2 - timeNew1;
			long d = timeDyn2 - timeDyn1;
			
			System.out.println(p + "\t" + nDim + "\t" + max + "\t" + (o) + "\t" + (n) + "\t" + (d));
			
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	
		
		/*BasicSpatialObject q = new BasicSpatialObject(new double[]{60,60});
		ArrayList<ISpatialObject> result = lva.getNeighbors(q, 10, 800);*/

	}

	public void testBasic()
	{
		ArrayList<ISpatialObject> dataset = new ArrayList<ISpatialObject>();
		
		long time1 = System.currentTimeMillis();
		BasicSpatialObject bso0 = new BasicSpatialObject(new double[]{0,0});
		dataset.add(bso0);
		BasicSpatialObject bso1 = new BasicSpatialObject(new double[]{600,600});
		dataset.add(bso1);
		BasicSpatialObject bso2 = new BasicSpatialObject(new double[]{599,599});
		dataset.add(bso2);
		BasicSpatialObject bso3 = new BasicSpatialObject(new double[]{300,300});
		dataset.add(bso3);
		BasicSpatialObject bso4 = new BasicSpatialObject(new double[]{0,600});
		dataset.add(bso4);
		BasicSpatialObject bso5 = new BasicSpatialObject(new double[]{600,0});
		dataset.add(bso5);
		lva.add(dataset);
		long time2 = System.currentTimeMillis();
		System.out.println( "time" + (time2 - time1) );
		
		BasicSpatialObject q = new BasicSpatialObject(new double[]{60,60});
		
		ArrayList<ISpatialObject> result = lva.getNeighbors(q, 10, 800);
	}
	
	public void testGetNeighborsISpatialObjectInt() {
		//fail("Not yet implemented");
	}

	public void testGetNeighborsISpatialObjectIntDouble() {
		//fail("Not yet implemented");
	}
	
	public static void main(String[] args)
	{
		LVAFileTest t = new LVAFileTest();
		t.testLVABuildingBenchmark();
	}

}
