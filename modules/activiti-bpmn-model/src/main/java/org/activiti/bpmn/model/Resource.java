package org.activiti.bpmn.model;

import java.util.ArrayList;
import java.util.List;

public class Resource extends BaseElement {
	protected String id;
	protected String name;
	protected List<ResourceParameter> resourceParameters = new ArrayList<ResourceParameter>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ResourceParameter> getResourceParameters() {
		return resourceParameters;
	}

	public void setResourceParameters(List<ResourceParameter> resourceParameters) {
		this.resourceParameters = resourceParameters;
	}

	@Override
	public Resource clone() {
		Resource resource = new Resource();
		resource.setValues(this);
		return resource;
	}

	public void setValues(Resource otherResource) {
		super.setValues(otherResource);
		setId(otherResource.getId());
		setName(otherResource.getName());
		if (otherResource.getResourceParameters() == null) {
			resourceParameters = null;
		} else {
			resourceParameters = new ArrayList<ResourceParameter>();
			for (ResourceParameter parameter : otherResource.getResourceParameters()) {
				resourceParameters.add(parameter.clone());
			}
		}
	}
}
