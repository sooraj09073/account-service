package com.sooraj.accountservice.services;

import com.sooraj.accountservice.dto.UserAddressDTO;
import com.sooraj.accountservice.exceptions.NoDataChangeException;
import com.sooraj.accountservice.models.AccountUser;
import com.sooraj.accountservice.models.UserAddress;
import com.sooraj.accountservice.repo.UserAddressRepository;
import com.sooraj.accountservice.repo.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAddressService {

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    public UserAddressDTO postUserAddress(UserAddressDTO userAddressDTO, String user) {

        AccountUser accountUser = userProfileRepository.findByEmailId(user);
        UserAddress userAddress = userAddressRepository.findByAccountUserUserId(accountUser.getUserId());
        if(userAddress == null) {
           userAddress = new UserAddress();
        }else if(isSameData(userAddress, userAddressDTO)){
            throw new NoDataChangeException("No changes detected in the data. No update required");
        }
        userAddress.setAddressLine1(userAddressDTO.addressLine1());
        userAddress.setAddressLine2(userAddressDTO.addressLine2());
        userAddress.setCity(userAddressDTO.city());
        userAddress.setState(userAddressDTO.state());
        userAddress.setCountry(userAddressDTO.country());
        userAddress.setPostalCode(userAddressDTO.postalCode());
        userAddress.setAccountUser(accountUser);

        UserAddress updatedAddress = userAddressRepository.save(userAddress);

        return new UserAddressDTO(
                updatedAddress.getAddressLine1(),
                updatedAddress.getAddressLine2(),
                updatedAddress.getCity(),
                updatedAddress.getState(),
                updatedAddress.getCountry(),
                updatedAddress.getPostalCode()
        );
    }

    private boolean isSameData(UserAddress userAddress, UserAddressDTO userAddressDTO) {
        return userAddress.getAddressLine1().equals(userAddressDTO.addressLine1())
                && userAddress.getAddressLine2().equals(userAddressDTO.addressLine2())
                && userAddress.getCity().equals(userAddressDTO.city())
                && userAddress.getState().equals(userAddressDTO.state())
                && userAddress.getCountry().equals(userAddressDTO.country())
                && userAddress.getPostalCode().equals(userAddressDTO.postalCode());
    }
}