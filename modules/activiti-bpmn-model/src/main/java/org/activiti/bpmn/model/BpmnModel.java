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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.model.parse.Problem;
import org.activiti.bpmn.model.parse.Warning;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Tijs Rademakers
 */
public class BpmnModel {
  
	protected List<Process> processes = new ArrayList<Process>();
	protected Map<String, GraphicInfo> locationMap = new LinkedHashMap<String, GraphicInfo>();
	protected Map<String, GraphicInfo> labelLocationMap = new LinkedHashMap<String, GraphicInfo>();
	protected Map<String, List<GraphicInfo>> flowLocationMap = new LinkedHashMap<String, List<GraphicInfo>>();
	protected Map<String, Signal> signalMap = new LinkedHashMap<String, Signal>();
	protected Map<String, Message> messageMap = new LinkedHashMap<String, Message>();
	protected Map<String, BPMNError> errorMap = new LinkedHashMap<String, BPMNError>();
	protected Map<String, ItemDefinition> itemDefinitionMap = new LinkedHashMap<String, ItemDefinition>();
	protected Map<String, Resource> resourceMap = new LinkedHashMap<String, Resource>();
	protected Map<String, EventDefinition> eventDefintionMap = new LinkedHashMap<String, EventDefinition>();
	protected Map<String, Category> categoryMap = new LinkedHashMap<String, Category>();
	protected Map<String, Group> groupMap = new LinkedHashMap<String, Group>();
	
	
	protected List<MessageFlow> messageFlows = new ArrayList<MessageFlow>();
	
	protected List<Pool> pools = new ArrayList<Pool>();
	protected List<Import> imports = new ArrayList<Import>();
	protected List<Interface> interfaces = new ArrayList<Interface>();
	protected List<Artifact> globalArtifacts = new ArrayList<Artifact>();
	protected List<Problem> problems = new ArrayList<Problem>();
	protected List<Warning> warnings = new ArrayList<Warning>();
	protected Map<String, String> namespaceMap = new LinkedHashMap<String, String>();
	protected String targetNamespace;
	protected int nextFlowIdCounter = 1;

	public Process getMainProcess() {
	  //if (getPools().size() > 0) {
		  //TODO check this solution
	    //return getProcess(getPools().get(0).getId());
		  return getProcess(null);
	  //} else {
	    //return getProcess(null);
		//  return getProcess(getPools().get(0).getId());
	  //}
		
	}

	public Process getProcess(String poolRef) {
	  for (Process process : processes) {
	    boolean foundPool = false;
	    for (Pool pool : pools) {
        if (StringUtils.isNotEmpty(pool.getProcessRef()) && pool.getProcessRef().equalsIgnoreCase(process.getId())) {
          
          if(poolRef != null) {
            if(pool.getId().equalsIgnoreCase(poolRef)) {
              foundPool = true;
            }
          } else {
            foundPool = true;
          }
        }
      }
	    
	    if(poolRef == null && foundPool == false) {
	      return process;
	    } else if(poolRef != null && foundPool == true) {
	      return process;
	    }
	  }
	  
	  return null;
  }
	
	public List<Process> getProcesses() {
	  return processes;
	}
	
	public void addProcess(Process process) {
	  processes.add(process);
	}
	
	public Pool getPool(String id) {
	  Pool foundPool = null;
	  if (StringUtils.isNotEmpty(id)) {
  	  for (Pool pool : pools) {
        if (id.equals(pool.getId())) {
          foundPool = pool;
          break;
        }
      }
	  }
	  return foundPool;
	}
	
	public Lane getLane(String id) {
	  Lane foundLane = null;
    if (StringUtils.isNotEmpty(id)) {
      for (Process process : processes) {
        for (Lane lane : process.getLanes()) {
          if (id.equals(lane.getId())) {
            foundLane = lane;
            break;
          }
        }
        if (foundLane != null) {
          break;
        }
      }
    }
    return foundLane;
  }
	
	public FlowElement getFlowElement(String id) {
	  FlowElement foundFlowElement = null;
	  for (Process process : processes) {
	    foundFlowElement = process.getFlowElement(id);
	    if (foundFlowElement != null) {
	      break;
	    }
	  }
	  
	  if (foundFlowElement == null) {
	    for (Process process : processes) {
	      for (FlowElement flowElement : process.findFlowElementsOfType(SubProcess.class)) {
	        foundFlowElement = getFlowElementInSubProcess(id, (SubProcess) flowElement);
	        if (foundFlowElement != null) {
	          break;
	        }
	      }
	      if (foundFlowElement != null) {
          break;
        }
	    }
	  }
	  
	  return foundFlowElement;
	}
	
