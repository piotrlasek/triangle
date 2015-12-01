package clusterapp.model.kmeans;

import java.awt.Graphics;

import rtree.CustomRTree;

import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringParameters;
import clusterapp.view.IClusteringObserver;
import lvaindex.vafile.Cell;
import lvaindex.vafile.LVAIndex;

public class KMeansRTree extends KMeansBase {

    public static final String NAME = "KMeansRTree";
    
    @Override
    protected void createIndex() {
        this.isp = new CustomRTree();
    }

    @Override
    public String getName() {
        return KMeansRTree.NAME;
    }

}
