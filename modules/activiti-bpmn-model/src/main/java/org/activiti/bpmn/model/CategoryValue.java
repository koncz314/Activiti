package org.activiti.bpmn.model;

public class CategoryValue extends BaseElement {

	private String value;

	public CategoryValue() {
		
	}
	
	public CategoryValue(String id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public CategoryValue clone() {
		CategoryValue clone = new CategoryValue();
		clone.setValues(this);
		return clone;
	}

	public void setValues(CategoryValue otherElement) {
		super.setValues(otherElement);
		setValue(otherElement.getValue());
	}
	
}