	protected FlowElement getFlowElementInSubProcess(String id, SubProcess subProcess) {
	  FlowElement foundFlowElement = subProcess.getFlowElement(id);
    if (foundFlowElement == null) {
      for (FlowElement flowElement : subProcess.getFlowElements()) {
        if (flowElement instanceof SubProcess) {
          foundFlowElement = getFlowElementInSubProcess(id, (SubProcess) flowElement);
          if (foundFlowElement != null) {
            break;
          }
        }
      }
    }
    return foundFlowElement;
	}
	
	public Collection<DataObject> collectVisibleDataObjectForElement(String id) {
		List<DataObject> dataObjects = null;
		for (Process process : processes) {
			if (process.getFlowElement(id) != null) {
				return process.getAllDataObjects();
		    }
		}
		boolean foundFlowElement = false;
		List<DataObjectContainer> path = new ArrayList<DataObjectContainer>();
		for (Process process : processes) {
			path.add(process);
			for (FlowElement flowElement : process.findFlowElementsOfType(SubProcess.class)) {
				foundFlowElement = exploreSubprocess(id, (SubProcess) flowElement, path);
				if (foundFlowElement != false) {
					break;
				}
			}
			if (foundFlowElement != false) {
				break;
			}
			path.remove(process);
		}
		
		if (foundFlowElement) {
			dataObjects = new ArrayList<DataObject>();
			for (DataObjectContainer container : path) {
				dataObjects.addAll(container.getAllDataObjects());
			}
			return dataObjects;
		}
		
		return null;
	}
	
	private boolean exploreSubprocess(String id, SubProcess subProcess, List<DataObjectContainer> path) {
		path.add(subProcess);
		Boolean foundFlowElement = subProcess.getFlowElement(id) == null ? false : true;
		if (foundFlowElement == false) {
		      for (FlowElement flowElement : subProcess.getFlowElements()) {
		        if (flowElement instanceof SubProcess) {
		          foundFlowElement = exploreSubprocess(id, (SubProcess) flowElement, path);
		          if (foundFlowElement == true) {
		            break;
		          }
		        }
		      }
		    }
		if (foundFlowElement == false) {
			path.remove(subProcess);
		}
		return foundFlowElement;
	}
	
	
	public Artifact getArtifact(String id) {
	  Artifact foundArtifact = null;
    for (Process process : processes) {
      foundArtifact = process.getArtifact(id);
      if (foundArtifact != null) {
        break;
      }
    }
    
    if (foundArtifact == null) {
      for (Process process : processes) {
        for (FlowElement flowElement : process.findFlowElementsOfType(SubProcess.class)) {
          foundArtifact = getArtifactInSubProcess(id, (SubProcess) flowElement);
          if (foundArtifact != null) {
            break;
          }
        }
        if (foundArtifact != null) {
          break;
        }
      }
    }
    
    return foundArtifact;
  }
  
  protected Artifact getArtifactInSubProcess(String id, SubProcess subProcess) {
    Artifact foundArtifact = subProcess.getArtifact(id);
    if (foundArtifact == null) {
      for (FlowElement flowElement : subProcess.getFlowElements()) {
        if (flowElement instanceof SubProcess) {
          foundArtifact = getArtifactInSubProcess(id, (SubProcess) flowElement);
          if (foundArtifact != null) {
            break;
          }
        }
      }
    }
    return foundArtifact;
  }
	
	public void addGraphicInfo(String key, GraphicInfo graphicInfo) {
		locationMap.put(key, graphicInfo);
	}
	
	public GraphicInfo getGraphicInfo(String key) {
		return locationMap.get(key);
	}
	
	public void removeGraphicInfo(String key) {
    locationMap.remove(key);
  }
	
	public List<GraphicInfo> getFlowLocationGraphicInfo(String key) {
    return flowLocationMap.get(key);
  }
	
	public void removeFlowGraphicInfoList(String key) {
	  flowLocationMap.remove(key);
  }
	
	public Map<String, GraphicInfo> getLocationMap() {
		return locationMap;
	}
	
