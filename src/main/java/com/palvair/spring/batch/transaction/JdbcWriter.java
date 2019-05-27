package com.palvair.spring.batch.transaction;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcWriter extends JdbcBatchItemWriter<User> {

    @Autowired
    public JdbcWriter(final DataSource dataSource) {
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        setSql("INSERT INTO user (name) VALUES (:name)");
        setDataSource(dataSource);
    }
}
