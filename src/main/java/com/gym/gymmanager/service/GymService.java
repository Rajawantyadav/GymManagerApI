package com.gym.gymmanager.service;

import com.gym.gymmanager.model.*;
import com.gym.gymmanager.repository.GymRepository;
import com.gym.gymmanager.request.LoginReq;
import com.gym.gymmanager.request.MemberDetails;
import com.gym.gymmanager.response.APiResp;
import com.gym.gymmanager.response.GymDetailsResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class GymService {
    @Autowired
    GymRepository repository;

    public APiResp addMember(MemberDetails memberDetails) {
        APiResp resp = new APiResp();
        if (memberDetails != null) {
            resp = repository.addMember(memberDetails);

        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");

        }
        return resp;
    }

    public APiResp addPlan(Plan planDetails) {
        APiResp resp = new APiResp();
        if (planDetails != null) {
            resp = repository.addPlan(planDetails);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;

    }

    public APiResp addBatch(Batch batchDetails) {
        APiResp resp = new APiResp();
        if (batchDetails != null) {
            resp = repository.addBatch(batchDetails);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;
    }

    public List<MemberAttendance> getAttendanceList(String ownerId) {

        List<MemberAttendance> attendances;
        attendances = repository.getAttendance(ownerId);
        if (!CollectionUtils.isEmpty(attendances)) {
            return attendances;

        } else {
            return new ArrayList<MemberAttendance>();
        }


    }

    public List<Member> getMembers(String ownerId) {

        List<Member> members;
        members = repository.getMembers(ownerId);
        if (!CollectionUtils.isEmpty(members)) {
            return members;

        } else {
            return new ArrayList<Member>();
        }

    }

    public List<Batch> getBatch(String ownerId) {
        List<Batch> batches;
        batches = repository.getBatch(ownerId);
        if (!CollectionUtils.isEmpty(batches)) {
            return batches;

        } else {
            return new ArrayList<Batch>();
        }

    }

    public List<Plan> getPlan(String ownerId) {
        List<Plan> plans;
        plans = repository.getPlan(ownerId);
        if (!CollectionUtils.isEmpty(plans)) {
            return plans;

        } else {
            return new ArrayList<Plan>();
        }
    }

    public GymDetailsResp getGymDetails(String ownerId) {

        return null;
    }

    public APiResp updatePlan(Plan planDetails) {
        APiResp resp = new APiResp();
        if (planDetails != null) {
            resp = repository.updatePlan(planDetails);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;

    }

    public APiResp updateBatch(Batch batchDetails) {
        APiResp resp = new APiResp();
        if (batchDetails != null) {
            resp = repository.updateBatch(batchDetails);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;
    }

    public APiResp login(LoginReq req) {
        APiResp resp = new APiResp();
        if (req != null) {
            resp = repository.checkLogin(req);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;
    }

    public APiResp addExpense(GymExpense expenseDetails) {
        APiResp resp = new APiResp();
        if (expenseDetails != null) {
            resp = repository.addExpense(expenseDetails);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;
    }

    public APiResp addEnquiryMember(EnquiryMember enquiryMemberDetails) {
        APiResp resp = new APiResp();
        if (enquiryMemberDetails != null) {
            resp = repository.addEnquiryMember(enquiryMemberDetails);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;
    }

    public APiResp updateExpense(GymExpense expenseDetails) {
        APiResp resp = new APiResp();
        if (expenseDetails != null) {
            resp = repository.updateExpense(expenseDetails);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;
    }

    public APiResp updateGymOwner(GymOwner gymOwnerDetails) {
        APiResp resp = new APiResp();
        if (gymOwnerDetails != null) {
            resp = repository.updateGymOwner(gymOwnerDetails);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;
    }

    public APiResp updateEnquiryMember(EnquiryMember enquiryMemberDetails) {
        APiResp resp = new APiResp();
        if (enquiryMemberDetails != null) {
            resp = repository.updateEnquiryMember(enquiryMemberDetails);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;
    }

    public APiResp addGymOwner(GymOwner gymOwnerDetails) {
        APiResp resp = new APiResp();
        if (gymOwnerDetails != null) {
            resp = repository.addGymOwner(gymOwnerDetails);
        } else {
            resp.setError("true");
            resp.setMsg("Some Parameters missing ??.");
        }
        return resp;
    }

    public GymOwner getGymOwner(String ownerId) {

        if (StringUtils.hasText(ownerId)) {
            return repository.getGymOwner(ownerId);
        }
        return new GymOwner();
    }

    public List<EnquiryMember> getEnquiryMembers(String ownerId) {
        List<EnquiryMember> enquiryMembers;
        enquiryMembers = repository.getEnquiryMembers(ownerId);
        if (!CollectionUtils.isEmpty(enquiryMembers)) {
            return enquiryMembers;
        } else {
            return new ArrayList<EnquiryMember>();
        }
    }

    public List<GymExpense> getGymExpense(String ownerId) {
        List<GymExpense> gymExpenses;
        gymExpenses = repository.getGymExpense(ownerId);
        if (!CollectionUtils.isEmpty(gymExpenses)) {
            return gymExpenses;
        } else {
            return new ArrayList<GymExpense>();
        }

    }
}
