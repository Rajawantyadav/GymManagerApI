package com.gym.gymmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    long memberId;
    String memberName;
    String joiningDate;
    String activePlanName;
    String planExpireDate;

    String ownerId;


}
