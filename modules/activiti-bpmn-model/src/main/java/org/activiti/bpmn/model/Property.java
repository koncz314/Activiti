package org.activiti.bpmn.model;

public class Property extends BaseElement {

	protected DataState dataState;
	protected String name;
	protected String itemSubjectRef;

	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemSubjectRef() {
		return itemSubjectRef;
	}

	public void setItemSubjectRef(String itemSubjectRef) {
		this.itemSubjectRef = itemSubjectRef;
	}

	public void setValues(Property other) {
		super.setValues(other);
		setName(other.getName());
		setItemSubjectRef(other.getItemSubjectRef());
		if (other.getDataState() != null) {
			setDataState(other.getDataState().clone());
		}
	}
	
	@Override
	public Property clone() {
		Property clone = new Property();
		clone.setValues(this);
		return clone;
	}

}
