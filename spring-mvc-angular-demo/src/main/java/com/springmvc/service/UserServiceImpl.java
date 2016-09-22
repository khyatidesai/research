package com.springmvc.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.Register;
import com.springmvc.domain.RegisterTemp;
import com.springmvc.domain.repo.RegisterRepo;
import com.springmvc.domain.repo.RegisterTempRepo;
import com.springmvc.model.User;

@Service("userService")
public class UserServiceImpl implements UserService, InitializingBean{
	
	private static List<User> users;
	
	@Autowired
	private RegisterTempRepo rtRepo;
	
	@Autowired
	private RegisterRepo rREpo;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("populating users");
		users = populateUsers();
	}
	
	public List<User> findAllUsers() {
		return users;
	}
	
	public User findById(long id) {
		for(User user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	public User findByName(String name) {
		for(User user : users){
			if(user.getUsername().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}
	
	public void saveUser(User user) {
		
		RegisterTemp rt = rtRepo.save(user.registerTemp());
		user.setId(rt.getId());
		users = populateUsers();
	}
	
	public void updateRegister(User user) {

		RegisterTemp rt = user.registerTemp();
		Register r = new Register();
		r.setAddress(rt.getAddress());
		r.setEmail(rt.getEmail());
		r.setId(rt.getId());
		r.setIsActive(true);
		r.setName(rt.getName());
		rREpo.save(r);

		rt.setIsActive(false);
		rtRepo.save(rt);
	}

	public void updateUser(User user) {
		rtRepo.save(user.registerTemp());
		users = populateUsers();
	}

	public void deleteUserById(long id) {
		
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
		    User user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(User user) {
		return findByName(user.getUsername())!=null;
	}
	
	public void deleteAllUsers(){
		users.clear();
	}

	private List<User> populateUsers() {
		if(users!=null)
			users.clear();
		List<RegisterTemp> wfentries = rtRepo.findByIsActive(true);
		List<User> lstUser = new ArrayList<User>();

		for (RegisterTemp rt : wfentries) {
			User user = new User();
			user.setAddress(rt.getAddress());
			user.setEmail(rt.getEmail());
			user.setId(rt.getId());
			user.setUsername(rt.getName());
			lstUser.add(user);

		}
		
		return lstUser;
	}

}
