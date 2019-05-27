package com.palvair.spring.batch.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
public class InMemoryReader implements ItemReader<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryReader.class);

    @PostConstruct
    public void init() {
        userList.add(new User("Dupond", false));
        userList.add(new User("Poutine", false));
        userList.add(new User("Obama", false));
        userList.add(new User("Macron", false));
    }

    private final List<User> userList = new ArrayList<>();

    private final int id;

    @Autowired
    public InMemoryReader(@Value("#{stepExecutionContext['id']}") final int id) {
        this.id = id;
    }

    @Override
    public User read() {
        final User user = userList.get(id);
        if (user.isDone()) {
            return null;
        } else {
            LOGGER.debug("Récupération de l'item avec l'id = {}", id);
            synchronized (userList) {
                userList.remove(id);
                userList.add(id, new User(user.getName(), true));
            }
            return user;
        }
    }
}
