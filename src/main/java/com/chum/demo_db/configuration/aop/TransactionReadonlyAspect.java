package com.chum.demo_db.configuration.aop;

import com.chum.demo_db.configuration.context.DatabaseContextHolder;
import com.chum.demo_db.configuration.context.DbType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class TransactionReadonlyAspect {

    @Around("@annotation(transactional)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, org.springframework.transaction.annotation.Transactional transactional) throws Throwable {
        System.out.println("Aspect executed");
        try {
            if (transactional.readOnly()) {
                DatabaseContextHolder.setDbType(DbType.SLAVE);
            }
            return proceedingJoinPoint.proceed();
        } finally {
            DatabaseContextHolder.clearDbType();
        }
    }
}
