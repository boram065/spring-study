package com.example.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Bean
public class MyCalculatorService {

    // 필드 주입 : 클래스 필드에 직접 어노테이션을 쓰는 방법
    // 상수 필드의 경우 생성자에서 대입은 허용하므로 final로 설정
    // @Autowired
    private Calculator calculator;

    // 생성자 주입 : 생성자 메소드 위에 어노테이션을 쓰는 방법
    // 의존성 주입을 통해 생성자로 Calculator 타입의 Bean 객체가 주입(=전달)됨
    @Autowired
    public MyCalculatorService(Calculator calculator) {
        System.out.println("from constructor");
        System.out.println(calculator);
        this.calculator = calculator;
    }

    // 세터 주입 : 세터 메소드 위에 어노테이션을 쓰는 방법
    @Autowired
    public void setCalculator(Calculator calculator) {
        System.out.println("from setCalculator");
        System.out.println(calculator);
        this.calculator = calculator;
    }

    public int calcAdd(int a, int b) {
        return calculator.add(a, b);
    }
}
