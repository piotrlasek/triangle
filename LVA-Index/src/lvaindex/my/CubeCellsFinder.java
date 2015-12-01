package lvaindex.my;

/**
 * 
 * @author piotr.lasek
 */
public class CubeCellsFinder
{
    int[] T;
    int tNumber;

    /**
     * 
     */
    void printT( )
    {
        System.out.print("tmp = " + tNumber + ", ");
        for ( int i = 0; i < T.length; i++ )
        {
            System.out.print( T[ i ] + " " );
        }
        System.out.println();
    }

    /**
     * 
     * @param T table
     * @param d number of dimensions
     * @param k number of a layer
     */
    void initTable( int k, int d )
    {
        T = new int[ d ];
        
        for ( int i = 0; i < d; i++ )
        {
            T[ i ] = -k;
        }
    }
    
    /**
     * 
     * @param T table
     * @param k number of a layer
     * @param d number of dimensions
     * @param j 
     * @return
     */
    boolean increment( int k, int d, int j )
    {
        T[ j ] += 1;
        
        if ( T[ j ] > k )
        {
            T[ j ] = -k;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * 
     * @param k
     * @param d
     */
    void getLayerCells1( int k, int d)
    {
        tNumber = d;
        int tNumberOfCellsInCube = (int) Math.pow( k * 2 + 1, d );

        initTable( k, d );

        for ( int i = 0; i < tNumberOfCellsInCube; i++ )
        {
            //if ( tNumber != 0 )
            printT();

            for ( int j = 0; j < d; j++ )
            {
                if ( T[ j ] == -k )
                {
                    tNumber -= 1;
                }
                else if ( T[ j ] == ( k - 1 ) )
                {
                    tNumber += 1;
                }

                if ( !increment( k, d, j ) )
                {
                    break;
                }
            }
        }        
    }

    /**
     * 
     * @param k
     * @param d
     */
    void getLayerCells2( int k, int d)
    {
        tNumber = d;
        //int tNumberOfCellsInCube = (int) Math.pow( k * 2, d );
        int tNumberOfCellsInCube = (int) ( Math.pow(2 * k + 1, d) 
                - Math.pow( 2 * k - 1, d) );

        initTable( k, d );
        
        for ( int i = 0; i < tNumberOfCellsInCube; i++ )
        {
            if ( tNumber != 0 )
                printT();

            if ( tNumber == 1 && T[0] == -k )
            {
                T[ 0 ] = k;
                continue;
            }

            for ( int j = 0; j < d; j++ )
            {
                if ( T[ j ] == -k )
                {
                    tNumber -= 1;
                }
                else if ( T[ j ] == ( k - 1 ) )
                {
                    tNumber += 1;
                }

                if ( !increment( k, d, j ) )
                {
                    break;
                }
            }
        }        
    }
    
    public static void main(String[] args)
    {
        CubeCellsFinder ccf = new CubeCellsFinder();
        
        /*int k = new Integer(args[0]).intValue();
        int d = new Integer(args[1]).intValue();*/
        
        ccf.getLayerCells2(2, 3);
    }
    
}
