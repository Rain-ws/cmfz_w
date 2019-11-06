package com.baizhi;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmfzWApplicationTests {
    public static void main(String[] args) {
        //控制行数
        for(int i =1; i<10;i++){
            //用于控制每行中最大数值 与行数相等
            for(int j= 1;j<=i;j++){
                System.out.println(j+"*"+i+"="+j*i+"\t");
            }
        }
    }

}
