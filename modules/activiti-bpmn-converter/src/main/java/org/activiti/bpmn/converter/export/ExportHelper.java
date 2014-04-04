package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;

public class ExportHelper {
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
