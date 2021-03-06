package clusterapp.model.dbscan;

import java.util.ArrayList;
import java.util.Collection;

import rtree.CustomRTreePoint;

import clusterapp.model.api.BasicClusteringData;
import clusterapp.model.api.BasicClusteringObject;
import clusterapp.model.api.BasicClusteringParameters;
import clusterapp.model.api.IClusteringObject;
import junit.framework.TestCase;
import lvaindex.vafile.ISpatialObject;

public class DBSCANRTreeTest extends TestCase {

    ArrayList<ISpatialObject> ds = new ArrayList<ISpatialObject>();
    BasicClusteringData bcd = new BasicClusteringData();
    Collection<IClusteringObject> bcoList;
    
    public void testCreateIndex() {
        DBSCANRTree dbscantree = new DBSCANRTree();
        dbscantree.setData(bcd);
        BasicClusteringParameters bcp = new BasicClusteringParameters();
        bcp.setValue("Eps", "12");
        bcp.setValue("minPts", "4");
        dbscantree.setParameters(bcp);
        dbscantree.run();
        assert(true);
    }
    
    // ------------------------------------------------------------------------
    
    protected void setUp() throws Exception {
        super.setUp();
        CustomRTreePoint bso = new CustomRTreePoint(new double[] { 0, 0 });
        ds.add(bso);
        bso = new CustomRTreePoint(new double[] { 1, 1 });
        ds.add(bso);
        bso = new CustomRTreePoint(new double[] { 1, 2 });
        ds.add(bso);
        bso = new CustomRTreePoint(new double[] { 1, 3 });
        ds.add(bso);
        bso = new CustomRTreePoint(new double[] { 3, 1 });
        ds.add(bso);
        bso = new CustomRTreePoint(new double[] { 2, 2 });
        ds.add(bso);
        bso = new CustomRTreePoint(new double[] { 0, 0.2 });
        ds.add(bso);
        bso = new CustomRTreePoint(new double[] { 0.2, 0 });
        ds.add(bso);

        bcoList = new ArrayList<IClusteringObject>();

        for (ISpatialObject p : ds) {
            BasicClusteringObject bco = new BasicClusteringObject();
            bco.setSpatialObject(p);
            bcoList.add(bco);
        }

        bcd.set(bcoList);
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
