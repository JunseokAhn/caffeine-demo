package com.example.caffeinedemo;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

    @Component
    public class Repository {
        private Map<Long, User> userMap= new ConcurrentHashMap<>();

        @PostConstruct
        private void init(){
            userMap.put(1l,new User(1l, "user1", 1));
            userMap.put(2l,new User(2l, "user2", 2));
            userMap.put(3l,new User(3l, "user3", 3));
            userMap.put(4l,new User(4l, "user4", 4));
            userMap.put(5l,new User(5l, "user5", 5));
            userMap.put(6l,new User(6l, "user6", 6));
            userMap.put(7l,new User(7l, "user7", 7));
            userMap.put(8l,new User(8l, "user8", 8));
            userMap.put(9l,new User(9l, "user9", 9));
        }
        @SneakyThrows
        @Cacheable(cacheNames = "user")
        public User getUser(long id) {
            Thread.sleep(5000l);
            User user = userMap.get(id);
            return user;
        }

        @SneakyThrows
        @Cacheable(cacheNames = "users")
        public List<User> getUserList() {
            Thread.sleep(5000l);
            return new ArrayList<>(userMap.values());
        }

    }
