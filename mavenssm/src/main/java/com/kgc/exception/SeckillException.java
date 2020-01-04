package com.kgc.exception;

/**
 * @Author: CuiJunJia
 * @Date: 2019-07-25 09:42
 * @Deprecated: 秒杀相关的异常
 */
public class SeckillException extends RuntimeException{


    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
