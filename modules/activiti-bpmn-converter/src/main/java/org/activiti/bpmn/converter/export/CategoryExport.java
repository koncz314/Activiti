package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Category;
import org.activiti.bpmn.model.CategoryValue;
import org.apache.commons.lang3.StringUtils;

public class CategoryExport implements BpmnXMLConstants {

	public static void writeCategories(BpmnModel model, XMLStreamWriter xtw) throws Exception{
		for (Category category : model.getCategories()) {
			xtw.writeStartElement(ELEMENT_CATEGORY);
			if (StringUtils.isNotEmpty(category.getId())) {
				xtw.writeAttribute(ATTRIBUTE_ID, category.getId());
			}
			if (StringUtils.isNotEmpty(category.getName())) {
				xtw.writeAttribute(ATTRIBUTE_NAME, category.getName());
			}
			
			for (CategoryValue categoryValue : category.getCategoryValues()) {
				xtw.writeStartElement(ELEMENT_CATEGORY_VALUE);
				
				if (StringUtils.isNotEmpty(categoryValue.getId())) {
					xtw.writeAttribute(ATTRIBUTE_ID, categoryValue.getId());
				}
				
				if (StringUtils.isNotEmpty(categoryValue.getValue())) {
					xtw.writeAttribute(ATTRIBUTE_VALUE, categoryValue.getValue());
				}
				
				xtw.writeEndElement();
			}
			xtw.writeEndElement();
		}
	}
}