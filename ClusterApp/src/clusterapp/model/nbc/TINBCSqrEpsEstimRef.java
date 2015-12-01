package clusterapp.model.nbc;

import java.util.ArrayList;

import lvaindex.vafile.ISpatialObject;

public class TINBCSqrEpsEstimRef extends TINBC {
    
    public static final String NAME = "TI-NBC-Sqr-Eps-Estim-Ref      ";
    
    TriangleIndexSqrEpsEstimRef ti;
    
    @Override
    public void createIndex2() {
        isp = new TriangleIndexSqrEpsEstimRef(k);
        ti = (TriangleIndexSqrEpsEstimRef) isp;
        ti.setClusteringLogger(logger);
        isp.add(dataset);
        // determineBorderCoordinates(objectsList);
        // zero = new Point(minCoordinates);
        // createSortedTableD(objectsList, zero);
        dataset = ((TriangleIndexSqrEpsEstimRef) isp).D;
        
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
        return TINBCSqrEpsEstimRef.NAME;
    }
}
