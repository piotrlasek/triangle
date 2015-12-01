
import java.util.Collection;

import javax.datamining.ExecutionHandle;
import javax.datamining.ExecutionStatus;
import javax.datamining.JDMException;
import javax.datamining.MiningAlgorithm;
import javax.datamining.MiningObject;
import javax.datamining.base.AlgorithmSettings;
import javax.datamining.clustering.AggregationFunction;
import javax.datamining.clustering.AttributeComparisonFunction;
import javax.datamining.clustering.Cluster;
import javax.datamining.clustering.ClusteringModel;
import javax.datamining.clustering.ClusteringSettings;
import javax.datamining.clustering.ClusteringSettingsFactory;
import javax.datamining.data.LogicalData;
import javax.datamining.data.PhysicalDataSet;
import javax.datamining.data.PhysicalDataSetFactory;
import javax.datamining.resource.Connection;
import javax.datamining.resource.ConnectionSpec;
import javax.datamining.rule.Rule;
import javax.datamining.task.BuildTask;
import javax.datamining.task.apply.RecordApplyTask;

import pl.iidml.*;


/**
 * @author Piotrek
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			MiningAlgorithm.addExtension("DBSCAN");
		} catch (JDMException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		

		System.out.println("Start!");
		
		FileConnectionFactory fcf = new FileConnectionFactory();
		ConnectionSpec cs = fcf.getConnectionSpec();
		cs.setURI("file:////C://data.txt");
		Connection conn = null;
		try {
			conn = fcf.getConnection(cs);
		} catch (JDMException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		MyPhysicalDataSetFactory pdsf = new MyPhysicalDataSetFactory();
		
		PhysicalDataSet mpds = null;
		
		try {
			mpds = pdsf.create("MyPhysicalDataset", true);
		} catch (JDMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conn.saveObject("MyPhysicalDataSet", mpds, true);
		} catch (JDMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MyClusteringSettingsFactory mcsf = new MyClusteringSettingsFactory();
		ClusteringSettings cls = null;

		boolean replaceObject = true;
		
		try {
			cls = mcsf.create();
			cls.setDescription("test description");
			
			//cls.setAlgorithmSettings(arg0)
			cls.setAggregationFunction(AggregationFunction.euclidean);
			
			conn.saveObject("ClusteringSettings", cls, true);
		} catch (JDMException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		MyBuildTaskFactory mbtf = new MyBuildTaskFactory();
		
		BuildTask bt = null;
		
		String clusteringOutputModel = "ClusteringOutputModel";
		
		try {
			bt = mbtf.create("MyPhysicalDataSet", "ClusteringSettings",
					clusteringOutputModel);
			
			
			
			
		} catch (JDMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		Long timeOut = null;
		MiningAlgorithm ma = MiningAlgorithm.kMeans;
		
		try {
			
			ExecutionStatus eh = conn.execute(bt, timeOut);
			
			
			ClusteringModel cm = 
					(ClusteringModel) conn.retrieveObject(clusteringOutputModel);
			
			Collection<Rule> rules = (Collection<Rule>) cm.getRules();
			
			Collection<Cluster> clusters = (Collection<Cluster>) cm.getClusters();
			
			for (Rule r : rules)
			{
				
			}
			
			
		} catch (JDMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("End.");
		
		
		//AttributeComparisonFunction.e
	}

}
