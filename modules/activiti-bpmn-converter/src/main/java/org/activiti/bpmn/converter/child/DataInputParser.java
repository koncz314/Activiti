package org.activiti.bpmn.converter.child;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataInput;
import org.activiti.bpmn.model.IOSpecification;
import org.activiti.bpmn.model.ThrowEvent;

public class DataInputParser extends BaseChildElementParser {

	@Override
	public String getElementName() {
		return ELEMENT_DATA_INPUT;
	}

	@Override
	public void parseChildElement(XMLStreamReader xtr,
			BaseElement parentElement, BpmnModel model) throws Exception {
		if (parentElement instanceof IOSpecification == false && parentElement instanceof ThrowEvent == false) {
			return;
		}
		DataInput dataSpec = new DataInput();
        BpmnXMLUtil.addXMLLocation(dataSpec, xtr);
        dataSpec.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
        dataSpec.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
        dataSpec.setItemSubjectRef(xtr.getAttributeValue(null, ATTRIBUTE_DATA_SUBJECT_REF));
        if (parentElement instanceof IOSpecification) {
        	((IOSpecification) parentElement).getDataInputs().add(dataSpec);
        } else {
        	((ThrowEvent) parentElement).getDataInputs().add(dataSpec);
        }
		
	}

}
