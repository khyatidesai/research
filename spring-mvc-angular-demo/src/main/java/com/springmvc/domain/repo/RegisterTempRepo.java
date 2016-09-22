package com.springmvc.domain.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.RegisterTemp;

@Repository
public interface RegisterTempRepo extends CrudRepository<RegisterTemp, Integer>{
	
	List<RegisterTemp> findByIsActive(Boolean isActive);

}
