package com.palvair.spring.batch.transaction;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CompositeWriter extends CompositeItemWriter<User> {

    @Autowired
    public CompositeWriter(JdbcBatchItemWriter<User> writer, LogWriter logWriter) {
        setDelegates(Arrays.asList(writer, logWriter));
    }
}
