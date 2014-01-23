package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Interface;
import org.activiti.bpmn.model.Operation;
import org.apache.commons.lang3.StringUtils;

public class InterfaceExport implements BpmnXMLConstants {

	public static void writeInterfaces(BpmnModel model, XMLStreamWriter xtw) throws Exception{
		for (Interface inter : model.getInterfaces()) {
			xtw.writeStartElement(ELEMENT_INTERFACE);
			if (StringUtils.isNotEmpty(inter.getId())) {
				xtw.writeAttribute(ATTRIBUTE_ID, inter.getId());
			}
			if (StringUtils.isNotEmpty(inter.getImplementationRef())) {
				xtw.writeAttribute(ATTRIBUTE_IMPLEMENTATION_REF, inter.getImplementationRef());
			}
			if (StringUtils.isNotEmpty(inter.getName())) {
				xtw.writeAttribute(ATTRIBUTE_NAME, inter.getName());
			}
			for (Operation oper : inter.getOperations()) {
				xtw.writeStartElement(ELEMENT_OPERATION);
				if (StringUtils.isNotEmpty(oper.getId())) {
					xtw.writeAttribute(ATTRIBUTE_ID, oper.getId());
				}
				if (StringUtils.isNotEmpty(oper.getName())) {
					xtw.writeAttribute(ATTRIBUTE_NAME, oper.getName());
				}
				if (StringUtils.isNotEmpty(oper.getImplementationRef())) {
					xtw.writeAttribute(ATTRIBUTE_IMPLEMENTATION_REF, oper.getImplementationRef());
				}
				if (StringUtils.isNotEmpty(oper.getInMessageRef())) {
					xtw.writeStartElement(ELEMENT_IN_MESSAGE);
					xtw.writeCharacters(oper.getInMessageRef());
					xtw.writeEndElement();
				}
				if (StringUtils.isNotEmpty(oper.getOutMessageRef())) {
					xtw.writeStartElement(ELEMENT_OUT_MESSAGE);
					xtw.writeCharacters(oper.getOutMessageRef());
					xtw.writeEndElement();
				}
				
				xtw.writeEndElement();
			}
			xtw.writeEndElement();
		}
		
	}

}
