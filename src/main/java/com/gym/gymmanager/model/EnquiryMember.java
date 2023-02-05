package com.gym.gymmanager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnquiryMember {
    String member_id;
    String member_name;
    String member_email;
    String member_mobile;
    String member_gender;
    String enquiry_date;
    String enquiry_desc;

    String ownerId;
}
