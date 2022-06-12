package com.example.mainproject0606;

public class Post {
    // 게시글 정보를 관리하기 위한 클래스
    // Post.java에서 사용할 변수 선언
    private String BoardingPoint, Destination, Time, Personnel, InputTime, Email;

    public Post() { }

    public String getBoardingPoint() {
        return BoardingPoint;
    }

    public void setBoardingPoint(String boardingPoint) {
        BoardingPoint = boardingPoint;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPersonnel() {
        return Personnel;
    }

    public void setPersonnel(String personnel) {
        Personnel = personnel;
    }

    public String getInputTime() {
        return InputTime;
    }

    public void setInputTime(String inputTime) {
        InputTime = inputTime;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
