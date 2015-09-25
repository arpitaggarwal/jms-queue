package com.xebia.domain;

import java.io.Serializable;

public final class Response implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String result;

	public Response(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

}
