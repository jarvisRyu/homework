package com.cordingrecipe.scheduleclone.user.entity;

import com.cordingrecipe.scheduleclone.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name="users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    @Column(unique = true) //해당필드를 unique 중복안되게 지정 email VARCHAR(255) UNIQUE
    private String email;
    private String password;

    public User(Long id) {
        this.id = id; //조회시 Id 만으로 연결하기위해 사용
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public static User fromUserId(Long id) {
        return new User(id);
    }

    public void update(String userName,String email,String password){
        this.userName=userName;
        this.email=email;
        this.password=password;
    }

}
