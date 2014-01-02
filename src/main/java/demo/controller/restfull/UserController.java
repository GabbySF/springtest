package demo.controller.restfull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.log.Log;

import demo.logic.bean.LogicTest;
import demo.persistence.jpa.entity.User;
import demo.persistence.jpa.repository.UserRepository;


@Controller
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	private UUID uidGenerator;
	
	@RequestMapping(value="/adduser",method=RequestMethod.GET)
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
	
	@RequestMapping("/update")
	public @ResponseBody User updateUser(@RequestParam(value="name", required=true) String name,@RequestParam(value="id", required=true) long id){
		
		
		User u = userRepo.findOne(id);
		u.setName(name);
		
		userRepo.save(u);
		
		return u;
		
	}
	
	@RequestMapping("/getbyuid")
	public @ResponseBody User getByUid(@RequestParam(value="uid", required=true) String uid){
		
		
		User u = userRepo.findByUid(uid);
		
		return u;
		
	}
	
	@Autowired
	JpaTransactionManager lcem;
	
	@RequestMapping("/log")
	public @ResponseBody Collection printLog(){
		
		EntityManager em=lcem.getEntityManagerFactory().createEntityManager();
		
		return em.createNativeQuery("select * from user").getResultList();
		
	}
	
	@Autowired
	private LogicTest test;
	
	@RequestMapping("/test")
	public @ResponseBody String execTest(){
		
		return test.testMethod();
		
	}
	
}
