package org.activiti.bpmn.converter;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;

public abstract class FlowElementXMLConverter extends BaseBpmnXMLConverter {

	@Override
	protected void writeAdditionalChildElements(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		if (element instanceof FlowElement) {
			FlowElement flow = (FlowElement) element;
			for (String categoryValueRef : flow.getCategoryValueRefs()) {
				writeElementWithText("categoryValueRef", categoryValueRef, xtw);
			}
		}

	}


	@Override
	protected void writeAdditionalAttributes(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		if (element instanceof FlowElement) {
			FlowElement flow = (FlowElement) element;
			writeDefaultAttribute(ATTRIBUTE_NAME,flow.getName(), xtw);
		}
		
	}

}
