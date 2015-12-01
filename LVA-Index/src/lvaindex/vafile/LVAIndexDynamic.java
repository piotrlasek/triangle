package lvaindex.vafile;

import java.util.ArrayList;
import java.util.Collection;

public class LVAIndexDynamic extends LVAIndex {

    public static final String NAME = "LVA-Index-Dynamic";
    
    int k;

    public LVAIndexDynamic(int numberOfDimensions, int[] bitsPerDimensions, int k) {
        // set maxLayersCount to be equal to null
        super(numberOfDimensions, bitsPerDimensions, 0);
        this.k = k;
    }
    
    @Override
    public String getName() {
        return LVAIndexDynamic.NAME;
    }
    
    @Override
    public void add(Collection<ISpatialObject> objectsList)
    {
        super.add(objectsList);
        refine();
    }
    
    /**
     * 
     */
    public void refine()
    {
        CubeCellsFinder ccf = new CubeCellsFinder();
        
        for(ISpatialObject o:objects) {
            Cell thisCell = o.getParentCell();
            o.getParentCellCoordinates();
            int[] cellcoords = o.getParentCellCoordinates();
            
            int layerIndex = 0;
            int neighborsCount = thisCell.getObjectsCount();
            int maxLayerIndex = getNumberOfCellsPerDimension(0);
            
            while(layerIndex < maxLayerIndex) {
                // 0th layer is the cell itself

                //ArrayList<int[]> layerCellCoordinates = ccf.getLayerCells2(layerIndex+1, dimensionsCount);
                //for(int i = 0; i < layerCellCoordinates.size(); i++) {
                ccf.reset(layerIndex + 1, dimensionsCount);
                int[] layercellcoords = null;

                // ...
                while ((layercellcoords = ccf.getLayerCellsNext(layerIndex + 1, dimensionsCount)) != null) {
                    //int[] layercellcoords = layerCellCoordinates.get(i);
                    if (coordinatesValid(cellcoords, layercellcoords)) { 
                        Cell layerCell = this.getCell2(layercellcoords);

                        if (layerCell != null) {
                            ArrayList<ISpatialObject > objects = layerCell.getObjects();
                            
                            if (objects != null && objects.size() != 0) {
                                // add cell to the appropriate cell-layer of the current cell 
                                /*int diff = thisCell.getNeighborLayersCount() - layerIndex; 
                                if (diff != 0) {
                                    thisCell.addLayer(1);
                                }*/
                                if (!thisCell.getNeighborCells(layerIndex).contains(layerCell)) {
                                    thisCell.getNeighborCells(layerIndex).add(layerCell);
                                    neighborsCount += layerCell.getObjectsCount();
                                }
                            }
                        }
                    }
                }
                
                // break the loop
                if (neighborsCount >= k) {
                    break;
                }
                
                layerIndex++;
            }
        }
    }

    /**
     * Coordinates do not exceed existing cells coordinates.
     * @param cellcoords
     * @param layercellcoords
     * @return
     */
    private boolean coordinatesValid(int[] cellcoords, int[] layercellcoords) {
        boolean valid = true;
        for(int ii=0;ii<dimensionsCount;ii++) {
                layercellcoords[ii]+=cellcoords[ii];
                if (layercellcoords[ii]<0 || layercellcoords[ii]>=getNumberOfCellsPerDimension(ii)) {
                    valid = false;
                    break;
                }
        }
        return valid;
    }
}
