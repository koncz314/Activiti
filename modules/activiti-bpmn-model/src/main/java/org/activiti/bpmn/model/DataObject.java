package org.activiti.bpmn.model;


public class DataObject extends FlowElement {
  
  protected String itemSubjectRef;
  protected boolean isCollection = false;
  
  public String getItemSubjectRef() {
    return itemSubjectRef;
  }
  public void setItemSubjectRef(String itemSubjectRef) {
    this.itemSubjectRef = itemSubjectRef;
  }
  public boolean isCollection() {
    return isCollection;
  }
  public void setCollection(boolean isCollection) {
    this.isCollection = isCollection;
  }
  
  public DataObject clone() {
    DataObject clone = new DataObject();
    clone.setValues(this);
    return clone;
  }
  
  public void setValues(DataObject otherDataSpec) {
    super.setValues((FlowElement)otherDataSpec);
    setItemSubjectRef(otherDataSpec.getItemSubjectRef());
    setCollection(otherDataSpec.isCollection());
  }
}
