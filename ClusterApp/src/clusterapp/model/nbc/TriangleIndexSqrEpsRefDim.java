package clusterapp.model.nbc;

import java.util.Collection;

import lvaindex.vafile.ISpatialObject;

public class TriangleIndexSqrEpsRefDim extends TriangleIndexSqrEpsRef {

    public TriangleIndexSqrEpsRefDim(int k) {
        super(k);    
    }
    
    @Override
    public void add(Collection<ISpatialObject> objectsList) {
        logger.sortingStart();
        determineBorderCoordinates(objectsList);
        RefPoints.add(new Point(minCoordinates));
        double[] tmp = minCoordinates.clone();
        tmp[0] = maxCoordinates[0];
        RefPoints.add(new Point(tmp));
        int nDim = objectsList.iterator().next().getCoordinates().length;
      //-------------------------------------------------------------
      double minCoord = 0;
      for (int i = 0; i < nDim; i++) {
          int j = 0;
          Point minPoint = null;
          for (ISpatialObject co : objectsList) {
              Point point = new Point(co.getCoordinates().clone());
              if (j == 0) {
                  minCoord = point.getCoordinates()[i];
              }
              if (point.getCoordinates()[i] <= minCoord) {
                  minCoord = point.getCoordinates()[i];
                  minPoint = point;
              }
              j++;
          }
          RefPoints.add(minPoint);
      }

        
        //zero = new Point(minCoordinates);
        createSortedTableD(objectsList);
        logger.sortingEnd();
     }

}
