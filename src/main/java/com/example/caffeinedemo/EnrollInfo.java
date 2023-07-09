package com.example.caffeinedemo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EnrollInfo {
    private User user;
    private List<Long> lectureIdList;
}
