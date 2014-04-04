package org.activiti.bpmn.converter.child;

import org.activiti.bpmn.model.PotentialOwner;
import org.activiti.bpmn.model.ResourceRole;

public class PotentialOwnerParser extends ResourceRoleParser {

	@Override
	public String getElementName() {
		return ELEMENT_POTENTIAL_OWNER;
	}

	@Override
	protected ResourceRole getNewInstance() {
		return new PotentialOwner();
	}

}
