package clusterapp.model.nbc;

import java.util.ArrayList;
import java.util.Collection;

import rtree.CustomRTree;

import util.Distance;


import lvaindex.vafile.Cell;
import lvaindex.vafile.ISpatialObject;
import clusterapp.model.api.BasicClusteringData;
import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringObject;
import clusterapp.model.api.IClusteringParameters;

public class Tests extends NBCBase {
    public static final String NAME = "IND-TESTS";
    int k;
    int n;
    
    
    //TriangleIndexSqrRef ti;
    //TriangleIndexSqrEps ti;
    //TriangleIndexSqrEpsRef ti;
    //TriangleIndexSqrEpsRefNoSqrt ti;
    //TriangleIndexSqrEpsNoSqrt ti;
    TriangleIndexSqrEpsEstim ti;
    CustomRTree rt;
    
    /** Creates a new instance of NBC */
    public Tests() {

    }
    
    @Override
    public void run() {
        logger.addDescription(this.getDescription());
        
        for (int i = 0; i < dataset.size(); i++) {
            NbcSpatialObject p = (NbcSpatialObject) dataset.get(i);

//            if (!(p.getCoordinates()[0] == 109.0 && p.getCoordinates()[1] == 226.0) || 
//                   (p.getCoordinates()[0] == 114.0 && p.getCoordinates()[1] == 237.0))
//                continue;
            
            
            System.out.println(">>>>> Query point p = " + p);
            
            ArrayList<ISpatialObject> nti = ti.getNeighbors(p, k);
            ArrayList<ISpatialObject> nrt = (ArrayList<ISpatialObject>) rt.getNeighbors(p, k+1);
            
            System.out.println();
            System.out.println("RESULT:");
            
            int s = nti.size();
            System.out.print("ti: " + p + " - " + nti.size() + " -> ");
            for(int j =0 ; j < nti.size(); j++) {
                //System.out.print(Distance.Distance(p, nti.get(j)) + " ");
                double d = Distance.Distance(p, nti.get(j));
                System.out.print(nti.get(j) + " " + d + ", ");
            }
            System.out.println();
            System.out.print("rt: " + p + " - " + nrt.size() + " -> ");
            for(int j =0 ; j < nrt.size(); j++) {
                //System.out.print(Distance.Distance(p, nrt.get(j)) + " ");
                double d = Distance.Distance(p, nrt.get(j));
                System.out.print(nrt.get(j) + " " + d + ", ");
            }
            
            System.out.println();
            System.out.println();
            System.out.println("#######################################################################");
        }
        
        observer.handleNotify(logger.getLog());
    }
    
    @Override
    public int getLayersCount(ISpatialObject p) {
        return 1;
    }
    
    @Override
    public ArrayList<ISpatialObject> getLayer(ISpatialObject p, int layer_no) {
        return (ArrayList<ISpatialObject>) isp.getNeighbors(p, this.k);
    }

    @Override
    public ArrayList<ISpatialObject> getNeighbors(ISpatialObject q, int k) {
        return (ArrayList<ISpatialObject>) isp.getNeighbors(q, this.k);
    }

    @Override
    public void setParameters(IClusteringParameters parameters) {
        super.setParameters(parameters);
        k = new Integer(parameters.getValue("k")).intValue();
        n = 0;
        Cell.maxLayersCount = n;
        logger.setParameters(parameters.toString());
    }

    /**
     * 
     */
    protected void createIndex() {
        //ti = new TriangleIndexSqrEps(k);
        //ti = new TriangleIndexSqrEpsRef(k);
        //ti = new TriangleIndexSqrEpsNoSqrt(k);
        ti = new TriangleIndexSqrEpsEstim(k);
        
        rt = new CustomRTree();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return Tests.NAME;
    }
    
    @Override
    protected void addData(IClusteringData data) {
        nDim = data.get().iterator().next().getSpatialObject().getCoordinates().length;

        this.data = (BasicClusteringData) data;

        Collection<IClusteringObject> input = data.get();
        dataset = new ArrayList<ISpatialObject>();

        for (IClusteringObject co : input) {
            dataset.add(new Point(co.getSpatialObject()));
        }
        
        createIndex();
        ti.add(dataset);
        rt.add(dataset);
        int  a= 0;
    }


}
