package clusterapp.model.dbscan;

import java.awt.Graphics;

import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringParameters;
import clusterapp.view.IClusteringObserver;
import lvaindex.vafile.Cell;
import lvaindex.vafile.LVAIndex;
import lvaindex.vafile.LVAIndexDynamic;

public class DBSCANLVA extends DBSCANBase {

    public static final String NAME = "DBSCAN-LVA";
    
    /**
     * Number of layers to be stored for each cell in the LVA-Index.
     */
    int n = 0;

    @Override
    public void setParameters(IClusteringParameters parameters) {
        super.setParameters(parameters);
        n = new Integer(parameters.getValue("n")).intValue();
        Cell.maxLayersCount = n;
    }

    @Override
    protected void createIndex() {
        int[] bits = new int[nDim];
        for (int i = 0; i < nDim; i++)
            bits[i] = b;
        isp = new LVAIndex(nDim, bits, n);
        //isp = new LVAIndexDynamic(nDim, bits, n);
    }

    @Override
    public String getName() {
        return DBSCANLVA.NAME;
    }
}
