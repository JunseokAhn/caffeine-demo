package com.example.caffeinedemo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Repository {
    private final CacheManager cacheManager;
    private Map<Long, User> userMap = new ConcurrentHashMap<>();
    private Map<Lecture.CompositeKey, Lecture> lectureMap = new ConcurrentHashMap<>();
    private Map<User, EnrollInfo> enrollMentMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        User user1 = new User(1L, "user1", 1);
        User user2 = new User(2L, "user2", 1);
        User user3 = new User(3L, "user3", 1);
        Lecture lecture1 = new Lecture(1L, "20231", "lecture1");
        Lecture lecture2 = new Lecture(2L, "20231", "lecture2");
        Lecture lecture3 = new Lecture(3L, "20231", "lecture3");
        Lecture lecture4 = new Lecture(4L, "20231", "lecture4");
        Lecture lecture5 = new Lecture(5L, "20231", "lecture5");
        List<Long> lectureIdList1 = Arrays.asList(1L, 2L, 3L);
        List<Long> lectureIdList2 = Arrays.asList(3L, 4L, 5L);
        List<Long> lectureIdList3 = Arrays.asList(1L, 3L, 5L);
        EnrollInfo enrollInfo1 = new EnrollInfo(user1, lectureIdList1);
        EnrollInfo enrollInfo2 = new EnrollInfo(user2, lectureIdList2);
        EnrollInfo enrollInfo3 = new EnrollInfo(user3, lectureIdList3);


        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
        userMap.put(user3.getId(), user3);
        lectureMap.put(lecture1.getCompositeKey(), lecture1);
        lectureMap.put(lecture2.getCompositeKey(), lecture2);
        lectureMap.put(lecture3.getCompositeKey(), lecture3);
        lectureMap.put(lecture4.getCompositeKey(), lecture4);
        lectureMap.put(lecture5.getCompositeKey(), lecture5);
        enrollMentMap.put(user1, enrollInfo1);
        enrollMentMap.put(user2, enrollInfo2);
        enrollMentMap.put(user3, enrollInfo3);

    }

    @SneakyThrows
    @Cacheable(cacheNames = "user")
    public User getUser(long id) {
        Thread.sleep(5000L);
        User user = userMap.get(id);
        return user;
    }

    @SneakyThrows
    @Cacheable(cacheNames = "users")
    public List<User> getUserList() {
        Thread.sleep(5000L);
        return new ArrayList<>(userMap.values());
    }

    // Lecture정보가 CompositeKey ( 강의id, 학기정보 ) 로 캐시되어 빠른결과 반환
    public List<Lecture> getEnrollLectureList(Long userId) {
        User user = userMap.get(userId);
        EnrollInfo enrollInfo = enrollMentMap.get(user);
        List<Long> lectureIdList = enrollInfo.getLectureIdList();

        return getLectureByLectureIds(lectureIdList);
    }

    @SneakyThrows
    public List<Lecture> getLectureByLectureIds(List<Long> lectureIdList) {

        //@Cachable을 제외하고 cacheManager를 사용해 수동으로 캐시메모리 조작
        Cache lectureCache = cacheManager.getCache("lectures");
        List<Lecture> lectureList = new ArrayList<>();

        for (Long lectureId : lectureIdList) {
            Lecture.CompositeKey compositeKey = new Lecture.CompositeKey(lectureId, "20231");
            Cache.ValueWrapper cachedLecture = lectureCache.get(compositeKey);

            // 캐시되어있으면 데이터 즉시 반환
            if (cachedLecture != null){
                lectureList.add((Lecture) cachedLecture.get());
            }
            // 캐시되어있지않으면 지연시간을 2초 추가하고, 캐시에 추가
            if (cachedLecture == null) {
                Thread.sleep(2000L);
                Lecture lecture = lectureMap.get(compositeKey);
                if (lecture == null) {
                    throw new Exception("강의를 찾을수 없습니다.");
                }
                lectureList.add(lecture);
                //캐시에 강의정보 추가
                lectureCache.put(compositeKey, lecture);
            }
        }

        return lectureList;
    }
}
