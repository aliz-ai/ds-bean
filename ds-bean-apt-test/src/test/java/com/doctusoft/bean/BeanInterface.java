package com.doctusoft.bean;

import com.doctusoft.Property;

public interface BeanInterface {
	
	@Property
	public String getStringAttribute();
	public void setStringAttribute(String stringAttribute);

	@Property(readonly=true)
	public int getIntAttribute();
	
	@Property
	public boolean isBooleanAttribute();
	public void setBooleanAttribute(boolean booleanAttribute);

}
