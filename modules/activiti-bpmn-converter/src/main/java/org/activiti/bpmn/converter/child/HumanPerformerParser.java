package org.activiti.bpmn.converter.child;

import hu.clickandlike.bpmn.model.interfaces.PerformerType;


public class HumanPerformerParser extends ResourceRoleParser {

	@Override
	public String getElementName() {
		return ELEMENT_HUMAN_PERFORMER;
	}

	@Override
	public PerformerType getPerformerType() {
		return PerformerType.HUMAN_PERFORMER;
	}



}
