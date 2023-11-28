package com.example.mytodolist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //Application 클래스에 이 어노테이션이 달려있으면 모든 test들이 항상 Jpa 관련 bean을 필요로 하는 상태가 된다.
public class JpaAuditingConfiguration {

}
