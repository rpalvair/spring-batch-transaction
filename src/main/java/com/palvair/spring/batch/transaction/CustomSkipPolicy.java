package com.palvair.spring.batch.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

@Component
public class CustomSkipPolicy implements SkipPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSkipPolicy.class);

    @Override
    public boolean shouldSkip(Throwable t, int skipCount) {
        LOGGER.error("Une erreur est survenue", t);
        return true;
    }
}
