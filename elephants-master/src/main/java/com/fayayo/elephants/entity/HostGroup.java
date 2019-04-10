package com.fayayo.elephants.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
@Table(name = "host_group")
public class HostGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "effective", nullable = false)
    private Integer effective;


    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;


    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    @Basic
    @Column(name = "description", nullable = false)
    private String description;



}
