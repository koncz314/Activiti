package org.activiti.bpmn.model;

public class StandardLoopCharacteristics extends LoopCharacteristics {

	protected String loopCondition;
	protected boolean testBefore;
	protected String loopMaximum;
	
	public StandardLoopCharacteristics() {
		testBefore = false;
	}

	public String getLoopCondition() {
		return loopCondition;
	}

	public void setLoopCondition(String loopCondition) {
		this.loopCondition = loopCondition;
	}

	public boolean isTestBefore() {
		return testBefore;
	}

	public void setTestBefore(boolean testBefore) {
		this.testBefore = testBefore;
	}

	public String getLoopMaximum() {
		return loopMaximum;
	}

	public void setLoopMaximum(String loopMaximum) {
		this.loopMaximum = loopMaximum;
	}

	public void setValues(StandardLoopCharacteristics otherElement) {
		super.setValues(otherElement);
		setLoopCondition(otherElement.getLoopCondition());
		setLoopMaximum(otherElement.getLoopMaximum());
		setTestBefore(otherElement.isTestBefore());
	}
	
	@Override
	public StandardLoopCharacteristics clone() {
		StandardLoopCharacteristics clone = new StandardLoopCharacteristics();
		clone.setValues(this);
		return clone;
	}

}
