package org.activiti.bpmn.model;

import java.util.ArrayList;
import java.util.List;

public class Category extends BaseElement {

	private String name;
	private List<CategoryValue> categoryValues = new ArrayList<CategoryValue>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CategoryValue> getCategoryValues() {
		return categoryValues;
	}

	public void setCategoryValues(List<CategoryValue> categoryValues) {
		this.categoryValues = categoryValues;
	}

	@Override
	public Category clone() {
		Category clone = new Category();
		clone.setValues(this);
		return clone;
	}

	public void setValues(Category otherElement) {
		super.setValues(otherElement);
		setName(otherElement.getName());
		categoryValues = new ArrayList<CategoryValue>();
		for (CategoryValue value : otherElement.getCategoryValues()) {
			categoryValues.add(value.clone());
		}
	}
	
}
