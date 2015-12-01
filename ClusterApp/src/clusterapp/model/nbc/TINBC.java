package clusterapp.model.nbc;

import java.util.ArrayList;
import java.util.Collection;

import util.Distance;


import lvaindex.vafile.Cell;
import lvaindex.vafile.ISpatialObject;
import clusterapp.model.api.BasicClusteringData;
import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringObject;
import clusterapp.model.api.IClusteringParameters;

public class TINBC extends NBCBase {
    public static final String NAME = "TI-NBC                        ";
    int k;
    int n;
    /** Creates a new instance of NBC */
    TriangleIndex ti; 
            
    ArrayList<ISpatialObject> D;
    
    int NOISE = -1;
    int UNCLASSIFIED = -2;
    
    public TINBC() {

    }
    
    @Override
    public void run() {
        logger.addDescription(this.getDescription());
        logger.clusteringStart();
        
        Add_NBC_Statistics_k_Neighborhood_Index();
        TI_NBC();
        
        logger.clusteringEnd();
        observer.handleNotify(logger.getLog());
    }
    
//    Algorithm TI-NBC(set of points D);
    public void TI_NBC() {
        
//    /* assert:
//       Each element e in kNN-Index has the following constituents:
//       pId – point id (or point reference, …);
//       k-Neighborhood – k nearest neighbors of the point identified 
//                   by pId;  
//       NDF - ratio of the cardinality of the reversed k-Neighborhood
//                   to the cardinality of the k-Neighborhood,
//       ClusterId – identifier of the cluster of the point 
//                   identified by pId.                                                  
//
//       Each k-Neighborhood of point identified by pID is the set 
//       of point ids.
//    */
        
//    kNN-Index = TI-k-Neighborhood-Index(D)                          // create kNN-Index;
        int ClusterId = 0;
//    for each element e in kNN-Index do
        for(ISpatialObject e0:D) {
            Point e = (Point) e0;
//        if e.NDF < 1 then
            if (e.ndf < 1) {
//            e.ClusterId = NOISE;
                e.clst_no = NOISE;
//        else
            } else {
//            e.ClusterId = UNCLASSIFIED;
                e.clst_no = UNCLASSIFIED;
//        endif
            }
//    endfor
        }
//    for each element e in kNN-Index do
        for(ISpatialObject e0:D) {
            Point e = (Point) e0;
//        if ExpandCluster(kNN-Index, e, ClId) then
            if (e.clst_no == UNCLASSIFIED) {
                ExpandCluster(e, ClusterId);
//            ClusterId = NextId(ClusterId)
                ClusterId = ClusterId + 1;
//        endif
            }
//    endfor
        }
    }
//
//    function ExpandCluster(kNN-Index, element e, ClId)
        public void ExpandCluster(Point e, int ClId) {
//    if e.ClusterId = NOISE then
            ArrayList<Integer> seeds = new ArrayList<Integer>();
            
//            if (e.clst_no >= NOISE) {
//        return FALSE
//                return false;
//    else
//            } else {
//        seeds = {};
                seeds.clear();
//        e.ClusterId = ClId;
                e.clst_no = ClId;
//        for each neighborId in e.k-Neighborhood do
                for (int i = 0; i < e.neighbors.size(); i++) {
//            find element f in kNN-Index such that 
//                f.pId = neighborId;
                    int neighborId = e.neighbors.get(i);
                    Point f = (Point) D.get(neighborId);
//            if f.ClusterId = UNCLASSIFIED then
                    if (f.clst_no == UNCLASSIFIED) {
//                f.ClusterId = ClId;
                        f.clst_no = ClId;
//                add neighborId to seeds;
                        seeds.add(neighborId);
//            elseif f.ClusterId = NOISE
                    } else if (f.clst_no == NOISE) {
//                f.ClusterId = ClId;
                        f.clst_no = ClId;
//            endif
                    }
//        endfor
                }
//        while |seeds| > 0 do
                while (!seeds.isEmpty()) {
//                    System.out.println("seeds size = " + seeds.size());
//            find element curE in kNN-Index such that 
//                curE.pId = first point id in seeds;
                    int firstPointId = seeds.get(0);
                    Point curE = (Point) D.get(firstPointId);
//            for each neighborId in curE.k-Neighborhood do
                    for(int i = 0; i < curE.neighbors.size(); i++) {
                        int neighborId = curE.neighbors.get(i);
//                find element f in kNN-Index such that 
//                    f.pId = neighborId;
                        Point f = (Point) D.get(neighborId);
//                if f.ClusterId = UNCLASSIFIED then
                        if (f.clst_no == UNCLASSIFIED) {
//                    f.ClusterId = ClId;
                            f.clst_no = ClId;
//                    add neighborId to seeds;
                            seeds.add(neighborId);
//                elseif f.ClusterId = NOISE
                        } else if (f.clst_no == NOISE) {
//                    f.ClusterId = ClId;
                            f.clst_no = ClId;
//                endif;
                        }
//            endfor;
                    }
//            delete first point id from seeds;
                    seeds.remove(0);
//        endwhile
                }
//        return TRUE
//                return true;
//    endif
//            }
        }
    
//
//        Algorithm Add-NBC-Statistics-k-Neighborhood-Index(k-Neighborhood-Index);
        public void Add_NBC_Statistics_k_Neighborhood_Index() {
//        for each element e = (i, I) in k-Neighborhood-Index do
            for(ISpatialObject e0:D) {
                Point e = (Point) e0;
//            e.k-NB-count = |I|;
                e.SizeOfkNB = e.neighbors.size();
//            e.R-k-NB-count = 0;
                for(Integer i:e.neighbors) {
                    Point e1 = (Point) D.get(i);
                    e1.SizeOfRkNB = e1.SizeOfRkNB + 1;
                }
//        endfor
            }

//        for each element e = (i, I) in k-Neighborhood-Index do
//            for each j in I do
//                find element e’ = (j, J) in k-Neighborhood-Index;
//                e’.R-k-NB-count = e’.R-k-NB-count + 1;
//        endfor


//        for each element e = (i, I) in k-Neighborhood-Index do
            for(ISpatialObject e0:D) {
                Point e = (Point) e0;
//              e.NDF = e.R-k-NB-count / e.k-NB-count;
                e.ndf = (double) e.SizeOfRkNB / (double) e.SizeOfkNB;
                //System.out.println(e + " " + e.ndf);
//              endfor
            }
//
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
        // isp = new TriangleIndex(k);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return TINBC.NAME;
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

        logger.indexStart();
        createIndex2();
        logger.indexEnd();
    }

    public void createIndex2() {
        isp = new TriangleIndex(k);
        ti = (TriangleIndex) isp;
        ti.setClusteringLogger(logger);
        isp.add(dataset);
        // determineBorderCoordinates(objectsList);
        // zero = new Point(minCoordinates);
        // createSortedTableD(objectsList, zero);
        dataset = ((TriangleIndex) isp).D;
        ti = (TriangleIndex) isp;

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


}