	public Map<String, List<GraphicInfo>> getFlowLocationMap() {
    return flowLocationMap;
  }
	
	public GraphicInfo getLabelGraphicInfo(String key) {
    return labelLocationMap.get(key);
  }
	
	public void addLabelGraphicInfo(String key, GraphicInfo graphicInfo) {
		labelLocationMap.put(key, graphicInfo);
	}
	
	public void removeLabelGraphicInfo(String key) {
	  labelLocationMap.remove(key);
  }
	
	public Map<String, GraphicInfo> getLabelLocationMap() {
    return labelLocationMap;
  }
	
	public void addFlowGraphicInfoList(String key, List<GraphicInfo> graphicInfoList) {
		flowLocationMap.put(key, graphicInfoList);
	}
	
  public Collection<Signal> getSignals() {
    return signalMap.values();
  }
  
  public void setSignals(Collection<Signal> signalList) {
    if (signalList != null) {
      signalMap.clear();
      for (Signal signal : signalList) {
        addSignal(signal);
      }
    }
  }
  
  public void addSignal(Signal signal) {
    if (signal != null && StringUtils.isNotEmpty(signal.getId())) {
      signalMap.put(signal.getId(), signal);
    }
  }
  
  public boolean containsSignalId(String signalId) {
    return signalMap.containsKey(signalId);
  }
  
  public Signal getSignal(String id) {
    return signalMap.get(id);
  }

  public Collection<Message> getMessages() {
    return messageMap.values();
  }
  
  public void setMessages(Collection<Message> messageList) {
    if (messageList != null) {
      messageMap.clear();
      for (Message message : messageList) {
        addMessage(message);
      }
    }
  }

  public void addMessage(Message message) {
    if (message != null && StringUtils.isNotEmpty(message.getId())) {
      messageMap.put(message.getId(), message);
    }
  }
  
  public Message getMessage(String id) {
    return messageMap.get(id);
  }
  
  public boolean containsMessageId(String messageId) {
    return messageMap.containsKey(messageId);
  }
  
  public Collection<BPMNError> getErrors() {
    return errorMap.values();
  }
  
  public void setErrors(Map<String, BPMNError> errorMap) {
    this.errorMap = errorMap;
  }

  public void addError(BPMNError error) {
	  if (error != null && StringUtils.isNotEmpty(error.getId())) {
      errorMap.put(error.getId(), error);
    }
  }
  
  public BPMNError getError(String id) {
	  return errorMap.get(id);
  }
  
  public boolean containsErrorId(String errorId) {
    return errorMap.containsKey(errorId);
  }
  
  public Map<String, ItemDefinition> getItemDefinitions() {
    return itemDefinitionMap;
  }
  
  public void setItemDefinitions(Map<String, ItemDefinition> itemDefinitionMap) {
    this.itemDefinitionMap = itemDefinitionMap;
  }

  public void addItemDefinition(String id, ItemDefinition item) {
    if (StringUtils.isNotEmpty(id)) {
      itemDefinitionMap.put(id, item);
    }
  }
  
  public boolean containsItemDefinitionId(String id) {
    return itemDefinitionMap.containsKey(id);
  }

  public Collection<Resource> getResources() {
	return resourceMap.values();
  }
	  
  public void setResources(Collection<Resource> resourceList) {
	if (resourceList != null) {
		resourceMap.clear();
		for (Resource resoure : resourceList) {
			addResource(resoure);
		}
	}
  }

  public void addResource(Resource resource) {
	if (resource != null && StringUtils.isNotEmpty(resource.getId())) {
		resourceMap.put(resource.getId(), resource);
	}
  }
	  
  public boolean containsResourceId(String id) {
	return resourceMap.containsKey(id);
  }
  
  public Resource getResource(String id) {
	return resourceMap.get(id);
  }
  
  public List<Pool> getPools() {
    return pools;
  }
  
  public void setPools(List<Pool> pools) {
    this.pools = pools;
  }
  
  public List<Import> getImports() {
    return imports;
  }

  public void setImports(List<Import> imports) {
    this.imports = imports;
  }

  public List<Interface> getInterfaces() {
    return interfaces;
  }

  public void setInterfaces(List<Interface> interfaces) {
    this.interfaces = interfaces;
  }

  public List<Artifact> getGlobalArtifacts() {
    return globalArtifacts;
  }

  public void setGlobalArtifacts(List<Artifact> globalArtifacts) {
    this.globalArtifacts = globalArtifacts;
  }

