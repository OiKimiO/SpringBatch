package io.springbatch.springbatchlecture.template;

import io.springbatch.springbatchlecture.RetryableExceptixon;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.classify.Classifier;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

// input : String type, output : Customer type = ItemProcessor<String, Customer>
public class RetryItemProcessor2 implements ItemProcessor<String, Customer> {

    @Autowired
    private RetryTemplate retryTemplate;

    private int cnt = 0;

    @Override
    public Customer process(String item) throws Exception {
        Classifier<Throwable,Boolean> rollbackClassifier = new BinaryExceptionClassifier(true);

        // 3번째 인자값인 state가 없으면 retry를 시도하다가 Recover로 회복을 시도함
        Customer customer = retryTemplate.execute(new RetryCallback<Customer, RuntimeException>() {
                                                     @Override
                                                     public Customer doWithRetry(RetryContext retryContext) throws RuntimeException {

                                                         if(item.equals("1") || item.equals("2")){
                                                             cnt++;
                                                             throw new RetryableExceptixon("failed cnt : " + cnt);
                                                         }

                                                         return new Customer(item);
                                                     }
                                                 },
                                                 new RecoveryCallback<Customer>() {
                                                     @Override
                                                     public Customer recover(RetryContext retryContext) throws Exception {
                                                         return new Customer(item);
                                                     }
                                                 },
                                                 new DefaultRetryState(item,rollbackClassifier));
        return customer;
    }
}
