package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.SiteDetail;

public interface SiteDetailRepository extends CrudRepository<SiteDetail, Long> {

    SiteDetail findOneByName(String name);

    List<SiteDetail> findAllByOrderByName();
}
