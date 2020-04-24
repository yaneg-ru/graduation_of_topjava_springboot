package ru.yaneg.graduation_of_topjava_springboot.io.entitiy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "VOTE", uniqueConstraints = {@UniqueConstraint(columnNames = {"DATA", "USER_ID"}, name = "VOTE_UNIQUE_DATA_USER_IDX")})
public class VoteEntity extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EATERY_ID", nullable = false)
    @NotNull
    private EateryEntity eatery;

    @Column(name = "DATA", nullable = false)
    @NotNull
    private LocalDate date;

    public VoteEntity() {
    }

    public VoteEntity(EateryEntity eatery, UserEntity user, LocalDate date) {
        this.eatery = eatery;
        this.user = user;
        this.date = date;
    }

    public VoteEntity(int id, EateryEntity eatery, UserEntity user, LocalDate date) {
        super(id);
        this.eatery = eatery;
        this.user = user;
        this.date = date;
    }

    public VoteEntity(VoteEntity vote) {
        super(vote.getId());
        this.eatery =  vote.getEatery();
        this.user =  vote.getUser();
        this.date =  vote.getDate();
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public EateryEntity getEatery() {
        return eatery;
    }

    public void setEatery(EateryEntity eatery) {
        this.eatery = eatery;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}