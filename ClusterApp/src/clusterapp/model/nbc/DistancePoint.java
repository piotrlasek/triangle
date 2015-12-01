package clusterapp.model.nbc;

import lvaindex.vafile.ISpatialObject;

public class DistancePoint {
    
    private static double table[][];

	// ---------------------------
    public static double getDistance(Point a, Point b) {
        return Math.sqrt(Distance2(a, b));
    }
    
    public static double Distance(Point a, Point b) {
    	return getDistance(a, b);
    }
    
    public static double sqrt(double dist) {
        return Math.sqrt(dist);
    }
    
    public static void init(int length) {
        table = new double[length][];
        for(int i = 0; i < table.length; i++) {
            table[i] = new double[length];
            for (int j =0; j < table[i].length; j++) {
                table[i][j] = -1;
            }
        }
    }

    public static double Distance2(Point a, Point b) {

        double dist2 = DistancePoint.table[a.pos][b.pos];
        if (dist2 == -1) {
            double[] ac = a.getCoordinates();
            double[] bc = b.getCoordinates();
            for (int i = 0; i < ac.length; i++) {
                dist2 += Math.pow(ac[i] - bc[i], 2);
            }
            DistancePoint.table[a.pos][b.pos] = dist2;
            DistancePoint.table[b.pos][a.pos] = dist2;
        }
        return dist2;
    }
}
