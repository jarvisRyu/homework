package com.cordingrecipe.scheduleclone.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 공통필드,매핑정보를 상속을 통해 공유할 수 있도록 도와줌
@EntityListeners(AuditingEntityListener.class) //Entity 에서 생성,수정이 발생했을 때 감지
public class BaseEntity {

    @CreatedDate  //생성시점 날짜를 자동으로 기록
    @Column(updatable = false) //변경될 수 없도록 함
    @Temporal(TemporalType.TIMESTAMP)//DB에 어떤 타입으로 매핑할건지 지정.날짜+시간
    private LocalDateTime createdAt;

    @LastModifiedDate //수정시점 날짜를 자동 기록
    @Column//DB 컬럼으로 매핑시켜줌 //생략가능
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

}
