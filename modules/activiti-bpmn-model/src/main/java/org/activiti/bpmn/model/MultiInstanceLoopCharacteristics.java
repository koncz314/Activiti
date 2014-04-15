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


/**
 * @author Tijs Rademakers
 */
public class MultiInstanceLoopCharacteristics extends LoopCharacteristics {

  
  protected String loopCardinality;
  protected boolean sequential;
  protected String completionCondition;
  protected String loopDataInputRef;
  protected String loopDataOutputRef;
  protected DataInput inputDataItem;
  protected DataOutput outputDataItem;

  public DataInput getInputDataItem() {
    return inputDataItem;
  }
  public void setInputDataItem(DataInput inputDataItem) {
    this.inputDataItem = inputDataItem;
  }
  
  public DataOutput getOutputDataItem() {
	return outputDataItem;
  }
  public void setOutputDataItem(DataOutput outputDataItem) {
	this.outputDataItem = outputDataItem;
  }
  
  public String getLoopCardinality() {
    return loopCardinality;
  }
  public void setLoopCardinality(String loopCardinality) {
    this.loopCardinality = loopCardinality;
  }
  public String getCompletionCondition() {
    return completionCondition;
  }
  public void setCompletionCondition(String completionCondition) {
    this.completionCondition = completionCondition;
  }
  public String getLoopDataInputRef() {
    return loopDataInputRef;
  }
  public void setLoopDataInputRef(String loopDataInputRef) {
    this.loopDataInputRef = loopDataInputRef;
  }
  public String getLoopDataOutputRef() {
    return loopDataOutputRef;
  }
  public void setLoopDataOutputRef(String loopDataOutputRef) {
    this.loopDataOutputRef = loopDataOutputRef;
  }
  public boolean isSequential() {
    return sequential;
  }
  public void setSequential(boolean sequential) {
    this.sequential = sequential;
  }
  
  public MultiInstanceLoopCharacteristics clone() {
    MultiInstanceLoopCharacteristics clone = new MultiInstanceLoopCharacteristics();
    clone.setValues(this);
    return clone;
  }
  
  public void setValues(MultiInstanceLoopCharacteristics otherLoopCharacteristics) {
    super.setValues(otherLoopCharacteristics);
    if (otherLoopCharacteristics.getInputDataItem() != null) {
    	setInputDataItem(otherLoopCharacteristics.getInputDataItem().clone());
    }
    if (otherLoopCharacteristics.getOutputDataItem() != null) {
    	setOutputDataItem(otherLoopCharacteristics.getOutputDataItem().clone());
    }
	setLoopCardinality(otherLoopCharacteristics.getLoopCardinality());
    setCompletionCondition(otherLoopCharacteristics.getCompletionCondition());
    setLoopDataInputRef(otherLoopCharacteristics.getLoopDataInputRef());
    setLoopDataOutputRef(otherLoopCharacteristics.getLoopDataOutputRef());
    setSequential(otherLoopCharacteristics.isSequential());
  }
}
