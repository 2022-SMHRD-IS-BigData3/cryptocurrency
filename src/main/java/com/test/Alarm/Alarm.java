package com.test.Alarm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmseq; // alarm_seq

    private String ccd; // crypto_cd
    private int price; // price
    @Column(insertable = false, columnDefinition = "datetime default now()", updatable = false)
    private Date alarmat; // alarm_at
    private String uid; // user_id
    // 생성자, getter, setter, 기타 필요한 메소드를 작성합니다.

    // 예시로 간단히 작성한 것이므로 필요에 따라 데이터를 추가할 수 있습니다.
}
