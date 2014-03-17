package org.activiti.bpmn.converter.child;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataInputSet;
import org.activiti.bpmn.model.IOSpecification;
import org.activiti.bpmn.model.ThrowEvent;
import org.apache.commons.lang3.StringUtils;

public class InputSetParser extends BaseChildElementParser {


	@Override
	public String getElementName() {
		return ELEMENT_DATA_INPUTSET;
	}

	@Override
	public void parseChildElement(XMLStreamReader xtr,
			BaseElement parentElement, BpmnModel model) throws Exception {
		if (parentElement instanceof IOSpecification == false && parentElement instanceof ThrowEvent) {
			return;
		}
		DataInputSet inputSet = new DataInputSet();
    	boolean readyWithInputSet = false;
    	while (readyWithInputSet == false && xtr.hasNext()) {
    		xtr.next();
    		if (xtr.isStartElement() && ELEMENT_DATA_INPUT_REFS.equalsIgnoreCase(xtr.getLocalName())) {
    			String dataInputRefs = xtr.getElementText();
    			if (StringUtils.isNotEmpty(dataInputRefs)) {
    				inputSet.getDataInputRefs().add(dataInputRefs);
    			}
    			
    		} if (xtr.isEndElement() && ELEMENT_DATA_INPUTSET.equalsIgnoreCase(xtr.getLocalName())) {
    			readyWithInputSet = true;
    			if (parentElement instanceof IOSpecification) {
    				((IOSpecification)parentElement).setDataInputSet(inputSet);
    			} else if (parentElement instanceof ThrowEvent) {
    				((ThrowEvent)parentElement).setDataInputSet(inputSet);
    			}
    		}
    	}

	}

}
