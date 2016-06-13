package com.elliothutchinson.cms.service.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.SiteDetail;
import com.elliothutchinson.cms.repository.SiteDetailRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class RestSiteDetailService {
    
    private SiteDetailRepository siteDetailRepository;
    
    @Autowired
    public RestSiteDetailService(SiteDetailRepository siteDetailRepository) {
        this.siteDetailRepository = siteDetailRepository;
    }
    
    public String findAllSiteDetails() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("siteDetails");

        return writer.writeValueAsString(siteDetailRepository.findAllByOrderByName());
    }

    public String findSiteDetailFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        SiteDetail siteDetail = siteDetailRepository.findOne(id);

        if (siteDetail == null) {
            map.put("error", "There was a problem requesting resource: site detail not found");
            return mapper.writeValueAsString(map);
        }

        return mapper.writeValueAsString(siteDetail);
    }
    
    public String createSiteDetailFromProxy(SiteDetail proxySiteDetail) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        SiteDetail siteDetail = new SiteDetail(proxySiteDetail.getName(), proxySiteDetail.getTitle(),
                proxySiteDetail.getContent());

        siteDetailRepository.save(siteDetail);

        return mapper.writeValueAsString(siteDetail);
    }

    public String updateSiteDetailFromProxy(long id, SiteDetail proxySiteDetail) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (id != proxySiteDetail.getId()) {
            map.put("error", "There was a problem updating resource: site detail id mismatch");
            return mapper.writeValueAsString(map);
        }

        SiteDetail siteDetail = siteDetailRepository.findOne(proxySiteDetail.getId());
        if (siteDetail == null) {
            map.put("error", "There was an error updating resource: site detail not found");
            return mapper.writeValueAsString(map);
        }

        SiteDetail testUniqueName = siteDetailRepository.findOneByName(proxySiteDetail.getName());
        if (testUniqueName != null && testUniqueName.getId() != siteDetail.getId()) {
            map.put("error", "There was an error updating resource: name already exists");
            return mapper.writeValueAsString(map);
        }

        siteDetail.setName(proxySiteDetail.getName());
        siteDetail.setTitle(proxySiteDetail.getTitle());
        siteDetail.setContent(proxySiteDetail.getContent());

        siteDetailRepository.save(siteDetail);

        return mapper.writeValueAsString(siteDetail);
    }

    public String deleteSiteDetailFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        SiteDetail siteDetail = siteDetailRepository.findOne(id);

        if (siteDetail == null) {
            map.put("error", "There was an error deleting resource: site detail not found");
            return mapper.writeValueAsString(map);
        }
        
        siteDetailRepository.delete(siteDetail);

        map.put("success", "Site detail resource successfully deleted");
        return mapper.writeValueAsString(map);
    }
}
