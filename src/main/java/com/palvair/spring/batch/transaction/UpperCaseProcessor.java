package com.palvair.spring.batch.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UpperCaseProcessor implements ItemProcessor<User, User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpperCaseProcessor.class);

    @Override
    public User process(User item) throws Exception {
        LOGGER.debug("Process user {}", item.getName());
        return new User(item.getName().toUpperCase(), item.isDone());
    }
}
