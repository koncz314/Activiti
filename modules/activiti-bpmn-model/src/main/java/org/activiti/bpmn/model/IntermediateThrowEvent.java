package org.activiti.bpmn.model;

public class IntermediateThrowEvent extends ThrowEvent {
	public IntermediateThrowEvent clone() {
		IntermediateThrowEvent clone = new IntermediateThrowEvent();
	    clone.setValues(this);
	    return clone;
	  }
	  
	  public void setValues(IntermediateThrowEvent otherEvent) {
	    super.setValues(otherEvent);
	  }
	
	
}
