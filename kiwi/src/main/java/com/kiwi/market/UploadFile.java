package com.kiwi.market;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.entity.Market;


@Component
public class UploadFile {
	
	@Value("${resource}")
	private String path;
	
	public void fildUpload(Market market, MultipartFile file) throws IOException {
		
		String oriFileName = file.getOriginalFilename();
		
		UUID uuid = UUID.randomUUID();
		
		String fileName = File.separator + uuid + "_" + file.getOriginalFilename();
		
		File uploadFile = new File(path, fileName);
		
		uploadFile.createNewFile();
		
		FileCopyUtils.copy(file.getBytes(), uploadFile);
		
		market.setFilename(fileName);
		market.setFilepath(File.separator + "image\\title" + fileName);
		market.setOriImgName(oriFileName);
	}
	
	public void fildUpload2(Market market, MultipartFile file) throws IOException {
		
		UUID uuid = UUID.randomUUID();
		
		String fileName = File.separator + uuid + "_" + file.getOriginalFilename();
		String origin = file.getOriginalFilename();
		
		File uploadFile = new File(path, fileName);
		
		uploadFile.createNewFile();
		
		FileCopyUtils.copy(file.getBytes(), uploadFile);
		
		market.setFilename(fileName);
		market.setFilepath(File.separator + "image\\title" + fileName);
		market.setOriImgName(origin);
	}
}
