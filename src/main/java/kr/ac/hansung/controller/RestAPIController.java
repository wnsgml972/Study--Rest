package kr.ac.hansung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import kr.ac.hansung.exception.UserDuplicatedException;
import kr.ac.hansung.exception.UserNotFoundException;
import kr.ac.hansung.model.User;
import kr.ac.hansung.service.UserService;

@RestController
@RequestMapping("/api")
public class RestAPIController {
	
	@Autowired
	UserService userService;
	
	// --- Retrieve All Users
	// list users를 json format으로 바꿔서 넘겨 주어 여러 정보를 담은 객체를 리턴하게 함
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() { //header, body(json), HTTP.status
		
		List<User> users = userService.findAllUser();
		if(users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	// --- Retrieve Single User
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> getuser(@PathVariable("id") long id) { 
		
		User user = userService.findUserById(id);
		if(user == null) {
			throw new UserNotFoundException(id);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	
	
	// --- Create a User
	@RequestMapping(value="/users", method=RequestMethod.POST) //request body(json)
	public ResponseEntity<Void> createUser(@RequestBody User user
			, UriComponentsBuilder ucBuilder) { 
		
		if(userService.isUserExist(user)){
			throw new UserDuplicatedException(user.getName());
		}
		userService.saveUser(user);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/users/{id}").
				buildAndExpand(user.getId()).toUri());
		
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
		
	// --- Update a User
	@RequestMapping(value="/users/{id}", method=RequestMethod.PUT) //request body(json)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) { 
		
		User currentUser = userService.findUserById(id);
		
		if(currentUser == null){
			throw new UserNotFoundException(id);
		}
		
		currentUser.setName(user.getName());
		currentUser.setAge(user.getAge());
		currentUser.setSalary(user.getSalary());
		
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	// --- Delete a User
	@RequestMapping(value="/users/{id}", method=RequestMethod.DELETE) //request body(json)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) { 
		
		User currentUser = userService.findUserById(id);
		
		if(currentUser == null){
			throw new UserNotFoundException(id);
		}
		
		userService.deleteUserById(id);
		
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// --- Delete All Users
	@RequestMapping(value="/users", method=RequestMethod.DELETE) //request body(json)
	public ResponseEntity<User> deleteAllUsers() { 
		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

}
