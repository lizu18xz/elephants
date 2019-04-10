package com.fayayo.elephants.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author dalizu on 2019/2/26.
 * @version v1.0
 * @desc
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_host")
public class WorkHost {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "host", nullable = false)
    private String host;

    @Basic
    @Column(name = "host_group_id", nullable = false)
    private Long hostGroupId;


    @Basic
    @Column(name = "domain", nullable = false)
    private String domain;


}
