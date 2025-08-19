//package com.kiwi.shop.service;
//
//import lombok.extern.java.Log;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.UUID;
//
//@Service
//@Log
//public class FileService {
//
//    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
//        // 파일 이름 중복을 피하기 위해 랜덤으로 생성
//        UUID uuid = UUID.randomUUID();
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//        // 랜덤으로 생성한 UUID랑 원래 파일 이름의 확장자를 조합해서 저장할 파일 이름 생성
//        String savedFileName = uuid.toString() + extension;
//        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
//        // 생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일에 쓸 파일 출력 스트림을 만듬
//        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
//        // fileData를 파일 출력 스트림에 입력
//        fos.write(fileData);
//        fos.close();
//        // 업로드된 파일 이름을 반환
//        return savedFileName;
//    }
//
//    public void deleteFile(String filePath) throws Exception{
//        // 파일이 저장된 경로를 이용하여 파일 객체 생성
//        File deleteFile = new File(filePath);
//
//        // 해당 파일이 존재하면 삭제합니다.
//        if(deleteFile.exists()){
//            deleteFile.delete();
//            log.info("파일을 삭제하였습니다.");
//        } else {
//            log.info("파일이 존재하지 않습니다.");
//        }
//    }
//}
