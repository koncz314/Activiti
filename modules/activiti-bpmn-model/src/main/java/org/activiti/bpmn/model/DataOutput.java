package org.activiti.bpmn.model;

public class DataOutput extends BaseElement {

	protected String itemSubjectRef;
	protected boolean isCollection = false;
	protected String name;
	
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

	public boolean isCollection() {
		return isCollection;
	}

	public void setCollection(boolean isCollection) {
		this.isCollection = isCollection;
	}

	public DataOutput clone() {
		DataOutput clone = new DataOutput();
		clone.setValues(this);
		return clone;
	}

	public void setValues(DataOutput otherDataSpec) {
		super.setValues((BaseElement) otherDataSpec);
		setName(otherDataSpec.getName());
		setItemSubjectRef(otherDataSpec.getItemSubjectRef());
		setCollection(otherDataSpec.isCollection());
	}
}
