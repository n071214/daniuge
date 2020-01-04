package com.kgc.exception;

/**
 * @Author: CuiJunJia
 * @Date: 2019-07-25 09:44
 * @Deprecated: 秒杀关闭相关异常
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
