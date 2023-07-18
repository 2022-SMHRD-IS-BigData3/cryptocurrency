package com.test.login;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.login.tbl_user;

@Repository
public interface DashboardRepository extends JpaRepository<tbl_user, String>{

	public tbl_user findUserByUidAndUpw(String uid, String upw);
}
