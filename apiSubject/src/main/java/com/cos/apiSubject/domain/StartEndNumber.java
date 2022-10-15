package com.cos.apiSubject.domain;

import lombok.Data;

@Data
public class StartEndNumber {

	private String start;
	private String end;
	private String result;
	
	public StartEndNumber() {}
	
	public StartEndNumber(String result) {
		this.result=result;
	}
}
