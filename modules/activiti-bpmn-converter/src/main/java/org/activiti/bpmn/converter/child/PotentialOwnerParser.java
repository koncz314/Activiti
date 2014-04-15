package org.activiti.bpmn.converter.child;

import hu.clickandlike.bpmn.model.interfaces.PerformerType;

import org.activiti.bpmn.model.ResourceRole;

public class PotentialOwnerParser extends ResourceRoleParser {

	@Override
	public String getElementName() {
		return ELEMENT_POTENTIAL_OWNER;
	}

	

	@Override
	public PerformerType getPerformerType() {
		return PerformerType.POTENTIAL_OWNER;
	}

}
