package org.activiti.bpmn.model;

import java.util.ArrayList;
import java.util.List;

public class DataInputSet extends BaseElement {

	protected List<String> dataInputRefs = new ArrayList<String>();
	protected String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getDataInputRefs() {
		return dataInputRefs;
	}

	public void setDataInputRefs(List<String> dataInputRefs) {
		this.dataInputRefs = dataInputRefs;
	}

	@Override
	public DataInputSet clone() {
		DataInputSet clone = new DataInputSet();
		clone.setValues(this);
		return clone;
	}

	public void setValues(DataInputSet otherElement) {
		setName(otherElement.getName());
		dataInputRefs = new ArrayList<String>(otherElement.getDataInputRefs());
	}
	
}
