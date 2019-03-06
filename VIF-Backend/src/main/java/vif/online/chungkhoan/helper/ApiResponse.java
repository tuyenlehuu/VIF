package vif.online.chungkhoan.helper;

public class ApiResponse {
	private int code;
	private Boolean status;
	private String errors;
	private Object data;
	
	public ApiResponse(int code, String errors, Object data) {
		super();
		this.code = code;
		this.errors = errors;
		this.data = data;
	}
	
	public ApiResponse(int code, String errors) {
		super();
		this.code = code;
		this.errors = errors;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
