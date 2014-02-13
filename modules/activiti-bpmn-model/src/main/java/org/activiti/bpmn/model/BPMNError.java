package org.activiti.bpmn.model;

public class BPMNError extends BaseElement {

	protected String name;
	protected String errorCode;
	protected String structureRef;
	
	public BPMNError() {
	}

	public BPMNError(String name, String errorCode, String structureRef) {
		this.name = name;
		this.errorCode = errorCode;
		this.structureRef = structureRef;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getStructureRef() {
		return structureRef;
	}

	public void setStructureRef(String structureRef) {
		this.structureRef = structureRef;
	}

	@Override
	public BPMNError clone() {
		BPMNError clone = new BPMNError();
		clone.setValues(this);
		return clone;
	}
	
	public void setValues(BPMNError otherElement) {
	    super.setValues(otherElement);
	    setName(otherElement.getName());
	    setErrorCode(otherElement.getErrorCode());
	    setStructureRef(otherElement.getStructureRef());
	  }
	

}
