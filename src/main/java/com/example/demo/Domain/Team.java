package com.example.demo.Domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "team")
public class Team {
    /* detached entity passed to persist 오류?
     * 이 오류는 Hibernate의 키 생성 전략과 MySQL의 키 생성 전략이 맞지 않아서 생기는 오류이다.
     * 해당 전략은 IDENTITY 인데, 이것은 반드시 auto_increment와 같이 쓰여야한다.
     * 그 이유는 IDENTITY는 MySQL에 해당하는 전략으로, 기본 키 생성을 데이터베이스에 넘기는 전략이다. 이런 기본 키 생성은 MySQL의 AUTO_INCREMENT가 맡아서 값을 증가시키게 된다.
     * 하지만 auto_increment가 없다면 MySQL은 키를 증가시키지 않는다.
     * 따라서 키를 데이터베이스가 생성하지 않으므로, Field id doesn't have a default value!라는 오류가 나게 된다.
     * 즉 데이터를 저장하고 싶어도 키 값이 생성되지 않으므로, 저장이 되지 않는다.
     * 따라서 primary key 설정을 auto_increment로 바꿔야 정상적인 동작이 가능하다.
     *
     * auto_increment가 적용되지않는 컬럼(varchar 등)은 자동 생성할 수 없으므로, @GeneratedValue 방식이 아닌 직접 id를 할당하는 방식을 택해야 한다.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<User> users = new ArrayList<>();

    @Builder
    public Team(int id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }
}
