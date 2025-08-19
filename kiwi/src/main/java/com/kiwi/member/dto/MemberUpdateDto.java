package com.kiwi.member.dto;

import org.springframework.web.multipart.MultipartFile;

import com.kiwi.member.constant.Address;
import com.kiwi.member.constant.Bank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateDto {
	
	private Address address;
    
    private String pnum;   
        
    private Bank bname;
    
    private String bnumber;
    
    private MultipartFile filename;
}
