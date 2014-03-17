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

import java.util.ArrayList;
import java.util.List;


/**
 * @author Tijs Rademakers
 */
public class ThrowEvent extends Event {
	protected List<DataInput> dataInputs = new ArrayList<DataInput>();
	protected List<DataAssociation> dataInputAssociations = new ArrayList<DataAssociation>();
	protected DataInputSet dataInputSet = null;
	
	
  public List<DataInput> getDataInputs() {
		return dataInputs;
	}

	public void setDataInputs(List<DataInput> dataInputs) {
		this.dataInputs = dataInputs;
	}

	public List<DataAssociation> getDataInputAssociations() {
		return dataInputAssociations;
	}

	public void setDataInputAssociations(List<DataAssociation> dataInputAssociations) {
		this.dataInputAssociations = dataInputAssociations;
	}

	public DataInputSet getDataInputSet() {
		return dataInputSet;
	}

	public void setDataInputSet(DataInputSet dataInputSet) {
		this.dataInputSet = dataInputSet;
	}

public ThrowEvent clone() {
    ThrowEvent clone = new ThrowEvent();
    clone.setValues(this);
    return clone;
  }
  
  public void setValues(ThrowEvent other) {
    super.setValues(other);
    dataInputs = new ArrayList<DataInput>();
	for (DataInput data : other.getDataInputs()) {
		dataInputs.add(data.clone());
	}
	dataInputAssociations = new ArrayList<DataAssociation>();
	for (DataAssociation data : other.getDataInputAssociations()) {
		dataInputAssociations.add(data.clone());
	}
	if (other.getDataInputSet() != null) {
		setDataInputSet(other.getDataInputSet().clone());
	}
    
  }
}
