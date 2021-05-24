package com.example.demo.Integration;

import com.example.demo.Domain.Team;
import com.example.demo.Domain.User;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;


/* 통합 테스트란?
 * 앞서 사용한 Mock API와는 달리 실제 Bean들을 등록하여 테스트하는 방법으로, Mock 방법보다는 테스트 실행이 느리다.
 * 실제로 값이 DB에 저장되고 코드도 동작하므로 사용시에 주의가 필요하다.
 * 어노테이션으로는 @SpringBootTest를 사용하며, 트랜잭션 관리를 위한 @Transactional과, Mvc를 위한 @AutoConfigureMockMvc를 사용한다.
 *
 * 실제 Bean들이 동작한다는 것 외에는 Mock API와 동일하므로 그 점만 주의하자! */
@RunWith(SpringRunner.class)
// 테스트에 사용할 웹 환경을 설정한다. 여기선 포트를 랜덤 포트로 지정했다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// MockMvc를 자동설정 해준다.
@AutoConfigureMockMvc
@Transactional
@Disabled
public class UserIntegrationConfig {
    @Autowired
    protected MockMvc mockMvc;

    // 실제 앱을 돌리는 것이므로, W.A.C도 주입해줘야 한다.
    @Autowired
    private WebApplicationContext webApplicationContext;

    // 현재 API는 json 형태로 입력받는다. 즉 문자열의 형태로 입력받는단 것이다.
    // 따라서 POST 방식으로 객체를 입력받기 위해선, 객체를 문자열로 변경해줘야 한다. 바로 그 역할을 ObjectMapper가 한다.
    @Autowired
    protected ObjectMapper objectMapper;

    // 더이상 @MockBean이 아닌 @Autowird로 실제 Bean을 등록한다.
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    protected User testUser;

    @Before
    public void setUp() {
        // MockMvc를 설정해준다. 주입한 W.A.C를 설정하고 필터를 설정하는 등 다양한 설정을 할 수 있다.
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .build();

        // 통합 테스트는 실제로 진행되는 것이기 때문에, 레포지토리로 DB에 저장할 수 있다.
        Team team = Team.builder()
                .name("팀1")
                .build();

        Team testTeam = teamRepository.save(team);

        User user = User.builder()
                .name("유저1")
                .description("유저1 입니다.")
                .team(testTeam)
                .build();

        testUser = userRepository.save(user);
    }
}
