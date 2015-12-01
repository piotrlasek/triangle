package clusterapp.view;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.event.KeyEvent;
import javax.swing.JButton;

import java.util.*;
import java.text.*;
import java.awt.*;
import java.io.*;

import clusterapp.controller.DataSourceManager;
import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringDataSource;
import clusterapp.model.api.IClusteringObject;

import java.awt.Rectangle;
import java.io.FileInputStream;

import lvaindex.vafile.ISpatialObject;

public class DataSourceFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel1 = null;
	private JCheckBox jCheckBoxDS2D = null;
	private JLabel jLabel2 = null;
	private JButton jButtonLoad = null;
	private JButton jButtonSave = null;
	DataSourceFrame thisFrame;
	MainWindow mw;
	private JLabel jLabel11 = null;
	private JLabel jLabelActiveDataSource = null;
	private JButton jButtonNew = null;
	/**
	 * This is the default constructor
	 */
	public DataSourceFrame(MainWindow mw, DataSourceManager dsm) {
		super();
		initialize();
		this.setLocation(mw.af.getLocation().x, mw.af.getLocation().y + mw.af.getSize().height);
		this.setSize(mw.af.getSize().width, mw.af.getSize().height);
		this.mw = mw;
		thisFrame = this;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(726, 118);
		this.setTitle("Data source");
		this.setContentPane(getJContentPane());
		//this.setTitle("Data source");
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
			jContentPane.add(getJPanel2(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabelActiveDataSource = new JLabel();
			jLabelActiveDataSource.setHorizontalAlignment(SwingConstants.LEFT);
			jLabelActiveDataSource.setText("");
			jLabelActiveDataSource.setSize(new Dimension(604, 26));
			jLabelActiveDataSource.setLocation(new Point(110, 59));
			jLabelActiveDataSource.setPreferredSize(new Dimension(56, 26));
			jLabel11 = new JLabel();
			jLabel11.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel11.setText("Active:");
			jLabel11.setSize(new Dimension(84, 26));
			jLabel11.setLocation(new Point(5, 59));
			jLabel11.setPreferredSize(new Dimension(56, 26));
			jLabel2 = new JLabel();
			jLabel2.setLocation(new Point(5, 32));
			jLabel2.setPreferredSize(new Dimension(56, 26));
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2.setText("Show:");
			jLabel2.setSize(new Dimension(85, 26));
			jLabel1 = new JLabel();
			jLabel1.setLocation(new Point(5, 5));
			jLabel1.setPreferredSize(new Dimension(56, 26));
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setText("Data source:");
			jLabel1.setSize(new Dimension(85, 26));
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(0, 60));
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJCheckBoxDS2D(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJButtonSave(), null);
			jPanel2.add(getJButtonk(), null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabelActiveDataSource, null);
			jPanel2.add(getJButtonNew(), null);
		}
		return jPanel2;
	}


	/**
	 * This method initializes jCheckBoxDS2D	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxDS2D() {
		if (jCheckBoxDS2D == null) {
			jCheckBoxDS2D = new JCheckBox();
			jCheckBoxDS2D.setLocation(new Point(110, 32));
			jCheckBoxDS2D.setPreferredSize(new Dimension(56, 26));
			jCheckBoxDS2D.setMnemonic(KeyEvent.VK_UNDEFINED);
			jCheckBoxDS2D.setText("2D plot (if possible)");
			jCheckBoxDS2D.setSelected(true);
			jCheckBoxDS2D.setSize(new Dimension(143, 24));
		}
		return jCheckBoxDS2D;
	}

	/**
	 * This method initializes jButtonLoad	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonk() {
		if (jButtonLoad == null) {
			jButtonLoad = new JButton();
			jButtonLoad.setText("Load");
			jButtonLoad.setBounds(new Rectangle(110, 5, 64, 26));
			jButtonLoad.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					String title = "Open...";
					FileDialog fd = new FileDialog(thisFrame, title, FileDialog.LOAD);
					fd.setLocation(50, 50);
					fd.setVisible(true);

					if ( fd.getFile() != null )
					{
						String path = fd.getDirectory() + "/" + fd.getFile();
						String id = null;
						try
						{
							if ( jCheckBoxDS2D.isSelected() )
							{
								id = mw.dsm.readFromFile(path, "2D");
							}
							else
							{
								id = mw.dsm.readFromFile(path, "nD");
							}
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
						
						//jLabelDataSourceFile.setText(id);
						jLabelActiveDataSource.setText(fd.getFile());
						mw.dsm.showDataSource(id);
					}

				}
			});
		}
		return jButtonLoad;
	}

	/**
	 * This method initializes jButtonSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSave() {
		if (jButtonSave == null) {
			jButtonSave = new JButton();
			jButtonSave.setLocation(new Point(182, 5));
			jButtonSave.setText("Save");
			jButtonSave.setSize(new Dimension(64, 26));
			jButtonSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
			        String title = "Save...";
			        
			        IClusteringDataSource dv = mw.dsm.getActiveDataSource();
			        
			        if (dv != null)
			        {
			        
			        String f = "aa";
			        FileDialog fd = new FileDialog(thisFrame, title, FileDialog.SAVE);
			        //fd.setFile(fileType);
			        fd.setFile(dv.getId());
			        fd.setDirectory("");
			        fd.setLocation(50, 50);
			        fd.setVisible(true);
			        
			        if (fd.getFile() != null)
			        {
				        
				         
				        try {
				            FileOutputStream fout =  new FileOutputStream(fd.getDirectory() + "/" + fd.getFile());
				            BufferedWriter myOutput = new BufferedWriter(new OutputStreamWriter(fout));
				            IClusteringData data = dv.getData();
				            Collection<IClusteringObject> collection = data.get();
				            
				            for(IClusteringObject o:collection)
				            {
				            	myOutput.write(toLine(o) + "\n");
				            }				            
	
				            myOutput.flush();
				            
				        } catch (Exception ex) {
				           ex.printStackTrace();
				        }
			        }
			        }
			        else
			        {
			        	JOptionPane.showMessageDialog(null, "Select data source.");
			        }
				}
			});
		}
		return jButtonSave;
	}
	
	public String toLine(IClusteringObject co)
	{
    	ISpatialObject so = co.getSpatialObject();
    	double[] coord = so.getCoordinates();
    	int group = co.getClusterInfo().getClusterId();

		String s = new String();
		for (double d:coord)
		{
			s += d + ":" ;
		}
		s += group;
		return s;
	}


	/**
	 * This method initializes jButtonNew	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonNew() {
		if (jButtonNew == null) {
			jButtonNew = new JButton();
			jButtonNew.setText("New");
			jButtonNew.setSize(new Dimension(64, 26));
			jButtonNew.setLocation(new Point(254, 5));
			jButtonNew.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					try
					{
						String id;
						
						if (jCheckBoxDS2D.isSelected())
						{
							id = mw.dsm.createNewDataSource("2D");
						}
						else
						{
							id = mw.dsm.createNewDataSource("nD");
						}
						mw.dsm.showDataSource(id);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			});
		}
		return jButtonNew;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
