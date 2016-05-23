package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.Archive;

public interface ArchiveRepository extends CrudRepository<Archive, Long> {
	
	List<Archive> findAllByOrderByYearDescMonthDesc();
	List<Archive> findByYearAndMonth(Integer year, Integer month);
}
