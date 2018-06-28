package kr.ac.hansung.exception;

public class UserNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -4451685904438277749L;
	
	private long userId;
	
	public UserNotFoundException(long userId){
		this.userId = userId;
	}
	public long getUserId(){
		return userId;
	}
}
