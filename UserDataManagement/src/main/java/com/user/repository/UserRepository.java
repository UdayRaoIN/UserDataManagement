package com.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Procedure(procedureName = "sp_insert_user")
    void saveUser(@Param("p_username") String username, @Param("p_department") String department, @Param("p_manager") String manager);

    @Procedure(procedureName = "sp_update_user")
    void updateUser(@Param("p_id") int id, @Param("p_username") String username, @Param("p_department") String department, @Param("p_manager") String manager);

    @Procedure(procedureName = "sp_delete_user")
    void deleteUser(@Param("p_id") int id);
    
   
    @Procedure(procedureName = "sp_retrieve_user")
    UserEntity retrieveUser(@Param("p_username") String username, @Param("p_department") String department, @Param("p_manager") String manager);
}