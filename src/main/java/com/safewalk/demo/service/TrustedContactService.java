package com.safewalk.demo.service;

import com.safewalk.demo.model.TrustedContact;
import com.safewalk.demo.model.User;
import com.safewalk.demo.repository.TrustedContactRepository;
import com.safewalk.demo.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrustedContactService {

    private final TrustedContactRepository trustedContactRepository;
    private final UserRepository userRepository;

    public TrustedContactService(
            TrustedContactRepository trustedContactRepository,
            UserRepository userRepository) {

        this.trustedContactRepository = trustedContactRepository;
        this.userRepository = userRepository;
    }

    public TrustedContact addContact(
            Long userId,
            String name,
            String phone,
            String relationship) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        TrustedContact contact = new TrustedContact();

        contact.setUser(user);
        contact.setName(name);
        contact.setPhone(phone);
        contact.setRelationship(relationship);

        return trustedContactRepository.save(contact);
    }

    public List<TrustedContact> getContacts(Long userId) {
        return trustedContactRepository.findByUserId(userId);
    }

    public void deleteContact(Long contactId) {

        if (!trustedContactRepository.existsById(contactId)) {
            throw new RuntimeException("Trusted contact not found");
        }

        trustedContactRepository.deleteById(contactId);
    }
}