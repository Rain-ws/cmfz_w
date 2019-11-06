package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "密码")
    private String password;
    @Excel(name = "盐")
    private String salt; //盐 MD5
    @Excel(name = "名称")
    private String nickname; //绰号，名称
    @Excel(name = "电话")
    private String phone; //电话
    @Excel(name = "省")
    private String province; //省
    @Excel(name = "城市")
    private String city; //城市
    @Excel(name = "签名")
    private String sign; //签名
    @Excel(name = "头像",type = 2)
    private String photo; //头像
    @Excel(name = "性别")
    private String sex;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间")
    private Data createDate;
    @Column(name="starid")
    private String starId;

}
