package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.User;
import com.lych.cargomanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrentUserProvider implements UserProvider {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        final Authentication authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .orElseThrow(() -> new AccessDeniedException("Access denied."));
        final org.springframework.security.core.userdetails.User user =
            (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return userRepository.findOneByLogin(user.getUsername()).get();
    }
}
