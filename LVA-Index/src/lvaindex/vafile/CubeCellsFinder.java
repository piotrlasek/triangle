package lvaindex.vafile;

import java.util.ArrayList;

import junit.framework.Assert;

/**
 * 
 * @author piotr.lasek
 */
public class CubeCellsFinder
{
    int[] T;

    /**
     * 
     */
    void printT( )
    {
        for ( int i = 0; i < T.length; i++ )
        {
            System.out.print( T[ i ] + " " );
        }
        System.out.println();
    }

    /**
     * 
     */
    void printT(int[] t)
    {
        for ( int i = 0; i < t.length; i++ )
        {
            System.out.print( t[ i ] + " " );
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
    public void getLayerCells1(int k, int d)
    {
        int tNumber = d;
        int tNumberOfCellsInCube = (int) Math.pow( k * 2 + 1, d );
        int jj = 0;
        initTable( k, d );

        for ( int i = 0; i < tNumberOfCellsInCube; i++ )
        {
            if ( tNumber != 0 )
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
            if (T[jj] == -k)
            {
                tNumber -=1 ;
            }
            else if (T[jj] == (k-1))
            {
                tNumber += 1;
            }
            if (!increment(k,d,jj))
            {
                jj++;
            }
            if (jj == d)
                break;
        }        
    }

    /**
     * 
     * @param k
     * @param d
     */
    public ArrayList<int[]> getLayerCells2(int k, int d)
    {
    	ArrayList<int[]> list = new ArrayList<int[]>();
        int tNumber = d;
        
        int tNumberOfCellsInCube = 0;
        
        if (k!=0)
        {
	        tNumberOfCellsInCube = (int) ( Math.pow(2 * k + 1, d) 
	                - Math.pow( 2 * k - 1, d) );
	
	        initTable( k, d );
	        
	        for ( int i = 0; i < tNumberOfCellsInCube; i++ )
	        {
	            if ( tNumber != 0 )
	            {
	            	int[] Tc = new int[d];
	            	System.arraycopy(T, 0, Tc, 0, d);
	            	list.add(Tc);
	                printT();
	            }
	
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
        else
        {
        	list.add(new int[d]);
        }
        return list;
    }
    
    
    // ---
    
    int m_i;
    int m_tNumber;
    int m_tNumberOfCellsInCube;
    
    public void reset(int k, int d)
    {
        m_i = 0;
        m_tNumber = d;
        m_tNumberOfCellsInCube = (int) ( Math.pow(2 * k + 1, d) 
                - Math.pow( 2 * k - 1, d) );
        
        initTable(k, d);
    }
    
    /**
     * 
     * @param k
     * @param d
     */
    public int[] getLayerCellsNext(int k, int d)
    {
        boolean ret = false;
        int[] Tc = new int[d];
        
        for ( int i = m_i; i < m_tNumberOfCellsInCube; )
        {
            if (ret)
                return Tc;

            i++;
            m_i = i;
            
            if ( m_tNumber != 0 )
            {
                System.arraycopy(T, 0, Tc, 0, d);
                ret = true;
            }

            if ( m_tNumber == 1 && T[0] == -k )
            {
                T[ 0 ] = k;
                continue;
            }

            for ( int j = 0; j < d; j++ )
            {
                if ( T[ j ] == -k )
                {
                    m_tNumber -= 1;
                }
                else if ( T[ j ] == ( k - 1 ) )
                {
                    m_tNumber += 1;
                }

                if ( !increment( k, d, j ) )
                {
                    break;
                }
            }
        }
        
        if (ret)
            return Tc;
        else
            return null;
    }
    
    public static void main(String[] args) {
        CubeCellsFinder ccf = new CubeCellsFinder();
        
        int k = 2;
        int d = 2;
        int[] res;
        ccf.reset(k, d);
        ArrayList<int[]> a1 = new ArrayList<int[]>();
        
        long a = System.currentTimeMillis();
        do {
            res = ccf.getLayerCellsNext(k, d);
            if (res != null) {
                a1.add(res);
                //ccf.printT(res);                
            }       
        } while (res != null);
        
        long b = System.currentTimeMillis();
        
        System.out.println("Determining cells coordinates, d = " + d + ", k = " + k + ": " + (b - a) + " ms");
        
        ArrayList<int[]> a2 = ccf.getLayerCells2(k, d);
        
        boolean comp = true;
        
        if (a1.size() != a2.size() ) {
            comp = false;
        }

        for (int i = 0; i < a1.size(); i++) {
            int[] c1 = a1.get(i);
            int[] c2 = a2.get(i);
            
            if (!cmp(c1, c2)) {
                comp = false;
                break;
            }
        }
        
        if (comp) {
            System.out.println("Coordinates are the same.");
        } else {
            System.out.println("Error!");
        }
    }
    
    static boolean cmp(int[] a, int[] b) {
        boolean res = true;
        
        if (a.length == b.length) {
            for(int i = 0; i < a.length && res == true; i++) {
                if (a[i] != b[i]) {
                    res = false;
                }
            }
        }
        
        return res;
    }
    
}
