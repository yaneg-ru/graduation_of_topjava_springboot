package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.yaneg.graduation_of_topjava_springboot.exceptions.NotFoundEntityException;
import ru.yaneg.graduation_of_topjava_springboot.exceptions.VoteException;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.UserEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.VoteEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.EateryRepository;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.VoteRepository;
import ru.yaneg.graduation_of_topjava_springboot.security.UserPrincipal;
import ru.yaneg.graduation_of_topjava_springboot.shared.Messages;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.VoteResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("votes")
@Transactional(readOnly=true)
public class VoteController extends AbstractController {

    @Autowired
    EateryRepository eateryRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    Messages messages;


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public Integer getCountVotesByDateAndEateryId(@RequestParam(value = "date") LocalDate date,
                                                  @RequestParam(value = "eateryId", required = false) Integer eateryId) {

        if (eateryId != null) {
            EateryEntity eateryEntity = eateryRepository.findById(eateryId)
                    .orElseThrow(() -> new NotFoundEntityException("error.eatery.notFoundById"));
            return voteRepository.countAllByDateAndEatery(date, eateryEntity);
        }

        return voteRepository.countAllByDate(date);
    }


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public VoteResponse vote(@AuthenticationPrincipal UserPrincipal userPrincipal,
                             @RequestParam(value = "eateryId") Integer eateryId,
                             HttpServletRequest request) {

        EateryEntity eateryEntity = eateryRepository.findById(eateryId)
                .orElseThrow(() -> new NotFoundEntityException("error.eatery.notFoundById"));

        UserEntity currentUser = userPrincipal.getUserEntity();

        LocalDateTime now = LocalDateTime.now();

        VoteEntity vote = voteRepository.findByDateAndUser(now.toLocalDate(), currentUser);

        if (now.toLocalTime().isAfter(LocalTime.of(11, 00, 00)) && vote != null) {
            throw new VoteException("vote.tooLate");
        } else {
            if (vote == null) {
                vote = new VoteEntity(eateryEntity, currentUser, now.toLocalDate());
            } else {
                vote.setEatery(eateryEntity);
            }
            voteRepository.save(vote);
        }

        VoteResponse voteResponse = new VoteResponse();
        voteResponse.setDate(now.toLocalDate());
        voteResponse.setEateryId(eateryEntity.getId());
        voteResponse.setPublicUserId(currentUser.getPublicUserId());
        voteResponse.setResultOfVote(messages.getMessage("vote.success", request.getLocale()));

        return voteResponse;

    }

}
