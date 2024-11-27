package com.sooraj.accountservice.repo;

import com.sooraj.accountservice.models.UserAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends CrudRepository<UserAddress, Long> {
    UserAddress findByAccountUserUserId(Long userId);
}
