package org.activiti.bpmn.converter.child;

import hu.clickandlike.bpmn.model.interfaces.IResourceRoleContainer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Expression;
import org.activiti.bpmn.model.FormalExpression;
import org.activiti.bpmn.model.ResourceAssignmentExpression;
import org.activiti.bpmn.model.ResourceParameterBinding;
import org.activiti.bpmn.model.ResourceRole;
import org.apache.commons.lang3.StringUtils;

public abstract class ResourceRoleParser extends BaseChildElementParser {

	@Override
	public abstract String getElementName();
	
	protected abstract ResourceRole getNewInstance();

	@Override
	public void parseChildElement(XMLStreamReader xtr,
			BaseElement parentElement, BpmnModel model) throws Exception {
		ResourceRole performer = getNewInstance();
		BpmnXMLUtil.addXMLLocation(performer, xtr);
		
		String id = xtr.getAttributeValue(null, ATTRIBUTE_ID);
		String name = xtr.getAttributeValue(null, ATTRIBUTE_NAME);
		
		if (StringUtils.isNotEmpty(id)) {
			performer.setId(id);
		}
		if (StringUtils.isNotEmpty(name)) {
			performer.setName(name);
		}
		
		boolean readyWithResourceRole = false;
		while (readyWithResourceRole == false && xtr.hasNext()) {
			xtr.next();
			if (xtr.isStartElement() && xtr.getLocalName().equals(ELEMENT_RESOURCE_REF)) {
				String resourceRef = xtr.getElementText();
				if (StringUtils.isNotEmpty(resourceRef)) {
					performer.setResourceRef(resourceRef);
				}
			} else if (xtr.isStartElement() && xtr.getLocalName().equals(ELEMENT_RESOURCE_PARAMETER_BINDING)) {
				ResourceParameterBinding binding = parseResourceParameterBinding(xtr);
				performer.addResourceParameterBinding(binding);
			} else if (xtr.isStartElement() && xtr.getLocalName().equals(ELEMENT_RESOURCE_ASSIGNMENT_EXPRESSION)) {
				ResourceAssignmentExpression assignmentExpression = parseResourceAssignmentExpression(xtr);
				performer.setResourceAssignmentExpression(assignmentExpression);
			} else if (xtr.isEndElement() && getElementName().equals(xtr.getLocalName())) {
				readyWithResourceRole = true;
			}
			
			
		}
		
		((IResourceRoleContainer)parentElement).addResourceRole(performer);

	}

	private ResourceParameterBinding parseResourceParameterBinding(XMLStreamReader xtr) throws XMLStreamException {
		ResourceParameterBinding binding = new ResourceParameterBinding();
		String id = xtr.getAttributeValue(null, ATTRIBUTE_ID);
		String parameterRef = xtr.getAttributeValue(null, ATTRIUBTE_PARAMETER_REF);
		
		if (StringUtils.isNotEmpty(id)) {
			binding.setId(id);
		}
		
		if (StringUtils.isNotEmpty(parameterRef)) {
			binding.setParameterRef(parameterRef);
		}
		
		boolean readyWithBinding = false;
		while (readyWithBinding == false && xtr.hasNext()) {
			xtr.next();
			if (xtr.isStartElement() && (xtr.getLocalName().equals(ELEMENT_EXPRESSION) || xtr.getLocalName().equals(ELEMENT_FORMAL_EXPRESSION))) {
				Expression expression = parseExpression(xtr);
				binding.setExpression(expression);
			} else if (xtr.isEndElement() && xtr.getLocalName().equals(ELEMENT_RESOURCE_PARAMETER_BINDING)) {
				readyWithBinding = true;
			}
		}
		
		return binding;
	}
	
	private ResourceAssignmentExpression parseResourceAssignmentExpression(XMLStreamReader xtr) throws XMLStreamException {
		ResourceAssignmentExpression assigmentExpression = new ResourceAssignmentExpression();
		
		String id = xtr.getAttributeValue(null, ATTRIBUTE_ID);
		
		if (StringUtils.isNotEmpty(id)) {
			assigmentExpression.setId(id);
		}
		
		boolean readyWithAssigmentExpression = false;
		while (readyWithAssigmentExpression == false && xtr.hasNext()) {
			xtr.next();
			if (xtr.isStartElement() && (xtr.getLocalName().equals(ELEMENT_EXPRESSION) || xtr.getLocalName().equals(ELEMENT_FORMAL_EXPRESSION))) {
				Expression expression = parseExpression(xtr);
				assigmentExpression.setExpression(expression);
			} else if (xtr.isEndElement() && xtr.getLocalName().equals(ELEMENT_RESOURCE_ASSIGNMENT_EXPRESSION)) {
				readyWithAssigmentExpression = true;
			}
		}
		return assigmentExpression;
	}
	
	private Expression parseExpression (XMLStreamReader xtr) throws XMLStreamException {
		Expression expression = null;
		
		
		if (xtr.getLocalName().equals(ELEMENT_EXPRESSION)) {
			expression = new Expression();
		} else {
			expression = new FormalExpression();
			String language = xtr.getAttributeValue(null, ATTRIBUTE_LANGUAGE);
			String evaluatesToTypeRef = xtr.getAttributeValue(null, ATTRIBUTE_EVALUATES_TO_TYPE_REF);
			
			if (StringUtils.isNotEmpty(language)) {
				((FormalExpression)expression).setLanguage(language);
			}
			
			if (StringUtils.isNotEmpty(evaluatesToTypeRef)) {
				((FormalExpression)expression).setEvaluatesToTypeRef(evaluatesToTypeRef);
			}
			
		}
		String expressionId = xtr.getAttributeValue(null, ATTRIBUTE_ID);
		String expressionValue = xtr.getElementText();
		
		if (StringUtils.isNotEmpty(expressionId)) {
			expression.setId(expressionId);
		}
		if (StringUtils.isNotEmpty(expressionValue)) {
			expression.setExpressionValue(expressionValue);
		}
		
		return expression;
	}
}
