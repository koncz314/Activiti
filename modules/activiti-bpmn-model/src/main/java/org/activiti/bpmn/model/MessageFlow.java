package org.activiti.bpmn.model;

import hu.clickandlike.bpmn.model.interfaces.IMessageRef;

public class MessageFlow extends BaseElement implements IMessageRef {

	private String name;
	private String sourceRef;
	private String targetRef;
	private String messageRef;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceRef() {
		return sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}

	public String getMessageRef() {
		return messageRef;
	}

	public void setMessageRef(String messageRef) {
		this.messageRef = messageRef;
	}

	@Override
	public MessageFlow clone() {
		MessageFlow clone = new MessageFlow();
		clone.setValues(this);
		return clone;
	}
	
	public void setValues(MessageFlow otherElement) { 
		super.setValues(otherElement);
		setName(otherElement.getName());
		setSourceRef(otherElement.getSourceRef());
		setTargetRef(otherElement.getTargetRef());
		setMessageRef(otherElement.getMessageRef());
	}

}
