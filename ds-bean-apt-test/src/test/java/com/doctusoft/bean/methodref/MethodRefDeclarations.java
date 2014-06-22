package com.doctusoft.bean.methodref;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.doctusoft.MethodRef;

/**
 * This tests whether APT generates proper code 
 */
public interface MethodRefDeclarations<T extends Comparable<T> & Serializable> {
	
	@MethodRef
	public List<String> listProcessor(List<Map<Integer, String>> input);
	
	@MethodRef
	public T simpleProvider();

}
