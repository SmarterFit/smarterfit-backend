package com.smarterfit.modules.useraccess.service;

import com.smarterfit.common.util.CryptoUtil;
import com.smarterfit.common.util.SensitiveDataDecryptor;
import com.smarterfit.modules.useraccess.dto.request.user.CreateUserRequestDTO;
import com.smarterfit.modules.useraccess.dto.request.user.UpdateUserEmailRequestDTO;
import com.smarterfit.modules.useraccess.dto.request.user.UpdateUserPasswordRequestDTO;
import com.smarterfit.modules.useraccess.dto.request.user.UpdateUserRolesRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import com.smarterfit.modules.useraccess.entity.Profile;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.entity.UserRole;
import com.smarterfit.modules.useraccess.mapper.UserMapper;
import com.smarterfit.modules.useraccess.repository.UserRepository;
import com.smarterfit.modules.useraccess.validation.ProfileValidation;
import com.smarterfit.modules.useraccess.validation.UserValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidation userValidation;
    private final ProfileValidation profileValidation;
    private final PasswordEncoder passwordEncoder;
    private final CryptoUtil cryptoUtil;
    private final SensitiveDataDecryptor sensitiveDataDecryptor;

    @Autowired
    public UserService(UserRepository userRepository,
            UserValidation userValidation,
            ProfileValidation profileValidation,
            PasswordEncoder passwordEncoder,
            CryptoUtil cryptoUtil,
            SensitiveDataDecryptor sensitiveDataDecryptor) {
        this.userRepository = userRepository;
        this.userValidation = userValidation;
        this.profileValidation = profileValidation;
        this.passwordEncoder = passwordEncoder;
        this.cryptoUtil = cryptoUtil;
        this.sensitiveDataDecryptor = sensitiveDataDecryptor;
    }

    @Transactional
    public UserResponseDTO createUser(CreateUserRequestDTO requestDTO) {
        String encryptedCpf = cryptoUtil.encrypt(requestDTO.getCpf());

        userValidation.validatePasswords(requestDTO.getPassword(), requestDTO.getConfirmPassword());
        userValidation.validateEmailAvailability(requestDTO.getEmail());
        profileValidation.validateCpfAvailability(encryptedCpf);

        User user = UserMapper.toEntity(requestDTO, new Profile());

        String encryptedPassword = passwordEncoder.encode(requestDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.getProfile().setCpf(encryptedCpf);
        userRepository.save(user);

        return sensitiveDataDecryptor.decrypt(UserMapper.toResponse(user));
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(UUID id) {
        User user = userValidation.validateUserById(id);
        return sensitiveDataDecryptor.decrypt(UserMapper.toResponse(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> searchUsersByEmail(String emailPart) {
        return userRepository.findByEmailContainingIgnoreCase(emailPart).stream()
                .map(UserMapper::toResponse)
                .map(sensitiveDataDecryptor::decrypt)
                .toList();
    }

    @Transactional
    public UserResponseDTO updateUserEmailById(UUID id, UpdateUserEmailRequestDTO requestDTO) {
        User user = userValidation.validateUserById(id);

        if (!user.getEmail().equals(requestDTO.getEmail())) {
            userValidation.validateEmailAvailability(requestDTO.getEmail());

            user.setEmail(requestDTO.getEmail());
            userRepository.save(user);
        }

        return sensitiveDataDecryptor.decrypt(UserMapper.toResponse(user));
    }

    @Transactional
    public UserResponseDTO updateUserPasswordById(UUID id, UpdateUserPasswordRequestDTO requestDTO) {
        User user = userValidation.validateUserById(id);

        userValidation.validateCurrentPassword(user, requestDTO.getCurrentPassword());
        userValidation.validatePasswords(requestDTO.getNewPassword(), requestDTO.getConfirmNewPassword());

        String encryptedPassword = passwordEncoder.encode(requestDTO.getNewPassword());
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        return sensitiveDataDecryptor.decrypt(UserMapper.toResponse(user));
    }

    @Transactional
    public UserResponseDTO updateUserRolesById(UUID id, UpdateUserRolesRequestDTO requestDTO) {
        User user = userValidation.validateUserById(id);

        user.getRoles().clear();
        user.getRoles().addAll(requestDTO.getRoles().stream()
                .map(roleType -> {
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRoleType(roleType);
                    return userRole;
                })
                .collect(Collectors.toList()));

        userRepository.save(user);

        return sensitiveDataDecryptor.decrypt(UserMapper.toResponse(user));
    }

    @Transactional
    public void deleteUserById(UUID id) {
        User user = userValidation.validateUserById(id);
        userRepository.delete(user);
    }
}
