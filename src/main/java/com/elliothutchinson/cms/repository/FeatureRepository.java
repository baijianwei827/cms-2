package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.Feature;

public interface FeatureRepository extends CrudRepository<Feature, Long> {

    List<Feature> findTop10ByPublishIsTrueOrderByDateCreatedDesc();

    Long countByArticleId(Long id);
}
