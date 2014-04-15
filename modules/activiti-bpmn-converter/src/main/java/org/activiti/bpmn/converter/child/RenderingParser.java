package org.activiti.bpmn.converter.child;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Rendering;
import org.activiti.bpmn.model.UserTask;

public class RenderingParser extends BaseChildElementParser {

	@Override
	public String getElementName() {
		return "rendering";
	}

	@Override
	public void parseChildElement(XMLStreamReader xtr,
			BaseElement parentElement, BpmnModel model) throws Exception {
		Rendering rendering = new Rendering();
	    BpmnXMLUtil.addXMLLocation(rendering, xtr);
	    BpmnXMLUtil.parseChildElements(getElementName(), rendering, xtr, model);
	    ((UserTask) parentElement).setRendering(rendering);


	}

}
