package org.activiti.bpmn.converter.child;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.apache.commons.lang3.StringUtils;

public class CategoryValueRefParser extends BaseChildElementParser {

	@Override
	public String getElementName() {
		return "categoryValueRef";
	}

	@Override
	public void parseChildElement(XMLStreamReader xtr,
			BaseElement parentElement, BpmnModel model) throws Exception {
		String categoryValueRef = xtr.getElementText();
	    if(StringUtils.isNotEmpty(categoryValueRef)) {
	    	if (parentElement instanceof FlowElement) {
	    		((FlowElement) parentElement).getCategoryValueRefs().add(categoryValueRef);
	    	} 
	    }
	  }
	

}
