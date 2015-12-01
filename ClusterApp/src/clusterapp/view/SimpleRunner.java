package clusterapp.view;

import java.util.Formatter;

import clusterapp.controller.AlgorithmFactory;
import clusterapp.controller.DataSourceManager;
import clusterapp.model.api.BasicClusteringParameters;
import clusterapp.model.api.IClusteringAlgorithm;
import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringDataSource;
import clusterapp.model.api.IClusteringParameters;
import clusterapp.model.dbscan.DBSCANLVADyn;
import clusterapp.model.dbscan.DBSCANRTree;
import clusterapp.model.dbscan.DBSCANTriangle;
import clusterapp.model.dbscan.DBSCANTriangleRef;
import clusterapp.model.dbscan.DBSCANTriangleRefMink;
import clusterapp.model.nbc.NBCLVA;
import clusterapp.model.nbc.NBCLVADyn;
import clusterapp.model.nbc.NBCRTree;
import clusterapp.model.nbc.TINBC;
import clusterapp.model.nbc.TINBCMink;
import clusterapp.model.nbc.TINBCSqr;
import clusterapp.model.nbc.NBCVAFile;
import clusterapp.model.nbc.TINBCSqrEps;
import clusterapp.model.nbc.TINBCSqrEpsEstim;
import clusterapp.model.nbc.TINBCSqrEpsEstimRef;
import clusterapp.model.nbc.TINBCSqrEpsEstimRefDim;
import clusterapp.model.nbc.TINBCSqrEpsNoSqrt;
import clusterapp.model.nbc.TINBCSqrEpsRef;
import clusterapp.model.nbc.TINBCSqrEpsRefDim;
import clusterapp.model.nbc.TINBCSqrEpsRefNoSqrt;
import clusterapp.model.nbc.TINBCSqrRef;
import clusterapp.model.nbc.Tests;


public class SimpleRunner {

    public class MyObserver implements IClusteringObserver
    {
        @Override
        public void handleNotify(Object object) {
            // TODO Auto-generated method stub
            
        }

        /* (non-Javadoc)
         * @see clusterapp.view.IClusteringObserver#handleNotify(clusterapp.model.api.IClusteringData)
         */
        @Override
        public void handleNotify(IClusteringData data) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void handleNotify(String message) {
            System.out.println(message);            
        }
}
    
