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
	
	private String status;
	private String message;
	private Object data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Response(String status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

}
