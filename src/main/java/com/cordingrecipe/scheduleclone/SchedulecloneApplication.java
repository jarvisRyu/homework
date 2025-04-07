package com.cordingrecipe.scheduleclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing//JPA Auditing 기능활성화. 선언해줘야 @CreateDate,@LastModifiedDate 쓸수있음.
@SpringBootApplication
public class SchedulecloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulecloneApplication.class, args);
    }

}
