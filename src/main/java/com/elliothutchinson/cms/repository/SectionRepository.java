package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.Section;

public interface SectionRepository extends CrudRepository<Section, Long> {

    Section findOneByTitle(String title);

    List<Section> findAllByOrderByTitleAsc();
}
