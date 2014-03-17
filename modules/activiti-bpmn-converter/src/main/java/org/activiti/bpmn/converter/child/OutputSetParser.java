package org.activiti.bpmn.converter.child;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CatchEvent;
import org.activiti.bpmn.model.DataOutputSet;
import org.activiti.bpmn.model.IOSpecification;
import org.apache.commons.lang3.StringUtils;

public class OutputSetParser extends BaseChildElementParser {

	@Override
	public String getElementName() {
		return ELEMENT_DATA_OUTPUTSET;
	}

	@Override
	public void parseChildElement(XMLStreamReader xtr,
			BaseElement parentElement, BpmnModel model) throws Exception {
		if (parentElement instanceof IOSpecification == false && parentElement instanceof CatchEvent == false) {
			return;
		}
		
		DataOutputSet outputSet = new DataOutputSet();
    	boolean readyWithOutputSet = false;
    	while (readyWithOutputSet == false && xtr.hasNext()) {
    		xtr.next();
    		if (xtr.isStartElement() && ELEMENT_DATA_OUTPUT_REFS.equalsIgnoreCase(xtr.getLocalName())) {
    			String dataOutputRefs = xtr.getElementText();
    			if (StringUtils.isNotEmpty(dataOutputRefs)) {
    				outputSet.getDataOutputRefs().add(dataOutputRefs);
    			}
    			
    		} if (xtr.isEndElement() && ELEMENT_DATA_OUTPUTSET.equalsIgnoreCase(xtr.getLocalName())) {
    			readyWithOutputSet = true;
    			if (parentElement instanceof IOSpecification) {
    				((IOSpecification)parentElement).setDataOutputSet(outputSet);
    			} else if (parentElement instanceof CatchEvent) {
    				((CatchEvent)parentElement).setDataOutputSet(outputSet);
    			}
    		}
    	}

	}

}
