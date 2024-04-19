package com.example.demo.old.Domain;

import com.example.demo.Domain.Team;
import com.example.demo.Domain.User;
import com.example.demo.Repository.TeamRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


/* JPA 테스트
 * 테스트에 사용되는 클래스나 의존성 등은 기본적으로 POM.xml에서 scope: test인 것 빼고는 없다.
 * 즉 아무것도 설정되어 있지 않고 사용할 수 없다. 따라서 일일히 뭘 사용할 것인지 주입을 시켜줘야 한다.
 * 이러한 주입을 위해, JPA 테스트에는 총 3가지 에노테이션을 사용한다. */

// JPA에 관련한 의존성 주입을 해준다. JPA를 사용할 수 있도록 환경을 구축해준다고 생각하면 된다.
@DataJpaTest

// 의존성을 주입한 Junit4에 관련된 설정을 하기위해 필요한 어노테이션이다.
@RunWith(SpringRunner.class)

/* replace? */
// Test로 사용할 데이터소스는 어떤건지 설정한다. NONE과 AUTO_CONFIGURED, ANY가 있는데,
// AURO_CONFIGURED를 설정하면 auto-configure된 내장 데이터소스를 사용하게 된다.
// ANY는 default 값으로, 자동 설정되었건 아니건 내장 데이터소스를 사용한다.
// 아무 설정을 하지 않았다면, Hikari 내장 데이터소스에 H2 내장 데이터베이스를 사용한다.
// NONE으로 설정하면 어떤 내장 데이터소스도 사용하지 않는다. 따라서 DataSourceAutoConfiguration.class에 의해
// 현재 작성한 데이터소스(.properties)가 설정되고, 기존에 작성된 DB를 사용하게 된다.

/* connection? */
// 사용할 데이터베이스를 설정한다.
// H2, DERBY, HSQL의 내장 데이터베이스를 제공한다.
// 현재 아무것도 설정하지 않았으므로, 개별로 설정한 데이터베이스를 사용한다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeamTest {
    // 엔티티 매니저의 Test 버전이다. 테스트용으로 기대값을 도출할 때 사용한다.
    @Autowired
    private TestEntityManager testEntityManager;

    // 테스트가 필요한 실제 작성한 Repository를 주입한다. 결과값을 도출하여, TestEntityManger의 기대값과 비교하여 테스트하는 방식이다.
    @Autowired
    private TeamRepository teamRepository;

    // 테스트 데이터
    private List<User> users = new ArrayList<>();

    @DisplayName("테스트에 사용할 데이터 미리 넣기")
    @Before
    public void beforeMethod() {
        User user = User.builder()
                .name("유저1")
                .description("유저1입니다.")
                .build();
        users.add(user);
    }

    @DisplayName("Team 한건 조회하기")
    @Test
    public void selectTeamTest() {
        // Given
        Team team = Team.builder()
                .name("팀2")
                .users(users)
                .build();
        Team expectedTeam = testEntityManager.persist(team);

        // When
        Team resultTeam = teamRepository.findById(expectedTeam.getId()).get();

        // Then
        Assert.assertEquals(expectedTeam, resultTeam);
    }
}
