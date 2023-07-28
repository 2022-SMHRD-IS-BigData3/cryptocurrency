package com.test.Alarm;


import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, Long> {
	
	ArrayList<Alarm> findByUid(String uid);
}
