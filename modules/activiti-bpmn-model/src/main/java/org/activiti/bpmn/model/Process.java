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

import hu.clickandlike.bpmn.model.interfaces.DataObjectContainer;
import hu.clickandlike.bpmn.model.interfaces.IResourceRoleContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tijs Rademakers
 */
public class Process extends BaseElement implements FlowElementsContainer, HasExecutionListeners, DataObjectContainer, IResourceRoleContainer {

  protected String name;
  protected boolean executable = true;
  protected boolean closed = false;
  protected String processType;
  protected String documentation;
  protected IOSpecification ioSpecification;
  protected List<ActivitiListener> executionListeners = new ArrayList<ActivitiListener>();
  protected List<Lane> lanes = new ArrayList<Lane>();
  protected List<FlowElement> flowElementList = new ArrayList<FlowElement>();
  protected List<Artifact> artifactList = new ArrayList<Artifact>();
  protected List<String> candidateStarterUsers = new ArrayList<String>();
  protected List<String> candidateStarterGroups = new ArrayList<String>();
  protected List<EventListener> eventListeners = new ArrayList<EventListener>();
  protected Map<String,DataObject> dataObjectMap = new LinkedHashMap<String, DataObject>();
  protected List<ResourceRole> resourceRoles = new ArrayList<ResourceRole>();
  
  public String getDocumentation() {
    return documentation;
  }

