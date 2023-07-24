package com.test.favorite;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class tbl_favorites {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //자동증가로 데이터 입력하도록 하는 annotation
	private int fav_seq;
	private String ccd;
	private String uid;
}
