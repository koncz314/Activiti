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
package org.activiti.bpmn.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.converter.child.BaseChildElementParser;
import org.activiti.bpmn.converter.export.ActivitiListenerExport;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataInput;
import org.activiti.bpmn.model.DataInputSet;
import org.activiti.bpmn.model.DataOutput;
import org.activiti.bpmn.model.DataOutputSet;
import org.activiti.bpmn.model.DataState;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.FormValue;
import org.activiti.bpmn.model.Gateway;
import org.activiti.bpmn.model.IOSpecification;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Property;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.TerminateEventDefinition;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.UserTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tijs Rademakers
 */
public abstract class BaseBpmnXMLConverter implements BpmnXMLConstants {

  protected static final Logger LOGGER = LoggerFactory.getLogger(BaseBpmnXMLConverter.class);
  
  protected BpmnModel model;
  protected Process activeProcess;
  protected Map<String, BaseChildElementParser> childElementParsers = new HashMap<String, BaseChildElementParser>();
  
  protected boolean didWriteExtensionStartElement = false;
  
  protected static final List<ExtensionAttribute> defaultElementAttributes = Arrays.asList(
      new ExtensionAttribute(ATTRIBUTE_ID),
      new ExtensionAttribute(ATTRIBUTE_NAME)
  );
  
