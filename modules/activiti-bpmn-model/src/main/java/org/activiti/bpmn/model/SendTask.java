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
package org.activiti.bpmn.model;

import hu.clickandlike.bpmn.model.interfaces.IImplementation;
import hu.clickandlike.bpmn.model.interfaces.IMessageRef;
import hu.clickandlike.bpmn.model.interfaces.IOperationRef;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tijs Rademakers
 * @author Krisztian Koncz (krisztian.koncz@clickandlike.hu
 */

//messageRef added by Krisztian Koncz

public class SendTask extends Task implements IImplementation, IOperationRef, IMessageRef {

  protected String type;
  protected String implementationType;
  protected String messageRef;
  protected String operationRef;
  protected List<FieldExtension> fieldExtensions = new ArrayList<FieldExtension>();
  
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getImplementationType() {
    return implementationType;
  }
  public void setImplementationType(String implementationType) {
    this.implementationType = implementationType;
  }
  public String getOperationRef() {
    return operationRef;
  }
  public void setOperationRef(String operationRef) {
    this.operationRef = operationRef;
  }
  
	public String getMessageRef() {
		return messageRef;
	}

	public void setMessageRef(String messageRef) {
		this.messageRef = messageRef;
	}

  public List<FieldExtension> getFieldExtensions() {
    return fieldExtensions;
  }
  public void setFieldExtensions(List<FieldExtension> fieldExtensions) {
    this.fieldExtensions = fieldExtensions;
  }
  
  public SendTask clone() {
    SendTask clone = new SendTask();
    clone.setValues(this);
    return clone;
  }
  
  public void setValues(SendTask otherElement) {
    super.setValues(otherElement);
    setType(otherElement.getType());
    setImplementationType(otherElement.getImplementationType());
    setOperationRef(otherElement.getOperationRef());
    setMessageRef(otherElement.getMessageRef());
    
    fieldExtensions = new ArrayList<FieldExtension>();
    if (otherElement.getFieldExtensions() != null && otherElement.getFieldExtensions().size() > 0) {
      for (FieldExtension extension : otherElement.getFieldExtensions()) {
        fieldExtensions.add(extension.clone());
      }
    }
  }
}
