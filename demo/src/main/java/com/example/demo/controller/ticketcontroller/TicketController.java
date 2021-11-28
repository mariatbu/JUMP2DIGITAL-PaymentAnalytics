package com.example.demo.controller.ticketcontroller;

import javax.validation.Valid;

import com.example.demo.application.ticketapplication.TicketApplication;
import com.example.demo.dto.ticketdto.CreateUpdateTicketDTO;
import com.example.demo.dto.ticketdto.TicketDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/ticket")
public class TicketController {

    private final TicketApplication ticketApplication;

    @Autowired
    public TicketController(TicketApplication ticketApplication){
        this.ticketApplication = ticketApplication;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid final CreateUpdateTicketDTO dto){
        TicketDTO ticketDTO = this.ticketApplication.create(dto);
        return ResponseEntity.status(201).body(ticketDTO);
    }
    
}
