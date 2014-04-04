package org.activiti.bpmn.converter.child;

import org.activiti.bpmn.model.Performer;
import org.activiti.bpmn.model.ResourceRole;

public class PerformerParser extends ResourceRoleParser {

	@Override
	public String getElementName() {
		return ELEMENT_PERFORMER;
	}

	@Override
	protected ResourceRole getNewInstance() {
		return new Performer();
	}

}
