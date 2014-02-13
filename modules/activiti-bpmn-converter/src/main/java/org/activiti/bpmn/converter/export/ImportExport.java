package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Import;
import org.apache.commons.lang3.StringUtils;

public class ImportExport implements BpmnXMLConstants {

	public static void writeImports(BpmnModel model, XMLStreamWriter xtw) throws Exception{
		for (Import importItem : model.getImports()) {
			xtw.writeStartElement(ELEMENT_IMPORT);
			if (StringUtils.isNotEmpty(importItem.getImportType())) {
				xtw.writeAttribute(ATTRIBUTE_IMPORT_TYPE, importItem.getImportType());
			}
			if (StringUtils.isNotEmpty(importItem.getLocation())) {
				xtw.writeAttribute(ATTRIBUTE_LOCATION, importItem.getLocation());
			}
			if (StringUtils.isNotEmpty(importItem.getNamespace())) {
				xtw.writeAttribute(ATTRIBUTE_NAMESPACE, importItem.getNamespace());
			}
			xtw.writeEndElement();
		}
		
	}

}
