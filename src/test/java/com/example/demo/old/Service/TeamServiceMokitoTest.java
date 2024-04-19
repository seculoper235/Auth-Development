package com.example.demo.old.Service;

import com.example.demo.Domain.Team;
import com.example.demo.Entity.TeamDto;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Service.TeamService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/* Mock과 InjectMocks?
 * Mockito 에서는 아래와 같은 두가지 어노테이션이 있다.
 * 둘 다 테스트를 위해 가짜 객체를 생성한다는 뜻은 맞는데, InjectMocks는 Mock 객체를 주입받는다.
 * 즉, InjectMocks와 Mock이 같이 쓰인다면, @Mock은 @Autowired와 동일한 의미를 가진다고 생각하면 된다.*/
public class TeamServiceMokitoTest extends ServiceConfig {
    // 가짜 teamService를 생성한다.
    @InjectMocks
    private TeamService teamService;

    // InjectMocks로 생성한 teamService에 teamRepository를 주입한다.
    @Mock
    private TeamRepository teamRepository;

    private TeamDto teamDto;

    /* 주의 사항!
     * 사실 로직이 없는 간단한 CRUD 메소드의 경우, 결과값이 제대로 나오는 것이 중요하기 때문에 Mock API를 사용하는 것이 좋다.
     * 하지만 배우는 겸 사용해보도록 하자.
     */
    @Before
    @DisplayName("데이터 셋업")
    public void setUp() {
        teamDto = TeamDto.builder()
                .teamName("팀1")
                .build();
    }

    /* Mockito 테스트 방법
     * Mockito는 결과값을 중요시하지 않으므로, 필요한 결과값은 특정하여 사용할 수 있다.
     * 이때 사용하는 것이 given()/when() 메소드 인데, 사용법은 다음과 같다.*/
    @DisplayName("팀 한건 조회 테스트")
    @Test
    public void selectTeamByNameTest() {
        // Given
        final String name = teamDto.getTeamName();
        final Team expectTeam = Team.builder()
                .name(name)
                .build();
        /* given 사용법
         * Service의 selectBy~~ 메소드의 경우, 내부의 teamRepository.findTeamByName()를 Mockito로 실행하면 반드시 null이 나온다.
         * 따라서 "이 메소드의 결과값은 이걸 사용할 거야~"라고 특정해줘야 한다.
         * given() 에다가는 해당 메소드를 적어주고, willReturn()으론 특정할 반환값을 적어준다.
         * (여기서 any()는 "어떤 값을 넣더라도~"라는 의미이다.)
         */
        given(teamRepository.findTeamByName(any())).willReturn(expectTeam);


        // When
        TeamDto resultTeam = teamService.selectTeamByName(teamDto.getTeamName());

        // Then
        assertThat(resultTeam).isNotNull();
        assertThat(resultTeam.getTeamId()).isEqualTo(expectTeam.getId());
        assertThat(resultTeam.getTeamName()).isEqualTo(expectTeam.getName());
    }
}
