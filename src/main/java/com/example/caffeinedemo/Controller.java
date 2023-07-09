package com.example.caffeinedemo;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final Repository repository;

    @GetMapping("user")
    public User getUser(long id){

        return repository.getUser(id);
    };
    @GetMapping("users")
    public List<User> getUserList(){
        return repository.getUserList();
    };

    @GetMapping("enrollLectures")
    public List<Lecture> getEnrollLectureList(Long id){
        return repository.getEnrollLectureList(id);
    }

}
