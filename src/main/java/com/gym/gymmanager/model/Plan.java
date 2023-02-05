package com.gym.gymmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    String planId;
    String planName;
    String planPrice;
    String planDuration;
    String planDescription;
    String planAcive;
    String ownerId;


}
