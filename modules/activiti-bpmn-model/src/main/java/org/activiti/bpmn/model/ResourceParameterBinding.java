package org.activiti.bpmn.model;

public class ResourceParameterBinding extends BaseElement {
	protected String parameterRef;
	protected Expression expression;
	
	public String getParameterRef() {
		return parameterRef;
	}

	public void setParameterRef(String parameterRef) {
		this.parameterRef = parameterRef;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public ResourceParameterBinding clone() {
		ResourceParameterBinding newBindign = new ResourceParameterBinding();
		newBindign.setValues(this);
		return newBindign;
	}

	public void setValues(ResourceParameterBinding otherBinding) {
		super.setValues(otherBinding);
		setParameterRef(otherBinding.getParameterRef());
		setExpression(otherBinding.getExpression().clone());
	}
}
