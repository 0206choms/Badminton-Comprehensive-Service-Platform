package com.kiwi.shop.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;


@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
// 등록일, 수정일, 등록자, 수정자가 다 필요한 엔티티는 BaseEntity 상속 받으면 됨
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

}
