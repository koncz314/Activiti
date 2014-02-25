package org.activiti.bpmn.converter;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.converter.export.LoopCharacteristicsExport;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.BaseElement;

public abstract class ActivityXMLConverter extends BaseBpmnXMLConverter {

	@Override
	protected void writeAdditionalChildElements(BaseElement element,
			XMLStreamWriter xtw) throws Exception {
		if (element instanceof Activity) {
			Activity activity = (Activity) element;
			if (activity.getIoSpecification() != null) {
				writeIOSpecification(activity.getIoSpecification(), xtw);
			}
			LoopCharacteristicsExport.writeLoopCharacteristics(activity, xtw);
		}

	}

}
