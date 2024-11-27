package com.sooraj.accountservice.services;

import com.sooraj.accountservice.dto.RegisterRequest;
import com.sooraj.accountservice.dto.UserAddressDTO;
import com.sooraj.accountservice.dto.UserProfileDTO;
import com.sooraj.accountservice.exceptions.NoDataChangeException;
import com.sooraj.accountservice.models.AccountUser;
import com.sooraj.accountservice.models.UserAddress;
import com.sooraj.accountservice.models.UserAuthentication;
import com.sooraj.accountservice.models.UserRole;
import com.sooraj.accountservice.repo.AuthenticationRepository;
import com.sooraj.accountservice.repo.UserProfileRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    AuthenticationRepository authenticationRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    public void registerUser(RegisterRequest registerRequest) {
        AccountUser accountUser = new AccountUser();
        accountUser.setFirstName(registerRequest.firstName());
        accountUser.setLastName(registerRequest.lastName());
        accountUser.setEmailId(registerRequest.email());

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setUserId(registerRequest.email());
        userAuthentication.setPassword(bCryptPasswordEncoder.encode(registerRequest.password()));
        userAuthentication.setEnabled(true);

        UserRole userRole = new UserRole();
        userRole.setRoleName("USER");
        userRole.setUserAuthentication(userAuthentication);
        userAuthentication.setRoles(List.of(userRole));

        authenticationRepository.save(userAuthentication);
        userProfileRepository.save(accountUser);
    }

    public UserProfileDTO getProfile(String user) {
        return mapEntityToDto(userProfileRepository.findByEmailId(user));
    }

    public UserProfileDTO update(UserProfileDTO userProfileDTO, String user) throws BadRequestException {
        AccountUser accountUser = userProfileRepository.findByEmailId(user);
        if(accountUser == null) {
            throw new BadRequestException("User not found");
        }else if(!userProfileDTO.emailId().equals(user)){
            throw new BadRequestException("Email id mismatch");
        }else if(isSameData(accountUser, userProfileDTO)){
            throw new NoDataChangeException("No changes detected in the data. No update required.");
        }

        accountUser.setFirstName(userProfileDTO.firstName());
        accountUser.setLastName(userProfileDTO.lastName());
        accountUser.setPhoneNumber(userProfileDTO.phoneNumber());

        UserAddress address = accountUser.getUserAddress();
        address.setAddressLine1(userProfileDTO.address().addressLine1());
        address.setAddressLine2(userProfileDTO.address().addressLine2());
        address.setCity(userProfileDTO.address().city());
        address.setState(userProfileDTO.address().state());
        address.setCountry(userProfileDTO.address().country());
        address.setPostalCode(userProfileDTO.address().postalCode());
        accountUser.setUserAddress(address);
        return mapEntityToDto(userProfileRepository.save(accountUser));
    }

    private UserProfileDTO mapEntityToDto(AccountUser accountUser) {
        return new UserProfileDTO(
                accountUser.getFirstName(),
                accountUser.getLastName(),
                accountUser.getEmailId(),
                accountUser.getPhoneNumber(),
                new UserAddressDTO(accountUser.getUserAddress().getAddressLine1(),
                        accountUser.getUserAddress().getAddressLine2(),
                        accountUser.getUserAddress().getCity(),
                        accountUser.getUserAddress().getState(),
                        accountUser.getUserAddress().getCountry(),
                        accountUser.getUserAddress().getPostalCode())
        );
    }

    private boolean isSameData(AccountUser entity, UserProfileDTO dto) {
        return entity.getFirstName().equals(dto.firstName()) &&
                entity.getLastName().equals(dto.lastName())
                && entity.getEmailId().equals(dto.emailId())
                && entity.getPhoneNumber().equals(dto.phoneNumber())
                && entity.getUserAddress().getAddressLine1().equals(dto.address().addressLine1())
                && entity.getUserAddress().getAddressLine2().equals(dto.address().addressLine2())
                && entity.getUserAddress().getCity().equals(dto.address().city())
                && entity.getUserAddress().getState().equals(dto.address().state())
                && entity.getUserAddress().getCountry().equals(dto.address().country())
                && entity.getUserAddress().getPostalCode().equals(dto.address().postalCode());
    }
}
