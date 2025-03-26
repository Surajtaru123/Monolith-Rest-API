package com.love.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceAlreadyExistsException(String s) {
        super(s);
    }
}
