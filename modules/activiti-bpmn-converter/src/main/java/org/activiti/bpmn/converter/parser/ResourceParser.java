package org.activiti.bpmn.converter.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.ResourceParameter;

public class ResourceParser implements BpmnXMLConstants {
	protected static final Logger LOGGER = Logger.getLogger(ResourceParser.class.getName());
	  
	  public void parse(XMLStreamReader xtr, BpmnModel model) throws Exception {
	    
	    Resource resource = new Resource();
	    BpmnXMLUtil.addXMLLocation(resource, xtr);
	    
	    
	    resource.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
	    resource.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
	 
	    boolean readyWithResource = false;
	    ResourceParameter parameter = null;
	    try {
	      while (readyWithResource == false && xtr.hasNext()) {
	        xtr.next();
	        if (xtr.isStartElement() && ELEMENT_RESOURCE_PARAMETER.equals(xtr.getLocalName())) {
	        	parameter = new ResourceParameter();
	        	BpmnXMLUtil.addXMLLocation(parameter, xtr);
	        	parameter.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
	        	parameter.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
	        	parameter.setType(xtr.getAttributeValue(null, ATTRIBUTE_TYPE));
	        	parameter.setRequired(Boolean.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_IS_REQUIRED)));
	          
	        } else if (xtr.isEndElement() && ELEMENT_RESOURCE_PARAMETER.equalsIgnoreCase(xtr.getLocalName())) {
	          if (parameter != null) {
	            resource.getResourceParameters().add(parameter);
	          }
	          
	        } else if (xtr.isEndElement() && ELEMENT_RESOURCE.equals(xtr.getLocalName())) {
	          readyWithResource = true;
	        }
	      }
	    } catch (Exception e) {
	      LOGGER.log(Level.WARNING, "Error parsing interface child elements", e);
	    }
	    
	    model.addResource(resource);
	  }
	  
}
