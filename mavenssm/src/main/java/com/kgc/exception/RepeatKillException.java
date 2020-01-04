package com.kgc.exception;

/**
 * @Author: CuiJunJia
 * @Date: 2019-07-25 09:41
 * @Deprecated: 重复秒杀异常（运行期异常）
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
