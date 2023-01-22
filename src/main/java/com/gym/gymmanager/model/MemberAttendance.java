package com.gym.gymmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAttendance {
    long memberAttendanceId;
    String memberName;
    String punchDate;
    String punchInTime;
    String punchOutTime;

   }
