package com.elliothutchinson.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.SiteDetail;
import com.elliothutchinson.cms.repository.SiteDetailRepository;

@Service
public class SiteDetailService {

    private SiteDetailRepository siteDetailRepository;

    protected SiteDetailService() {
    }

    @Autowired
    public SiteDetailService(SiteDetailRepository siteDetailRepository) {
        this.siteDetailRepository = siteDetailRepository;
    }

    public String findSiteName() {
        String name;
        SiteDetail siteDetail = siteDetailRepository.findOneByName("siteName");
        if (siteDetail == null) {
            name = "CMS";
        } else {
            name = siteDetail.getContent();
        }

        return name;
    }

    public String findContentTitle(String name) {
        SiteDetail siteDetail = siteDetailRepository.findOneByName(name);
        if (siteDetail == null) {
            return null;
        } else {
            return siteDetail.getTitle();
        }
    }

    public SiteDetail findSiteDetailFromName(String name) {
        return siteDetailRepository.findOneByName(name);
    }
}
