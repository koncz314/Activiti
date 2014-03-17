package org.activiti.bpmn.model;

import java.util.ArrayList;
import java.util.List;

public class CatchEvent extends Event {

	protected List<DataOutput> dataOutputs = new ArrayList<DataOutput>();
	protected List<DataAssociation> dataOutputAssociations = new ArrayList<DataAssociation>();
	protected DataOutputSet dataOutputSet = null;	
	
	public List<DataOutput> getDataOutputs() {
		return dataOutputs;
	}


	public void setDataOutputs(List<DataOutput> dataOutputs) {
		this.dataOutputs = dataOutputs;
	}


	public List<DataAssociation> getDataOutputAssociations() {
		return dataOutputAssociations;
	}


	public void setDataOutputAssociations(
			List<DataAssociation> dataOutputAssociations) {
		this.dataOutputAssociations = dataOutputAssociations;
	}


	public DataOutputSet getDataOutputSet() {
		return dataOutputSet;
	}


	public void setDataOutputSet(DataOutputSet dataOutputSet) {
		this.dataOutputSet = dataOutputSet;
	}

	
	public void setValues(CatchEvent other) {
		super.setValues(other);
		dataOutputs = new ArrayList<DataOutput>();
		for (DataOutput data : other.getDataOutputs()) {
			dataOutputs.add(data.clone());
		}
		dataOutputAssociations = new ArrayList<DataAssociation>();
		for (DataAssociation data : other.getDataOutputAssociations()) {
			dataOutputAssociations.add(data.clone());
		}
		if (other.getDataOutputSet() != null) {
			setDataOutputSet(other.getDataOutputSet().clone());
		}
		
		
		
	}

	@Override
	public CatchEvent clone() {
		CatchEvent clone = new CatchEvent();
		clone.setValues(this);
		return clone;
	}

}
