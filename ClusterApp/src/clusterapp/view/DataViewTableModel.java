package clusterapp.view;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import clusterapp.model.api.IClusteringData;
import clusterapp.model.api.IClusteringDataSource;
import clusterapp.model.api.IClusteringObject;

public class DataViewTableModel implements TableModel {
    
	int columnCount;
	ArrayList<IClusteringObject> data;
	IClusteringData clusteringData;
	
	DataViewTableModel(IClusteringDataSource datasource)
	{
		if ( datasource != null &&
				(clusteringData = datasource.getData()) != null &&
				(data = (ArrayList<IClusteringObject>)clusteringData.get()) != null &&
				data.iterator().hasNext() )
		{
			columnCount = data.iterator().next().getSpatialObject().getCoordinates().length + 3;
		}
		else
		{
			columnCount = 0;
		}
	}

	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnCount;
	}

	@Override
	public String getColumnName(int arg0) {
		if (data != null)
		{
			if (arg0 == columnCount - 2)
				return "Clust.";
			if (arg0 == columnCount - 1)
				return "Prev.";
			if (arg0 == 0 )
				return "No.";
			else
				return "Att_" + arg0;
		}
		else
			return "";
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		if (data != null)
			return data.size();
		else
			return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if(arg1 == 0)
			return "" + (arg0 + 1);
		else
		{
			arg1--;
			if (data != null)
			{
				IClusteringObject o = data.get(arg0);
				double[]coord = o.getSpatialObject().getCoordinates();
				
				if (arg1 == coord.length)
					return new Integer(o.getClusterInfo().getClusterId());
				else if (arg1 == coord.length + 1)
					return new Integer(o.getClusterInfo().getPreviousClusterId());
				else
					return new Double(coord[arg1]);
			}
			else
			{
				return null;
			}
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	

}