  protected static final List<ExtensionAttribute> defaultActivityAttributes = Arrays.asList(
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_ACTIVITY_ASYNCHRONOUS), 
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_ACTIVITY_EXCLUSIVE), 
      new ExtensionAttribute(ATTRIBUTE_DEFAULT), 
      new ExtensionAttribute(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_ACTIVITY_ISFORCOMPENSATION)
  );
  
  public void convertToBpmnModel(XMLStreamReader xtr, BpmnModel model, Process activeProcess, 
      List<SubProcess> activeSubProcessList) throws Exception {
    
    this.model = model;
    this.activeProcess = activeProcess;
    
    String elementId = xtr.getAttributeValue(null, ATTRIBUTE_ID);
    String elementName = xtr.getAttributeValue(null, ATTRIBUTE_NAME);
    boolean async = parseAsync(xtr);
    boolean notExclusive = parseNotExclusive(xtr);
    String defaultFlow = xtr.getAttributeValue(null, ATTRIBUTE_DEFAULT);
    boolean isForCompensation = parseForCompensation(xtr);
    
    BaseElement parsedElement = convertXMLToElement(xtr);
    
    if (parsedElement instanceof Artifact) {
      Artifact currentArtifact = (Artifact) parsedElement;
      currentArtifact.setId(elementId);

      if (isInSubProcess(activeSubProcessList)) {
        final SubProcess currentSubProcess = activeSubProcessList.get(activeSubProcessList.size() - 2);
        currentSubProcess.addArtifact(currentArtifact);

      } else {
        this.activeProcess.addArtifact(currentArtifact);
      }
    }
    
    if(parsedElement instanceof FlowElement) {
      
      FlowElement currentFlowElement = (FlowElement) parsedElement;
      currentFlowElement.setId(elementId);
      currentFlowElement.setName(elementName);
      
      if(currentFlowElement instanceof Activity) {
        
        Activity activity = (Activity) currentFlowElement;
        activity.setAsynchronous(async);
        activity.setNotExclusive(notExclusive);
        activity.setForCompensation(isForCompensation);
        if(StringUtils.isNotEmpty(defaultFlow)) {
          activity.setDefaultFlow(defaultFlow);
        }
      }
      
      if(currentFlowElement instanceof Gateway) {
        if(StringUtils.isNotEmpty(defaultFlow)) {
          ((Gateway) currentFlowElement).setDefaultFlow(defaultFlow);
        }
      }
      
      if (activeSubProcessList.size() > 0) {
        activeSubProcessList.get(activeSubProcessList.size() - 1).addFlowElement(currentFlowElement);
      } else {
        this.activeProcess.addFlowElement(currentFlowElement);
      }
    }
  }
  
  public void convertToXML(XMLStreamWriter xtw, BaseElement baseElement, BpmnModel model) throws Exception {
    
    this.model = model;
    xtw.writeStartElement(getXMLElementName());
    didWriteExtensionStartElement = false;
    writeDefaultAttribute(ATTRIBUTE_ID, baseElement.getId(), xtw);
    
    writeAdditionalAttributes(baseElement, xtw);

    	//BpmnXMLUtil.writeCustomAttributes(baseElement.getAttributes().values(), xtw, defaultElementAttributes);
    
    if (baseElement instanceof Gateway) {
        final Gateway gateway = (Gateway) baseElement;
        
        if (StringUtils.isNotEmpty(gateway.getDefaultFlow())) {
          FlowElement defaultFlowElement = model.getFlowElement(gateway.getDefaultFlow());
          if (defaultFlowElement != null && defaultFlowElement instanceof SequenceFlow) {
            writeDefaultAttribute(ATTRIBUTE_DEFAULT, gateway.getDefaultFlow(), xtw);
          }
        }
      }
    
    writeElementWithText(ELEMENT_DOCUMENTATION, baseElement.getDocumentation(), xtw);
    
    
    writeExtensionChildElements(baseElement, xtw);
    didWriteExtensionStartElement = writeListeners(baseElement, xtw);
    didWriteExtensionStartElement = BpmnXMLUtil.writeExtensionElements(baseElement, didWriteExtensionStartElement, xtw);
    
    if (didWriteExtensionStartElement) {
      xtw.writeEndElement();
    }
    
    
    
    writeAdditionalChildElements(baseElement, xtw);
    
    xtw.writeEndElement();
  }
  
  protected abstract BaseElement convertXMLToElement(XMLStreamReader xtr) throws Exception;
  
  protected abstract String getXMLElementName();
  
  protected abstract void writeAdditionalAttributes(BaseElement element, XMLStreamWriter xtw) throws Exception;
  
  protected abstract void writeExtensionChildElements(BaseElement element, XMLStreamWriter xtw) throws Exception;
  
  protected abstract void writeAdditionalChildElements(BaseElement element, XMLStreamWriter xtw) throws Exception;
  
  // To BpmnModel converter convenience methods
  
  protected void parseChildElements(String elementName, BaseElement parentElement, XMLStreamReader xtr) throws Exception {
    Map<String, BaseChildElementParser> childParsers = new HashMap<String, BaseChildElementParser>();
    if (childElementParsers != null) {
      childParsers.putAll(childElementParsers);
    }
    BpmnXMLUtil.parseChildElements(elementName, parentElement, xtr, childParsers, model);
  }
  
  @SuppressWarnings("unchecked")
  protected ExtensionElement parseExtensionElement(XMLStreamReader xtr) throws Exception {
    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName(xtr.getLocalName());
    if (StringUtils.isNotEmpty(xtr.getNamespaceURI())) {
      extensionElement.setNamespace(xtr.getNamespaceURI());
    }
    if (StringUtils.isNotEmpty(xtr.getPrefix())) {
      extensionElement.setNamespacePrefix(xtr.getPrefix());
    }

    BpmnXMLUtil.addCustomAttributes(xtr, extensionElement, defaultElementAttributes);

    boolean readyWithExtensionElement = false;
    while (readyWithExtensionElement == false && xtr.hasNext()) {
      xtr.next();
      if (xtr.isCharacters()) {
        if (StringUtils.isNotEmpty(xtr.getText().trim())) {
          extensionElement.setElementText(xtr.getText().trim());
        }
      } else if (xtr.isStartElement()) {
        ExtensionElement childExtensionElement = parseExtensionElement(xtr);
        extensionElement.addChildElement(childExtensionElement);
      } else if (xtr.isEndElement() && extensionElement.getName().equalsIgnoreCase(xtr.getLocalName())) {
        readyWithExtensionElement = true;
      }
    }
    return extensionElement;
  }

  protected boolean parseAsync(XMLStreamReader xtr) {
    boolean async = false;
    String asyncString = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_ACTIVITY_ASYNCHRONOUS);
    if (ATTRIBUTE_VALUE_TRUE.equalsIgnoreCase(asyncString)) {
      async = true;
    }
    return async;
  }
  
  protected boolean parseNotExclusive(XMLStreamReader xtr) {
    boolean notExclusive = false;
    String exclusiveString = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_ACTIVITY_EXCLUSIVE);
    if (ATTRIBUTE_VALUE_FALSE.equalsIgnoreCase(exclusiveString)) {
      notExclusive = true;
    }
    return notExclusive;
  }
  
  protected boolean parseForCompensation(XMLStreamReader xtr) {
    boolean isForCompensation = false;
    String compensationString = xtr.getAttributeValue(null, ATTRIBUTE_ACTIVITY_ISFORCOMPENSATION);
    if (ATTRIBUTE_VALUE_TRUE.equalsIgnoreCase(compensationString)) {
      isForCompensation = true;
    }
    return isForCompensation;
  }
  
  protected List<String> parseDelimitedList(String expression) {
    return BpmnXMLUtil.parseDelimitedList(expression);
  }
  
  private boolean isInSubProcess(List<SubProcess> subProcessList) {
    if(subProcessList.size() > 1) {
      return true;
    } else {
      return false;
    }
  }
  
  // To XML converter convenience methods
  
  protected String convertToDelimitedString(List<String> stringList) {
    return BpmnXMLUtil.convertToDelimitedString(stringList);
  }
  
  protected void writeFormProperties(FlowElement flowElement, XMLStreamWriter xtw) throws Exception {
    
    List<FormProperty> propertyList = null;
    if (flowElement instanceof UserTask) {
      propertyList = ((UserTask) flowElement).getFormProperties();
    } else if (flowElement instanceof StartEvent) {
      propertyList = ((StartEvent) flowElement).getFormProperties();
    }
    
    if (propertyList != null) {
    
      for (FormProperty property : propertyList) {
        
        if (StringUtils.isNotEmpty(property.getId())) {
          
          if (didWriteExtensionStartElement == false) { 
            xtw.writeStartElement(ELEMENT_EXTENSIONS);
            didWriteExtensionStartElement = true;
          }
          
          xtw.writeStartElement(ACTIVITI_EXTENSIONS_PREFIX, ELEMENT_FORMPROPERTY, ACTIVITI_EXTENSIONS_NAMESPACE);
          writeDefaultAttribute(ATTRIBUTE_FORM_ID, property.getId(), xtw);
          
          writeDefaultAttribute(ATTRIBUTE_FORM_NAME, property.getName(), xtw);
          writeDefaultAttribute(ATTRIBUTE_FORM_TYPE, property.getType(), xtw);
          writeDefaultAttribute(ATTRIBUTE_FORM_EXPRESSION, property.getExpression(), xtw);
          writeDefaultAttribute(ATTRIBUTE_FORM_VARIABLE, property.getVariable(), xtw);
          writeDefaultAttribute(ATTRIBUTE_FORM_DEFAULT, property.getDefaultExpression(), xtw);
          writeDefaultAttribute(ATTRIBUTE_FORM_DATEPATTERN, property.getDatePattern(), xtw);
          if (property.isReadable() == false) {
            writeDefaultAttribute(ATTRIBUTE_FORM_READABLE, ATTRIBUTE_VALUE_FALSE, xtw);
          }
          if (property.isWriteable() == false) {
            writeDefaultAttribute(ATTRIBUTE_FORM_WRITABLE, ATTRIBUTE_VALUE_FALSE, xtw);
          }
          if (property.isRequired()) {
            writeDefaultAttribute(ATTRIBUTE_FORM_REQUIRED, ATTRIBUTE_VALUE_TRUE, xtw);
          }
          
          for (FormValue formValue : property.getFormValues()) {
            if (StringUtils.isNotEmpty(formValue.getId())) {
              xtw.writeStartElement(ACTIVITI_EXTENSIONS_PREFIX, ELEMENT_VALUE, ACTIVITI_EXTENSIONS_NAMESPACE);
              xtw.writeAttribute(ATTRIBUTE_ID, formValue.getId());
              xtw.writeAttribute(ATTRIBUTE_NAME, formValue.getName());
              xtw.writeEndElement();
            }
          }
          
          xtw.writeEndElement();
        }
      }
    }
  }
  
  protected boolean writeListeners(BaseElement element, XMLStreamWriter xtw) throws Exception {
    return ActivitiListenerExport.writeListeners(element, didWriteExtensionStartElement, xtw);
  }
  
  protected void writeEventDefinitions(Event parentEvent, List<EventDefinition> eventDefinitions, XMLStreamWriter xtw) throws Exception {
    for (EventDefinition eventDefinition : eventDefinitions) {
      if (eventDefinition instanceof TimerEventDefinition) {
        writeTimerDefinition(parentEvent, (TimerEventDefinition) eventDefinition, xtw);
      } else if (eventDefinition instanceof SignalEventDefinition) {
        writeSignalDefinition(parentEvent, (SignalEventDefinition) eventDefinition, xtw);
      } else if (eventDefinition instanceof MessageEventDefinition) {
        writeMessageDefinition(parentEvent, (MessageEventDefinition) eventDefinition, xtw);
      } else if (eventDefinition instanceof ErrorEventDefinition) {
        writeErrorDefinition(parentEvent, (ErrorEventDefinition) eventDefinition, xtw);
      } else if (eventDefinition instanceof TerminateEventDefinition) {
        writeTerminateDefinition(parentEvent, (TerminateEventDefinition) eventDefinition, xtw);
      }
    }
  }
  
  protected void writeEventDefinitionRefs(Event parentEvent, XMLStreamWriter xtw) throws Exception {
	  for (String ref : parentEvent.getEventDefinitionsRefs()) {
		  writeElementWithText("eventDefinitionRef", ref, xtw);
	  }
  }
  
  protected void writeTimerDefinition(Event parentEvent, TimerEventDefinition timerDefinition, XMLStreamWriter xtw) throws Exception {
    xtw.writeStartElement(ELEMENT_EVENT_TIMERDEFINITION);
    if (StringUtils.isNotEmpty(timerDefinition.getId())) {
    	writeDefaultAttribute(ATTRIBUTE_ID, timerDefinition.getId(), xtw);
    }
    boolean didWriteExtensionStartElement = BpmnXMLUtil.writeExtensionElements(timerDefinition, false, xtw);
    if (didWriteExtensionStartElement) {
      xtw.writeEndElement();
    }
    if (StringUtils.isNotEmpty(timerDefinition.getTimeDate())) {
      xtw.writeStartElement(ATTRIBUTE_TIMER_DATE);
      xtw.writeCharacters(timerDefinition.getTimeDate());
      xtw.writeEndElement();
      
    } else if (StringUtils.isNotEmpty(timerDefinition.getTimeCycle())) {
      xtw.writeStartElement(ATTRIBUTE_TIMER_CYCLE);
      xtw.writeCharacters(timerDefinition.getTimeCycle());
      xtw.writeEndElement();
      
    } else if (StringUtils.isNotEmpty(timerDefinition.getTimeDuration())) {
      xtw.writeStartElement(ATTRIBUTE_TIMER_DURATION);
      xtw.writeCharacters(timerDefinition.getTimeDuration());
      xtw.writeEndElement();
    }
    
    xtw.writeEndElement();
  }
  
  protected void writeSignalDefinition(Event parentEvent, SignalEventDefinition signalDefinition, XMLStreamWriter xtw) throws Exception {
    xtw.writeStartElement(ELEMENT_EVENT_SIGNALDEFINITION);
    writeDefaultAttribute(ATTRIBUTE_SIGNAL_REF, signalDefinition.getSignalRef(), xtw);
    if (StringUtils.isNotEmpty(signalDefinition.getId())) {
    	writeDefaultAttribute(ATTRIBUTE_ID, signalDefinition.getId(), xtw);
    }
    if (parentEvent instanceof ThrowEvent && signalDefinition.isAsync()) {
      BpmnXMLUtil.writeQualifiedAttribute(ATTRIBUTE_ACTIVITY_ASYNCHRONOUS, "true", xtw);
    }
    boolean didWriteExtensionStartElement = BpmnXMLUtil.writeExtensionElements(signalDefinition, false, xtw);
    if (didWriteExtensionStartElement) {
      xtw.writeEndElement();
    }
    xtw.writeEndElement();
  }
  
  protected void writeMessageDefinition(Event parentEvent, MessageEventDefinition messageDefinition, XMLStreamWriter xtw) throws Exception {
    xtw.writeStartElement(ELEMENT_EVENT_MESSAGEDEFINITION);
    
    if (StringUtils.isNotEmpty(messageDefinition.getId())) {
    	writeDefaultAttribute(ATTRIBUTE_ID, messageDefinition.getId(), xtw);
    }
    String messageRef = messageDefinition.getMessageRef();
    /*if (StringUtils.isNotEmpty(messageRef)) {
      // remove the namespace from the message id if set
      if (messageRef.startsWith(model.getTargetNamespace())) {
        messageRef = messageRef.replace(model.getTargetNamespace(), "");
        messageRef = messageRef.replaceFirst(":", "");
      } else {
        for (String prefix : model.getNamespaces().keySet()) {
          String namespace = model.getNamespace(prefix);
          if (messageRef.startsWith(namespace)) {
            messageRef = messageRef.replace(model.getTargetNamespace(), "");
            messageRef = prefix + messageRef;
          }
        }
      }
    }*/
    writeDefaultAttribute(ATTRIBUTE_MESSAGE_REF, messageRef, xtw);
    if (StringUtils.isNotEmpty(messageDefinition.getOperationRef())) {
    	xtw.writeStartElement(ATTRIBUTE_TASK_OPERATION_REF);
    	xtw.writeCharacters(messageDefinition.getOperationRef());
    	xtw.writeEndElement();
    }
    boolean didWriteExtensionStartElement = BpmnXMLUtil.writeExtensionElements(messageDefinition, false, xtw);
    if (didWriteExtensionStartElement) {
      xtw.writeEndElement();
    }
    xtw.writeEndElement();
  }
  
  protected void writeErrorDefinition(Event parentEvent, ErrorEventDefinition errorDefinition, XMLStreamWriter xtw) throws Exception {
    xtw.writeStartElement(ELEMENT_EVENT_ERRORDEFINITION);
    if (StringUtils.isNotEmpty(errorDefinition.getId())) {
    	writeDefaultAttribute(ATTRIBUTE_ID, errorDefinition.getId(), xtw);
    }
    writeDefaultAttribute(ATTRIBUTE_ERROR_REF, errorDefinition.getErrorRef(), xtw);
    boolean didWriteExtensionStartElement = BpmnXMLUtil.writeExtensionElements(errorDefinition, false, xtw);
    if (didWriteExtensionStartElement) {
      xtw.writeEndElement();
    }
    xtw.writeEndElement();
  }
  
  protected void writeTerminateDefinition(Event parentEvent, TerminateEventDefinition terminateDefinition, XMLStreamWriter xtw) throws Exception {
    xtw.writeStartElement(ELEMENT_EVENT_TERMINATEDEFINITION);
    if (StringUtils.isNotEmpty(terminateDefinition.getId())) {
    	writeDefaultAttribute(ATTRIBUTE_ID, terminateDefinition.getId(), xtw);
    }
    boolean didWriteExtensionStartElement = BpmnXMLUtil.writeExtensionElements(terminateDefinition, false, xtw);
    if (didWriteExtensionStartElement) {
      xtw.writeEndElement();
    }
    xtw.writeEndElement();
  }
  
  protected void writeDataInput(DataInput input, XMLStreamWriter xtw) throws Exception {
	  xtw.writeEmptyElement(ELEMENT_DATA_INPUT);
	  writeDefaultAttribute(ATTRIBUTE_ID, input.getId(), xtw);
	  writeDefaultAttribute(ATTRIBUTE_NAME, input.getName(), xtw);
	  writeDefaultAttribute(ATTRIBUTE_DATA_SUBJECT_REF, input.getItemSubjectRef(), xtw);
	  writeDefaultAttribute(ATTRIBUTE_ISCOLLECTION, String.valueOf(input.isCollection()), xtw);
  }
  
  protected void writeDataOutput(DataOutput output, XMLStreamWriter xtw) throws Exception {
	  xtw.writeEmptyElement(ELEMENT_DATA_OUTPUT);
	  writeDefaultAttribute(ATTRIBUTE_ID, output.getId(), xtw);
	  writeDefaultAttribute(ATTRIBUTE_NAME, output.getName(), xtw);
	  writeDefaultAttribute(ATTRIBUTE_DATA_SUBJECT_REF, output.getItemSubjectRef(), xtw);
	  writeDefaultAttribute(ATTRIBUTE_ISCOLLECTION, String.valueOf(output.isCollection()), xtw);
  }
  
  protected void writeDataInputSet(DataInputSet set, XMLStreamWriter xtw) throws Exception {
	  xtw.writeStartElement(ELEMENT_DATA_INPUTSET);
	  for (String value : set.getDataInputRefs()) {
		  writeDataInputRefs(value, xtw);
	  }
	  xtw.writeEndElement();
  }
  
  protected void writeDataOutputSet(DataOutputSet set, XMLStreamWriter xtw) throws Exception {
	  xtw.writeStartElement(ELEMENT_DATA_OUTPUTSET);
	  for (String value : set.getDataOutputRefs()) {
		  writeDataOutputRefs(value, xtw);
	  }
	  xtw.writeEndElement();
  }
  
  protected void writeIOSpecification(IOSpecification iospec, XMLStreamWriter xtw) throws Exception {
	  if (iospec != null) {
		  xtw.writeStartElement(ELEMENT_IOSPECIFICATION);
		  if (iospec.getDataInputs() != null && iospec.getDataInputs().size() > 0) {
			  for (DataInput data : iospec.getDataInputs()) {
				  writeDataInput(data, xtw);
			  }
		  }
		  if (iospec.getDataOutputs() != null && iospec.getDataOutputs().size() > 0) {
			  for (DataOutput data : iospec.getDataOutputs()) {
				  writeDataOutput(data, xtw);
			  }
		  }
		  writeDataInputSet(iospec.getDataInputSet(), xtw);
		  writeDataOutputSet(iospec.getDataOutputSet(), xtw);
		  xtw.writeEndElement();
	  }
  }
  
  protected void writeDataInputRefs(String value, XMLStreamWriter xtw) throws Exception {
	  writeElementWithText(ELEMENT_DATA_INPUT_REFS, value, xtw);
  }
  
  protected void writeDataOutputRefs(String value, XMLStreamWriter xtw) throws Exception {
	  writeElementWithText(ELEMENT_DATA_OUTPUT_REFS, value, xtw);
  }
  
  protected void writeDefaultAttribute(String attributeName, String value, XMLStreamWriter xtw) throws Exception {
    BpmnXMLUtil.writeDefaultAttribute(attributeName, value, xtw);
  }
  
  protected void writeQualifiedAttribute(String attributeName, String value, XMLStreamWriter xtw) throws Exception {
    BpmnXMLUtil.writeQualifiedAttribute(attributeName, value, xtw);
  }
  
  protected void writeElementWithText(String elementName, String value, XMLStreamWriter xtw) throws Exception {
	  BpmnXMLUtil.writeElementWithText(elementName, value, xtw);
  }
  
  protected void writeProperty(Property prop, XMLStreamWriter xtw) throws Exception {
	  if (prop != null) {
		  boolean emptyDataState = prop.getDataState() == null;
		  if (emptyDataState) {
			  xtw.writeEmptyElement("property");
		  } else {
			  xtw.writeStartElement("property");
		  }
		  writeDefaultAttribute(ATTRIBUTE_NAME, prop.getName(), xtw);
		  writeDefaultAttribute(ATTRIBUTE_DATA_SUBJECT_REF, prop.getItemSubjectRef(), xtw);
		  if (!emptyDataState) {
			  writeDataState(prop.getDataState(), xtw);
			  xtw.writeEndElement();
		  }
	  }
  }
  
  protected void writeDataState(DataState state, XMLStreamWriter xtw) {
	  
  }
}
