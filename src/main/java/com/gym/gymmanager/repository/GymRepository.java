package com.gym.gymmanager.repository;

import com.gym.gymmanager.model.*;
import com.gym.gymmanager.request.LoginReq;
import com.gym.gymmanager.request.MemberDetails;
import com.gym.gymmanager.response.APiResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class GymRepository {
    @Autowired
    EntityManager entityManager;


    public APiResp addMember(MemberDetails memberDetails) {
        APiResp resp = new APiResp();
        String planId = getPlanIdByName(memberDetails.getMemberPlan());
        String batchId = getBatchIdByName(memberDetails.getMemberBatch());
        String sql = "INSERT INTO practice.gym_member\n" + "(member_name, email, mobile, dob, gender, weight, plan_id, batch_id,member_joining_date,plan_taken_date,member_active)\n" + "VALUES('" + memberDetails.getMemberName() + "', '" + memberDetails.getMemberEmail() + "', '" + memberDetails.getMemberMobile() + "'," + " '" + memberDetails.getMemberDob() + "', '" + memberDetails.getMemberGender() + "', '" + memberDetails.getMemberWeight() + "'" + ",'" + planId + "', '" + batchId + "'" + ",'" + LocalDate.now() + "','" + LocalDate.now() + "','1')";
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

    private String getBatchIdByName(String memberBatch) {
        String sql = "SELECT batch_id  from gym_batch gb where batch_name ='" + memberBatch + "' and batch_active ='1'";
        try {
            List<Tuple> datalist = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(datalist)) {
                return String.valueOf(datalist.get(0).get("batch_id"));
            }

        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    private String getPlanIdByName(String memberPlan) {
        String sql = "SELECT plan_id  from gym_plan gp where plan_name ='" + memberPlan + "' and plan_active ='1' ";
        try {
            List<Tuple> datalist = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(datalist)) {
                return String.valueOf(datalist.get(0).get("plan_id"));
            }

        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
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

    public List<MemberAttendance> getAttendance(String ownerId) {
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

    public List<Member> getMembers(String ownerId) {
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

    public List<Batch> getBatch(String ownerId) {
        List<Batch> batchList = new ArrayList<>();
        String sql = "SELECT batch_id, batch_name, batch_limit, batch_start_time, batch_end_time, batch_active\n" + "FROM practice.gym_batch";
        try {
            List<Tuple> datalist = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(datalist)) {
                for (Tuple tuple : datalist) {
                    Batch batch = new Batch();
                    batch.setBatchId(String.valueOf(tuple.get("batch_id")));
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

    public List<Plan> getPlan(String ownerId) {
        List<Plan> planList = new ArrayList<>();
        String sql = "SELECT plan_id, plan_name, plan_fees, plan_duration, plan_desc, plan_active\n" + "FROM practice.gym_plan";
        try {
            List<Tuple> datalist = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(datalist)) {
                for (Tuple tuple : datalist) {
                    Plan plan = new Plan();
                    plan.setPlanId(String.valueOf(tuple.get("plan_id")));
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

    public APiResp updatePlan(Plan planDetails) {
        APiResp resp = new APiResp();
        String sql = "UPDATE practice.gym_plan\n" + "SET plan_name='" + planDetails.getPlanName() + "', plan_fees='" + planDetails.getPlanPrice() + "', plan_duration='" + planDetails.getPlanDuration() + "', plan_desc='" + planDetails.getPlanDescription() + "'\n" + "WHERE plan_id ='" + planDetails.getPlanId() + "'";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("Plan updated successfully...");
        return resp;
    }

    public APiResp updateBatch(Batch batchDetails) {
        APiResp resp = new APiResp();
        String sql = "UPDATE practice.gym_batch\n" + "SET batch_name='" + batchDetails.getBatchName() + "', batch_limit='" + batchDetails.getLimit() + "'," + " batch_start_time='" + batchDetails.getBatchStartTime() + "', batch_end_time='" + batchDetails.getBatchEndTime() + "'\n" + "WHERE batch_id='" + batchDetails.getBatchId() + "'";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("Batch updated successfully...");
        return resp;

    }

    public APiResp checkLogin(LoginReq req) {
        APiResp resp = new APiResp();
        String sql = "SELECT * from gym_owner_member gom where owner_email ='" + req.getUserEmailId() + "' and owner_password ='" + req.getPassword() + "' and owner_active ='1'\n";
        try {
            List<Tuple> tupleList = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(tupleList)) {
                resp.setError("false");
                resp.setMsg("Login Success..");
            } else {
                resp.setError("true");
                resp.setMsg("Incorrect email or password ");
            }
        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        return resp;

    }

    public APiResp addExpense(GymExpense expenseDetails) {
        APiResp resp = new APiResp();
        String sql = "INSERT INTO practice.gym_expense\n" +
                "(expense_title, expense_amount, expense_date)\n" +
                "VALUES('" + expenseDetails.getExpense_title() + "', '" + expenseDetails.getExpense_amount() + "', '" + expenseDetails.getExpense_date() + "')";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();

        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("Expense added successfully...");
        return resp;
    }

    public APiResp addEnquiryMember(EnquiryMember enquiryMemberDetails) {
        APiResp resp = new APiResp();
        String sql = "INSERT INTO practice.gym_enqiury_member\n" +
                "(member_name, member_email, member_gender, enquiry_date, enquiry_desc)\n" +
                "VALUES('" + enquiryMemberDetails.getMember_name() + "', '" + enquiryMemberDetails.getMember_email() + "'," +
                " '" + enquiryMemberDetails.getMember_gender() + "', '" + enquiryMemberDetails.getEnquiry_date() + "', '" + enquiryMemberDetails.getEnquiry_desc() + "')";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();

        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("EnquiryMember added successfully...");
        return resp;
    }

    public APiResp updateEnquiryMember(EnquiryMember enquiryMemberDetails) {
        APiResp resp = new APiResp();
        String sql = "UPDATE practice.gym_enqiury_member\n" +
                "SET member_name='" + enquiryMemberDetails.getMember_name() + "', member_email='" + enquiryMemberDetails.getMember_email() + "', member_gender='" + enquiryMemberDetails.getMember_gender() + "'," +
                " enquiry_date='" + enquiryMemberDetails.getEnquiry_date() + "', enquiry_desc='" + enquiryMemberDetails.getEnquiry_desc() + "'\n" +
                "WHERE member_id=" + enquiryMemberDetails.getMember_id() + "";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("EnquiryMember updated successfully...");
        return resp;
    }

    public APiResp updateGymOwner(GymOwner gymOwnerDetails) {
        APiResp resp = new APiResp();
        String sql = "UPDATE practice.gym_owner_member\n" +
                "SET owner_name='" + gymOwnerDetails.getOwner_name() + "', owner_email='" + gymOwnerDetails.getOwner_email() + "', owner_password='" + gymOwnerDetails.getOwner_password() + "'," +
                " owner_active='" + gymOwnerDetails.getOwner_active() + "'" +
                "WHERE owner_id=" + gymOwnerDetails.getOwner_id() + "";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("GymOwner updated successfully...");
        return resp;
    }

    public APiResp updateExpense(GymExpense expenseDetails) {
        APiResp resp = new APiResp();
        String sql = "UPDATE practice.gym_expense\n" +
                "SET expense_title='" + expenseDetails.getExpense_title() + "', expense_amount='" + expenseDetails.getExpense_amount() + "', expense_date='" + expenseDetails.getExpense_date() + "'\n" +
                "WHERE expense_id=" + expenseDetails.getExpense_id() + "";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("Expense updated successfully...");
        return resp;
    }

    public APiResp addGymOwner(GymOwner gymOwnerDetails) {
        APiResp resp = new APiResp();
        String sql = "INSERT INTO practice.gym_owner_member\n" +
                "(owner_name, owner_email, owner_password, owner_active, is_password_reset)\n" +
                "VALUES('" + gymOwnerDetails.getOwner_name() + "', '" + gymOwnerDetails.getOwner_email() + "', '" + gymOwnerDetails.getOwner_password() + "', '1', '0');";
        try {
            entityManager.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.getStackTrace();
            resp.setError("true");
            resp.setMsg("Some Exception while Execution.");
        }
        resp.setError("false");
        resp.setMsg("GymOwner added successfully...");
        return resp;
    }

    public GymOwner getGymOwner(String ownerId) {
        GymOwner gymOwner = new GymOwner();
        String sql = "";
        try {
            List<Tuple> list = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            if (!CollectionUtils.isEmpty(list)) {
                Tuple gymOwnerDetails = list.get(0);
                gymOwner.setOwner_id(String.valueOf(gymOwnerDetails.get("")));
                gymOwner.setOwner_email(String.valueOf(gymOwnerDetails.get("")));
                gymOwner.setOwner_active(String.valueOf(gymOwnerDetails.get("")));
                gymOwner.setOwner_name(String.valueOf(gymOwnerDetails.get("")));
                gymOwner.setOwner_mobile(String.valueOf(gymOwnerDetails.get("")));
                gymOwner.setOwner_password(String.valueOf(gymOwnerDetails.get("")));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        return gymOwner;

    }
}
