package com.kp.framework.transactiona;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 统一事务处理。
 * @author lipeng
 * 2020/9/7
 */
@Aspect
@Configuration
public class TransactionalConfigForBoot {

    //    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.mrp.data.module.*.service.*.*(..)) or execution(* com.galaxy.data.service.module.*.*(..)) or execution(* com.supervise.data.module.*.service.*.*(..))";
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* *..service.*.*(..))) or execution(* *..service.impl.*.*(..)))";
    //    or execution(* *..service.impl.*.*(..)))
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {

        DefaultTransactionAttribute txAttr_REQUIRED = new DefaultTransactionAttribute();
        txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        DefaultTransactionAttribute txAttr_REQUIRED_READONLY = new DefaultTransactionAttribute();
        txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttr_REQUIRED_READONLY.setReadOnly(true);

        DefaultTransactionAttribute TXATTR_SECURE = new DefaultTransactionAttribute();
        TXATTR_SECURE.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TXATTR_SECURE.setIsolationLevel(Isolation.SERIALIZABLE.value());

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        /*       source.addTransactionalMethod("query*", txAttr_REQUIRED_READONLY);*/
        source.addTransactionalMethod("add", txAttr_REQUIRED);
        source.addTransactionalMethod("add*", txAttr_REQUIRED);
        source.addTransactionalMethod("save", txAttr_REQUIRED);
        source.addTransactionalMethod("save*", txAttr_REQUIRED);
        source.addTransactionalMethod("update", txAttr_REQUIRED);
        source.addTransactionalMethod("update*", txAttr_REQUIRED);
        source.addTransactionalMethod("remove", txAttr_REQUIRED);
        source.addTransactionalMethod("remove*", txAttr_REQUIRED);
        source.addTransactionalMethod("delete", txAttr_REQUIRED);
        source.addTransactionalMethod("delete*", txAttr_REQUIRED);
        source.addTransactionalMethod("discard", txAttr_REQUIRED);
        source.addTransactionalMethod("discard*", txAttr_REQUIRED);
        source.addTransactionalMethod("batch", txAttr_REQUIRED);
        source.addTransactionalMethod("batch*", txAttr_REQUIRED);
        source.addTransactionalMethod("do", txAttr_REQUIRED);
        source.addTransactionalMethod("do*", txAttr_REQUIRED);
        source.addTransactionalMethod("secure", TXATTR_SECURE);
        source.addTransactionalMethod("secure*", TXATTR_SECURE);

        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
