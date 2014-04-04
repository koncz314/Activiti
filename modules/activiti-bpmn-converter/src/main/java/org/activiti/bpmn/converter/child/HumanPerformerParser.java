package org.activiti.bpmn.converter.child;

import org.activiti.bpmn.model.HumanPerformer;
import org.activiti.bpmn.model.ResourceRole;

public class HumanPerformerParser extends ResourceRoleParser {

	@Override
	public String getElementName() {
		return ELEMENT_HUMAN_PERFORMER;
	}

	@Override
	protected ResourceRole getNewInstance() {
		return new HumanPerformer();
	}

}
