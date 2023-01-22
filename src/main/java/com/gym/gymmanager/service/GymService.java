package com.gym.gymmanager.service;

import com.gym.gymmanager.model.Batch;
import com.gym.gymmanager.model.Member;
import com.gym.gymmanager.model.MemberAttendance;
import com.gym.gymmanager.model.Plan;
import com.gym.gymmanager.repository.GymRepository;
import com.gym.gymmanager.request.MemberDetails;
import com.gym.gymmanager.response.APiResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    public List<MemberAttendance> getAttendanceList() {

        List<MemberAttendance> attendances;
        attendances = repository.getAttendance();
        if (!CollectionUtils.isEmpty(attendances)) {
            return attendances;

        } else {
            return new ArrayList<MemberAttendance>();
        }


    }

    public List<Member> getMembers() {

        List<Member> members;
        members = repository.getMembers();
        if (!CollectionUtils.isEmpty(members)) {
            return members;

        } else {
            return new ArrayList<Member>();
        }

    }

    public List<Batch> getBatch() {
        List<Batch> batches;
        batches = repository.getBatch();
        if (!CollectionUtils.isEmpty(batches)) {
            return batches;

        } else {
            return new ArrayList<Batch>();
        }

    }

    public List<Plan> getPlan() {
        List<Plan> plans;
        plans = repository.getPlan();
        if (!CollectionUtils.isEmpty(plans)) {
            return plans;

        } else {
            return new ArrayList<Plan>();
        }
    }
}
