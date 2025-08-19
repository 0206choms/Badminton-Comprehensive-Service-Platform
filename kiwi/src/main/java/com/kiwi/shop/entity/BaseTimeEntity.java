package com.kiwi.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Auditing 적용할려면 @EntitiyListeners 어노테이션 추가함!
@EntityListeners(value = {AuditingEntityListener.class})
// 공통된 매핑 정보가 필요할 때 사용하는 어노테이션으로 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공한다.
@MappedSuperclass
@Getter @Setter
// 등록일, 수정일만 필요한 엔티티는 BaseTimeEntity 상속 받으면 됨
public abstract class BaseTimeEntity {
    @CreatedDate                            // 엔티티가 생성되어 저장될 때 시간을 자동으로 저장함
    @Column(updatable = false)              // update 시점에 컬럼 관련 타입이나 이름 변경 막는다.
    private String regTime;

    @LastModifiedDate                       // 엔티티의 값을 변경할 때 시간을 자동으로 저장함
    private String updateTime;
    
    @PrePersist
    public void onPrePersist(){
        this.regTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updateTime = this.regTime;
    }
    
    @PreUpdate
    public void onPreUpdate(){
    this.updateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }



}
