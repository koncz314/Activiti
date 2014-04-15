package org.activiti.bpmn.model;

public class Rendering extends BaseElement {

	@Override
	public Rendering clone() {
		Rendering rendering = new Rendering();
		rendering.setValues(this);
		return rendering;
	}

}
