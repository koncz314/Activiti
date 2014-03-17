package org.activiti.bpmn.converter;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.converter.child.DataInputAssociationParser;
import org.activiti.bpmn.converter.child.DataOutputAssociationParser;
import org.activiti.bpmn.converter.export.DataAssociationExport;
import org.activiti.bpmn.converter.export.LoopCharacteristicsExport;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.DataAssociation;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.apache.commons.lang3.StringUtils;

public abstract class ActivityXMLConverter extends FlowNodeXMLConverter {

	public ActivityXMLConverter() {
		DataInputAssociationParser inParser = new DataInputAssociationParser();
		DataOutputAssociationParser outParser = new DataOutputAssociationParser();
		childElementParsers.put(inParser.getElementName(), inParser);
		childElementParsers.put(outParser.getElementName(), outParser);
	}

	@Override
	protected void writeAdditionalChildElements(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		super.writeAdditionalChildElements(element, xtw);
		if (element instanceof Activity) {
			Activity activity = (Activity) element;
			if (activity.getIoSpecification() != null) {
				writeIOSpecification(activity.getIoSpecification(), xtw);
			}
			
			if (activity.getDataInputAssociations() != null && activity.getDataInputAssociations().size() > 0) {
				for (DataAssociation ass : activity.getDataInputAssociations()) {
					DataAssociationExport.writeDataAssociations(true, ass, xtw);
				}
			}
			
			if (activity.getDataOutputAssociations() != null && activity.getDataOutputAssociations().size() > 0) {
				for (DataAssociation ass : activity.getDataOutputAssociations()) {
					DataAssociationExport.writeDataAssociations(false, ass, xtw);
				}
			}
			
			LoopCharacteristicsExport.writeLoopCharacteristics(activity, xtw);

		}

	}

	@Override
	protected void writeAdditionalAttributes(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		super.writeAdditionalAttributes(element, xtw);
		if (element instanceof Activity) {
			Activity activity = (Activity) element;

			if (activity.isAsynchronous()) {
				writeQualifiedAttribute(ATTRIBUTE_ACTIVITY_ASYNCHRONOUS,
						ATTRIBUTE_VALUE_TRUE, xtw);
			}
			if (activity.isNotExclusive()) {
				writeQualifiedAttribute(ATTRIBUTE_ACTIVITY_EXCLUSIVE,
						ATTRIBUTE_VALUE_FALSE, xtw);
			}
			if (StringUtils.isNotEmpty(activity.getDefaultFlow())) {
				FlowElement defaultFlowElement = model.getFlowElement(activity
						.getDefaultFlow());
				if (defaultFlowElement != null
						&& defaultFlowElement instanceof SequenceFlow) {
					writeDefaultAttribute(ATTRIBUTE_DEFAULT,
							activity.getDefaultFlow(), xtw);
				}
			}
		}
	}

}
