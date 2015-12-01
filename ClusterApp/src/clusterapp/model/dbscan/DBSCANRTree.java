package clusterapp.model.dbscan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import lvaindex.vafile.BasicSpatialObject;
import lvaindex.vafile.ISpatialObject;

import rtree.CustomRTree;
import spatialindex.spatialindex.IData;
import spatialindex.spatialindex.IEntry;
import spatialindex.spatialindex.INearestNeighborComparator;
import spatialindex.spatialindex.INode;
import spatialindex.spatialindex.IShape;
import spatialindex.spatialindex.IVisitor;
import spatialindex.spatialindex.Region;

import clusterapp.model.api.BasicClusterInfo;
import clusterapp.model.api.BasicClusteringData;
import clusterapp.model.api.BasicClusteringObject;
import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringObject;
import clusterapp.model.nbc.NBCRTreePoint;
import clusterapp.model.rtree.*;

public class DBSCANRTree extends DBSCANBase {

    public static final String NAME = "DBSCAN-RTree";

    @Override
    public String getName() {
        return DBSCANRTree.NAME;
    }
    
    @Override
    protected void createIndex() {
        this.isp = new CustomRTree();
    }
    
    public void addLines() {
        if (observer != null) {
            observer.handleNotify((Object) null);
        }
    }
}
