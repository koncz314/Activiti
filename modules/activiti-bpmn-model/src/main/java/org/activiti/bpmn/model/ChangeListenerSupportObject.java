package org.activiti.bpmn.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class ChangeListenerSupportObject {
	protected List<PropertyChangeListener> propertyChangeListeners = new ArrayList<PropertyChangeListener>();
	
	public List<PropertyChangeListener> getPropertyChangeListeners() {
		return propertyChangeListeners;
	}

	public void setPropertyChangeListeners(List<PropertyChangeListener> propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public void addListener(PropertyChangeListener listener) {
		propertyChangeListeners.add(listener);
	}
	
	public void removeListener(PropertyChangeListener listener) {
		propertyChangeListeners.remove(listener);
	}
	
	public void clearListeners() {
		propertyChangeListeners.clear();
	}
	
	public void firePropertyChangeEvent(String propertyName, Object oldValue, Object newValue) {
		PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		for (PropertyChangeListener listener : propertyChangeListeners) {
			listener.propertyChange(event);
			System.out.println("Property Fired to: " + listener + " - " + propertyName + " - "+ oldValue + " -> " + newValue );
		}
	}
}
