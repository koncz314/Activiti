package org.activiti.bpmn.converter.child;

import java.util.Arrays;
import java.util.List;

import hu.clickandlike.bpmn.model.interfaces.DataObjectContainer;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataObject;
import org.activiti.bpmn.model.ExtensionAttribute;

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
		
		List<ExtensionAttribute> defaultElementAttributes = Arrays.asList(
			      new ExtensionAttribute(ATTRIBUTE_ID),
			      new ExtensionAttribute(ATTRIBUTE_NAME),
			      new ExtensionAttribute(ATTRIBUTE_DATA_SUBJECT_REF),
			      new ExtensionAttribute(ATTRIBUTE_ISCOLLECTION)
			  );
		BpmnXMLUtil.addCustomAttributes(xtr, data, defaultElementAttributes);
		
		((DataObjectContainer)parentElement).addDataObject(data);
	}

	
	
}
