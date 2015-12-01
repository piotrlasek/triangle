package clusterapp.model.nbc;

import java.util.ArrayList;

import lvaindex.vafile.ISpatialObject;

public class TINBCSqrEpsRefNoSqrt extends TINBC {
    
    public static final String NAME = "TI-NBC-Sqr-Eps-Ref-NoSqrt     ";

    
    TriangleIndexSqrEpsRefNoSqrt ti;
    
    @Override
    public void createIndex2() {
        isp = new TriangleIndexSqrEpsRefNoSqrt(k);
        ti = (TriangleIndexSqrEpsRefNoSqrt) isp;
        ti.setClusteringLogger(logger);
        isp.add(dataset);
        // determineBorderCoordinates(objectsList);
        // zero = new Point(minCoordinates);
        // createSortedTableD(objectsList, zero);
        dataset = ((TriangleIndexSqrEpsRefNoSqrt) isp).D;
        
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
        return TINBCSqrEpsRefNoSqrt.NAME;
    }
}
