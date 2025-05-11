package com.example.file_mannager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class result <T>{
    private Integer code;//业务状态码 1成功 0失败
    private String message;//提示信息
    private T data;//相应数据

    public static <E>result<E> success(E data){
        return new result<>(1,"操作成功",data);
    }
    public static result success(){
        return new result(1,"操作成功",null);
    }
    public static result error(String message){
        return new result(0,message,null);
    }

}
