package org.activiti.bpmn.model;

import java.util.Collection;

public interface DataObjectContainer {
	public void addDataObject(DataObject data);

	public boolean containsDataObject(DataObject data);

	public DataObject getDataObject(String id);

	public Collection<DataObject> getAllDataObjects();

	public Collection<String> getDataObjectIds();
}
