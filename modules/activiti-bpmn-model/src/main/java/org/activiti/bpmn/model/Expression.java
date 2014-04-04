package org.activiti.bpmn.model;

public class Expression extends BaseElement {
	protected String expressionValue;
	
	public String getExpressionValue() {
		return expressionValue;
	}

	public void setExpressionValue(String expressionValue) {
		this.expressionValue = expressionValue;
	}

	@Override
	public Expression clone() {
		Expression expression = new Expression();
		expression.setValues(this);
		return expression;
	}
	
	public void setValues(Expression otherExpression) {
		super.setValues(otherExpression);
		setExpressionValue(otherExpression.getExpressionValue());
	}

}
