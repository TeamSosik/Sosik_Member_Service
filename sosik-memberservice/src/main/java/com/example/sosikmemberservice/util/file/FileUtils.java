package com.example.sosikmemberservice.util.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Component
public class FileUtils {
    @Value("${file.location}")
    private String uploadPath;
    private final String uploadFolder = System.getProperty("user.home");
    public String getFullPath(String storeFilename){
        return uploadPath +File.separator + storeFilename;
    }

    public List<ResultFileStore> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<ResultFileStore> storeFileResult = new ArrayList<>();
        if(!multipartFiles.isEmpty()){
            for(MultipartFile multipartFile: multipartFiles){
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }
    public ResultFileStore storeFile(MultipartFile multipartFile) throws IOException{
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalFilename= multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String folderPath = uploadPath;

        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new ResultFileStore(folderPath,storeFileName);
    }

    private String createStoreFileName(String originalFileName){
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalFileName;
    }
    public ResultFileStore storeProfileFile(MultipartFile multipartFile) throws IOException {
        String folderPath = uploadPath;
        if(Objects.isNull(multipartFile)){
            return new ResultFileStore(folderPath,"cat.jpg");
        }
        else{
            String originalFilename = multipartFile.getOriginalFilename();
            String storeFileName = createStoreFileName(originalFilename);
            multipartFile.transferTo(new File(getFullPath(storeFileName)));

            return new ResultFileStore(folderPath,storeFileName);
        }
    }



}
