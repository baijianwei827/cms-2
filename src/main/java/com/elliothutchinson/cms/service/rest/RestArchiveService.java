package com.elliothutchinson.cms.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Archive;
import com.elliothutchinson.cms.dto.ArchiveDto;
import com.elliothutchinson.cms.repository.ArchiveRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class RestArchiveService {

    private ArchiveRepository archiveRepository;

    @Autowired
    public RestArchiveService(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }

    public String findAllArchives() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("archives");
        
        List<ArchiveDto> archiveDtos = new ArrayList<>();
        List<Archive> archives = archiveRepository.findAllByOrderByYearDescMonthDesc();
        for (Archive a : archives) {
            archiveDtos.add(new ArchiveDto(a));
        }

        return writer.writeValueAsString(archiveDtos);
    }
}
