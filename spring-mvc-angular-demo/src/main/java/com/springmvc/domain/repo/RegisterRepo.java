package com.springmvc.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.Register;

@Repository
public interface RegisterRepo extends CrudRepository<Register, String>{

}
