package org.activiti.bpmn.converter.parser;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.MessageFlow;
import org.apache.commons.lang3.StringUtils;

public class MessageFlowParser implements BpmnXMLConstants {

	public void parse(XMLStreamReader xtr, BpmnModel model) throws Exception {
	    if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_ID))) {
	      String id = xtr.getAttributeValue(null, ATTRIBUTE_ID);
	      String name = xtr.getAttributeValue(null, ATTRIBUTE_NAME);
	      String sourceRef = xtr.getAttributeValue(null, ELEMENT_SOURCE_REF);
	      String targetRef = xtr.getAttributeValue(null, ELEMENT_TARGET_REF);
	      String messageRef = xtr.getAttributeValue(null, ATTRIBUTE_MESSAGE_REF);
	      MessageFlow flow = new MessageFlow();
	      flow.setId(id);
	      flow.setName(name);
	      flow.setSourceRef(sourceRef);
	      flow.setTargetRef(targetRef);
	      flow.setMessageRef(messageRef);
	      BpmnXMLUtil.addXMLLocation(flow, xtr);
	      BpmnXMLUtil.parseChildElements(ELEMENT_MESSAGE_FLOW, flow, xtr, model);
	      model.getMessageFlows().add(flow);
	    }
	  }

}



/*
package org.activiti.bpmn.converter;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.MessageFlow;

public class MessageFlowXMLConverter extends BaseBpmnXMLConverter {
	  
	  public static String getXMLType() {
	    return ELEMENT_MESSAGE_FLOW;
	  }
	  
	  public static Class<? extends BaseElement> getBpmnElementType() {
	    return MessageFlow.class;
	  }
	  
	  @Override
	  protected String getXMLElementName() {
	    return ELEMENT_MESSAGE_FLOW;
	  }
	  
	  @Override
	  protected BaseElement convertXMLToElement(XMLStreamReader xtr) throws Exception {
	    MessageFlow messageFlow = new MessageFlow();
	    BpmnXMLUtil.addXMLLocation(messageFlow, xtr);
	    messageFlow.setSourceRef(xtr.getAttributeValue(null, ATTRIBUTE_FLOW_SOURCE_REF));
	    messageFlow.setTargetRef(xtr.getAttributeValue(null, ATTRIBUTE_FLOW_TARGET_REF));
	    messageFlow.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
	    messageFlow.setMessageRef(xtr.getAttributeValue(null, ATTRIBUTE_MESSAGE_REF));
	    parseChildElements(getXMLElementName(), messageFlow, xtr);
	    
	    return messageFlow;
	  }

	  @Override
	  protected void writeAdditionalAttributes(BaseElement element, XMLStreamWriter xtw) throws Exception {
	    MessageFlow messageFlow = (MessageFlow) element;
	    writeDefaultAttribute(ATTRIBUTE_FLOW_SOURCE_REF, messageFlow.getSourceRef(), xtw);
	    writeDefaultAttribute(ATTRIBUTE_FLOW_TARGET_REF, messageFlow.getTargetRef(), xtw);
	    writeDefaultAttribute(ATTRIBUTE_MESSAGE_REF, messageFlow.getMessageRef(), xtw);
	    writeDefaultAttribute(ATTRIBUTE_NAME, messageFlow.getName(), xtw);
	  }
	  
	  @Override
	  protected void writeExtensionChildElements(BaseElement element, XMLStreamWriter xtw) throws Exception {
	  }

	  @Override
	  protected void writeAdditionalChildElements(BaseElement element, XMLStreamWriter xtw) throws Exception {
	    
	  }
	}
*/