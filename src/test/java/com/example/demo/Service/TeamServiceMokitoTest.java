package com.example.demo.Service;

import com.example.demo.Entity.TeamDto;
import com.example.demo.Repository.TeamRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
}
