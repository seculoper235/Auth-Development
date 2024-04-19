package com.example.demo.old.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/* Mockito란?
 * @Mock을 사용할 때는 Mockito를 사용하는데, 이것은 우리가 일반적으로 사용하는 Web 환경의 Mock 객체와는 다르다.
 * Web 환경은 Mock API 테스트라고 하는데, 이것은 실제로 값을 넣고 출력된 값이 제대로 나오는지 확인하는 등 검증의 의미가 있는 반면,
 * Mockito는 값이 Insert/Select 되는데에는 괸심이 없다. 즉, 결과물은 일절 관심이 없고, 코드(로직)가 잘 돌아가느냐?를 테스트하는 방법이다.
 * 따라서 메소드 수행 결과가 참이냐 아니냐인 boolean 이나, void 를 제외하면, 어떤 return 값이라도 null 값이 반환된다.
 * (단 Optional 객체는 비어있는 객체가 반환된다. 일반 객체가 아닌 null-safe한 객체이기 때문이다.) */

// Mockito는 SpringRunner가 아닌, MokitoJUnitRunner라는 클래스를 사용한다.
@RunWith(MockitoJUnitRunner.class)
@Disabled
public class ServiceConfig {
}
