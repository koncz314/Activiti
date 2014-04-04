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
import org.activiti.bpmn.model.DataAssociation;
import org.activiti.bpmn.model.ThrowEvent;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Tijs Rademakers
 */
public class DataInputAssociationParser extends BaseChildElementParser {

  public String getElementName() {
    return ELEMENT_INPUT_ASSOCIATION;
  }
  
  public void parseChildElement(XMLStreamReader xtr, BaseElement parentElement, BpmnModel model) throws Exception {
    
	  if (parentElement instanceof Activity == false && parentElement instanceof ThrowEvent == false) {
			return;
		}
    
    DataAssociation dataAssociation = new DataAssociation();
    BpmnXMLUtil.addXMLLocation(dataAssociation, xtr);
    if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, ATTRIBUTE_ID))) {
		dataAssociation.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));
	}
    DataAssociationParser.parseDataAssociation(dataAssociation, getElementName(), xtr);
    
    if (parentElement instanceof Activity) {
    	((Activity) parentElement).getDataInputAssociations().add(dataAssociation);
    } else if (parentElement instanceof ThrowEvent) {
    	((ThrowEvent) parentElement).getDataInputAssociations().add(dataAssociation);
    }
  }
}
