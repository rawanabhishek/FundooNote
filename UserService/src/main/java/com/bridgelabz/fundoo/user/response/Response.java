/******************************************************************************
 
 *  Purpose: To create a Response Class Will send HTTP(Hyper test transfer 
 *            protocol) , Exception message and  data Object.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   21-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.response;

public class Response {
	
	private int statusCode;
	
	private String message;
	
	private Object data;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	
	
	
	public Response(int statusCode, String message, Object data) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}
	@Override
	public String toString() {
		return "Response [statusCode=" + statusCode + ", message=" + message + ", data=" + data + "]";
	}
	
}
