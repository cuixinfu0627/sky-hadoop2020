package com.sky.hadoop.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @name: AEntity <tb>
 * @title: 测试哈  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2021/1/13 9:33 <tb>
 */
@Data
public class AEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long abc;

}