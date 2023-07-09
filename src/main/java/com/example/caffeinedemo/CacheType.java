package com.example.caffeinedemo;

import lombok.Getter;

@Getter
public enum CacheType {
    USER(
            "user",       // 캐시 이름: user
            5 * 60,                 // 만료 시간: 5 분
            10000                   // 최대 갯수: 10000
    ),
    USERS(
            "users",      // 캐시 이름: users
            5 * 60,                 // 만료 시간: 5 분
            10000                   // 최대 갯수: 10000
    ),
    LECTURES(
            "lectures",   // 캐시 이름: lectures
            5 * 60,                 // 만료 시간: 5 분
            10000                   // 최대 갯수: 10000
    );
    private final String cacheName;
    private final int expireAfterWrite;
    private final int maximumSize;

    CacheType(String cacheName, int expireAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expireAfterWrite = expireAfterWrite;
        this.maximumSize = maximumSize;
    }

}