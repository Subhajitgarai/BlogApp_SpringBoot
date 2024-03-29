package com.blog.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
@Service
public interface FileService {
    //For File Uploading
    String uploadImage(String path, MultipartFile file)throws IOException;
    //Get The file
    InputStream getResource(String path,String fileName)throws FileNotFoundException;
}
