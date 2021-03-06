/*
 * NBC.java
 *
 * Created on 14 maj 2005, 09:07
 *
 */

package clusterapp.model.nbc;

import spatialindex.spatialindex.*;
import spatialindex.storagemanager.*;
import spatialindex.rtree.*;

import java.awt.Graphics;
import java.awt.Dialog.ModalityType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

import lvaindex.vafile.BasicSpatialObject;
import lvaindex.vafile.ClusteringLogger;
import lvaindex.vafile.ISpatialObject;

import java.util.*;

import clusterapp.model.api.BasicClusterInfo;
import clusterapp.model.api.BasicClusteringObject;
import clusterapp.model.api.IClusteringAlgorithm;
import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringObject;
import clusterapp.model.api.IClusteringParameters;
import clusterapp.model.api.BasicClusteringData;
import clusterapp.view.IClusteringObserver;
import clusterapp.view.MainWindow;
import clusterapp.view.ParametersFrame;

/**
 * 
 * @author pl
 */
public class NBCRTree implements IClusteringAlgorithm {
    
    public static final String NAME = "NBC-RTree                     ";
    
    String description;

    IClusteringObserver observer;
    ISpatialIndex tree;
    IClusteringParameters parameters;

    ArrayList<NBCRTreePoint> Dataset;
    
    ClusteringLogger logger = new ClusteringLogger(getName());

    int nDim = 0;
    //int id = 0;
    int k;
    //String desc = "NBC (RTree): ";

    /** Creates a new instance of NBC */
    public void run() {
        logger.addDescription(this.getDescription());
        //long begin_time1 = System.currentTimeMillis();
        logger.clusteringStart();

        ArrayList<NBCRTreePoint> NoiseSet = new ArrayList();
        int cluster_count = 0;

        // 		
        //long begin_time2 = System.currentTimeMillis();
        CalcNDF();
        //long end_time2 = System.currentTimeMillis();
        long time3 = System.currentTimeMillis();
        //desc += " CaldNDF = " + (end_time2 - begin_time2) + " ms";
        //long time4 = System.currentTimeMillis();
        NoiseSet.clear();
        cluster_count = 0;
        ArrayList<NBCRTreePoint> DPSet = new ArrayList();

        //long XY = 0;
        //int xy = 0;

        // for each object p in Dataset
        ListIterator li = Dataset.listIterator();
        while (li.hasNext()) {
            NBCRTreePoint p = (NBCRTreePoint) li.next();
            // if (p.clst_no != NULL or p.ndf < 1)) continue;
            if (p.clst_no != -1 || p.ndf < 1)
                continue;
            p.clst_no = cluster_count; // label a new cluster
            DPSet.clear(); // initialize DPSet

            // for each object q in kNB(p)

            // for each object q in kNB(p)
            //long timeX = System.currentTimeMillis();
            MyVisitor kNN = new MyVisitor();
            tree.nearestNeighborQuery(k, p, kNN);
            //long timeY = System.currentTimeMillis();
            //XY += (timeY - timeX);

            for (int i = 0; i < kNN.neighbours.size(); i++) {
                // q.clst_no = cluster_count
                NBCRTreePoint q = (NBCRTreePoint) kNN.neighbours.get(i);
                q.clst_no = cluster_count;
                if (q.ndf >= 1)
                    DPSet.add(q);
            }

            // while (DPSet is not empty) // expanding the cluster
            while (!DPSet.isEmpty()) {
                // p = DPset.getFirstObject();
                NBCRTreePoint pD = (NBCRTreePoint) DPSet.remove(0);

                // for each object q in kNB(pD)
                MyVisitor kNNpD = new MyVisitor();
                tree.nearestNeighborQuery(k, pD, kNNpD);
                ListIterator liKNNpD = kNNpD.neighbours.listIterator();
                while (liKNNpD.hasNext()) {
                    NBCRTreePoint q = (NBCRTreePoint) liKNNpD.next();
                    // if (q.clst_no!NULL) continue
                    if (q.clst_no != -1)
                        continue;

                    q.clst_no = cluster_count;

                    // if (q.ndf >= 1) DPSet.add(q)
                    if (q.ndf >= 1)
                        DPSet.add(q);
                }
                // DPSet.remove(p);
            }
            cluster_count++;
        }

        // for each object p in Dataset // label noise
        li = Dataset.listIterator();
        while (li.hasNext()) {
            // if (p.clst_no=NULL) NoiseSet.add(p)
            NBCRTreePoint p = (NBCRTreePoint) li.next();
            if (p.clst_no == -1)
                p.clst_no = -2;
        }

        //long end_time1 = System.currentTimeMillis();
        // ----------------------
        // now = new Date();
        // observer.handleNotify("Clusters created.");
        // observer.handleNotify("NBC finished - . K = " + k +
        // ", number of clusters: "
        // + (cluster_count - 1));
        // double after = now.getTime();
        // observer.handleNotify("[size: " + Dataset.size() + ", time: " + (int)
        // (after - before) + " ms, k:"
        // + k + ", clusters:" + (cluster_count-1) + "]");
        logger.clusteringEnd();
        
        //desc += ", Total = " + (end_time1 - begin_time1 - (time4 - time3))
        //        + " ms "  + parameters.toString();
        observer.handleNotify(logger.getLog());
        addLines();

        //System.out.println("==>>> xy = " + xy + ", " + "XY = " + XY);
    }

