package com.example.di;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DiApplication {

	public static void main(String[] args) {
		// run 메서드의 반환값이 Bean들이 저장될 컨테이너로 활용되는 ApplicationContext 타입의 객체
		ApplicationContext context = SpringApplication.run(DiApplication.class, args);

		// 모든 Bean들의 이름 가져오기
		String[] beanNames = context.getBeanDefinitionNames();

		// 출력
		for (String beanName : beanNames) {
			// 단, 기본적으로 스프링에서 제공하는 Bean들은 가급적 필터링해서 보여주기 (전부 다 걸러지진 않음)
			if(!beanName.startsWith("org.") && !beanName.startsWith("spring.")) {
				System.out.println(beanName);
			}
		}

//		Person p = (Person) context.getBean("myPerson");
//		p.sayName();

		MyCalculatorService c = (MyCalculatorService) context.getBean("myCalculatorService");
		System.out.println(c.calcAdd(3, 4));

		MyDatabaseService db = (MyDatabaseService) context.getBean("myDatabaseService");
		System.out.println(db.simpleRepository instanceof SimpleCrudRepositoryImpl);
		System.out.println(db.complexRepository instanceof ComplexCrudRepositoryImpl);
	}
}
