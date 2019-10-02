package com.moro.brtitgbot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class KeepAwakeController {

    @GetMapping
    public ResponseEntity keepAwake() {
        return ResponseEntity.ok("I will not fall asleep!");
    }
}
