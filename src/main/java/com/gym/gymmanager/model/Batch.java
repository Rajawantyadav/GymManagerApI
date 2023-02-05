package com.gym.gymmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
    String batchId;
    String batchName;
    String batchStartTime;
    String batchEndTime;
    String limit;
    String batchActive;

    String ownerId;


}
