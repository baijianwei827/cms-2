package com.elliothutchinson.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.File;
import com.elliothutchinson.cms.repository.FileRepository;

@Service
public class FileService {

    private FileRepository fileRepository;

    protected FileService() {
    }

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> findSiteDetailFiles() {
        return fileRepository.findAllByArticleIsNullOrderByFilename();
    }
}
