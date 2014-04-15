/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.bpmn.converter.child;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataInput;
import org.activiti.bpmn.model.DataOutput;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Tijs Rademakers
 */
public class MultiInstanceParser extends BaseChildElementParser {

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
    /*multiInstanceDef.setInputDataItem(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_MULTIINSTANCE_COLLECTION));
    multiInstanceDef.setElementVariable(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_MULTIINSTANCE_VARIABLE));
    multiInstanceDef.setElementIndexVariable(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_MULTIINSTANCE_INDEX_VARIABLE));*/

    boolean readyWithMultiInstance = false;
    try {
      while (readyWithMultiInstance == false && xtr.hasNext()) {
        xtr.next();
        if (xtr.isStartElement() && ELEMENT_MULTIINSTANCE_CARDINALITY.equalsIgnoreCase(xtr.getLocalName())) {
          multiInstanceDef.setLoopCardinality(xtr.getElementText());

        } else if (xtr.isStartElement() && ELEMENT_LOOP_DATA_INPUT_REF.equalsIgnoreCase(xtr.getLocalName())) {
        	String text = xtr.getElementText();
        	if (StringUtils.isNotEmpty(text)) {
				multiInstanceDef.setLoopDataInputRef(text);
			}

        } else if (xtr.isStartElement() && ELEMENT_LOOP_DATA_OUTPUT_REF.equalsIgnoreCase(xtr.getLocalName())) {
        	String text = xtr.getElementText();
        	if (StringUtils.isNotEmpty(text)) {
				multiInstanceDef.setLoopDataOutputRef(text);
			}

        } else if (xtr.isStartElement() && ELEMENT_INPUT_DATAITEM.equalsIgnoreCase(xtr.getLocalName())) {
        	DataInput input = new DataInput();
        	if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_ID))) {
				input.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
			}
        	if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_ISCOLLECTION))) {
				input.setCollection(Boolean.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_ISCOLLECTION)));
			}
        	if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_NAME))) {
				input.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
			}
        	if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_DATA_SUBJECT_REF))) {
				input.setItemSubjectRef(xtr.getAttributeValue(null, ATTRIBUTE_DATA_SUBJECT_REF));
			}
        	multiInstanceDef.setInputDataItem(input);
        	
        } else if (xtr.isStartElement() && ELEMENT_OUTPUT_DATAITEM.equalsIgnoreCase(xtr.getLocalName())) {
        	DataOutput output = new DataOutput();
        	if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_ID))) {
        		output.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
			}
        	if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_ISCOLLECTION))) {
        		output.setCollection(Boolean.valueOf(xtr.getAttributeValue(null, ATTRIBUTE_ISCOLLECTION)));
			}
        	if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_NAME))) {
        		output.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
			}
        	if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_DATA_SUBJECT_REF))) {
        		output.setItemSubjectRef(xtr.getAttributeValue(null, ATTRIBUTE_DATA_SUBJECT_REF));
			}
        	multiInstanceDef.setOutputDataItem(output);
        	
        }
        
        
        else if (xtr.isStartElement() && ELEMENT_MULTIINSTANCE_CONDITION.equalsIgnoreCase(xtr.getLocalName())) {
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
