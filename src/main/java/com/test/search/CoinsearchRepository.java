package com.test.search;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinsearchRepository extends JpaRepository<tbl_cryptocurrency, String>{

	public tbl_cryptocurrency findByCname(String cname);
}
