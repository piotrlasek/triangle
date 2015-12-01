package clusterapp.model.dbscan;

import lvaindex.vafile.Cell;
import lvaindex.vafile.LVAIndexDynamic;
import clusterapp.model.api.IClusteringParameters;

public class DBSCANLVADyn extends DBSCANBase {
    
    public static final String NAME = "DBSCAN-LVA-Dyn"; 

    /**
     * 
     */
    int k = 0;

    @Override
    public void setParameters(IClusteringParameters parameters) {
        super.setParameters(parameters);
        k = new Integer(parameters.getValue("k")).intValue();
        Cell.maxLayersCount = 0;
    }

    @Override
    protected void createIndex() {
        int[] bits = new int[nDim];
        for (int i = 0; i < nDim; i++)
            bits[i] = b;
        isp = new LVAIndexDynamic(nDim, bits, k);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return DBSCANLVADyn.NAME;
    }

}
