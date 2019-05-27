package com.palvair.spring.batch.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogWriter implements ItemWriter<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogWriter.class);

    @Override
    public void write(List<? extends User> items) {
        final long count = items.stream()
                .peek(o -> {
                    LOGGER.debug("Réception de {}", o.getName());
                    if (o.getName().equals("OBAMA")) {
                        LOGGER.error("Obama error");
                        throw new RuntimeException("Obama error");
                    }
                })
                .count();
        LOGGER.debug("{} utilisateur(s) réceptionné(s)", count);
    }
}
