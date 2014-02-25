package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.DataObject;
import org.apache.commons.lang3.StringUtils;

public class DataObjectExport implements BpmnXMLConstants {

	public static void writeDataObject(DataObject data, XMLStreamWriter xtw) throws XMLStreamException {
		  xtw.writeEmptyElement(ELEMENT_DATA_OBJECT);
		  xtw.writeAttribute(ATTRIBUTE_ID, data.getId());
		  if (StringUtils.isNotEmpty(data.getName())) {
			xtw.writeAttribute(ATTRIBUTE_NAME, data.getName());
		}
		  if (StringUtils.isNotEmpty(data.getItemSubjectRef())) {
			xtw.writeAttribute(ATTRIBUTE_DATA_SUBJECT_REF, data.getItemSubjectRef());
		}
		  xtw.writeAttribute(ATTRIBUTE_ISCOLLECTION, String.valueOf(data.isCollection()));
		  
	  }

}
