package org.activiti.bpmn.model;

public class HumanPerformer extends ResourceRole {

	@Override
	public HumanPerformer clone() {
		HumanPerformer newPerformer = new HumanPerformer();
		newPerformer.setValues(this);
		return newPerformer;
	}

}
