package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.ResourceParameter;
import org.apache.commons.lang3.StringUtils;

public class ResourceExport implements BpmnXMLConstants {
	public static void writeResources(BpmnModel model, XMLStreamWriter xtw) throws Exception{
		for (Resource resource : model.getResources()) {
			boolean empty = false;
			if (resource.getResourceParameters() != null && resource.getResourceParameters().size() > 0) {
				xtw.writeStartElement(ELEMENT_RESOURCE);
				
			} else {
				xtw.writeEmptyElement(ELEMENT_RESOURCE);
				empty = true;
			}
			
			if (StringUtils.isNotEmpty(resource.getId())) {
				xtw.writeAttribute(ATTRIBUTE_ID, resource.getId());
			}
			
			if (StringUtils.isNotEmpty(resource.getName())) {
				xtw.writeAttribute(ATTRIBUTE_NAME, resource.getName());
			}
			
			if (!empty) {
				for (ResourceParameter parameter : resource.getResourceParameters()) {
					xtw.writeEmptyElement(ELEMENT_RESOURCE_PARAMETER);
					if (StringUtils.isNotEmpty(parameter.getId())) {
						xtw.writeAttribute(ATTRIBUTE_ID, parameter.getId());
					}
					if (StringUtils.isNotEmpty(parameter.getName())) {
						xtw.writeAttribute(ATTRIBUTE_NAME, parameter.getName());
					}
					if (StringUtils.isNotEmpty(parameter.getType())) {
						xtw.writeAttribute(ATTRIBUTE_TYPE, parameter.getType());
					}
	
					xtw.writeAttribute(ATTRIBUTE_IS_REQUIRED, String.valueOf(parameter.isRequired()));	
					
				}
				if (!empty) {
					xtw.writeEndElement();
				}
			}
		}
	}
}
