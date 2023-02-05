package com.gym.gymmanager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymExpense {
    String expense_id;
    String expense_title;
    String expense_amount;
    String expense_date;

    String ownerId;

}
