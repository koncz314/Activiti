package org.activiti.bpmn.converter;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.Group;

public class GroupXMLConverter extends BaseBpmnXMLConverter {

	public static Class<? extends BaseElement> getBpmnElementType() {
		return Group.class;
	}

	@Override
	protected String getXMLElementName() {
		return ELEMENT_GROUP;
	}

	public static String getXMLType() {
		return ELEMENT_GROUP;
	}

	@Override
	protected BaseElement convertXMLToElement(XMLStreamReader xtr)
			throws Exception {
		Group group = new Group();
		BpmnXMLUtil.addXMLLocation(group, xtr);
		group.setCategoryValueRef(xtr.getAttributeValue(null,
				ATTRIBUTE_CATEGORY_VALUE_REF));

		return group;
	}

	@Override
	protected void writeAdditionalAttributes(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		Group group = (Group) element;
		writeDefaultAttribute(ATTRIBUTE_CATEGORY_VALUE_REF,
				group.getCategoryValueRef(), xtw);

	}

	@Override
	protected void writeExtensionChildElements(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeAdditionalChildElements(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		// TODO Auto-generated method stub

	}

}
