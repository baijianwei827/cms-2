package com.elliothutchinson.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Feature;
import com.elliothutchinson.cms.repository.FeatureRepository;

@Service
public class FeatureService {

    private FeatureRepository featureRepository;

    protected FeatureService() {
    }

    @Autowired
    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public List<Feature> findAllFeatures() {
        return featureRepository.findTop10ByPublishIsTrueOrderByDateCreatedDesc();
    }
}
