package com.sooraj.accountservice.repo;

import com.sooraj.accountservice.models.UserAuthentication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends CrudRepository<UserAuthentication, Long> {

    UserAuthentication findByUserId(String userId);
}
