package org.activiti.bpmn.model;

public class FormalExpression extends Expression {
	protected String language;
	protected String evaluatesToTypeRef;
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getEvaluatesToTypeRef() {
		return evaluatesToTypeRef;
	}
	public void setEvaluatesToTypeRef(String evaluatesToTypeRef) {
		this.evaluatesToTypeRef = evaluatesToTypeRef;
	}
	
	@Override
	public FormalExpression clone() {
		FormalExpression expression = new FormalExpression();
		expression.setValues(this);
		return expression;
	}
	
	public void setValues(FormalExpression otherExpression) {
		super.setValues(otherExpression);
		setLanguage(otherExpression.getLanguage());
		setEvaluatesToTypeRef(otherExpression.getEvaluatesToTypeRef());
	}
}
