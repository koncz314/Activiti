package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.Assignment;
import org.activiti.bpmn.model.DataAssociation;

public class DataAssociationExport implements BpmnXMLConstants {

	public static void writeDataAssociations(boolean isInput, DataAssociation dataAssociation, XMLStreamWriter xtw) throws Exception {
		String elementName = isInput ? ELEMENT_INPUT_ASSOCIATION : ELEMENT_OUTPUT_ASSOCIATION;
		if (dataAssociation != null) {
			xtw.writeStartElement(elementName);
			writeElementWithText(ELEMENT_SOURCE_REF, dataAssociation.getSourceRef(), xtw);
			writeElementWithText(ELEMENT_TARGET_REF, dataAssociation.getTargetRef(), xtw);
			writeElementWithCData(ELEMENT_TRANSFORMATION, dataAssociation.getTransformation(), xtw);
			for (Assignment ass : dataAssociation.getAssignments()) {
				xtw.writeStartElement(ELEMENT_ASSIGNMENT);
				writeDefaultAttribute(ATTRIBUTE_ID, ass.getId(), xtw);
				writeElementWithCData(ELEMENT_FROM, ass.getFrom(), xtw);
				writeElementWithCData(ELEMENT_TO, ass.getTo(), xtw);
				xtw.writeEndElement();
			}
			xtw.writeEndElement();
		}
	}

	protected static void writeDefaultAttribute(String attributeName, String value, XMLStreamWriter xtw) throws Exception {
	    BpmnXMLUtil.writeDefaultAttribute(attributeName, value, xtw);
	  }
	  
	  protected static void writeQualifiedAttribute(String attributeName, String value, XMLStreamWriter xtw) throws Exception {
	    BpmnXMLUtil.writeQualifiedAttribute(attributeName, value, xtw);
	  }
	  
	  protected static void writeElementWithText(String elementName, String value, XMLStreamWriter xtw) throws Exception {
		  BpmnXMLUtil.writeElementWithText(elementName, value, xtw);
	  }
	
	  protected static void writeElementWithCData(String elementName, String value, XMLStreamWriter xtw) throws Exception {
		  BpmnXMLUtil.writeElementWithCData(elementName, value, xtw);
	  }
}
