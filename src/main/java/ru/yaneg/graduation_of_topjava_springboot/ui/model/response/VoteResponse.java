package ru.yaneg.graduation_of_topjava_springboot.ui.model.response;

import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;

import java.time.LocalDate;

public class VoteResponse {

    private String resultOfVote;
    private String publicUserId;
    private Integer eateryId;
    private LocalDate date;

    public VoteResponse() {
    }

    public String getResultOfVote() {
        return resultOfVote;
    }

    public void setResultOfVote(String resultOfVote) {
        this.resultOfVote = resultOfVote;
    }

    public String getPublicUserId() {
        return publicUserId;
    }

    public void setPublicUserId(String publicUserId) {
        this.publicUserId = publicUserId;
    }

    public Integer getEateryId() {
        return eateryId;
    }

    public void setEateryId(Integer eateryId) {
        this.eateryId = eateryId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}