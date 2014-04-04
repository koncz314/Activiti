package hu.clickandlike.bpmn.model.interfaces;

public interface IData {
	String getId();
	void setId(String id);
	String getName();
	void setName(String name);
	String getItemSubjectRef() ;
	void setItemSubjectRef(String itemSubjectRef);
	boolean isCollection();
	void setCollection(boolean isCollection);
}
