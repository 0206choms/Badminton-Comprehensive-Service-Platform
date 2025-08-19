package com.kiwi.member.dto;

import com.kiwi.member.constant.Address;
import com.kiwi.member.constant.Bank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OauthAddInfoDto {
 
    private Address address;
    
    private String pnum;   
        
    private Bank bname;
    
    private String bnumber;

}
