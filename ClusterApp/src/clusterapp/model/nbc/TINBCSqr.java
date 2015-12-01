package clusterapp.model.nbc;

import java.util.ArrayList;

import lvaindex.vafile.ISpatialObject;

public class TINBCSqr extends TINBC {
    
    public static final String NAME = "TI-NBC-Sqr               ";

    
    TriangleIndexSqr ti;
    
    @Override
    public void createIndex2() {
        isp = new TriangleIndexSqr(k);
        ti = (TriangleIndexSqr) isp;
        ti.setClusteringLogger(logger);
        isp.add(dataset);
        dataset = ((TriangleIndexSqr) isp).D;
        
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
        return TINBCSqr.NAME;
    }
}
