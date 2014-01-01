package demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.persistence.jpa.entity.User;
import demo.persistence.jpa.repository.UserRepository;


@Controller
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	private UUID uidGenerator;
	
	@RequestMapping("/adduser")
	public @ResponseBody User addUser(@RequestParam(value="name", required=true) String name){
		
		User u =new User(uidGenerator.randomUUID().toString(),name);
		userRepo.save(u);
		
		return u;
		
	}
	
	@RequestMapping("/getall")
	public @ResponseBody Collection getAllUsers(){
		Iterator<User> it = userRepo.findAll().iterator();
		ArrayList list = new ArrayList();
		while(it.hasNext()){
			list.add(it.next());
		}
		return list;
	}
	
}
