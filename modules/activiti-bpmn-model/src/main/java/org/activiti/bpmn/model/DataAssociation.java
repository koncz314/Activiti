package org.activiti.bpmn.model;

import java.util.ArrayList;
import java.util.List;

public class DataAssociation extends BaseElement {// implements PropertyChangeListener{

  protected String sourceRef;
  protected String targetRef;
  protected String transformation;
  protected List<Assignment> assignments = new ArrayList<Assignment>();
  
  public String getSourceRef() {
    return sourceRef;
  }
  public void setSourceRef(String sourceRef) {
    this.sourceRef = sourceRef;
  }

  public String getTargetRef() {
    return targetRef;
  }
  public void setTargetRef(String targetRef) {
    this.targetRef = targetRef;
  }
  public String getTransformation() {
    return transformation;
  }
  public void setTransformation(String transformation) {
    this.transformation = transformation;
  }
  public List<Assignment> getAssignments() {
    return assignments;
  }
  public void setAssignments(List<Assignment> assignments) {
    this.assignments = assignments;
  }
  
  public DataAssociation clone() {
    DataAssociation clone = new DataAssociation();
    clone.setValues(this);
    return clone;
  }
  
  public void setValues(DataAssociation otherAssociation) {
    super.setValues(otherAssociation);
	setSourceRef(otherAssociation.getSourceRef());
    setTargetRef(otherAssociation.getTargetRef());
    setTransformation(otherAssociation.getTransformation());
    
    assignments = new ArrayList<Assignment>();
    if (otherAssociation.getAssignments() != null && otherAssociation.getAssignments().size() > 0) {
      for (Assignment assignment : otherAssociation.getAssignments()) {
        assignments.add(assignment.clone());
      }
    }
  }
  /*
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("id")) {
			String oldId = (String) evt.getOldValue();
			String newId = (String) evt.getNewValue();
			if (sourceRef.equals(oldId)) {
				sourceRef = newId;
			}
			if (targetRef.equals(oldId)) {
				targetRef = newId;			
			}
			
		}
		
	}*/

}
