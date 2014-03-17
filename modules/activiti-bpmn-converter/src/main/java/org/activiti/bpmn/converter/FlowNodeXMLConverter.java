package org.activiti.bpmn.converter;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;

public abstract class FlowNodeXMLConverter extends FlowElementXMLConverter {

	@Override
	protected void writeAdditionalChildElements(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		// TODO Auto-generated method stub
		super.writeAdditionalChildElements(element, xtw);
		if (element instanceof FlowNode) {
			FlowNode node = (FlowNode) element;
			for (SequenceFlow flow : node.getIncomingFlows()) {
				writeElementWithText("incoming", flow.getId(), xtw);
			}
			for (SequenceFlow flow : node.getOutgoingFlows()) {
				writeElementWithText("outgoing", flow.getId(), xtw);
			}
		}
	}

	

}
