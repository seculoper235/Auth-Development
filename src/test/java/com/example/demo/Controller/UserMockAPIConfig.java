package com.example.demo.Controller;

import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

/* Mock API란?
 * Mockito와 비슷하지만 Web 환경에서의 테스트를 다루며, 로직보다는 결과값이 제대로 나오는지에 중점을 둔다.
 * 통합 테스트와도 비슷하나, 실제로 API를 돌리지 않고 가짜 API를 돌린다는 것이 차이점이다. 때문에 실 서버를 돌려서 검증하기 힘든 부분을 테스트할 수 있다.
 * (OAuth를 통한 로그인 인증 테스트, 결제 모듈 테스트 등)
 * Controller를 테스트 할 때 사용하며, 지금처럼 간단한 CRUD 메소드 에서는 Service를 건너뛰고 바로 Controller를 테스트해도 된다.
 */

// Mock API는 @WebMvcTest 어노테이션을 사용하며, 파라미터로 해당하는 컨트롤러를 넣어준다.
@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
@Disabled
public class UserMockAPIConfig {
}
