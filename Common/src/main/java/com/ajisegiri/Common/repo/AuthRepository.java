package com.ajisegiri.Common.repo;

import com.ajisegiri.Common.model.hashes.Auth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends CrudRepository<Auth,String> {
}
