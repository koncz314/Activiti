package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.MessageFlow;

public class MessageFlowExport implements BpmnXMLConstants {

	public static void writeMessageFlows(BpmnModel model, XMLStreamWriter xtw) throws Exception {
		for (MessageFlow flow : model.getMessageFlows()) {
			xtw.writeStartElement(ELEMENT_MESSAGE_FLOW);
			BpmnXMLUtil.writeDefaultAttribute(ATTRIBUTE_ID, flow.getId(), xtw);
			BpmnXMLUtil.writeDefaultAttribute(ATTRIBUTE_NAME, flow.getName(), xtw);
			BpmnXMLUtil.writeDefaultAttribute(ATTRIBUTE_MESSAGE_REF, flow.getMessageRef(), xtw);
			BpmnXMLUtil.writeDefaultAttribute(ELEMENT_SOURCE_REF, flow.getSourceRef(), xtw);
			BpmnXMLUtil.writeDefaultAttribute(ELEMENT_TARGET_REF, flow.getTargetRef(), xtw);
			xtw.writeEndElement();
		
		}
	}

}