    public void log(String s)
    {
        System.out.println(s);
    }
    

    
    public static void main(String[] args) 
    {
        SimpleRunner sr = new SimpleRunner();
        boolean t = false;
        boolean f = false;
        
        //sr.log("Start...");
        
        BasicClusteringParameters b = new BasicClusteringParameters();
        
        //String r = "C:\\Documents and Settings\\piotr.lasek\\workspace\\Data\\";
        
        String r = "C:\\Users\\plasek\\Documents\\STUDIA\\PHD\\SRC\\workspace\\Data\\";
        
        //String l = "manual\\my-file-2d-20.txt";
        String l = "manual\\my-file-2d-5-2658.txt";
        //String l = "manual\\my-file-2d-14453.txt";
        //String l = "manual\\my-file-2d-511.txt";
        //String l = "syntetic\\ND\\d-20-500.txt";
        
//        b.sv("k",   "4");                   sr.runAlgorithm(Tests.NAME,             b,  r + l, t, null); b.clear();
//        System.exit(0);
        
        String[] in = {
//                        "manual\\my-file-2d-5-2658.txt"
//                        ,"manual\\my-file-2d-14453.txt"
//        		"syntetic\\ND\\d-20-500.txt"
//        		,"syntetic\\ND\\d-30-500.txt"
//        		,"syntetic\\ND\\d-40-500.txt"
//        		,"syntetic\\ND\\d-50-500.txt"
//        		,"syntetic\\ND\\d-100-2000.txt"
//        		,"syntetic\\ND\\d-200-2000.txt"
//        		,"syntetic\\ND\\d-300-2000.txt"
//                        ,"syntetic\\ND\\d-400-2000.txt"
//                ,"syntetic\\ND\\d-500-2000.txt"
//                ,"syntetic\\ND\\d-600-2000.txt"
//                ,"syntetic\\ND\\d-700-2000.txt"
//                ,"syntetic\\ND\\d-800-2000.txt"
//                ,"syntetic\\ND\\d-900-2000.txt"
//                ,"syntetic\\ND\\d-1000-2000.txt"
//                "standard\\birch\\birch1.txt"
//                ,"standard\\covtype\\covtype.data"
//                ,"standard\\mnist784\\mnist_test.amat"
//                
//                ,"syntetic\\ND\\d----10-10000.txt"
//                ,"syntetic\\ND\\d----10-20000.txt"
//                ,"syntetic\\ND\\d----10-50000.txt"
//                ,"syntetic\\ND\\d----10-100000.txt"  
//                
//                ,"syntetic\\ND\\d----50-10000.txt"
//                ,"syntetic\\ND\\d----50-20000.txt"
//                ,"syntetic\\ND\\d----50-50000.txt"
//                ,"syntetic\\ND\\d----50-100000.txt"
//                  
//                ,"syntetic\\ND\\d---100-10000.txt"
//                ,"syntetic\\ND\\d---100-20000.txt"
//                ,"syntetic\\ND\\d---100-50000.txt"
//                ,"syntetic\\ND\\d---100-100000.txt"
//                  
//                ,"syntetic\\ND\\d---500-10000.txt"
//                ,"syntetic\\ND\\d---500-20000.txt"
//                ,"syntetic\\ND\\d---500-50000.txt"
//                ,"syntetic\\ND\\d---500-100000.txt"
//                
//                ,"syntetic\\ND\\d--1000-10000.txt"
//                ,"syntetic\\ND\\d--1000-20000.txt"
//                ,"syntetic\\ND\\d--1000-50000.txt"
//                ,"syntetic\\ND\\d--1000-100000.txt"
                "syntetic\\ND\\d-----3-50000.txt"
                ,"syntetic\\ND\\d-----5-100000.txt"
                	};
        String e = "10";
        String m = "4";
        String k = "10";
            
       // for (String fn:in) {
        String fn = null;
        
        
        fn = args[0];
        
        //fn = "manual\\my-file-2d-5-2658.txt";
        
        //String x = "i";
        String x = args[1];
            
        String sss = String.format("%s                                                      ", fn);
        
        
	        System.out.print(sss.substring(0, 35) + "\t");
//                b.sv("k",   k);                   sr.runAlgorithm(TINBC.NAME,                b,  r + fn, t, null); b.clear();
//                b.sv("k",   k);                   sr.runAlgorithm(TINBCSqr.NAME,             b,  r + fn, t, null); b.clear();
//                b.sv("k",   k);                   sr.runAlgorithm(TINBCSqrEps.NAME,          b,  r + fn, t, null); b.clear();
//                b.sv("k",   k);                   sr.runAlgorithm(TINBCSqrEpsEstim.NAME,     b,  r + fn, t, null); b.clear();
//                b.sv("k",   k);                   sr.runAlgorithm(TINBCSqrEpsRef.NAME,       b,  r + fn, t, null); b.clear();
//                b.sv("k",   k);                   sr.runAlgorithm(TINBCSqrEpsNoSqrt.NAME,    b,  r + fn, t, null); b.clear();
//                b.sv("k",   k);                   sr.runAlgorithm(TINBCSqrEpsRefNoSqrt.NAME, b,  r + fn, t, null); b.clear();


/*	        if (x.equals("d")) try {b.sv("k", k); sr.runAlgorithm(TINBC.NAME,                b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err4");System.exit(0);}
	        if (x.equals("e")) try {b.sv("k", k); sr.runAlgorithm(TINBCSqr.NAME,             b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err5");System.exit(0);}
	        if (x.equals("f")) try {b.sv("k", k); sr.runAlgorithm(TINBCSqrEps.NAME,          b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err6");System.exit(0);}
	        if (x.equals("g")) try {b.sv("k", k); sr.runAlgorithm(TINBCSqrEpsEstim.NAME,     b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err7");System.exit(0);}
	        if (x.equals("h")) try {b.sv("k", k); sr.runAlgorithm(TINBCSqrEpsRef.NAME,       b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err8");System.exit(0);}
	        if (x.equals("i")) try {b.sv("k", k); sr.runAlgorithm(TINBCSqrEpsNoSqrt.NAME,    b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err9");System.exit(0);}
	        if (x.equals("j")) try {b.sv("k", k); sr.runAlgorithm(TINBCSqrEpsRefNoSqrt.NAME, b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err10");System.exit(0);}

                if (x.equals("a")) try {b.sv("Eps", e).sv("minPts", m); sr.runAlgorithm(DBSCANTriangle.NAME,    b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err1");}
                if (x.equals("b")) try {b.sv("Eps", e).sv("minPts", m); sr.runAlgorithm(DBSCANTriangleRef.NAME, b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err2");}
                if (x.equals("c")) try {b.sv("Eps", e).sv("minPts", m); sr.runAlgorithm(DBSCANRTree.NAME,       b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err3");}
*/
/*	        if (x.equals("m")) try {b.sv("k", k).sv("b", "7").sv("n", "3"); sr.runAlgorithm(NBCLVA.NAME,             b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err13");System.exit(0);}
	        if (x.equals("n")) try {b.sv("k", k).sv("b", "7"); sr.runAlgorithm(NBCLVADyn.NAME,             b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err14");System.exit(0);}
*/
	        
                if (x.equals("nbc-rtree"))              try {b.sv("k", k); sr.runAlgorithm(NBCRTree.NAME,             b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err11");System.exit(0);}
                if (x.equals("nbc-va-file"))            try {b.sv("k", k).sv("b", "7"); sr.runAlgorithm(NBCVAFile.NAME,             b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err12");System.exit(0);}
                if (x.equals("nbc-ti-sqr"))             try {b.sv("k", k);                 sr.runAlgorithm(TINBCSqr.NAME,  b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err5" );System.exit(0);}
	        if (x.equals("nbc-ti-mink"))            try {b.sv("k", k); b.sv("m", "1"); sr.runAlgorithm(TINBCMink.NAME, b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err15");System.exit(0);}
	        if (x.equals("nbc-ti-sqr-eps"))         try {b.sv("k", k);                 sr.runAlgorithm(TINBCSqrEps.NAME,          b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err16");System.exit(0);}
	        if (x.equals("nbc-ti-sqr-eps-ref"))     try {b.sv("k", k);                 sr.runAlgorithm(TINBCSqrEpsRef.NAME,          b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err17");System.exit(0);}
	        if (x.equals("nbc-ti-sqr-eps-ref-dim")) try {b.sv("k", k);                 sr.runAlgorithm(TINBCSqrEpsRefDim.NAME,          b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err18");System.exit(0);}
	        
	        
	        if (x.equals("nbc-ti-eps-estim"))        try {b.sv("k", k);                 sr.runAlgorithm(TINBCSqrEpsEstim.NAME,          b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err16");System.exit(0);}
	        if (x.equals("nbc-ti-eps-estim-ref"))    try {b.sv("k", k);                 sr.runAlgorithm(TINBCSqrEpsEstimRef.NAME,          b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err16");System.exit(0);}
	        if (x.equals("nbc-ti-eps-estim-ref-dim"))try {b.sv("k", k);                 sr.runAlgorithm(TINBCSqrEpsEstimRefDim.NAME,          b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err16");System.exit(0);}
	        

                if (x.equals("dbscan-ti"))             try {b.sv("Eps", e).sv("minPts", m);              sr.runAlgorithm(DBSCANTriangle.NAME,    b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err1");}
                if (x.equals("dbscan-ti-ref"))         try {b.sv("Eps", e).sv("minPts", m);              sr.runAlgorithm(DBSCANTriangleRef.NAME, b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err2");}
                if (x.equals("dbscan-rtree"))          try {b.sv("Eps", e).sv("minPts", m);              sr.runAlgorithm(DBSCANRTree.NAME,       b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err3");}
	        if (x.equals("dbscan-ti-ref-mink"))    try {b.sv("Eps", e).sv("minPts", m).sv("m", "1"); sr.runAlgorithm(DBSCANTriangleRefMink.NAME,          b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err16");System.exit(0);}
	        
	        
                //
//                b.sv("k", k).sv("b", "8");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();
//                b.sv("k", k).sv("b", "7").sv("n", "7"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
//                b.sv("k", k).sv("b", "5");              sr.runAlgorithm(NBCVAFile.NAME, b,  r + l, t, null); b.clear();
//                b.sv("k", k);                           sr.runAlgorithm(NBCRTree.NAME,  b,  r + l, t, null); b.clear();

    //    }
   
        /*
                for (String fn:in) {                    
                        System.out.println(fn);
                        try {b.sv("Eps", e).sv("minPts", m); sr.runAlgorithm(DBSCANTriangle.NAME,    b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err1");}
                        try {b.sv("Eps", e).sv("minPts", m); sr.runAlgorithm(DBSCANTriangleRef.NAME, b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err2");}
                        try {b.sv("Eps", e).sv("minPts", m); sr.runAlgorithm(DBSCANRTree.NAME,       b,  r + fn, t, null); b.clear();} catch(Exception e1) {System.out.println("err3");}
                }      */
        
        //l = "syntetic\\3D\\syn_3d_1000.txt";
        //b.sv("Eps", "50").sv("minPts", "4"); sr.runAlgorithm(DBSCANTriangle.NAME, b,  r + l, t, null); b.clear();
        
        /*
        l = "syntetic\\3D\\syn_3d_1000.txt";
        k = "10";
        System.out.println(l);        
        b.sv("k", k).sv("b", "5");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "6");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "7");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "5").sv("n", "2"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "6").sv("n", "3"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "7").sv("n", "4"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "5").sv("n", "5"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "6").sv("n", "6"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "7").sv("n", "7"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "5");              sr.runAlgorithm(NBCVAFile.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "5");              sr.runAlgorithm(NBCVAFile.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "5");              sr.runAlgorithm(NBCVAFile.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k);                           sr.runAlgorithm(NBCRTree.NAME,  b,  r + l, t, null); b.clear();
        b.sv("k", k);                           sr.runAlgorithm(NBCRTree.NAME,  b,  r + l, t, null); b.clear();
        b.sv("k", k);                           sr.runAlgorithm(NBCRTree.NAME,  b,  r + l, t, null); b.clear();
        System.out.println();
        */
        
        /*
        l = "syntetic\\ND\\d-5-2000.txt";
        k = "5";
        System.out.println(l);
        b.sv("k", k).sv("b", "2");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "3");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "4");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();                
        //b.sv("k", k).sv("b", "5");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "4").sv("n", "3"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "4").sv("n", "4"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "4").sv("n", "5"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "5").sv("n", "5"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "6").sv("n", "6"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "7").sv("n", "7"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "3");              sr.runAlgorithm(NBCVAFile.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "3");              sr.runAlgorithm(NBCVAFile.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "3");              sr.runAlgorithm(NBCVAFile.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k);                           sr.runAlgorithm(NBCRTree.NAME,  b,  r + l, t, null); b.clear();
        b.sv("k", k);                           sr.runAlgorithm(NBCRTree.NAME,  b,  r + l, t, null); b.clear();
        b.sv("k", k);                           sr.runAlgorithm(NBCRTree.NAME,  b,  r + l, t, null); b.clear();*/
        
        /*
        l = "syntetic\\ND\\d-7-200.txt";
        k = "5";
        System.out.println(l);
        //b.sv("k", k).sv("b", "2");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "3");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "4");              sr.runAlgorithm(NBCLVADyn.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "3").sv("n", "3"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "4").sv("n", "4"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "4").sv("n", "5"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "2").sv("n", "5"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "3").sv("n", "6"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "4").sv("n", "7"); sr.runAlgorithm(NBCLVA.NAME,    b,  r + l, t, null); b.clear();
        b.sv("k", k).sv("b", "3");              sr.runAlgorithm(NBCVAFile.NAME, b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "4");              sr.runAlgorithm(NBCVAFile.NAME, b,  r + l, t, null); b.clear();
        //b.sv("k", k).sv("b", "5");              sr.runAlgorithm(NBCVAFile.NAME, b,  r + l, t, null); b.clear();
        b.sv("k", k);                           sr.runAlgorithm(NBCRTree.NAME,  b,  r + l, t, null); b.clear();
        //b.sv("k", k);                           sr.runAlgorithm(NBCRTree.NAME,  b,  r + l, t, null); b.clear();
        //b.sv("k", k);                           sr.runAlgorithm(NBCRTree.NAME,  b,  r + l, t, null); b.clear();
        */
        
        //sr.log("End...");
    }
    
    public void runAlgorithm(String n, IClusteringParameters p, String file, boolean show, String desc) {
        // set up
        try {
            IClusteringObserver obs = new MyObserver();
    
            // data source
            DataSourceManager dsm = new DataSourceManager(null);
            String id="";
            try {
                id = dsm.readFromFile(file, "2D");
                dsm.setActiveDataSource(id);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            IClusteringDataSource cds = dsm.getActiveDataSource();
    
            IClusteringAlgorithm a;
            
            a = AlgorithmFactory.createAlgorithm(n);
            a.setParameters(p);
            a.addDescription(desc);
            a.setObserver(obs);
            a.setData(cds.getData());
    
            DataView2D dv = null;
            try {
                dv = (DataView2D)dsm.getActiveDataSource();
                a.setObserver(dv);
            } catch (Exception e) {}
            
            a.run();
            
            if (show) {
                // show results
                IClusteringData cd = a.getResult();
                cds.setData(cd);
                if (dv != null) dv.handleNotify(cd);
                cds.showDataSource();
            }
            
            dsm.close();
        } catch (Exception e) {
            //System.out.println("Blad! " + n + ", "+ p.toString() + ", " + e.toString());
        	System.out.println("Blad! " + n + ", "+ p.toString() + ": ");
        	e.printStackTrace();
        }
                
    }

}
