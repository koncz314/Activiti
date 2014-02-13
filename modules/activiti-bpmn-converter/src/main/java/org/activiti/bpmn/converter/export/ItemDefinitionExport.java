package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ItemDefinition;
import org.apache.commons.lang3.StringUtils;

public class ItemDefinitionExport implements BpmnXMLConstants {

	public static void writeItemDefinitions(BpmnModel model, XMLStreamWriter xtw) throws Exception{
		for (ItemDefinition itemDef : model.getItemDefinitions().values()) {
			xtw.writeStartElement(ELEMENT_ITEM_DEFINITION);
			if (StringUtils.isNotEmpty(itemDef.getId())) {
				xtw.writeAttribute(ATTRIBUTE_ID, itemDef.getId());
			}
			if (StringUtils.isNotEmpty(itemDef.getItemKind())) {
				xtw.writeAttribute(ATTRIBUTE_ITEM_KIND, itemDef.getItemKind());
			}
			if (StringUtils.isNotEmpty(itemDef.getStructureRef())) {
				xtw.writeAttribute(ATTRIBUTE_STRUCTURE_REF, itemDef.getStructureRef());
			}
			xtw.writeAttribute(ATTRIBUTE_ISCOLLECTION, String.valueOf(itemDef.isCollection()));
			
			xtw.writeEndElement();
		}
		
	}

}
