package clusterapp.model.nbc;

import clusterapp.model.api.IClusteringParameters;
import lvaindex.vafile.Cell;
import lvaindex.vafile.ISpatialObject;
import lvaindex.vafile.LVAIndexDynamic;

public class NBCLVADyn extends NBCLVA {
    
    public static final String NAME = "NBC-LVA-Dyn                   ";

    // k is fixed
    // int k;
    
    @Override
    public String getName() {
        return NBCLVADyn.NAME;
    }    
    
    @Override
    public void setParameters(IClusteringParameters parameters) {
        this.parameters = parameters;
        k = new Integer( parameters.getValue("k") ).intValue();
        b = new Integer( parameters.getValue("b") ).intValue();
        n = -1;
        Cell.maxLayersCount = 0;
        logger.setParameters(parameters.toString());
    }

    @Override
    protected void createIndex()
    {
        int[] bits = new int[nDim];
        for(int i = 0; i < nDim; i++)
                bits[i] = b;
        isp = new LVAIndexDynamic(nDim, bits, k);
    }
    
    @Override
    public int getLayersCount(ISpatialObject p) {
        return p.getParentCell().getNeighborLayersCount();
    }
}
