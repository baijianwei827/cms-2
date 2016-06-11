package com.elliothutchinson.cms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Section;
import com.elliothutchinson.cms.repository.SectionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class RestSectionService {

    private SectionRepository sectionRepository;

    @Autowired
    public RestSectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public String findAllSections() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("sections");

        return writer.writeValueAsString(sectionRepository.findAllByOrderByTitleAsc());
    }

    public String findSectionFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Section section = sectionRepository.findOne(id);

        if (section == null) {
            map.put("error", "There was a problem requesting resource: section not found");
            return mapper.writeValueAsString(map);
        }

        return mapper.writeValueAsString(section);
    }

    public String createSectionFromProxy(Section proxySection) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Section section = new Section(proxySection.getTitle(), proxySection.getDescription());

        sectionRepository.save(section);

        return mapper.writeValueAsString(section);
    }

    public String updateSectionFromProxy(long id, Section proxySection) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (id != proxySection.getId()) {
            map.put("error", "There was a problem updating resource: section id mismatch");
            return mapper.writeValueAsString(map);
        }

        Section section = sectionRepository.findOne(proxySection.getId());
        if (section == null) {
            map.put("error", "There was an error updating resource: section not found");
            return mapper.writeValueAsString(map);
        }

        Section testUniqueTitle = sectionRepository.findOneByTitle(proxySection.getTitle());
        if (testUniqueTitle != null && testUniqueTitle.getId() != section.getId()) {
            map.put("error", "There was an error updating resource: title already exists");
            return mapper.writeValueAsString(map);
        }

        section.setTitle(proxySection.getTitle());
        section.setDescription(proxySection.getDescription());

        sectionRepository.save(section);

        return mapper.writeValueAsString(section);
    }

    public String deleteSectionFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Section section = sectionRepository.findOne(id);

        if (section == null) {
            map.put("error", "There was an error deleting resource: section not found");
            return mapper.writeValueAsString(map);
        }

        sectionRepository.delete(section);

        map.put("success", "Section resource successfully deleted");
        return mapper.writeValueAsString(map);
    }
}
