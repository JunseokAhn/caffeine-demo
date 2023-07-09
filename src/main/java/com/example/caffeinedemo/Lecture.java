package com.example.caffeinedemo;

import lombok.Data;

@Data
public class Lecture {
    private CompositeKey compositeKey;
    private String lectureName;

    private String info1;
    private String info2;
    private String info3;
    private String info4;
    private String info5;
    private String info6;
    private String info7;
    private String info8;
    private String info9;

    public Lecture(long lectureId, String termCode, String lectureName) {
        this.compositeKey = new CompositeKey(lectureId, termCode);
        this.lectureName = lectureName;
    }

    @Data
    static class CompositeKey {
        private String termCode;
        private long lectureId;

        public CompositeKey(long lectureId, String termCode) {
            this.lectureId = lectureId;
            this.termCode = termCode;
        }
    }
}