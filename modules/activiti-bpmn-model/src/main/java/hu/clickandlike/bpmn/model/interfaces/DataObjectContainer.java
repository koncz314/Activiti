package hu.clickandlike.bpmn.model.interfaces;

import java.util.Collection;

import org.activiti.bpmn.model.DataObject;

public interface DataObjectContainer {
	public void addDataObject(DataObject data);

	public boolean containsDataObject(DataObject data);

	public DataObject getDataObject(String id);

	public Collection<DataObject> getAllDataObjects();

	public Collection<String> getDataObjectIds();
}
