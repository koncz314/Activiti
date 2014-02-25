package org.activiti.bpmn.converter.child;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataObject;
import org.activiti.bpmn.model.DataObjectContainer;

public class DataObjectParser extends BaseChildElementParser {


	@Override
	public String getElementName() {
		return ELEMENT_DATA_OBJECT;
	}

	@Override
	public void parseChildElement(XMLStreamReader xtr,
			BaseElement parentElement, BpmnModel model) throws Exception {
		if (!(parentElement instanceof DataObjectContainer)) {
			return;
		}
		DataObject data = new DataObject();
		BpmnXMLUtil.addXMLLocation(data, xtr);
		data.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
		data.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
		data.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
		data.setItemSubjectRef(xtr.getAttributeValue(null, ATTRIBUTE_DATA_SUBJECT_REF));
		data.setCollection(Boolean.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_ISCOLLECTION)));
		((DataObjectContainer)parentElement).addDataObject(data);
	}

	
	
}
