package com.example.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MyDatabaseService {
//    @Autowired
//    @Qualifier("complexCrudRepositoryImpl")
//    private CrudRepository repository;

    public final CrudRepository simpleRepository;
    public final CrudRepository complexRepository;

    // 생성자를 이용한 의존성 주입 진행
    @Autowired
    public MyDatabaseService(
            @Qualifier("simpleCrudRepositoryImpl") CrudRepository simpleRepository,
            @Qualifier("complexCrudRepositoryImpl") CrudRepository complexRepository) {
        this.simpleRepository = simpleRepository;
        this.complexRepository = complexRepository;
    }
}