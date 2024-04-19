package com.example.demo.old.Domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {
    @DisplayName("테스트에 사용할 데이터 미리 넣기")
    @Before
    public void beforeMethod() {

    }

    @DisplayName("User 한건 조회하기")
    @Test
    public void selectUserTest() {
        //
    }
}
