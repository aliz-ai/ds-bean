package com.doctusoft.common.core.bean;

import com.doctusoft.Attribute;

public interface BeanInterface {
	
	@Attribute
	public String getStringAttribute();
	public void setStringAttribute(String stringAttribute);

	@Attribute(readonly=true)
	public int getIntAttribute();
	
	@Attribute
	public boolean isBooleanAttribute();
	public void setBooleanAttribute(boolean booleanAttribute);

}
