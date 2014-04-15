package hu.clickandlike.bpmn.model.interfaces;

import java.util.List;

import org.activiti.bpmn.model.ResourceRole;

public interface IResourceRoleContainer {
	public void setResourceRoles(List<ResourceRole> roleList);

	public List<ResourceRole> getResourceRoles();

}
