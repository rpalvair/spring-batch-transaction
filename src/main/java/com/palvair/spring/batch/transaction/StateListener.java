package com.palvair.spring.batch.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateListener.class);

    private final JdbcUserRepository jdbcUserRepository;

    @Autowired
    public StateListener(final JdbcUserRepository jdbcUserRepository) {
        this.jdbcUserRepository = jdbcUserRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.debug("Fin du step");
        final long count = jdbcUserRepository.findAll()
                .stream()
                .peek(user -> LOGGER.debug("User en base {}", user.getName()))
                .count();
        LOGGER.debug("{} utilisateur(s) en base", count);
        return ExitStatus.COMPLETED;
    }
}