  public void addNamespace(String prefix, String uri) {
    namespaceMap.put(prefix, uri);
  }
  
  public boolean containsNamespacePrefix(String prefix) {
    return namespaceMap.containsKey(prefix);
  }
  
  public String getNamespace(String prefix) {
    return namespaceMap.get(prefix);
  }
  
  public Map<String, String> getNamespaces() {
    return namespaceMap;
  }
  
  public String getTargetNamespace() {
    return targetNamespace;
  }

  public void setTargetNamespace(String targetNamespace) {
    this.targetNamespace = targetNamespace;
  }
  
  public void addProblem(String errorMessage, XMLStreamReader xtr) {
    problems.add(new Problem(errorMessage, xtr));
  }
  
  public void addProblem(String errorMessage, BaseElement element) {
    problems.add(new Problem(errorMessage, element));
  }
  
  public void addProblem(String errorMessage, GraphicInfo graphicInfo) {
    problems.add(new Problem(errorMessage, graphicInfo));
  }
  
  public List<Problem> getProblems() {
    return problems;
  }
  
  public void addWarning(String warningMessage, XMLStreamReader xtr) {
    warnings.add(new Warning(warningMessage, xtr));
  }
  
  public void addWarning(String warningMessage, BaseElement element) {
    warnings.add(new Warning(warningMessage, element));
  }
  
  public List<Warning> getWarning() {
    return warnings;
  }
  
	
  
  	public void addEventDefinition(EventDefinition eventDefinition) {
		if (eventDefinition != null
				&& StringUtils.isNotEmpty(eventDefinition.getId())) {
			eventDefintionMap.put(eventDefinition.getId(), eventDefinition);
		}
	}

	public EventDefinition getEventDefinition(String id) {
		return eventDefintionMap.get(id);
	}

	public boolean containsEventDefinitionId(String eventDefinitionId) {
		return eventDefintionMap.containsKey(eventDefinitionId);
	}
  
  	public Collection<EventDefinition> getEventDefinitions() {
		return eventDefintionMap.values();
	}

	public void setEventDefinitions(
			Collection<EventDefinition> eventDefinitonList) {
		if (eventDefinitonList != null) {
			eventDefintionMap.clear();
			for (EventDefinition eventDefinition : eventDefinitonList) {
				addEventDefinition(eventDefinition);
			}
		}
	}
	
	public void addCategory(Category category) {
		if (category != null
				&& StringUtils.isNotEmpty(category.getId())) {
			categoryMap.put(category.getId(), category);
		}
	}

	public Category getCategory(String id) {
		return categoryMap.get(id);
	}

	public boolean containsCategoryId(String categoryId) {
		return categoryMap.containsKey(categoryId);
	}
	

	public Collection<Category> getCategories() {
		return categoryMap.values();
	}

	public void setCategories(Collection<Category> categoryList) {
		if (categoryList != null) {
			categoryMap.clear();
			for (Category category : categoryList) {
				addCategory(category);
			}
		}
	}
	
	public List<CategoryValue> getAllCategoryValue() {
		List<CategoryValue> list = new ArrayList<CategoryValue>();
		for(Category category : getCategories()) {
			list.addAll(category.getCategoryValues());
		}
		return list;
	}
	
	//
	
	public void addGroup(Group group) {
		if (group != null
				&& StringUtils.isNotEmpty(group.getId())) {
			groupMap.put(group.getId(), group);
		}
	}

	public Group getGroup(String id) {
		return groupMap.get(id);
	}

	public boolean containsGroupId(String groupId) {
		return groupMap.containsKey(groupId);
	}
	

	public Collection<Group> getGroups() {
		return groupMap.values();
	}

	public void setGroups(Collection<Group> groupList) {
		if (groupList != null) {
			groupMap.clear();
			for (Group group : groupList) {
				addGroup(group);
			}
		}
	}
	
	
	

	public List<MessageFlow> getMessageFlows() {
		return messageFlows;
	}
	
	public void setMessageFlows(List<MessageFlow> messageFlows) {
		this.messageFlows = messageFlows;
	
	}
	
	public void deleteMessageFlowById(String idToDelete) {
		for (MessageFlow messageFlow : messageFlows) {
			if (messageFlow.getId().equals(idToDelete)) {
				messageFlows.remove(messageFlow);
			}
		}
	}

}
