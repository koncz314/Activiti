package org.activiti.bpmn.converter.child;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CatchEvent;
import org.activiti.bpmn.model.DataOutput;
import org.activiti.bpmn.model.IOSpecification;


public class DataOutputParser extends BaseChildElementParser {

	@Override
	public String getElementName() {
		return ELEMENT_DATA_OUTPUT;
	}

	@Override
	public void parseChildElement(XMLStreamReader xtr,
			BaseElement parentElement, BpmnModel model) throws Exception {
		if (parentElement instanceof IOSpecification == false && parentElement instanceof CatchEvent == false) {
			return;
		}
		DataOutput dataSpec = new DataOutput();
        BpmnXMLUtil.addXMLLocation(dataSpec, xtr);
        dataSpec.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
        dataSpec.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
        dataSpec.setItemSubjectRef(xtr.getAttributeValue(null, ATTRIBUTE_DATA_SUBJECT_REF));
        if (parentElement instanceof IOSpecification) {
        	((IOSpecification) parentElement).getDataOutputs().add(dataSpec);
        } else {
        	((CatchEvent) parentElement).getDataOutputs().add(dataSpec);
        }
		
	}

}
