package com.proejct.ClassActionClaim.domain.baseEntities;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class DateTimeEntity {

    @CreatedDate
    @Column(updatable = false, insertable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP") // default 값으로 현재 시간을 저장
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(updatable = false , insertable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP") // default  값으로 현재 시간을 저장 &&  튜플을 update 할때마다 현재 시간으로 업데이트
    private LocalDateTime lastModifiedDate;
}

