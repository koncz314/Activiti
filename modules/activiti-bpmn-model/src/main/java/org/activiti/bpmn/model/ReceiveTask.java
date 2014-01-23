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

/**
 * @author Tijs Rademakers
 * @author Krisztian Koncz (krisztian.koncz@clickandlike.hu
 */

//updates added by Krisztian Koncz
public class ReceiveTask extends Task implements IImplementation, IOperationRef, IMessageRef {

	protected String implementationType;
	protected String messageRef;
	protected String operationRef;
	protected Boolean instantiate;
	
	
	public String getImplementationType() {
		return implementationType;
	}

	public void setImplementationType(String implementationType) {
		this.implementationType = implementationType;
	}

	public String getMessageRef() {
		return messageRef;
	}

	public void setMessageRef(String messageRef) {
		this.messageRef = messageRef;
	}

	public String getOperationRef() {
		return operationRef;
	}

	public void setOperationRef(String operationRef) {
		this.operationRef = operationRef;
	}

	public Boolean getInstantiate() {
		return instantiate;
	}

	public void setInstantiate(Boolean instantiate) {
		this.instantiate = instantiate;
	}

public ReceiveTask clone() {
    ReceiveTask clone = new ReceiveTask();
    clone.setValues(this);
    return clone;
  }
  
  public void setValues(ReceiveTask otherElement) {
    super.setValues(otherElement);
    setInstantiate(otherElement.getInstantiate());
    setImplementationType(otherElement.getImplementationType());
    setOperationRef(otherElement.getOperationRef());
    setMessageRef(otherElement.getMessageRef());
  }
}
