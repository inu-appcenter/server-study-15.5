package com.example.mytodolist.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //상위 클래스인 Time 클래스의 필드와 매핑정보를 하위 엔티티 클래스로 상속시키는 역할.
@EntityListeners(value = {AuditingEntityListener.class}) //엔티티의 변화를 감지하는 리스너
@Getter
public abstract class Time {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedDate;

}
