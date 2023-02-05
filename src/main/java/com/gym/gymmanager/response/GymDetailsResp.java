package com.gym.gymmanager.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GymDetailsResp {
    String error;
    String msg;
    List<String> totalCollection;
    List<String> liveMembers;
    List<String> expiredMembers;
    List<String> expiredToday;
    List<String> expireInOneToFive;
    List<String> expireInSixToTen;
    List<String> expireInElevenToFifteen;
    List<String> unpaidMembers;
    List<String> birthdayToday;
    List<String> totalExpense;
    List<String> blockedMembers;

}
