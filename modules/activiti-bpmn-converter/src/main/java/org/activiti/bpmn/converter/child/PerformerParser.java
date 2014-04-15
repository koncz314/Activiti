package org.activiti.bpmn.converter.child;

import hu.clickandlike.bpmn.model.interfaces.PerformerType;

public class PerformerParser extends ResourceRoleParser {

	@Override
	public String getElementName() {
		return ELEMENT_PERFORMER;
	}

	@Override
	public PerformerType getPerformerType() {
		return PerformerType.PERFORMER;
	}

	

	

}
