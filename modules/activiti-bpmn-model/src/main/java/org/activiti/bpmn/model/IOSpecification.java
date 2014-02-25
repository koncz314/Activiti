package org.activiti.bpmn.model;

import java.util.ArrayList;
import java.util.List;

public class IOSpecification extends BaseElement {

  protected List<DataInput> dataInputs = new ArrayList<DataInput>();
  protected List<DataOutput> dataOutputs = new ArrayList<DataOutput>();
  protected DataInputSet dataInputSet = new DataInputSet();
  protected DataOutputSet dataOutputSet = new DataOutputSet();
  //protected List<String> dataInputRefs = new ArrayList<String>();
  //protected List<String> dataOutputRefs = new ArrayList<String>();
  
  public List<DataInput> getDataInputs() {
    return dataInputs;
  }
  public void setDataInputs(List<DataInput> dataInputs) {
    this.dataInputs = dataInputs;
  }
  public List<DataOutput> getDataOutputs() {
    return dataOutputs;
  }
  public void setDataOutputs(List<DataOutput> dataOutputs) {
    this.dataOutputs = dataOutputs;
  }
  /*public List<String> getDataInputRefs() {
    return dataInputRefs;
  }
  public void setDataInputRefs(List<String> dataInputRefs) {
    this.dataInputRefs = dataInputRefs;
  }
  public List<String> getDataOutputRefs() {
    return dataOutputRefs;
  }
  public void setDataOutputRefs(List<String> dataOutputRefs) {
    this.dataOutputRefs = dataOutputRefs;
  }*/
  
  public DataInputSet getDataInputSet() {
	return dataInputSet;
}
public void setDataInputSet(DataInputSet dataInputSet) {
	this.dataInputSet = dataInputSet;
}
public DataOutputSet getDataOutputSet() {
	return dataOutputSet;
}
public void setDataOutputSet(DataOutputSet dataOutputSet) {
	this.dataOutputSet = dataOutputSet;
}
public IOSpecification clone() {
    IOSpecification clone = new IOSpecification();
    clone.setValues(this);
    return clone;
  }
  
  public void setValues(IOSpecification otherSpec) {
    dataInputs = new ArrayList<DataInput>();
    if (otherSpec.getDataInputs() != null && otherSpec.getDataInputs().size() > 0) {
      for (DataInput dataSpec : otherSpec.getDataInputs()) {
        dataInputs.add(dataSpec.clone());
      }
    }
    
    dataOutputs = new ArrayList<DataOutput>();
    if (otherSpec.getDataOutputs() != null && otherSpec.getDataOutputs().size() > 0) {
      for (DataOutput dataSpec : otherSpec.getDataOutputs()) {
        dataOutputs.add(dataSpec.clone());
      }
    }
    
    setDataInputSet(otherSpec.getDataInputSet().clone());
    setDataOutputSet(otherSpec.getDataOutputSet().clone());
  }
}
