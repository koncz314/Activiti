/**
 * 
 */
package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.BPMNError;
import org.activiti.bpmn.model.BpmnModel;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Krisztian Koncz
 *
 */
public class ErrorExport implements BpmnXMLConstants {

	public static void writeErrors(BpmnModel model, XMLStreamWriter xtw) throws Exception{
		for (BPMNError error : model.getErrors()) {
			xtw.writeStartElement(ELEMENT_ERROR);
			if (StringUtils.isNotEmpty(error.getId())) {
				xtw.writeAttribute(ATTRIBUTE_ID, error.getId());
			}
			if (StringUtils.isNotEmpty(error.getName())) {
				xtw.writeAttribute(ATTRIBUTE_NAME, error.getName());
			}
			if (StringUtils.isNotEmpty(error.getStructureRef())) {
				xtw.writeAttribute(ATTRIBUTE_STRUCTURE_REF, error.getStructureRef());
			}
			if (StringUtils.isNotEmpty(error.getErrorCode())) {
				xtw.writeAttribute(ATTRIBUTE_ERROR_CODE, error.getErrorCode());
			}
			
			xtw.writeEndElement();
		}
		
	}

}
