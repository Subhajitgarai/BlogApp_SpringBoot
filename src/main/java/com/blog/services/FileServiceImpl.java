package com.blog.services;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Component
public class FileServiceImpl implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File Name
        String name=file.getOriginalFilename();

        //Random name generate for file
        String randomId= UUID.randomUUID().toString();
        String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));

        //Full Path
        String filePath=path+ File.separator+fileName1;

        //Create a folder if not created
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //File copy into the folder that was uploaded
        Files.copy(file.getInputStream(), Paths.get(filePath));
        System.out.println(fileName1);
        return fileName1;
    }
    //Getting the image
    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
