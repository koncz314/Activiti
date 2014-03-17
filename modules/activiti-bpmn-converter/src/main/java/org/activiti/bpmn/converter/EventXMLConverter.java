package org.activiti.bpmn.converter;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.Property;

public abstract class EventXMLConverter extends FlowNodeXMLConverter {

	@Override
	protected void writeAdditionalChildElements(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		super.writeAdditionalChildElements(element, xtw);
		if (element instanceof Event) {
			for (Property prop : ((Event)element).getPropertys()) {
				writeProperty(prop, xtw);
			}
		}
	}



}
