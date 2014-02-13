package org.activiti.bpmn.converter.parser;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BPMNError;
import org.activiti.bpmn.model.BpmnModel;
import org.apache.commons.lang3.StringUtils;

public class ErrorParser implements BpmnXMLConstants {

	public void parse(XMLStreamReader xtr, BpmnModel model) throws Exception {
	    String errorId = xtr.getAttributeValue(null, ATTRIBUTE_ID);
	    String errorName = xtr.getAttributeValue(null, ATTRIBUTE_NAME);
	    String errorStructureRef = xtr.getAttributeValue(null, ATTRIBUTE_STRUCTURE_REF);
	    String errorCode = xtr.getAttributeValue(null, ATTRIBUTE_ERROR_CODE);
	    if (StringUtils.isEmpty(errorId)) {
	      model.addProblem("Error must have an id", xtr);
	    } else if (StringUtils.isEmpty(errorName)) {
	      model.addProblem("Error with id '" + errorId + "' has no name", xtr);
	    } else {
	      
	      for (BPMNError error : model.getErrors()) {
	        if (error.getName().equals(errorName)) {
	          model.addProblem("duplicate error name", xtr);
	        }
	      }
	      
	      BPMNError error = new BPMNError(errorName,errorCode,errorStructureRef);
	      error.setId(errorId);
	      BpmnXMLUtil.addXMLLocation(error, xtr);
	      BpmnXMLUtil.parseChildElements(ELEMENT_ERROR, error, xtr, model);
	      model.addError(error);
	    }
	  }
	}
