/*
 * NBC.java
 *
 * Created on 14 maj 2005, 09:07
 *
 */

package clusterapp.model.nbc;

import lvaindex.common.*;
import lvaindex.my.*;
import lvaindex.vafile.*;
import lvaindex.view.*;

import java.awt.Graphics;
import java.awt.Dialog.ModalityType;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

import java.util.*;

import clusterapp.model.api.IClusteringParameters;
import clusterapp.view.IClusteringObserver;
import clusterapp.view.LvaNbcParametersFrame;
import clusterapp.view.MainWindow;
import clusterapp.view.ParametersFrame;

/**
 * 
 * @author pl
 */
public class NBCLVA extends NBCBase {
    
    public static final String NAME = "NBC-LVA                  ";
    
    int n; // number of stored layers in the LVA-Index

    /** Creates a new instance of NBC */
    public NBCLVA() {

    }

    @Override
    public ArrayList<ISpatialObject> getLayer(ISpatialObject p, int layer_no) {
        return (ArrayList<ISpatialObject>) ((LVAIndex) isp).getLayer(p,
                layer_no);
    }

    @Override
    public ArrayList<ISpatialObject> getNeighbors(ISpatialObject q, int k) {
        return (ArrayList<ISpatialObject>) isp.getNeighbors(q, k, NBCBase.max);
    }

    @Override
    public void setParameters(IClusteringParameters parameters) {
        super.setParameters(parameters);
        n = new Integer(parameters.getValue("n")).intValue();
        Cell.maxLayersCount = n;
        logger.setParameters(parameters.toString());
    }

    /**
     * 
     */
    protected void createIndex() {
        int[] bits = new int[nDim];
        for (int i = 0; i < nDim; i++)
            bits[i] = b;
        isp = new LVAIndex(nDim, bits, n);
        // isp = new LVAIndexDynamic(nDim, bits, n);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return NBCLVA.NAME;
    }

}
