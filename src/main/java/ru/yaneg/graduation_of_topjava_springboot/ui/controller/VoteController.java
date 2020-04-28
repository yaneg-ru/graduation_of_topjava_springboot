package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("eateries")
public class VoteController extends AbstractController {

    @Autowired
    EateryRepository eateryRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    Messages messages;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(path = "/{eateryId}/votes", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Integer getCountVotesByEateryIdAndDate(@PathVariable Integer eateryId,
                                                  @RequestParam(value = "date") LocalDate date) {

        EateryEntity eateryEntity = eateryRepository.findById(eateryId)
                .orElseThrow(() -> new NotFoundEntityException("error.eatery.notFoundById"));

        return voteRepository.countAllByDateAndEatery(date, eateryEntity);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping(path = "/{eateryId}/votes",
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public VoteResponse vote(@PathVariable Integer eateryId, HttpServletRequest request) {

        EateryEntity eateryEntity = eateryRepository.findById(eateryId)
                .orElseThrow(() -> new NotFoundEntityException("error.eatery.notFoundById"));

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity currentUser = userPrincipal.getUserEntity();

        LocalDateTime now = LocalDateTime.now();
        VoteEntity vote = new VoteEntity();

        if (now.toLocalTime().isAfter(LocalTime.of(11, 00, 00))) {
            throw new VoteException("vote.TooLate");
        } else {
            vote = voteRepository.findByDateAndUser(now.toLocalDate(), currentUser);
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

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(path = "/votes", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<EateryEntity, Integer> getAllVotesByDate(@RequestParam(value = "date") LocalDate date) {

        Map<EateryEntity, Integer> returnValue = new HashMap<>();
        List<VoteEntity> voteEntityList = voteRepository.findAllByDate(date);

        returnValue = voteEntityList
                .stream()
                .collect(Collectors
                        .toMap(VoteEntity::getEatery, voteEntity -> 1, (oldValue, newValue) -> oldValue + newValue)
                );

        return returnValue;
    }
}
