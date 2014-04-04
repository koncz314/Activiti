package org.activiti.bpmn.model;

public class ResourceAssignmentExpression extends BaseElement {
	protected Expression expression;
	
	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public ResourceAssignmentExpression clone() {
		ResourceAssignmentExpression expression = new ResourceAssignmentExpression();
		expression.setValues(this);
		return expression;
	}

	public void setValues(ResourceAssignmentExpression otherExpression) {
		super.setValues(otherExpression);
		setExpression(otherExpression.getExpression().clone());
	}
}