    private void CalcNDF() {
        ListIterator li = Dataset.listIterator();
        li = Dataset.listIterator();
        ArrayList CandidateSet = new ArrayList();
        MyVisitor kNN = new MyVisitor();

        // for each object p in Dataset
        while (li.hasNext()) {
            NBCRTreePoint p = (NBCRTreePoint) li.next();
            CandidateSet.clear();

            kNN.reset();
            tree.nearestNeighborQuery(k, p, kNN);
            p.numberOfkNB = kNN.kNB;
            // System.out.println("p: " + p.excell());

            // for each new object q in kNB(p)
            ListIterator lni = kNN.neighbours.listIterator();
            while (lni.hasNext()) {
                NBCRTreePoint q = (NBCRTreePoint) lni.next();
                q.numberOfRkNB++;
                // System.out.println("q: " + q.excell());
            }
        }

        // for each object q in kNB(p)
        li = Dataset.listIterator();
        while (li.hasNext()) {
            NBCRTreePoint p = (NBCRTreePoint) li.next();
            p.ndf = (double) p.numberOfRkNB / p.numberOfkNB;
        }

        // printing
        // li = Dataset.listIterator();
        // while ( li.hasNext() )
        // {
        // MyPoint p = (MyPoint) li.next();
        // System.out.println(p.excell());
        // }
    }

    public void initRTree() throws IOException {
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
    class MyVisitor implements IVisitor {
        public int m_indexIO = 0;

        public int m_leafIO = 0;

        public int kNB = 0;

        ArrayList<NBCRTreePoint> neighbours = new ArrayList<NBCRTreePoint>();
        ArrayList<IData> n = new ArrayList<IData>();

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
            //IShape shape = d.getShape();
            //String cls = shape.getClass().toString();
            int id = d.getIdentifier();
            //byte[] data = d.getData();
            neighbours.add(Dataset.get(id));
            n.add(d);
        }
    }

    @Override
    public IClusteringData getResult() {
        BasicClusteringData bcd = new BasicClusteringData();
        ArrayList<IClusteringObject> al = new ArrayList<IClusteringObject>();
        for (Object o : Dataset) {
            NBCRTreePoint mp = (NBCRTreePoint) o;
            // RTreeSpatialObject rso = new RTreeSpatialObject(mp.m_pCoords,
            // mp.clst_no );
            BasicClusteringObject bco = new BasicClusteringObject();
            // bco.s
            BasicSpatialObject rso = new BasicSpatialObject(mp.m_pCoords/*
                                                                         * ,mp.
                                                                         * clst_no
                                                                         */);
            bco.setSpatialObject(rso);
            BasicClusterInfo bci = new BasicClusterInfo();
            bci.setClusterId(mp.clst_no);
            bco.setClusterInfo(bci);
            al.add(bco);
        }
        bcd.set(al);

        return bcd;
    }

    @Override
    public void setData(IClusteringData data) {
        logger.indexStart();
        ArrayList<IClusteringObject> tmp = (ArrayList<IClusteringObject>) data
                .get();
        Dataset = new ArrayList();
        nDim = data.get().iterator().next().getSpatialObject().getCoordinates().length;
        try {
            initRTree();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        
        int id = 0;
        
        // building R-Tree
        for (IClusteringObject ico : tmp) {
            NBCRTreePoint mp = new NBCRTreePoint(ico.getSpatialObject()
                    .getCoordinates(), -1);
            Dataset.add(id, mp);
            byte[] d = new byte[]{-1};
            tree.insertData(d, mp, id);
            id++;
        }
        logger.indexEnd();
    }

    @Override
    public IClusteringParameters getParameters() {
        return parameters;
    }

    @Override
    public void setParameters(IClusteringParameters parameters) {
        /*
         * ParametersFrame pf = new ParametersFrame( parameters );
         * pf.setModalityType(ModalityType.DOCUMENT_MODAL); pf.setVisible( true
         * );
         */
        this.parameters = parameters;
        k = new Integer(parameters.getValue("k")).intValue();
        logger.setParameters(parameters.toString());
    }

    @Override
    public void setObserver(IClusteringObserver observer) {
        // TODO Auto-generated method stub
        this.observer = observer;
    }

    public void setGraphics(Graphics g) {

    }

    public void addLines() {
        this.observer.handleNotify((Object) null);
    }

    @Override
    public String getName() {
        return NBCRTree.NAME;
    }

    /**
     * 
     */
    public void addDescription(String description) {
        this.description = description;
    }
    
    /**
     * 
     */
    public String getDescription() {
        return description;
    }
    
}
