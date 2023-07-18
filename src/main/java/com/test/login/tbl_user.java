package com.test.login;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class tbl_user {

	@Id
	private String uid;
	private String upw;
	private String utelegramid;
	
}
