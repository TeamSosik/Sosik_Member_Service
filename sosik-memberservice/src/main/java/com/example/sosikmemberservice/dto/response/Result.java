package com.example.sosikmemberservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result<T>{
    private String resultCode;
    private T result;

    public static Result<Void> error(String errorCode){
        return new Result<>(errorCode,null);
    }

    public static Result<Void> success(){
        return new Result<Void>("데이터 전송에 성공하였습니다!",null);
    }

    public static <T> Result<T> success(T result){
        return new Result<>("데이터 전송에 성공하였습니다!",result);
    }

    public String toStream(){
        if(result ==null){
            return "{" +
                    "\"resultCode\":" + "\""+resultCode + "\"," +
                    "\"result\":" + null + "}";
        }
        {
            return "{" +
                    "\" resultCode\":" +"\""+resultCode + "\"," +
                    "\"result\" :" + "\"" + result + "\"" + "}";

        }

    }
}
