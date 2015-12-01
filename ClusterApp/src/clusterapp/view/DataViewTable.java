package clusterapp.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringDataSource;

public class DataViewTable extends JFrame implements IClusteringObserver, IClusteringDataSource
{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	
	IClusteringData data;
	String id;  //  @jve:decl-index=0:
	MainWindow mw;

	/**
	 * This is the default constructor
	 */
	public DataViewTable(MainWindow mw)
	{
		super();
		this.mw = mw;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowActivated(java.awt.event.WindowEvent e) {
				//System.out.println("windowActivated()"); // TODO Auto-generated Event stub windowActivated()
				try {mw.dsm.setActiveDataSource(id);}
				catch(Exception ex) {}
				//System.out.println("Focus gained: " + id);
			}
		});
		this.addFocusListener(new java.awt.event.FocusAdapter() {   
			public void focusLost(java.awt.event.FocusEvent e) {    
				System.out.println("focusLost()"); // TODO Auto-generated Event stub focusLost()
			}
			public void focusGained(java.awt.event.FocusEvent e) {
				mw.dsm.setActiveDataSource(id);
				System.out.println("Focus gained: " + id);
			}
		});
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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			//jTable = new JTable();
			jTable = new JTable(new DataViewTableModel(this));
		}
		return jTable;
	}

	@Override
	public void handleNotify(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleNotify(IClusteringData data) {
		// TODO Auto-generated method stub
		this.data = data;
		jTable = new JTable(new DataViewTableModel(this));
		jScrollPane.setViewportView(getJTable());
	}

	@Override
	public void handleNotify(String message) {
		// TODO Auto-generated method stub
		mw.addLog(message);
	}

	@Override
	public IClusteringData getData() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void saveDataSource() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setData(IClusteringData clusteringOutput) {
		// TODO Auto-generated method stub
		data = clusteringOutput;
		jTable = new JTable(new DataViewTableModel(this));
		jScrollPane.setViewportView(getJTable());
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public void showDataSource() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}

    @Override
    public void close() {
        //System.out.println("DataViewTable.close()");
        // TODO Auto-generated method stub
        
    }

}
