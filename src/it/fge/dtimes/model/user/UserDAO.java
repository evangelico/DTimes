package it.fge.dtimes.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaRepository<UserDTO, Long> {

    @Query(value = "SELECT u FROM UserDTO u WHERE u.username = :username")
    UserDTO findByUsername(@Param("username") String username);

}
