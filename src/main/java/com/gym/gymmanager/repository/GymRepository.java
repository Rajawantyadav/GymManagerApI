package com.gym.gymmanager.repository;

import com.gym.gymmanager.model.Batch;
import com.gym.gymmanager.model.Member;
import com.gym.gymmanager.model.MemberAttendance;
import com.gym.gymmanager.model.Plan;
import com.gym.gymmanager.request.MemberDetails;
import com.gym.gymmanager.response.APiResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class GymRepository {
    @Autowired
    EntityManager entityManager;


    public APiResp addMember(MemberDetails memberDetails) {
        APiResp resp = new APiResp();
        String sql = "INSERT INTO practice.gym_member\n" + "(member_name, email, mobile, dob, gender, weight, plan_id, batch_id,member_joining_date,plan_taken_date,member_active)\n" +
                "VALUES('" + memberDetails.getMemberName() + "', '" + memberDetails.getMemberEmail() + "', '" + memberDetails.getMemberMobile() + "'," + " '" + memberDetails.getMemberDob() + "', '" + memberDetails.getMemberGender() + "', '" + memberDetails.getMemberWeight() + "'" + ",'" + memberDetails.getMemberPlan() + "', '" + memberDetails.getMemberBatch() + "'" +
                ",'" + LocalDate.now() + "','" + LocalDate.now() + "','1')";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();

        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("Member added successfully...");
        return resp;
    }

    public APiResp addPlan(Plan planDetails) {
        APiResp resp = new APiResp();
        String sql = "INSERT INTO practice.gym_plan\n" + "(plan_name, plan_fees, plan_duration, plan_desc, plan_active)\n" + "VALUES('" + planDetails.getPlanName() + "', '" + planDetails.getPlanPrice() + "', '" + planDetails.getPlanDuration() + "'" + ", '" + planDetails.getPlanDescription() + "', '" + planDetails.getPlanAcive() + "')";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();

        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("Plan added successfully...");
        return resp;
    }

    public APiResp addBatch(Batch batchDetails) {
        APiResp resp = new APiResp();
        String sql = "INSERT INTO practice.gym_batch\n" + "(batch_name, batch_limit, batch_start_time, batch_end_time, batch_active)\n" + "VALUES('" + batchDetails.getBatchName() + "', '" + batchDetails.getLimit() + "', '" + batchDetails.getBatchStartTime() + "', " + "'" + batchDetails.getBatchEndTime() + "', '" + batchDetails.getBatchActive() + "')\n";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();

        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("Batch added successfully...");
        return resp;
    }

    public List<MemberAttendance> getAttendance() {
        List<MemberAttendance> attendanceList = new ArrayList<>();
        String sql = "select member_name,punch_date,punch_in_datetime,punch_out_datetime from gym_member_attendance";
        try {
            List<Tuple> datalist = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(datalist)) {
                for (Tuple tuple : datalist) {
                    MemberAttendance attendance = new MemberAttendance();
                    attendance.setMemberName(String.valueOf(tuple.get("member_name")));
                    attendance.setPunchDate(String.valueOf(tuple.get("punch_date")));
                    attendance.setPunchInTime(String.valueOf(tuple.get("punch_in_datetime")));
                    attendance.setPunchOutTime(String.valueOf(tuple.get("punch_out_datetime")));
                    attendanceList.add(attendance);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        return attendanceList;
    }

    public List<Member> getMembers() {
        List<Member> memberList = new ArrayList<>();
        String sql = "SELECT member_id, member_name, email, mobile, dob, gender, weight, plan_id, batch_id, member_active,member_joining_date,member_exit_date,plan_taken_date\n" + "FROM practice.gym_member where member_active='1'";
        try {
            List<Tuple> datalist = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(datalist)) {
                for (Tuple tuple : datalist) {
                    String planExpiredDate = getPlanExpireDate(String.valueOf(tuple.get("plan_taken_date")), String.valueOf(tuple.get("plan_id")));
                    String planName = getPlanName(String.valueOf(tuple.get("plan_id")));
                    Member member = new Member();
                    member.setMemberId(Long.parseLong(String.valueOf(tuple.get("member_id"))));
                    member.setMemberName(String.valueOf(tuple.get("member_name")));
                    member.setJoiningDate(String.valueOf(tuple.get("member_joining_date")));
                    member.setActivePlanName(planName);
                    member.setPlanExpireDate(planExpiredDate);
                    memberList.add(member);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        return memberList;

    }

    private String getPlanExpireDate(String memberJoiningDate, String planId) {



        String sql = "SELECT plan_name ,plan_duration  from gym_plan gp where plan_id ='" + planId + "'";
        try {
            List<Tuple> tupleList = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(tupleList)) {
                String planDuration = String.valueOf(tupleList.get(0).get("plan_duration"));
                return String.valueOf(LocalDate.parse(memberJoiningDate).plusDays(Long.parseLong(planDuration)));

            }

        } catch (Exception e) {
            e.getStackTrace();
        }


        return null;
    }

    private String getPlanName(String planId) {

        String sql = "SELECT plan_name  from gym_plan gp where plan_id ='" + planId + "'";
        try {
            List<Tuple> tupleList = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(tupleList)) {
                return String.valueOf(tupleList.get(0).get("plan_name"));
            }

        } catch (Exception e) {
            e.getStackTrace();
        }


        return null;
    }

    public List<Batch> getBatch() {
        List<Batch> batchList = new ArrayList<>();
        String sql = "SELECT batch_id, batch_name, batch_limit, batch_start_time, batch_end_time, batch_active\n" +
                "FROM practice.gym_batch";
        try {
            List<Tuple> datalist = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(datalist)) {
                for (Tuple tuple : datalist) {
                    Batch batch = new Batch();
                    batch.setBatchName(String.valueOf(tuple.get("batch_name")));
                    batch.setBatchStartTime(String.valueOf(tuple.get("batch_start_time")));
                    batch.setBatchEndTime(String.valueOf(tuple.get("batch_end_time")));
                    batch.setLimit(String.valueOf(tuple.get("batch_limit")));
                    batchList.add(batch);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        return batchList;
    }

    public List<Plan> getPlan() {
        List<Plan> planList = new ArrayList<>();
        String sql = "SELECT plan_id, plan_name, plan_fees, plan_duration, plan_desc, plan_active\n" +
                "FROM practice.gym_plan";
        try {
            List<Tuple> datalist = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(datalist)) {
                for (Tuple tuple : datalist) {
                    Plan plan = new Plan();
                    plan.setPlanName(String.valueOf(tuple.get("plan_name")));
                    plan.setPlanPrice(String.valueOf(tuple.get("plan_fees")));
                    plan.setPlanDuration(String.valueOf(tuple.get("plan_duration")));
                    plan.setPlanDescription(String.valueOf(tuple.get("plan_desc")));
                    planList.add(plan);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        return planList;

    }
}
