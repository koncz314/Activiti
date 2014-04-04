package org.activiti.bpmn.model;

public class ResourceParameter extends BaseElement {
	protected String name;
	protected String type;
	protected boolean isRequired;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public boolean isRequired() {
		return isRequired;
	}


	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}


	@Override
	public ResourceParameter clone() {
		ResourceParameter parameter = new ResourceParameter();
		parameter.setValues(this);
		return parameter;
	}
	
	public void setValues(ResourceParameter otherParameter) {
		super.setValues(otherParameter);
		setName(otherParameter.getName());
		setType(otherParameter.getType());
		setRequired(otherParameter.isRequired());
	}

}
