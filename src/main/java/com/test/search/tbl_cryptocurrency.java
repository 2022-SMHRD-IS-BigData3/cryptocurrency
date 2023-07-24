package com.test.search;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.test.login.tbl_user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class tbl_cryptocurrency {

	@Id
	private String cid;
	private String cname;
	
	
	
}
