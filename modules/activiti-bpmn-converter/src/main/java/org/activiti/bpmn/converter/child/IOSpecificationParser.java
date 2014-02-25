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
import org.activiti.bpmn.model.DataInputSet;
import org.activiti.bpmn.model.DataOutput;
import org.activiti.bpmn.model.DataOutputSet;
import org.activiti.bpmn.model.IOSpecification;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SendTask;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.SubProcess;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Tijs Rademakers
 */
public class IOSpecificationParser extends BaseChildElementParser {

  public String getElementName() {
    return ELEMENT_IOSPECIFICATION;
  }
  
  public void parseChildElement(XMLStreamReader xtr, BaseElement parentElement, BpmnModel model) throws Exception {
    
    /*if (parentElement instanceof ServiceTask == false && parentElement instanceof SendTask == false && 
        parentElement instanceof SubProcess == false && parentElement instanceof Process == false) return;
    */
	  
	  if (parentElement instanceof Activity == false && 
		        parentElement instanceof SubProcess == false && parentElement instanceof Process == false) return;
    IOSpecification ioSpecification = new IOSpecification();
    BpmnXMLUtil.addXMLLocation(ioSpecification, xtr);
    boolean readyWithIOSpecification = false;
    System.out.println("IOSPEC PARSE " + parentElement);
    try {
      while (readyWithIOSpecification == false && xtr.hasNext()) {
        xtr.next();
        if (xtr.isStartElement() && ELEMENT_DATA_INPUT.equalsIgnoreCase(xtr.getLocalName())) {
          DataInput dataSpec = new DataInput();
          BpmnXMLUtil.addXMLLocation(dataSpec, xtr);
          dataSpec.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
          dataSpec.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
          dataSpec.setItemSubjectRef(xtr.getAttributeValue(null, ATTRIBUTE_DATA_SUBJECT_REF));
          ioSpecification.getDataInputs().add(dataSpec);

        } else if (xtr.isStartElement() && ELEMENT_DATA_OUTPUT.equalsIgnoreCase(xtr.getLocalName())) {
          DataOutput dataSpec = new DataOutput();
          BpmnXMLUtil.addXMLLocation(dataSpec, xtr);
          dataSpec.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
          dataSpec.setName(xtr.getAttributeValue(null, ATTRIBUTE_NAME));
          dataSpec.setItemSubjectRef(xtr.getAttributeValue(null, ATTRIBUTE_DATA_SUBJECT_REF));
          ioSpecification.getDataOutputs().add(dataSpec);
          
        } else if (xtr.isStartElement() && ELEMENT_DATA_INPUTSET.equalsIgnoreCase(xtr.getLocalName())) {
        	DataInputSet inputSet = new DataInputSet();
        	boolean readyWithInputSet = false;
        	while (readyWithInputSet == false && xtr.hasNext()) {
        		xtr.next();
        		if (xtr.isStartElement() && ELEMENT_DATA_INPUT_REFS.equalsIgnoreCase(xtr.getLocalName())) {
        			String dataInputRefs = xtr.getElementText();
        			System.out.println(dataInputRefs);
        			if (StringUtils.isNotEmpty(dataInputRefs)) {
        				inputSet.getDataInputRefs().add(dataInputRefs);
        			}
        			
        		} if (xtr.isEndElement() && ELEMENT_DATA_INPUTSET.equalsIgnoreCase(xtr.getLocalName())) {
        			readyWithInputSet = true;
        			ioSpecification.setDataInputSet(inputSet);
        		}
        	}
        	
        } else if (xtr.isStartElement() && ELEMENT_DATA_OUTPUTSET.equalsIgnoreCase(xtr.getLocalName())) {
        	DataOutputSet outputSet = new DataOutputSet();
        	boolean readyWithOutputSet = false;
        	while (readyWithOutputSet == false && xtr.hasNext()) {
        		xtr.next();
        		if (xtr.isStartElement() && ELEMENT_DATA_OUTPUT_REFS.equalsIgnoreCase(xtr.getLocalName())) {
        			String dataOutputRefs = xtr.getElementText();
        			System.out.println("DATA OUTPUT REFS: "+dataOutputRefs);
        			if (StringUtils.isNotEmpty(dataOutputRefs)) {
        				outputSet.getDataOutputRefs().add(dataOutputRefs);
        			}
        			
        		} if (xtr.isEndElement() && ELEMENT_DATA_OUTPUTSET.equalsIgnoreCase(xtr.getLocalName())) {
        			readyWithOutputSet = true;
        			ioSpecification.setDataOutputSet(outputSet);
        		}
        	}
        } else if (xtr.isEndElement() && getElementName().equalsIgnoreCase(xtr.getLocalName())) {
          readyWithIOSpecification = true;
        }
      }
    } catch (Exception e) {
      LOGGER.warn("Error parsing ioSpecification child elements", e);
    }
    
    if (parentElement instanceof Process) {
      ((Process) parentElement).setIoSpecification(ioSpecification);
    } else {
      ((Activity) parentElement).setIoSpecification(ioSpecification);
    }
  }
  
  protected String parseItemSubjectRef(String itemSubjectRef, BpmnModel model) {
    String result = null;
    if (StringUtils.isNotEmpty(itemSubjectRef)) {
      int indexOfP = itemSubjectRef.indexOf(':');
      if (indexOfP != -1) {
        String prefix = itemSubjectRef.substring(0, indexOfP);
        String resolvedNamespace = model.getNamespace(prefix);
        result = resolvedNamespace + ":" + itemSubjectRef.substring(indexOfP + 1);
      } else {
        result = model.getTargetNamespace() + ":" + itemSubjectRef;
      }
    }
    return result;
  }
}
