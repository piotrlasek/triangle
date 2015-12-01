package clusterapp.model.kmeans;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

import com.sun.xml.internal.ws.api.pipe.NextAction;

import lvaindex.vafile.BasicSpatialObject;
import lvaindex.vafile.BasicVAFile;
import lvaindex.vafile.ISpatialIndex;
import lvaindex.vafile.ISpatialObject;

import clusterapp.model.api.BasicClusterInfo;
import clusterapp.model.api.BasicClusteringData;
import clusterapp.model.api.BasicClusteringObject;
import clusterapp.model.api.IClusteringAlgorithm;
import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringObject;
import clusterapp.model.api.IClusteringParameters;
import clusterapp.model.nbc.NBCBase;
import clusterapp.model.nbc.NbcSpatialObject;
import clusterapp.view.IClusteringObserver;

/**
 * The DBSCAN algorithm base class.
 * 
 * @author Piotr Lasek
 * 
 */
public abstract class KMeansBase implements IClusteringAlgorithm {
    
    String description;    

    /**
	 * 
	 */
    ArrayList<ISpatialObject> points;

    /**
	 * 
	 */
    private final int UNCLASSIFIED = -2;
    private final int NOISE = -1;

    /**
     * 
     */
    String desc = getName();

    /**
     * Number of dimensions.
     */
    int nDim = 0;

    int clusters[];

    /**
     * A reference to the index.
     */
    ISpatialIndex isp;

    /**
     * Number of clusters.
     */
    Integer k = 0;

    /**
	 * 
	 */
    protected IClusteringObserver observer;

    /**
	 * 
	 */
    IClusteringParameters parameters;

    /**
	 * 
	 */
    IClusteringData data;

    /**
	 * 
	 */
    Graphics graphics = null;

    /**
	 * 
	 */
    protected abstract void createIndex();

    @Override
    public IClusteringParameters getParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void run() {
        //logger.addDescription(this.getDescription());
        long begin_time1 = System.currentTimeMillis();

        KMeans();
        
        long end_time1 = System.currentTimeMillis();
        desc += " Total = " + (end_time1 - begin_time1) + " ms " + parameters.toString();
        try { observer.handleNotify(desc); } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void setParameters(IClusteringParameters parameters) {
        k = new Integer(parameters.getValue("k"));
    }

     
    // k-means
    // -----------------------------------------------------------------------
    private void KMeans() {
        generateClusters();
        
    }

    public void generateClusters() {
        clusters = new int[k];
        
        for (int i = 0; i < k; i++) {
            int index = (int) (points.size() * Math.random());
            for(int ii = 0; ii < k; ii++) {
            }                
        }                
    }


    /**
     * 
     * @param Point
     * @param Eps
     * @return
     */
    private ArrayList<ISpatialObject> regionQuery(ISpatialObject Point,
            double Eps) {
        ArrayList<ISpatialObject> neighbors = (ArrayList<ISpatialObject>) isp
                .getNeighbors(Point, Eps);
        return neighbors;
    }

    /**
     * 
     * @param Points
     * @param ClId
     * @return
     */
    public void changeClId(ArrayList<ISpatialObject> Points, int ClId) {
        for (ISpatialObject p : Points)
            changeClId(p, ClId);
    }

    /**
     * 
     * @param Point
     * @param NOISE
     */
    public void changeClId(ISpatialObject Point, int ClId) {
        //DbscanSpatialObject o = (DbscanSpatialObject) Point;
        //o.ClId = ClId;
        setClId(Point, ClId);
    }

    /**
     * 
     * @param Point
     * @return
     */
    public int getClId(ISpatialObject Point)
    {
        return Point.getValue();
    }

    /**
     * 
     * @param Point
     * @param ClId
     */
    public void setClId(ISpatialObject Point, int ClId)
    {
        Point.setValue(ClId);
    }

    
    @Override
    public IClusteringData getResult() {
        BasicClusteringData bcd = new BasicClusteringData();
        ArrayList<IClusteringObject> al = new ArrayList<IClusteringObject>();
        for (Object o : points) {
            BasicSpatialObject mp = (BasicSpatialObject) o;
            // observer.handleNotify(mp.excell());
            BasicClusteringObject bco = new BasicClusteringObject();
            //BasicSpatialObject rso = new BasicSpatialObject(mp.getCoordinates());
            bco.setSpatialObject(mp);
            BasicClusterInfo bci = new BasicClusterInfo();
            bci.setClusterId(getClId(mp));
            bco.setClusterInfo(bci);
            al.add(bco);
        }
        bcd.set(al);
        return bcd;
    }

    @Override
    public void setData(IClusteringData data) {
        nDim = data.get().iterator().next().getSpatialObject().getCoordinates().length;

        createIndex();

        this.data = (BasicClusteringData) data;

        Collection<IClusteringObject> input = data.get();
        points = new ArrayList<ISpatialObject>();

        for (IClusteringObject co : input) {
            points.add(new BasicSpatialObject(co.getSpatialObject().getCoordinates()));
        }

        isp.add((ArrayList<ISpatialObject>) points);
    }

    @Override
    public void setGraphics(Graphics g) {
        this.graphics = g;
    }

    @Override
    public void setObserver(IClusteringObserver observer) {
        this.observer = observer;
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
