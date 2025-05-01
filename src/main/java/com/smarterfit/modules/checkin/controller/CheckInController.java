/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.checkin.entity.CheckIn;
import com.smarterfit.modules.checkin.service.CheckInService;

/// TODO: Remover validações do controller: Handler de erros já faz isso

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