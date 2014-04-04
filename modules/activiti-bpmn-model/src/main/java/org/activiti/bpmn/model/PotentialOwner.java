package org.activiti.bpmn.model;

public class PotentialOwner extends ResourceRole {

	@Override
	public PotentialOwner clone() {
		PotentialOwner newPerformer = new PotentialOwner();
		newPerformer.setValues(this);
		return newPerformer;
	}

}
