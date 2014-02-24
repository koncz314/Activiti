package org.activiti.bpmn.converter.parser;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Category;
import org.activiti.bpmn.model.CategoryValue;
import org.apache.commons.lang3.StringUtils;

public class CategoryParser implements BpmnXMLConstants {
	
	public void parse(XMLStreamReader xtr, BpmnModel model) throws Exception {
		if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_ID))) {
			String id = xtr.getAttributeValue(null, ATTRIBUTE_ID);
			String name = xtr.getAttributeValue(null, ATTRIBUTE_NAME);
			Category category = new Category();
			category.setId(id);
			category.setName(name);
			
			boolean readyWithCategory = false;
			CategoryValue categoryValue = null;
			System.out.println("KEZD");
			while (readyWithCategory == false && xtr.hasNext()) {
				xtr.next();
				if (xtr.isStartElement() && ELEMENT_CATEGORY_VALUE.equals(xtr.getLocalName())) {
					System.out.println("CATEGORYVALUEKEZD");
					categoryValue = new CategoryValue();
					BpmnXMLUtil.addXMLLocation(categoryValue, xtr);
					categoryValue.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
					categoryValue.setValue(xtr.getAttributeValue(null, ATTRIBUTE_VALUE));
				} else if(xtr.isEndElement() && ELEMENT_CATEGORY_VALUE.equals(xtr.getLocalName())) {
					System.out.println("CATEGORYVALUEVEG");
					if (categoryValue != null) {
						category.getCategoryValues().add(categoryValue);
					}
				} else if (xtr.isEndElement() && ELEMENT_CATEGORY.equals(xtr.getLocalName())) {
					readyWithCategory = true;
					System.out.println("CATEGORYVEG");
				}
			}
			System.out.println("DONE");
			model.addCategory(category);
		}
	}

}
/*
boolean readyWithInterface = false;
Operation operation = null;
try {
  while (readyWithInterface == false && xtr.hasNext()) {
    xtr.next();
    if (xtr.isStartElement() && ELEMENT_OPERATION.equals(xtr.getLocalName())) {
      operation = new Operation();
      BpmnXMLUtil.addXMLLocation(operation, xtr);
      
      //operation.setId(model.getTargetNamespace() + ":" + xtr.getAttributeValue(null, ATTRIBUTE_ID));
      operation.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
      
      operation.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
      
      //operation.setImplementationRef(parseMessageRef(xtr.getAttributeValue(null, ATTRIBUTE_IMPLEMENTATION_REF), model));
      operation.setImplementationRef(xtr.getAttributeValue(null, ATTRIBUTE_IMPLEMENTATION_REF));
      
    } else if (xtr.isStartElement() && ELEMENT_IN_MESSAGE.equals(xtr.getLocalName())) {
      String inMessageRef = xtr.getElementText();
      if (operation != null && StringUtils.isNotEmpty(inMessageRef)) {
        //operation.setInMessageRef(parseMessageRef(inMessageRef.trim(), model));
    	  operation.setInMessageRef(inMessageRef);
      }
      
    } else if (xtr.isStartElement() && ELEMENT_OUT_MESSAGE.equals(xtr.getLocalName())) {
      String outMessageRef = xtr.getElementText();
      if (operation != null && StringUtils.isNotEmpty(outMessageRef)) {
       // operation.setOutMessageRef(parseMessageRef(outMessageRef.trim(), model));
    	  operation.setOutMessageRef(outMessageRef);
      }
      
    } else if (xtr.isStartElement() && ELEMENT_ERROR_REF.equals(xtr.getLocalName())) {
    	String errorRef = xtr.getElementText();
    	if (operation != null && StringUtils.isNotEmpty(errorRef)) {
            // operation.setOutMessageRef(parseMessageRef(outMessageRef.trim(), model));
         	  operation.getErrorMessageRef().add(errorRef);
           }
    	
    } else if (xtr.isEndElement() && ELEMENT_OPERATION.equalsIgnoreCase(xtr.getLocalName())) {
      if (operation != null && StringUtils.isNotEmpty(operation.getImplementationRef())) {
        interfaceObject.getOperations().add(operation);
      }
      
    } else if (xtr.isEndElement() && ELEMENT_INTERFACE.equals(xtr.getLocalName())) {
      readyWithInterface = true;
    }
  }
} catch (Exception e) {
  LOGGER.log(Level.WARNING, "Error parsing interface child elements", e);
}

model.getInterfaces().add(interfaceObject);
}


*/