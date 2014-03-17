package org.activiti.bpmn.model;

public class DataState extends BaseElement {

	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValues(DataState other) {
		super.setValues(other);
		setName(other.getName());
	}
	
	@Override
	public DataState clone() {
		DataState clone = new DataState();
		clone.setValues(this);
		return clone;
	}

}
