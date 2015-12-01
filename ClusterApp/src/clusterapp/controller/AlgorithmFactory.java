package clusterapp.controller;

import clusterapp.model.api.IClusteringAlgorithm;
import clusterapp.model.nbc.NBCLVADyn;
import clusterapp.model.nbc.NBCRTree;
import clusterapp.model.nbc.NBCLVA;
import clusterapp.model.nbc.TINBC;
import clusterapp.model.nbc.TINBCMink;
import clusterapp.model.nbc.TINBCSqr;
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
import clusterapp.model.api.IndicesTest;
import clusterapp.model.nbc.NBCVAFile;
import clusterapp.model.dbscan.*;

public class AlgorithmFactory {
    
    public static IClusteringAlgorithm createAlgorithm(String algorithmName) {
        IClusteringAlgorithm ca = null;

        if (algorithmName.equals(NBCRTree.NAME)) {
            ca = new NBCRTree();
        } else if (algorithmName.equals(NBCLVA.NAME)) {
            ca = new NBCLVA();
        } else if (algorithmName.equals(NBCLVADyn.NAME)) {
            ca = new NBCLVADyn();
        } else if (algorithmName.equals(NBCVAFile.NAME)) {
            ca = new NBCVAFile();
        } else if (algorithmName.equals(TINBC.NAME)) {
            ca = new TINBC();
        } else if (algorithmName.equals(TINBCSqr.NAME)) {
            ca = new TINBCSqr();
        } else if (algorithmName.equals(TINBCSqrRef.NAME)) {
            ca = new TINBCSqrRef();
        } else if (algorithmName.equals(TINBCSqrEps.NAME)) {
            ca = new TINBCSqrEps();
        } else if (algorithmName.equals(TINBCSqrEpsEstim.NAME)) {
            ca = new TINBCSqrEpsEstim();
        } else if (algorithmName.equals(TINBCSqrEpsRef.NAME)) {
            ca = new TINBCSqrEpsRef();
        } else if (algorithmName.equals(TINBCSqrEpsNoSqrt.NAME)) {
            ca = new TINBCSqrEpsNoSqrt();
        } else if (algorithmName.equals(TINBCSqrEpsRefNoSqrt.NAME)) {
            ca = new TINBCSqrEpsRefNoSqrt();
        } else if (algorithmName.equals(DBSCANLVA.NAME)) {
            ca = new DBSCANLVA();
        } else if (algorithmName.equals(DBSCANLVADyn.NAME)) {
            ca = new DBSCANLVADyn();
        } else if (algorithmName.equals(DBSCANVAFILE.NAME)) {
            ca = new DBSCANVAFILE();
        } else if (algorithmName.equals(DBSCANRTree.NAME)) {
            ca = new DBSCANRTree();
        } else if (algorithmName.equals(DBSCANTriangle.NAME)) {
            ca = new DBSCANTriangle();
        } else if (algorithmName.equals(DBSCANTriangleRef.NAME)) {
            ca = new DBSCANTriangleRef();
        } else if (algorithmName.equals(IndicesTest.NAME)) {
            ca = new IndicesTest();
        } else if (algorithmName.equals(Tests.NAME)) {
            ca = new Tests();
        } else if (algorithmName.equals(TINBCMink.NAME)) {
            ca = new TINBCMink();
        } else if (algorithmName.equals(TINBCSqrEpsRefDim.NAME)) {
            ca = new TINBCSqrEpsRefDim();
        } else if (algorithmName.equals(TINBCSqrEpsEstimRef.NAME)) {
            ca = new TINBCSqrEpsEstimRef();
        } else if (algorithmName.equals(TINBCSqrEpsEstimRefDim.NAME)) {
            ca = new TINBCSqrEpsEstimRefDim();
        } else if (algorithmName.equals(DBSCANTriangleRefMink.NAME)) {
            ca = new DBSCANTriangleRefMink();
        }
        

        return ca;
    }
}
