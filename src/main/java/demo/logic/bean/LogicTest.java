package demo.logic.bean;

import javax.persistence.metamodel.StaticMetamodel;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@Service
public class LogicTest {

	public String testMethod(){
		System.out.println("method");
		return "Method";
	}
	
}
