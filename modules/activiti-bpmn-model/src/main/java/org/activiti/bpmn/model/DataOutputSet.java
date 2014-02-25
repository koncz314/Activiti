package org.activiti.bpmn.model;

import java.util.ArrayList;
import java.util.List;

public class DataOutputSet extends BaseElement {

	protected List<String> dataOutputRefs = new ArrayList<String>();
	protected String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getDataOutputRefs() {
		return dataOutputRefs;
	}

	public void setDataOutputRefs(List<String> dataOutputRefs) {
		this.dataOutputRefs = dataOutputRefs;
	}

	@Override
	public DataOutputSet clone() {
		DataOutputSet clone = new DataOutputSet();
		clone.setValues(this);
		return clone;
	}

	public void setValues(DataOutputSet otherElement) {
		setName(otherElement.getName());
		dataOutputRefs = new ArrayList<String>(otherElement.getDataOutputRefs());
	}
	
}
