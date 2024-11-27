package com.sooraj.accountservice.repo;

import com.sooraj.accountservice.models.AccountUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<AccountUser, String> {
    AccountUser findByEmailId(String email);
}
