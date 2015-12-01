package clusterapp.model.nbc;

import java.util.ArrayList;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import clusterapp.model.api.IClusteringParameters;

import lvaindex.vafile.ISpatialObject;

public class TINBCMink extends TINBC {
    
    public static final String NAME = "TI-NBC-Mink                   ";

    int m = 0;
    TriangleIndexMink ti;
    
    @Override
    public void createIndex2() {
        isp = new TriangleIndexMink(k, m);
        ti = (TriangleIndexMink) isp;
        ti.setClusteringLogger(logger);
        isp.add(dataset);
        dataset = ((TriangleIndexMink) isp).D;
        
        // determineBorderCoordinates(objectsList);
        // zero = new Point(minCoordinates);
        // createSortedTableD(objectsList, zero);

        this.D = dataset;
        for(ISpatialObject e0:D) {
            Point e = (Point) e0;
            ArrayList<ISpatialObject> I = ti.getNeighbors(e, this.k);
            for(ISpatialObject o0:I) {
                Point o = (Point) o0;
                e.neighbors.add(o.pos);
            }
        }
    }
    
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return TINBCMink.NAME;
    }
    
    @Override
    public void setParameters(IClusteringParameters parameters)
    {
        super.setParameters(parameters);
        m = new Integer(parameters.getValue("m")).intValue();
    }
}
