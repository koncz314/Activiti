package org.activiti.bpmn.converter;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.converter.child.DataOutputAssociationParser;
import org.activiti.bpmn.converter.child.DataOutputParser;
import org.activiti.bpmn.converter.child.OutputSetParser;
import org.activiti.bpmn.converter.export.DataAssociationExport;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CatchEvent;
import org.activiti.bpmn.model.DataAssociation;
import org.activiti.bpmn.model.DataOutput;

public abstract class CatchEventXMLConverter extends EventXMLConverter {

	public CatchEventXMLConverter() {
		super();
		DataOutputParser outParser = new DataOutputParser();
		DataOutputAssociationParser outPutAssociationParser = new DataOutputAssociationParser();
		OutputSetParser outputSetParser = new OutputSetParser();
		
		childElementParsers.put(outParser.getElementName(), outParser);
		childElementParsers.put(outPutAssociationParser.getElementName(), outPutAssociationParser);
		childElementParsers.put(outputSetParser.getElementName(), outputSetParser);
	}

	@Override
	protected void writeAdditionalChildElements(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		
		super.writeAdditionalChildElements(element, xtw);
		if (element instanceof CatchEvent) {
			CatchEvent event = (CatchEvent)element;
			for (DataOutput data : event.getDataOutputs()) {
				writeDataOutput(data, xtw);
			}
			for (DataAssociation data : event.getDataOutputAssociations()) {
				DataAssociationExport.writeDataAssociations(false, data, xtw);	
			}
			if (event.getDataOutputSet() != null) {
				writeDataOutputSet(event.getDataOutputSet(), xtw);
			}
			writeEventDefinitions(event, event.getEventDefinitions(), xtw);
			writeEventDefinitionRefs(event, xtw);
		}
	}

	

}
