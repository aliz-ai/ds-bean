package com.doctusoft.bean;

/**
 * A wrapper for checked exceptions inside method references generated with APT.
 * 
 * We cannot declare checked exceptions on the generated apply methods because they override {@link ClassMethodReference#apply(Object, Object...)},
 * and we cannot extend the throws clause when overriding.  
 */
public class ReferencedInvocationException extends RuntimeException {
	
	public ReferencedInvocationException(Throwable cause) {
		super(cause);
	}

}
