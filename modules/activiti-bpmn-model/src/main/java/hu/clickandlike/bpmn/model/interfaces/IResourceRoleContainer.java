package hu.clickandlike.bpmn.model.interfaces;

import java.util.Collection;

import org.activiti.bpmn.model.ResourceRole;

public interface IResourceRoleContainer {
	public void addResourceRole(ResourceRole role);

	public boolean containsResourceRole(ResourceRole role);

	public ResourceRole getResourceRole(String id);

	public Collection<ResourceRole> getAllResourceRoles();

	public Collection<String> getResourceRoleIds();
}
