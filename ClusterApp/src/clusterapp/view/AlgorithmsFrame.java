package clusterapp.view;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import clusterapp.controller.ClusteringController;
import clusterapp.model.api.BasicClusteringParameters;
import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringDataSource;
import clusterapp.model.api.IClusteringObject;
import clusterapp.model.api.IndicesTest;
import clusterapp.model.dbscan.DBSCANLVA;
import clusterapp.model.dbscan.DBSCANRTree;
import clusterapp.model.dbscan.DBSCANVAFILE;
import clusterapp.model.nbc.NBCLVA;
import clusterapp.model.nbc.NBCRTree;
import clusterapp.model.nbc.NBCVAFile;

import java.awt.ComponentOrientation;
import java.awt.Point;
import javax.swing.SwingConstants;
import java.awt.event.KeyEvent;
import java.util.Collection;
import javax.swing.JRadioButton;

public class AlgorithmsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel1 = null;
	private JButton jButtonNbcLvaStart = null;
	private MainWindow mw;
	private JLabel jLabelNbcLvaK = null;
	private JTextField jTextFieldNbcLvaK = null;
	private JPanel jPanel11 = null;
	private JLabel jLabelNbcVAFileK = null;
	private JTextField jTextFieldNbcVAFileK = null;
	private JPanel jPanel111 = null;
	private JLabel jLabelNbcRTreeK = null;
	private JTextField jTextFieldNbcRTreeK = null;
	private JPanel jPanel = null;
	private JLabel jLabelNbcLvaL = null;
	private JTextField jTextFieldNbcLvaB = null;
	private JButton jButtonNbcVAFileStart = null;
	private JButton jButtonNbcRTreeStart2 = null;
	private JPanel jPanelIndicesTest = null;
	private JButton jButtonTestStart = null;
	private JLabel jLabelNbcLvaL1 = null;
	private JTextField jTextFieldNbcLvaN = null;
	private JTextField jTextFieldNbcVafB = null;
	private JLabel jLabelNbcLvaL2 = null;
	private JPanel jPanel1111 = null;
	private JLabel jLabelNbcRTreeK1 = null;
	private JTextField jTextFieldDbscanMinPts = null;
	private JButton jButtonNbcRTreeStart21 = null;
	private JRadioButton jRadioButtonDbscanLva = null;
	private JRadioButton jRadioButtonDbscanVafile = null;
	private JRadioButton jRadioButtonDbscanRTree = null;
	private JTextField jTextFieldDbscanEps = null;
	private JLabel jLabelDbscanEps = null;
	private JTextField jTextFieldDbscanB = null;
	private JLabel jLabelDbscanB = null;
	private JTextField jTextFieldDbscanN = null;
	private JLabel jLabelDbscanN = null;
	public AlgorithmsFrame(MainWindow mw) {
		super();
		initialize();
		this.setLocation(mw.getSize().width, 0);
		this.mw = mw;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(886, 182);
		this.setPreferredSize(new Dimension(300, 101));
		this.setContentPane(getJContentPane());
		this.setTitle("Algorithms");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.EAST);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setName("DBSCAN");
			jTabbedPane.setToolTipText("");
			jTabbedPane.setTabPlacement(JTabbedPane.TOP);
			jTabbedPane.addTab("NBC (LVA-Index)", null, getJPanel1(), null);
			jTabbedPane.addTab("NBC (VA-File)", null, getJPanel11(), null);
			jTabbedPane.addTab("NBC (RTree)", null, getJPanel111(), null);
			jTabbedPane.addTab("DBSCAN (LVA-Index)", null, getJPanel1111(), null);
			jTabbedPane.addTab("Test", null, getJPanelIndicesTest(), null);			
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					int index = jTabbedPane.getSelectedIndex();
					switch(index)
					{
					case 0:
						jTextFieldNbcLvaK.setSelectionStart(0);
						jTextFieldNbcLvaK.setSelectionEnd(jTextFieldNbcLvaK.getText().length());
						break;
					case 1:
						jTextFieldNbcVAFileK.setSelectionStart(0);
						jTextFieldNbcVAFileK.setSelectionEnd(jTextFieldNbcVAFileK.getText().length());
						break;
					case 2:
						jTextFieldNbcRTreeK.setSelectionStart(0);
						jTextFieldNbcRTreeK.setSelectionEnd(jTextFieldNbcRTreeK.getText().length());
						break;
					default:
					}
				}
			});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabelNbcLvaL1 = new JLabel();
			jLabelNbcLvaL1.setText("n =");
			jLabelNbcLvaL1.setLocation(new Point(5, 56));
			jLabelNbcLvaL1.setSize(new Dimension(24, 16));
			jLabelNbcLvaL = new JLabel();
			jLabelNbcLvaL.setText("b =");
			jLabelNbcLvaL.setLocation(new Point(5, 35));
			jLabelNbcLvaL.setSize(new Dimension(24, 16));
			jLabelNbcLvaK = new JLabel();
			jLabelNbcLvaK.setText("k = ");
			jLabelNbcLvaK.setLocation(new Point(5, 14));
			jLabelNbcLvaK.setSize(new Dimension(25, 16));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setName("");
			jPanel1.add(getJButtonNbcLvaStart(), null);
			jPanel1.add(jLabelNbcLvaK, null);
			jPanel1.add(getJTextFieldNbcLvaK(), null);
			jPanel1.add(jLabelNbcLvaL, null);
			jPanel1.add(getJTextFieldNbcLvaB(), null);
			jPanel1.add(jLabelNbcLvaL1, null);
			jPanel1.add(getJTextFieldNbcLvaN(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButtonNbcLvaStart	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonNbcLvaStart() {
        if (jButtonNbcLvaStart == null) {
            jButtonNbcLvaStart = new JButton();
            jButtonNbcLvaStart.setText("Start");
            jButtonNbcLvaStart.setBounds(new Rectangle(392, 64, 62, 26));

            jButtonNbcLvaStart
                    .addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                            BasicClusteringParameters bcp = new BasicClusteringParameters();
                            bcp.setValue("algorithm_name", NBCLVA.NAME);
                            bcp.setValue("k", jTextFieldNbcLvaK.getText());
                            bcp.setValue("b", jTextFieldNbcLvaB.getText());
                            bcp.setValue("n", jTextFieldNbcLvaN.getText());
                            runAlgorithm(bcp);
                        }
                    });
        }
        return jButtonNbcLvaStart;
    }

	/**
	 * This method initializes jTextFieldNbcLvaK	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldNbcLvaK() {
		if (jTextFieldNbcLvaK == null) {
			jTextFieldNbcLvaK = new JTextField();
			jTextFieldNbcLvaK.setSize(new Dimension(30, 20));
			jTextFieldNbcLvaK.setText("22");
			jTextFieldNbcLvaK.setHorizontalAlignment(JTextField.RIGHT);
			jTextFieldNbcLvaK.setLocation(new Point(30, 11));
		}
		return jTextFieldNbcLvaK;
	}

	/**
	 * This method initializes jPanel11	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jLabelNbcLvaL2 = new JLabel();
			jLabelNbcLvaL2.setBounds(new Rectangle(5, 35, 24, 16));
			jLabelNbcLvaL2.setText("b =");
			jLabelNbcVAFileK = new JLabel();
			jLabelNbcVAFileK.setText("k = ");
			jLabelNbcVAFileK.setLocation(new Point(5, 14));
			jLabelNbcVAFileK.setSize(new Dimension(25, 16));
			jPanel11 = new JPanel();
			jPanel11.setLayout(null);
			jPanel11.setName("");
			jPanel11.add(jLabelNbcVAFileK, null);
			jPanel11.add(getJTextFieldNbcVAFileK(), null);
			jPanel11.add(getJButtonNbcVAFileStart(), null);
			jPanel11.add(getJTextFieldNbcVafB(), null);
			jPanel11.add(jLabelNbcLvaL2, null);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jTextFieldNbcVAFileK	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldNbcVAFileK() {
		if (jTextFieldNbcVAFileK == null) {
			jTextFieldNbcVAFileK = new JTextField();
			jTextFieldNbcVAFileK.setText("22");
			jTextFieldNbcVAFileK.setLocation(new Point(30, 11));
			jTextFieldNbcVAFileK.setHorizontalAlignment(JTextField.RIGHT);
			jTextFieldNbcVAFileK.setSize(new Dimension(30, 20));
		}
		return jTextFieldNbcVAFileK;
	}

	/**
	 * This method initializes jPanel111	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel111() {
		if (jPanel111 == null) {
			jLabelNbcRTreeK = new JLabel();
			jLabelNbcRTreeK.setText("k = ");
			jLabelNbcRTreeK.setLocation(new Point(15, 14));
			jLabelNbcRTreeK.setSize(new Dimension(25, 16));
			jPanel111 = new JPanel();
			jPanel111.setLayout(null);
			jPanel111.setName("");
			jPanel111.add(jLabelNbcRTreeK, null);
			jPanel111.add(getJTextFieldNbcRTreeK(), null);
			jPanel111.add(getJButtonNbcRTreeStart2(), null);
		}
		return jPanel111;
	}

	/**
	 * This method initializes jTextFieldNbcRTreeK	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldNbcRTreeK() {
		if (jTextFieldNbcRTreeK == null) {
			jTextFieldNbcRTreeK = new JTextField();
			jTextFieldNbcRTreeK.setBounds(new Rectangle(39, 11, 31, 20));
			jTextFieldNbcRTreeK.setText("22");
		}
		return jTextFieldNbcRTreeK;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setPreferredSize(new Dimension(160, 0));
			jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextFieldNbcLvaB	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldNbcLvaB() {
		if (jTextFieldNbcLvaB == null) {
			jTextFieldNbcLvaB = new JTextField();
			jTextFieldNbcLvaB.setLocation(new Point(30, 32));
			jTextFieldNbcLvaB.setText("5");
			jTextFieldNbcLvaB.setHorizontalAlignment(JTextField.RIGHT);
			jTextFieldNbcLvaB.setSize(new Dimension(30, 20));
		}
		return jTextFieldNbcLvaB;
	}

	/**
	 * This method initializes jButtonNbcVAFileStart	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonNbcVAFileStart() {
		if (jButtonNbcVAFileStart == null) {
			jButtonNbcVAFileStart = new JButton();
			jButtonNbcVAFileStart.setPreferredSize(new Dimension(62, 26));
			jButtonNbcVAFileStart.setLocation(new Point(392, 64));
			jButtonNbcVAFileStart.setSize(new Dimension(62, 26));
			jButtonNbcVAFileStart.setText("Start");
			jButtonNbcVAFileStart.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
			    	BasicClusteringParameters bcp = new BasicClusteringParameters();
			    	bcp.setValue("algorithm_name", NBCVAFile.NAME);
			    	bcp.setValue("k", jTextFieldNbcVAFileK.getText());	
			    	bcp.setValue("b", jTextFieldNbcVafB.getText());
	    	    	runAlgorithm(bcp);
				}
			});
		}
		return jButtonNbcVAFileStart;
	}

	/**
	 * This method initializes jButtonNbcRTreeStart2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonNbcRTreeStart2() {
		if (jButtonNbcRTreeStart2 == null) {
			jButtonNbcRTreeStart2 = new JButton();
			jButtonNbcRTreeStart2.setPreferredSize(new Dimension(62, 26));
			jButtonNbcRTreeStart2.setSize(new Dimension(62, 26));
			jButtonNbcRTreeStart2.setText("Start");
			jButtonNbcRTreeStart2.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonNbcRTreeStart2.setLocation(new Point(392, 64));
			jButtonNbcRTreeStart2.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e)
				{
			    	BasicClusteringParameters bcp = new BasicClusteringParameters();
			    	bcp.setValue("algorithm_name", NBCRTree.NAME);
			    	bcp.setValue("k", jTextFieldNbcRTreeK.getText());	
	    	    	runAlgorithm(bcp);
				}
			});
			jButtonNbcRTreeStart2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jButtonNbcRTreeStart2;
	}

	private void runAlgorithm(BasicClusteringParameters bcp) {
		ClusteringController cc = new ClusteringController();
		cc.addObserver(mw);
		IClusteringDataSource ds = mw.dsm.getActiveDataSource();
		
		if (ds != null)
		{
			IClusteringData cd = ds.getData();
			if (cd != null)
			{
				Collection<IClusteringObject> collection = cd.get();
				if (collection != null && collection.size() > 0)
				{
					IClusteringObject o = collection.iterator().next();
					
					if (ds instanceof DataView2D &&  o.getSpatialObject().getCoordinates().length == 2)
					{
		    	    	cc.setGraphics(((DataView2D)ds).getGraphics());
		    	    	cc.addObserver((DataView2D)ds);
					}
					else
					{
						cc.addObserver(((DataViewTable)ds));
					}
			    	cc.setDataSource(ds);
			    	cc.run(bcp);
			    	ds.showDataSource();
				}
			}
		}
	}

	/**
	 * This method initializes jPanelIndicesTest	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelIndicesTest() {
		if (jPanelIndicesTest == null) {
			jPanelIndicesTest = new JPanel();
			jPanelIndicesTest.setLayout(null);
			jPanelIndicesTest.add(getJButtonTestStart(), null);
		}
		return jPanelIndicesTest;
	}

	/**
	 * This method initializes jButtonTestStart	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonTestStart() {
		if (jButtonTestStart == null) {
			jButtonTestStart = new JButton();
			jButtonTestStart.setSize(new Dimension(62, 26));
			jButtonTestStart.setText("Start");
			jButtonTestStart.setLocation(new Point(392, 64));
			jButtonTestStart.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					
			    	BasicClusteringParameters bcp = new BasicClusteringParameters();
			    	bcp.setValue("algorithm_name", IndicesTest.NAME);
			    	bcp.setValue("k", jTextFieldNbcRTreeK.getText());	
	    	    	runAlgorithm(bcp);

					
				}
			});
		}
		return jButtonTestStart;
	}

	/**
	 * This method initializes jTextFieldNbcLvaN	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldNbcLvaN() {
		if (jTextFieldNbcLvaN == null) {
			jTextFieldNbcLvaN = new JTextField();
			jTextFieldNbcLvaN.setHorizontalAlignment(JTextField.RIGHT);
			jTextFieldNbcLvaN.setSize(new Dimension(30, 20));
			jTextFieldNbcLvaN.setLocation(new Point(30, 53));
			jTextFieldNbcLvaN.setText("2");
		}
		return jTextFieldNbcLvaN;
	}

	/**
	 * This method initializes jTextFieldNbcVafB	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldNbcVafB() {
		if (jTextFieldNbcVafB == null) {
			jTextFieldNbcVafB = new JTextField();
			jTextFieldNbcVafB.setHorizontalAlignment(JTextField.RIGHT);
			jTextFieldNbcVafB.setLocation(new Point(30, 32));
			jTextFieldNbcVafB.setSize(new Dimension(30, 20));
			jTextFieldNbcVafB.setText("5");
		}
		return jTextFieldNbcVafB;
	}

	/**
	 * This method initializes jPanel1111	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1111() {
		if (jPanel1111 == null) {
			jLabelDbscanN = new JLabel();
			jLabelDbscanN.setBounds(new Rectangle(14, 85, 17, 16));
			jLabelDbscanN.setText("n =");
			jLabelDbscanB = new JLabel();
			jLabelDbscanB.setBounds(new Rectangle(15, 60, 31, 16));
			jLabelDbscanB.setText("b =");
			jLabelDbscanEps = new JLabel();
			jLabelDbscanEps.setBounds(new Rectangle(16, 35, 50, 16));
			jLabelDbscanEps.setText("Eps =");
			jLabelNbcRTreeK1 = new JLabel();
			jLabelNbcRTreeK1.setLocation(new Point(15, 14));
			jLabelNbcRTreeK1.setText("minPts =");
			jLabelNbcRTreeK1.setSize(new Dimension(61, 16));
			jPanel1111 = new JPanel();
			jPanel1111.setLayout(null);
			jPanel1111.setName("DBSCAN (LVA-Index)");
			jPanel1111.add(jLabelNbcRTreeK1, null);
			jPanel1111.add(getJTextFieldDbscanMinPts(), null);
			jPanel1111.add(getJButtonNbcRTreeStart21(), null);
			/*jPanel1111.add(getJRadioButtonDbscanLva(), null);
			jPanel1111.add(getJRadioButtonDbscanVafile(), null);
			jPanel1111.add(getJRadioButtonDbscanRTree(), null);*/
			jPanel1111.add(getJTextFieldDbscanEps(), null);
		    ButtonGroup group = new ButtonGroup();
		    group.add(getJRadioButtonDbscanLva());
		    group.add(getJRadioButtonDbscanVafile());
		    group.add(getJRadioButtonDbscanRTree());
		    //jPanel1111.add(group);
			jPanel1111.add(jLabelDbscanEps, null);
			jPanel1111.add(getJRadioButtonDbscanLva(), null);
			jPanel1111.add(getJRadioButtonDbscanVafile(), null);
			jPanel1111.add(getJRadioButtonDbscanRTree(), null);
			jPanel1111.add(getJTextFieldDbscanB(), null);
			jPanel1111.add(jLabelDbscanB, null);
			jPanel1111.add(getJTextFieldDbscanN(), null);
			jPanel1111.add(jLabelDbscanN, null);
		}
		return jPanel1111;
	}

	/**
	 * This method initializes jTextFieldDbscanMinPts	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldDbscanMinPts() {
		if (jTextFieldDbscanMinPts == null) {
			jTextFieldDbscanMinPts = new JTextField();
			jTextFieldDbscanMinPts.setBounds(new Rectangle(78, 12, 37, 20));
			jTextFieldDbscanMinPts.setText("4");
		}
		return jTextFieldDbscanMinPts;
	}

	/**
	 * This method initializes jButtonNbcRTreeStart21	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonNbcRTreeStart21() {
		if (jButtonNbcRTreeStart21 == null) {
			jButtonNbcRTreeStart21 = new JButton();
			jButtonNbcRTreeStart21.setLocation(new Point(392, 64));
			jButtonNbcRTreeStart21.setPreferredSize(new Dimension(62, 26));
			jButtonNbcRTreeStart21.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonNbcRTreeStart21.setText("Start");
			jButtonNbcRTreeStart21.setSize(new Dimension(62, 26));
			jButtonNbcRTreeStart21.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
			    	BasicClusteringParameters bcp = new BasicClusteringParameters();
			    	if (jRadioButtonDbscanLva.isSelected())
			    	{
				    bcp.setValue("algorithm_name", DBSCANLVA.NAME);
				    bcp.setValue("minPts", jTextFieldDbscanMinPts.getText());	
				    bcp.setValue("Eps", jTextFieldDbscanEps.getText());
				    bcp.setValue("b", jTextFieldDbscanB.getText());
				    bcp.setValue("n", jTextFieldDbscanN.getText());
		    	    	    runAlgorithm(bcp);
			    	}
			    	else if (jRadioButtonDbscanVafile.isSelected())
			    	{
				    bcp.setValue("algorithm_name", DBSCANVAFILE.NAME);
				    bcp.setValue("minPts", jTextFieldDbscanMinPts.getText());	
				    bcp.setValue("Eps", jTextFieldDbscanEps.getText());
				    bcp.setValue("b", jTextFieldDbscanB.getText());
		    	    	    runAlgorithm(bcp);
			    	}
			    	else if (jRadioButtonDbscanRTree.isSelected())
			    	{
				   bcp.setValue("algorithm_name", DBSCANRTree.NAME);
				   bcp.setValue("minPts", jTextFieldDbscanMinPts.getText());	
				   bcp.setValue("Eps", jTextFieldDbscanEps.getText());
		    	    	   runAlgorithm(bcp);
			    	}
			    		
				}
			});
		}
		return jButtonNbcRTreeStart21;
	}

	/**
	 * This method initializes jRadioButtonDbscanLva	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonDbscanLva() {
		if (jRadioButtonDbscanLva == null) {
			jRadioButtonDbscanLva = new JRadioButton();
			jRadioButtonDbscanLva.setText("LVA-Index");
			jRadioButtonDbscanLva.setSelected(true);
			jRadioButtonDbscanLva.setBounds(new Rectangle(161, 13, 104, 21));
		}
		return jRadioButtonDbscanLva;
	}

	/**
	 * This method initializes jRadioButtonDbscanVafile	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonDbscanVafile() {
		if (jRadioButtonDbscanVafile == null) {
			jRadioButtonDbscanVafile = new JRadioButton();
			jRadioButtonDbscanVafile.setText("VA-File");
			jRadioButtonDbscanVafile.setBounds(new Rectangle(161, 32, 83, 24));
		}
		return jRadioButtonDbscanVafile;
	}

	/**
	 * This method initializes jRadioButtonDbscanRTree	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonDbscanRTree() {
		if (jRadioButtonDbscanRTree == null) {
			jRadioButtonDbscanRTree = new JRadioButton();
			jRadioButtonDbscanRTree.setText("R-Tree");
			jRadioButtonDbscanRTree.setBounds(new Rectangle(161, 54, 83, 24));
		}
		return jRadioButtonDbscanRTree;
	}

	/**
	 * This method initializes jTextFieldDbscanEps	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldDbscanEps() {
		if (jTextFieldDbscanEps == null) {
			jTextFieldDbscanEps = new JTextField();
			jTextFieldDbscanEps.setBounds(new Rectangle(78, 33, 34, 20));
			jTextFieldDbscanEps.setText("12");
		}
		return jTextFieldDbscanEps;
	}

	/**
	 * This method initializes jTextFieldDbscanB	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldDbscanB() {
		if (jTextFieldDbscanB == null) {
			jTextFieldDbscanB = new JTextField();
			jTextFieldDbscanB.setBounds(new Rectangle(78, 57, 34, 20));
			jTextFieldDbscanB.setText("5");
		}
		return jTextFieldDbscanB;
	}

	/**
	 * This method initializes jTextFieldDbscanN	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldDbscanN() {
		if (jTextFieldDbscanN == null) {
			jTextFieldDbscanN = new JTextField();
			jTextFieldDbscanN.setBounds(new Rectangle(78, 82, 30, 20));
			jTextFieldDbscanN.setText("2");
		}
		return jTextFieldDbscanN;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
