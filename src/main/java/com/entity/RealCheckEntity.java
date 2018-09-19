package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
*@ClassName:RealCheckEntity
 @Description:TODO
 @Author:
 @Date:2018/9/18 15:08 
 @Version:v1.0
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealCheckEntity {
    private int id;
    private String phone;
    private String idCard;
    private String  idCardPositive;
    private String  idCardNegative;
    private String  idCardHand;
    private int status;
}
