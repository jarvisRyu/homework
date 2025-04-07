package com.cordingrecipe.scheduleclone.schedule.entity;

import com.cordingrecipe.scheduleclone.common.BaseEntity;
import com.cordingrecipe.scheduleclone.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity //JPA Entity 임을 명시 빠지면 인식못함.
@NoArgsConstructor //기본생성자 자동 생성  Entity 는 기본생성자가 필수. Lombok 을 사용해야한다.
@Table(name="schedules") //동륵할 테이블 이름을 지정. 생략시 클래스이름 그대로 사용 (schedule)
public class Schedule extends BaseEntity {//BaseEntity 상속 - createAt,updatedAt 자동 등록

    @Id//해당필드를 기본키(PK)로 지정 JPA 모든 Entity는 반드시 @Id필드를 가져야함
    @GeneratedValue(strategy = GenerationType.IDENTITY)//AUTO_INCREMENT 와 같은기능 자동으로 기본키 생성
    private Long id;  //->BIGINT

    @ManyToOne(fetch = FetchType.LAZY) //N:1관계의 테이블 schedule 이 N.
    // FetchType.Lazy= user 테이블에 접근할때 불러옴 지연로딩
    @JoinColumn(name = "user_id")//해당 필드가 매핑되는 DB컬럼 이름 user_id 외래키(FK)가 생김.
    private User user;


    private String title;
    private String contents;

    public Schedule(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contents){
        this.title=title;
        this.contents=contents;
    }
}


