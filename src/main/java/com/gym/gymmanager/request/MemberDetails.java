package com.gym.gymmanager.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetails {
    String memberName;
    String memberEmail;
    String memberMobile;
    String memberDob;
    String memberGender;
    String memberBatch;
    String memberPlan;
    String memberWeight;


}
