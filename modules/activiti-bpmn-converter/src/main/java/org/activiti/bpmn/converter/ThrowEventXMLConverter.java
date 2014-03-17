package org.activiti.bpmn.converter;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.converter.child.DataInputAssociationParser;
import org.activiti.bpmn.converter.child.DataInputParser;
import org.activiti.bpmn.converter.child.InputSetParser;
import org.activiti.bpmn.converter.export.DataAssociationExport;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.DataAssociation;
import org.activiti.bpmn.model.DataInput;
import org.activiti.bpmn.model.ThrowEvent;

public abstract class ThrowEventXMLConverter extends EventXMLConverter {

	public ThrowEventXMLConverter() {
		super();
		DataInputParser inputParser = new DataInputParser();
		DataInputAssociationParser inututAssociationParser = new DataInputAssociationParser();
		InputSetParser inputSetParser = new InputSetParser();
		
		childElementParsers.put(inputParser.getElementName(), inputParser);
		childElementParsers.put(inututAssociationParser.getElementName(), inututAssociationParser);
		childElementParsers.put(inputSetParser.getElementName(), inputSetParser);
	}
	
	@Override
	protected void writeAdditionalChildElements(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		
		super.writeAdditionalChildElements(element, xtw);
		if (element instanceof ThrowEvent) {
			ThrowEvent event = (ThrowEvent)element;
			for (DataInput data : event.getDataInputs()) {
				writeDataInput(data, xtw);
			}
			for (DataAssociation data : event.getDataInputAssociations()) {
				DataAssociationExport.writeDataAssociations(true, data, xtw);	
			}
			if (event.getDataInputSet() != null) {
				writeDataInputSet(event.getDataInputSet(), xtw);
			}
			writeEventDefinitions(event, event.getEventDefinitions(), xtw);
			writeEventDefinitionRefs(event, xtw);
		}
	}
	
	
}
