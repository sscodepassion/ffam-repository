package com.ffam.workdistapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.ffam.workdistapi.model.Skill;

public interface SkillRepository extends CrudRepository<Skill, Integer> {

	@Override
	@RestResource(exported = false)
	<S extends Skill> S save(S entity);
	
	@Override
	@RestResource(exported = false)
	<S extends Skill> Iterable<S> saveAll(Iterable<S> entities);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(Skill entity);

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends Skill> entities);

	@Override
	@RestResource(exported = false)	
	void deleteAll();
}