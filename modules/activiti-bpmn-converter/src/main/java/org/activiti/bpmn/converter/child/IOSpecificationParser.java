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
import org.activiti.bpmn.model.IOSpecification;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SubProcess;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Tijs Rademakers
 */
public class IOSpecificationParser extends BaseChildElementParser {
	
	private DataInputParser inputParser = new DataInputParser();
	private DataOutputParser outputParser = new DataOutputParser();
	private InputSetParser inputSetParser = new InputSetParser();
	private OutputSetParser outputsetParser = new OutputSetParser();
	
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
          inputParser.parseChildElement(xtr, ioSpecification, model);
          
        } else if (xtr.isStartElement() && ELEMENT_DATA_OUTPUT.equalsIgnoreCase(xtr.getLocalName())) {
          outputParser.parseChildElement(xtr, ioSpecification, model);
          
        } else if (xtr.isStartElement() && ELEMENT_DATA_INPUTSET.equalsIgnoreCase(xtr.getLocalName())) {
        	inputSetParser.parseChildElement(xtr, ioSpecification, model);
        	
        } else if (xtr.isStartElement() && ELEMENT_DATA_OUTPUTSET.equalsIgnoreCase(xtr.getLocalName())) {
        	outputsetParser.parseChildElement(xtr, ioSpecification, model);
        	
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
