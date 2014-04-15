package org.activiti.bpmn.model;

import hu.clickandlike.bpmn.model.interfaces.PerformerType;

import java.util.ArrayList;
import java.util.List;

public class ResourceRole extends BaseElement {
	protected String name;
	protected String resourceRef;
	protected List<ResourceParameterBinding> resourceParameterBindings;
	protected ResourceAssignmentExpression resourceAssignmentExpression;
	protected PerformerType performerType;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceRef() {
		return resourceRef;
	}

	public void setResourceRef(String resourceRef) {
		this.resourceRef = resourceRef;
		resourceAssignmentExpression = null;
	}
	public List<ResourceParameterBinding> getResourceParameterBinding() {
		return resourceParameterBindings;
	}

	public void setResourceParameterBinding(
			List<ResourceParameterBinding> resourceParameterBinding) {
		this.resourceParameterBindings = resourceParameterBinding;
		if (resourceParameterBinding != null) {
			resourceAssignmentExpression = null;
		}
	}

	public void addResourceParameterBinding(ResourceParameterBinding binding) {
		if (binding != null) {
			if (resourceParameterBindings == null) {
				resourceParameterBindings = new ArrayList<ResourceParameterBinding>();
			}
			resourceAssignmentExpression = null;
			resourceParameterBindings.add(binding);
		}
	}
	
	public void removeResourceParameterBinding(ResourceParameterBinding binding) {
		if (resourceParameterBindings != null) {
			resourceParameterBindings.remove(binding);
		}
		
	}
	
	public ResourceAssignmentExpression getResourceAssignmentExpression() {
		return resourceAssignmentExpression;
	}

	public void setResourceAssignmentExpression(
			ResourceAssignmentExpression resourceAssignmentExpression) {
		this.resourceAssignmentExpression = resourceAssignmentExpression;
		if (resourceAssignmentExpression != null) {
			resourceParameterBindings = null;
			resourceRef = null;
		}
	}

	
	
	public PerformerType getPerformerType() {
		return performerType;
	}

	public void setPerformerType(PerformerType performerType) {
		this.performerType = performerType;
	}

	@Override
	public ResourceRole clone() {
		ResourceRole newRole = new ResourceRole();
		newRole.setValues(this);
		return newRole;
	}

	public void setValues(ResourceRole otherRole) {
		super.setValues(otherRole);
		setName(otherRole.getName());
		setResourceRef(otherRole.getResourceRef());
		setPerformerType(otherRole.getPerformerType());
		if (otherRole.getResourceAssignmentExpression() != null) {
			setResourceAssignmentExpression(otherRole.getResourceAssignmentExpression().clone());
		}
		if (otherRole.getResourceParameterBinding() != null) {
			resourceParameterBindings = new ArrayList<ResourceParameterBinding>();
			for (ResourceParameterBinding binding : otherRole.getResourceParameterBinding()) {
				resourceParameterBindings.add(binding.clone());
			}
		}
	}
	
}
