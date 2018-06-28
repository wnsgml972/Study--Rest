package kr.ac.hansung.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import kr.ac.hansung.model.User;

@Service
public class UserService {
	
	private static final AtomicLong counter = new AtomicLong();
	//ªÛ»£ πË¡¶ «ÿ¡‹ æÍ∞°!!
	
	private static List<User> users;
	
	public UserService() {
		
		users = new ArrayList<User>();
		
		users.add(new User(counter.incrementAndGet(), "Sam", 30, 70000));
		users.add(new User(counter.incrementAndGet(), "Tom", 40, 50000));
		users.add(new User(counter.incrementAndGet(), "Jeroma", 45, 30000));
		users.add(new User(counter.incrementAndGet(), "Silvia", 50, 40000));
	}
	
	public List<User> findAllUser(){
		return users;
	}
	
	public User findUserById(long id){
		for(User user : users){
			if(user.getId() == id)
				return user;
		}
		return null;
	}
	
	public User findUserByName(String name){
		for(User user : users){
			if(user.getName().equalsIgnoreCase(name))
				return user;
		}
		return null;
	}
	
	public void saveUser(User user){
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(User user){
		int index = users.indexOf(user);
		users.set(index, user);
	}
	
	public void deleteUserById(long id){
		
		for(Iterator<User> it = users.iterator(); it.hasNext();){
			User user = it.next();
			if(user.getId() == id)
				it.remove();
		}
	}
	public void deleteAllUsers(){
		users.clear();
	}
	
	
	public boolean isUserExist(User user){
		return findUserByName(user.getName()) != null;
	}
}