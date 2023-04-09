package io.springbatch.springbatchlecture;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor_1 implements ItemProcessor<String,String> {
    int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        cnt++;
        return (item + cnt).toUpperCase();
    }
}
