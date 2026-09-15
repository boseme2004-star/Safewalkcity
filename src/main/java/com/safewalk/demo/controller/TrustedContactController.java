package com.safewalk.demo.controller;

import com.safewalk.demo.model.TrustedContact;
import com.safewalk.demo.service.TrustedContactService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trusted-contacts")
public class TrustedContactController {

    private final TrustedContactService trustedContactService;

    public TrustedContactController(
            TrustedContactService trustedContactService) {

        this.trustedContactService = trustedContactService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<TrustedContact> addContact(
            @PathVariable Long userId,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam(required = false) String relationship) {

        return ResponseEntity.ok(
                trustedContactService.addContact(
                        userId,
                        name,
                        phone,
                        relationship
                )
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TrustedContact>> getContacts(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                trustedContactService.getContacts(userId)
        );
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(
            @PathVariable Long contactId) {

        trustedContactService.deleteContact(contactId);

        return ResponseEntity.noContent().build();
    }
}