package com.deep.backend.model.dto;

public class TestDTO {
    private String test;
    private int count;

    public TestDTO(String test, int count) {
        this.test = test;
        this.count = count;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TestDTO{" +
                "test='" + test + '\'' +
                ", count=" + count +
                '}';
    }
}
