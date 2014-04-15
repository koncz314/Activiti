package org.activiti.bpmn.converter.export;

import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.Expression;
import org.activiti.bpmn.model.FormalExpression;
import org.activiti.bpmn.model.ResourceAssignmentExpression;
import org.activiti.bpmn.model.ResourceParameterBinding;
import org.activiti.bpmn.model.ResourceRole;

public class ResourceRoleExport extends ExportHelper implements BpmnXMLConstants {
	public static void writeResourceRole(ResourceRole role, XMLStreamWriter xtw) throws Exception {
		if (role != null) {
			switch (role.getPerformerType()) {
			case PERFORMER:
				xtw.writeStartElement(ELEMENT_PERFORMER);
				break;
			case HUMAN_PERFORMER:
				xtw.writeStartElement(ELEMENT_HUMAN_PERFORMER);
				break;
			case POTENTIAL_OWNER:
				xtw.writeStartElement(ELEMENT_POTENTIAL_OWNER);
				break;
			default:
				break;
			}
			
			writeDefaultAttribute(ATTRIBUTE_ID, role.getId(), xtw);
			writeDefaultAttribute(ATTRIBUTE_NAME, role.getName(), xtw);
			writeElementWithText(ELEMENT_RESOURCE_REF, role.getResourceRef(), xtw);
			if (role.getResourceParameterBinding() != null) {
				for (ResourceParameterBinding binding : role.getResourceParameterBinding()) {
					writeResourceParameterBinding(binding, xtw);
				}
			}
			if (role.getResourceAssignmentExpression() != null) {
				writeResourceAssignmentExpression(role.getResourceAssignmentExpression(), xtw);
			}
			xtw.writeEndElement();
			
		}
	}
	
	public static void writeResourceParameterBinding(ResourceParameterBinding binding, XMLStreamWriter xtw) throws Exception {
		xtw.writeStartElement(ELEMENT_RESOURCE_PARAMETER_BINDING);
		writeDefaultAttribute(ATTRIBUTE_ID, binding.getId(), xtw);
		writeDefaultAttribute(ATTRIUBTE_PARAMETER_REF, binding.getParameterRef(), xtw);
		writeExpression(binding.getExpression(), xtw);
		xtw.writeEndElement();
	}
	
	public static void writeResourceAssignmentExpression(ResourceAssignmentExpression assignmentExpression, XMLStreamWriter xtw) throws Exception {
		xtw.writeStartElement(ELEMENT_RESOURCE_ASSIGNMENT_EXPRESSION);
		writeDefaultAttribute(ATTRIBUTE_ID, assignmentExpression.getId(), xtw);
		writeExpression(assignmentExpression.getExpression(), xtw);
		xtw.writeEndElement();
		
	}
	
	public static void writeExpression(Expression expression, XMLStreamWriter xtw) throws Exception {
		if (expression != null) {
			if (expression instanceof FormalExpression) {
				xtw.writeStartElement(ELEMENT_FORMAL_EXPRESSION);
			} else {
				xtw.writeStartElement(ELEMENT_EXPRESSION);
			}
			writeDefaultAttribute(ATTRIBUTE_ID, expression.getId(), xtw);
			if (expression instanceof FormalExpression) {
				writeDefaultAttribute(ATTRIBUTE_LANGUAGE, ((FormalExpression)expression).getLanguage(), xtw);
				writeDefaultAttribute(ATTRIBUTE_EVALUATES_TO_TYPE_REF, ((FormalExpression)expression).getEvaluatesToTypeRef(), xtw);
			}
			xtw.writeCData(expression.getExpressionValue());
			xtw.writeEndElement();
		}
	}
}
