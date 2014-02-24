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
package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.LoopCharacteristics;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.StandardLoopCharacteristics;
import org.apache.commons.lang3.StringUtils;

public class LoopCharacteristicsExport implements BpmnXMLConstants {

	public static void writeLoopCharacteristics(Activity activity, XMLStreamWriter xtw) throws Exception {
		if (activity.getLoopCharacteristics() != null) {
			LoopCharacteristics characteristic = activity.getLoopCharacteristics();
			if (characteristic instanceof MultiInstanceLoopCharacteristics) {
				writeMultiInstance(characteristic, xtw);
			}
			else if (characteristic instanceof StandardLoopCharacteristics) {
				writeStandard(characteristic, xtw);
			}
		}
	}
	
	private static void writeStandard(LoopCharacteristics loopCharacteristic, XMLStreamWriter xtw) throws Exception {
		StandardLoopCharacteristics standard = (StandardLoopCharacteristics)loopCharacteristic;
		xtw.writeStartElement(ELEMENT_STANDARD_LOOP);
		xtw.writeAttribute(ATTRIBUTE_TEST_BEFORE, String.valueOf(standard.isTestBefore()));
		if (StringUtils.isNotEmpty(standard.getLoopMaximum())) {
			xtw.writeAttribute(ATTRIBUTE_LOOP_MAXIMUM, standard.getLoopMaximum());
		}
		
		if (StringUtils.isNotEmpty(standard.getLoopCondition())) {
			xtw.writeStartElement(ELEMENT_LOOP_CONDITION);
			xtw.writeCData(standard.getLoopCondition());
			xtw.writeEndElement();
		}
		xtw.writeEndElement();
	}
	
  private static void writeMultiInstance(LoopCharacteristics loopCharacteristic, XMLStreamWriter xtw) throws Exception {
    
      MultiInstanceLoopCharacteristics multiInstanceObject = (MultiInstanceLoopCharacteristics)loopCharacteristic;
      if (StringUtils.isNotEmpty(multiInstanceObject.getLoopCardinality()) ||
          StringUtils.isNotEmpty(multiInstanceObject.getInputDataItem()) ||
          StringUtils.isNotEmpty(multiInstanceObject.getCompletionCondition())) {
        
        xtw.writeStartElement(ELEMENT_MULTIINSTANCE);
        BpmnXMLUtil.writeDefaultAttribute(ATTRIBUTE_MULTIINSTANCE_SEQUENTIAL, String.valueOf(multiInstanceObject.isSequential()).toLowerCase(), xtw);
        if (StringUtils.isNotEmpty(multiInstanceObject.getInputDataItem())) {
          BpmnXMLUtil.writeQualifiedAttribute(ATTRIBUTE_MULTIINSTANCE_COLLECTION, multiInstanceObject.getInputDataItem(), xtw);
        }
        if (StringUtils.isNotEmpty(multiInstanceObject.getElementVariable())) {
          BpmnXMLUtil.writeQualifiedAttribute(ATTRIBUTE_MULTIINSTANCE_VARIABLE, multiInstanceObject.getElementVariable(), xtw);
        }
        if (StringUtils.isNotEmpty(multiInstanceObject.getLoopCardinality())) {
          xtw.writeStartElement(ELEMENT_MULTIINSTANCE_CARDINALITY);
          xtw.writeCharacters(multiInstanceObject.getLoopCardinality());
          xtw.writeEndElement();
        }
        if (StringUtils.isNotEmpty(multiInstanceObject.getCompletionCondition())) {
          xtw.writeStartElement(ELEMENT_MULTIINSTANCE_CONDITION);
          xtw.writeCharacters(multiInstanceObject.getCompletionCondition());
          xtw.writeEndElement();
        }
        xtw.writeEndElement();
      }
    }
  
}
