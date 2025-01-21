package com.hrmofturing.hrm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private static final String UPLOAD_DIR = "/path/to/upload/directory/";

    public String saveAvatar(MultipartFile file, String username) throws IOException {
        String filename = username + "_avatar_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + filename);
        Files.copy(file.getInputStream(), path);
        return filename; // 返回保存后的文件名或URL
    }
}
