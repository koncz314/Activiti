package org.activiti.bpmn.model;

public class Group extends Artifact {

	private String categoryValueRef;

	public String getCategoryValueRef() {
		return categoryValueRef;
	}

	public void setCategoryValueRef(String categoryValueRef) {
		this.categoryValueRef = categoryValueRef;
	}

	@Override
	public Artifact clone() {
		Group clone = new Group();
		clone.setValues(this);
		return clone;
	}

	public void setValues(Group otherElement) {
		super.setValues(otherElement);
		setCategoryValueRef(otherElement.getCategoryValueRef());
	}

}
