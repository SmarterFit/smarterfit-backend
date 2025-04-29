package com.smarterfit.controller;

import com.smarterfit.model.CheckIn;
import com.smarterfit.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/checkins")
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @PostMapping("/checkin")
    public ResponseEntity<CheckIn> doCheckIn(@RequestParam UUID userId) {
        try {
            CheckIn checkIn = checkInService.doCheckIn(userId);
            return new ResponseEntity<>(checkIn, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<CheckIn> doCheckOut(@RequestParam UUID userId) {
        try {
            CheckIn checkIn = checkInService.doCheckOut(userId);
            return new ResponseEntity<>(checkIn, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
