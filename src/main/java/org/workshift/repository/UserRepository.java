package org.workshift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workshift.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
