package clusterapp.model.nbc;

import java.util.ArrayList;

import lvaindex.vafile.BasicVAFile;
import lvaindex.vafile.Cell;
import lvaindex.vafile.ISpatialObject;
import lvaindex.vafile.LVAIndex;
import lvaindex.vafile.VAFile;

public class NBCVAFile extends NBCBase {
    
    public static final String NAME = "NBC-VAFILE                    ";
    
    @Override
    protected void createIndex() {
        int[] bits = new int[nDim];
        for (int i = 0; i < nDim; i++)
            bits[i] = b;
        isp = new VAFile(nDim, bits);
    }

    @Override
    public ArrayList<ISpatialObject> getLayer(ISpatialObject p, int layer_no) {
        return (ArrayList<ISpatialObject>) ((VAFile) isp).getLayer(p, layer_no);
    }

    @Override
    public ArrayList<ISpatialObject> getNeighbors(ISpatialObject q, int k) {
        return (ArrayList<ISpatialObject>) ((VAFile) isp).getNeighbors(q, k,
                NBCBase.max);
    }

    @Override
    public String getName() {
        return NBCVAFile.NAME;
    }
    
    public int getLayersCount(ISpatialObject p) {
        return 2;
    }

}
