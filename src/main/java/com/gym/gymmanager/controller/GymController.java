package com.gym.gymmanager.controller;

import com.gym.gymmanager.model.*;
import com.gym.gymmanager.request.LoginReq;
import com.gym.gymmanager.request.MemberDetails;
import com.gym.gymmanager.response.APiResp;
import com.gym.gymmanager.response.GymDetailsResp;
import com.gym.gymmanager.service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GymController {
    @Autowired
    private GymService service;
//get number of request details endpoint

    //    http://localhost:8080/actuator/metrics/http.server.requests
    @PostMapping("login")
    public ResponseEntity<APiResp> login(@RequestBody LoginReq req) {
        return new ResponseEntity<>(service.login(req), HttpStatus.OK);
    }

    @PostMapping("addMember")
    public ResponseEntity<APiResp> addMember(@RequestBody MemberDetails memberDetails) {
        return new ResponseEntity<>(service.addMember(memberDetails), HttpStatus.OK);
    }

    @PostMapping("addPlan")
    public ResponseEntity<APiResp> addPlan(@RequestBody Plan planDetails) {
        return new ResponseEntity<>(service.addPlan(planDetails), HttpStatus.OK);
    }


    @PostMapping("addBatch")
    public ResponseEntity<APiResp> addBatch(@RequestBody Batch batchDetails) {
        return new ResponseEntity<>(service.addBatch(batchDetails), HttpStatus.OK);
    }

    @PostMapping("addExpense")
    public ResponseEntity<APiResp> addExpense(@RequestBody GymExpense expenseDetails) {
        return new ResponseEntity<>(service.addExpense(expenseDetails), HttpStatus.OK);
    }

    @PostMapping("addEnquiryMember")
    public ResponseEntity<APiResp> addEnquiryMember(@RequestBody EnquiryMember enquiryMemberDetails) {
        return new ResponseEntity<>(service.addEnquiryMember(enquiryMemberDetails), HttpStatus.OK);
    }

    @PostMapping("addGymOwner")
    public ResponseEntity<APiResp> addGymOwner(@RequestBody GymOwner gymOwnerDetails) {
        return new ResponseEntity<>(service.addGymOwner(gymOwnerDetails), HttpStatus.OK);
    }

    @PostMapping("updatePlan")
    public ResponseEntity<APiResp> updatePlan(@RequestBody Plan planDetails) {
        return new ResponseEntity<>(service.updatePlan(planDetails), HttpStatus.OK);
    }

    @PostMapping("updateExpense")
    public ResponseEntity<APiResp> updateExpense(@RequestBody GymExpense expenseDetails) {
        return new ResponseEntity<>(service.updateExpense(expenseDetails), HttpStatus.OK);
    }

    @PostMapping("updateGymOwner")
    public ResponseEntity<APiResp> updateExpense(@RequestBody GymOwner gymOwnerDetails) {
        return new ResponseEntity<>(service.updateGymOwner(gymOwnerDetails), HttpStatus.OK);
    }

    @PostMapping("updateEnquiryMember")
    public ResponseEntity<APiResp> updateExpense(@RequestBody EnquiryMember enquiryMemberDetails) {
        return new ResponseEntity<>(service.updateEnquiryMember(enquiryMemberDetails), HttpStatus.OK);
    }

    @PostMapping("updateBatch")
    public ResponseEntity<APiResp> updateBatch(@RequestBody Batch batchDetails) {
        return new ResponseEntity<>(service.updateBatch(batchDetails), HttpStatus.OK);
    }

    @GetMapping("getAttendance/{ownerId}")
    public ResponseEntity<List<MemberAttendance>> getAttendance(@PathVariable String ownerId) {
        return new ResponseEntity<>(service.getAttendanceList(ownerId), HttpStatus.OK);
    }

    @GetMapping("getMembers/{ownerId}")
    public ResponseEntity<List<Member>> getMembers(@PathVariable String ownerId) {
        return new ResponseEntity<>(service.getMembers(ownerId), HttpStatus.OK);
    }

    @GetMapping("getEnquiryMembers/{ownerId}")
    public ResponseEntity<List<EnquiryMember>> getEnquiryMembers(@PathVariable String ownerId) {
        return new ResponseEntity<>(service.getEnquiryMembers(ownerId), HttpStatus.OK);
    }

    @GetMapping("getBatch/{ownerId}")
    public ResponseEntity<List<Batch>> getBatch(@PathVariable String ownerId) {
        return new ResponseEntity<>(service.getBatch(ownerId), HttpStatus.OK);
    }

    @GetMapping("getPlan/{ownerId}")
    public ResponseEntity<List<Plan>> getPlan(@PathVariable String ownerId) {
        return new ResponseEntity<>(service.getPlan(ownerId), HttpStatus.OK);
    }

    @GetMapping("getGymDetails/{ownerId}")
    public ResponseEntity<GymDetailsResp> getGymDetails(@PathVariable String ownerId) {
        return new ResponseEntity<>(service.getGymDetails(ownerId), HttpStatus.OK);
    }

    @GetMapping("getGymOwner/{ownerId}")
    public ResponseEntity<GymOwner> getGymOwner(@PathVariable String ownerId) {
        return new ResponseEntity<>(service.getGymOwner(ownerId), HttpStatus.OK);
    }

    @GetMapping("getGymExpense/{ownerId}")
    public ResponseEntity<List<GymExpense>> getGymExpense(@PathVariable String ownerId) {
        return new ResponseEntity<>(service.getGymExpense(ownerId), HttpStatus.OK);
    }
    @GetMapping("getMsg")
    public ResponseEntity<String> getMsg(@PathVariable String ownerId) {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }


}
