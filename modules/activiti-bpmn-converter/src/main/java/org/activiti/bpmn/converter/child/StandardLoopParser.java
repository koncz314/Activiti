package org.activiti.bpmn.converter.child;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.StandardLoopCharacteristics;
import org.apache.commons.lang3.StringUtils;

public class StandardLoopParser extends BaseChildElementParser {

	@Override
	public String getElementName() {
		return ELEMENT_STANDARD_LOOP;
	}

	@Override
	public void parseChildElement(XMLStreamReader xtr,
			BaseElement parentElement, BpmnModel model) throws Exception {
		StandardLoopCharacteristics standard = new StandardLoopCharacteristics();
		BpmnXMLUtil.addXMLLocation(standard, xtr);
		String testBefore = xtr.getAttributeValue(null, ATTRIBUTE_TEST_BEFORE);
		String loopMaximum = xtr.getAttributeValue(null, ATTRIBUTE_LOOP_MAXIMUM);
		
		if (testBefore == null || !testBefore.equals("true") || !testBefore.equals("false")) {
			testBefore = "false";
		}
		standard.setTestBefore(Boolean.valueOf(testBefore));
		if (StringUtils.isNotEmpty(loopMaximum)) {
			standard.setLoopMaximum(loopMaximum);
		}
		
		boolean readyWithStandardLoop = false;
		while (readyWithStandardLoop == false && xtr.hasNext()) {
			xtr.next();
			if (xtr.isStartElement() && ELEMENT_LOOP_CONDITION.equals(xtr.getLocalName())) {
				standard.setLoopCondition(xtr.getElementText());
			} else if (xtr.isEndElement() && getElementName().equals(xtr.getLocalName())) {
				readyWithStandardLoop = true;
			}
		}
		((Activity) parentElement).setLoopCharacteristics(standard);

	}

}

/*

public String getElementName() {
  return ELEMENT_MULTIINSTANCE;
}

public void parseChildElement(XMLStreamReader xtr, BaseElement parentElement, BpmnModel model) throws Exception {
  if (parentElement instanceof Activity == false) return;
  
  MultiInstanceLoopCharacteristics multiInstanceDef = new MultiInstanceLoopCharacteristics();
  BpmnXMLUtil.addXMLLocation(multiInstanceDef, xtr);
  if (xtr.getAttributeValue(null, ATTRIBUTE_MULTIINSTANCE_SEQUENTIAL) != null) {
    multiInstanceDef.setSequential(Boolean.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_MULTIINSTANCE_SEQUENTIAL)));
  }
  multiInstanceDef.setInputDataItem(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_MULTIINSTANCE_COLLECTION));
  multiInstanceDef.setElementVariable(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_MULTIINSTANCE_VARIABLE));
  multiInstanceDef.setElementIndexVariable(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_MULTIINSTANCE_INDEX_VARIABLE));

  boolean readyWithMultiInstance = false;
  try {
    while (readyWithMultiInstance == false && xtr.hasNext()) {
      xtr.next();
      if (xtr.isStartElement() && ELEMENT_MULTIINSTANCE_CARDINALITY.equalsIgnoreCase(xtr.getLocalName())) {
        multiInstanceDef.setLoopCardinality(xtr.getElementText());

      } else if (xtr.isStartElement() && ELEMENT_MULTIINSTANCE_DATAINPUT.equalsIgnoreCase(xtr.getLocalName())) {
        multiInstanceDef.setInputDataItem(xtr.getElementText());

      } else if (xtr.isStartElement() && ELEMENT_MULTIINSTANCE_DATAITEM.equalsIgnoreCase(xtr.getLocalName())) {
        if (xtr.getAttributeValue(null, ATTRIBUTE_NAME) != null) {
          multiInstanceDef.setElementVariable(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
        }

      } else if (xtr.isStartElement() && ELEMENT_MULTIINSTANCE_CONDITION.equalsIgnoreCase(xtr.getLocalName())) {
        multiInstanceDef.setCompletionCondition(xtr.getElementText());

      } else if (xtr.isEndElement() && getElementName().equalsIgnoreCase(xtr.getLocalName())) {
        readyWithMultiInstance = true;
      }
    }
  } catch (Exception e) {
    LOGGER.warn("Error parsing multi instance definition", e);
  }
  ((Activity) parentElement).setLoopCharacteristics(multiInstanceDef);
}
}
*/