  public void setDocumentation(String documentation) {
    this.documentation = documentation;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isExecutable() {
    return executable;
  }

  public void setExecutable(boolean executable) {
    this.executable = executable;
  }

  public boolean isClosed() {
	return closed;
}

public void setClosed(boolean closed) {
	this.closed = closed;
}

public String getProcessType() {
	return processType;
}

public void setProcessType(String processType) {
	this.processType = processType;
}

public IOSpecification getIoSpecification() {
    return ioSpecification;
  }

  public void setIoSpecification(IOSpecification ioSpecification) {
    this.ioSpecification = ioSpecification;
  }

  public List<ActivitiListener> getExecutionListeners() {
    return executionListeners;
  }

  public void setExecutionListeners(List<ActivitiListener> executionListeners) {
    this.executionListeners = executionListeners;
  }

  public List<Lane> getLanes() {
    return lanes;
  }

  public void setLanes(List<Lane> lanes) {
    this.lanes = lanes;
  }
  
  public FlowElement getFlowElement(String flowElementId) {
    return findFlowElementInList(flowElementId);
  }
  
  protected FlowElement findFlowElementInList(String flowElementId) {
    for (FlowElement f : flowElementList) {
      if (f.getId() != null && f.getId().equals(flowElementId)) {
        return f;
      }
    }
    return null;
  }
  
  public Collection<FlowElement> getFlowElements() {
    return flowElementList;
  }
  
  public void addFlowElement(FlowElement element) {
    flowElementList.add(element);
  }
  
  public void removeFlowElement(String elementId) {
    FlowElement element = findFlowElementInList(elementId);
    if (element != null) {
      flowElementList.remove(element);
    }
  }
  
  public Artifact getArtifact(String id) {
    Artifact foundArtifact = null;
    for (Artifact artifact : artifactList) {
      if (id.equals(artifact.getId())) {
        foundArtifact = artifact;
        break;
      }
    }
    return foundArtifact;
  }
  
  public Collection<Artifact> getArtifacts() {
    return artifactList;
  }
  
  public void addArtifact(Artifact artifact) {
    artifactList.add(artifact);
  }
  
  public void removeArtifact(String artifactId) {
    Artifact artifact = getArtifact(artifactId);
    if (artifact != null) {
      artifactList.remove(artifact);
    }
  }

  public List<String> getCandidateStarterUsers() {
    return candidateStarterUsers;
  }

  public void setCandidateStarterUsers(List<String> candidateStarterUsers) {
    this.candidateStarterUsers = candidateStarterUsers;
  }

  public List<String> getCandidateStarterGroups() {
    return candidateStarterGroups;
  }

  public void setCandidateStarterGroups(List<String> candidateStarterGroups) {
    this.candidateStarterGroups = candidateStarterGroups;
  }
  
  public List<EventListener> getEventListeners() {
	  return eventListeners;
  }
  
  public void setEventListeners(List<EventListener> eventListeners) {
	  this.eventListeners = eventListeners;
  }
  
  
  @SuppressWarnings("unchecked")
  public <FlowElementType extends FlowElement> List<FlowElementType> findFlowElementsOfType(Class<FlowElementType> type) {
    List<FlowElementType> foundFlowElements = new ArrayList<FlowElementType>();
    for (FlowElement flowElement : this.getFlowElements()) {
      if (type.isInstance(flowElement)) {
        foundFlowElements.add((FlowElementType) flowElement);
      }
      if (flowElement instanceof SubProcess) {
        foundFlowElements.addAll(findFlowElementsInSubProcessOfType((SubProcess) flowElement, type));
      }
    }
    return foundFlowElements;
  }
  
  @SuppressWarnings("unchecked")
  public <FlowElementType extends FlowElement> List<FlowElementType> findFlowElementsInSubProcessOfType(SubProcess subProcess, Class<FlowElementType> type) {
    List<FlowElementType> foundFlowElements = new ArrayList<FlowElementType>();
    for (FlowElement flowElement : subProcess.getFlowElements()) {
      if (type.isInstance(flowElement)) {
        foundFlowElements.add((FlowElementType) flowElement);
      }
      if (flowElement instanceof SubProcess) {
        foundFlowElements.addAll(findFlowElementsInSubProcessOfType((SubProcess) flowElement, type));
      }
    }
    return foundFlowElements;
  }
  
  public void addDataObject(DataObject data) {
	  dataObjectMap.put(data.getId(), data);
  }
  
  public boolean containsDataObject(DataObject data) {
	  return dataObjectMap.containsKey(data.getId());
  }
  
  public DataObject getDataObject(String id) {
	  return dataObjectMap.get(id);
  }
  
  public Collection<DataObject> getAllDataObjects() {
	  return dataObjectMap.values();
  }
  
  public Collection<String> getDataObjectIds() {
	  return dataObjectMap.keySet();
  }
  
  @Override
  public void setResourceRoles(List<ResourceRole> roleList) {
	this.resourceRoles = roleList;
	
  }
  @Override
  public List<ResourceRole> getResourceRoles() {
	return resourceRoles;
  }
  
  
  public Process clone() {
    Process clone = new Process();
    clone.setValues(this);
    return clone;
  }
  
  public void setValues(Process otherElement) {
    super.setValues(otherElement);
    
    setName(otherElement.getName());
    setExecutable(otherElement.isExecutable());
    setDocumentation(otherElement.getDocumentation());
    setClosed(otherElement.isClosed());
    setProcessType(otherElement.getProcessType());
    if (otherElement.getIoSpecification() != null) {
      setIoSpecification(otherElement.getIoSpecification().clone());
    }
    
    executionListeners = new ArrayList<ActivitiListener>();
    if (otherElement.getExecutionListeners() != null && otherElement.getExecutionListeners().size() > 0) {
      for (ActivitiListener listener : otherElement.getExecutionListeners()) {
        executionListeners.add(listener.clone());
      }
    }
    
    candidateStarterUsers = new ArrayList<String>();
    if (otherElement.getCandidateStarterUsers() != null && otherElement.getCandidateStarterUsers().size() > 0) {
      candidateStarterUsers.addAll(otherElement.getCandidateStarterUsers());
    }
    
    candidateStarterGroups = new ArrayList<String>();
    if (otherElement.getCandidateStarterGroups() != null && otherElement.getCandidateStarterGroups().size() > 0) {
      candidateStarterGroups.addAll(otherElement.getCandidateStarterGroups());
    }
    
    eventListeners = new ArrayList<EventListener>();
    if(otherElement.getEventListeners() != null && !otherElement.getEventListeners().isEmpty()) {
    	for(EventListener listener : otherElement.getEventListeners()) {
    		eventListeners.add(listener.clone());
    	}
    }
    dataObjectMap = new LinkedHashMap<String, DataObject>();
    for (DataObject data : otherElement.getAllDataObjects()) {
    	dataObjectMap.put(data.getId(), data.clone());
    }
    
    resourceRoles = new ArrayList<ResourceRole>();
    for (ResourceRole role : otherElement.getResourceRoles()) {
    	resourceRoles.add(role.clone());
    }
    
  }
}
