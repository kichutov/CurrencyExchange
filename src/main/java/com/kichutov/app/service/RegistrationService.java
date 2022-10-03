package com.kichutov.app.service;

import com.kichutov.app.dto.AppUserDto;
import com.kichutov.app.exception.AppUserRegistrationException;
import com.kichutov.app.model.AppUser;
import com.kichutov.app.model.Role;
import com.kichutov.app.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationService {

    private static Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(AppUserRepository appUserRepository,
                               PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void validateAppUserDto(AppUserDto appUserDto) {
        if (!appUserDto.getPassword().equals(appUserDto.getMatchingPassword())) {
            throw new AppUserRegistrationException("Пароли не совпадают");
        }
    }

    public void registerNewAppUserAccount(AppUserDto appUserDto) {
        if (isAppUserExists(appUserDto.getUsername())) {
            throw new AppUserRegistrationException(String.format("Пользователь с email %s уже зарегистрирован", appUserDto.getUsername()));
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(appUserDto.getUsername());
        appUser.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        appUser.setAuthorities(Collections.singleton(Role.USER));

        appUserRepository.save(appUser);
        LOGGER.info("Новый пользователь успешно зарегистрирован: {}", appUser);
    }

    private boolean isAppUserExists(String username) {
        return appUserRepository.findByUsername(username).isPresent();
    }
}
