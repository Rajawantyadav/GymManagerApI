package com.gym.gymmanager.controller;

import com.gym.gymmanager.model.Batch;
import com.gym.gymmanager.model.Member;
import com.gym.gymmanager.model.MemberAttendance;
import com.gym.gymmanager.model.Plan;
import com.gym.gymmanager.request.MemberDetails;
import com.gym.gymmanager.response.APiResp;
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
    GymService service;

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
    @GetMapping("getAttendance")
    public ResponseEntity<List<MemberAttendance>> getAttendance() {
        return new ResponseEntity<>(service.getAttendanceList(), HttpStatus.OK);
    }
    @GetMapping("getMembers")
    public ResponseEntity<List<Member>> getMembers() {
        return new ResponseEntity<>(service.getMembers(), HttpStatus.OK);
    }
    @GetMapping("getBatch")
    public ResponseEntity<List<Batch>> getBatch() {
        return new ResponseEntity<>(service.getBatch(), HttpStatus.OK);
    }
    @GetMapping("getPlan")
    public ResponseEntity<List<Plan>> getPlan() {
        return new ResponseEntity<>(service.getPlan(), HttpStatus.OK);
    }


}
