package org.activiti.bpmn.model;

public class Performer extends ResourceRole {

	@Override
	public Performer clone() {
		Performer newPerformer = new Performer();
		newPerformer.setValues(this);
		return newPerformer;
	}

}
