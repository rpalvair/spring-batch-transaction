package com.palvair.spring.batch.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ThreadLoadRangePartionner implements Partitioner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadLoadRangePartionner.class);

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        final Map<String, ExecutionContext> result = new HashMap<>();
        for (int i = 0; i < gridSize; i++) {
            ExecutionContext value = new ExecutionContext();
            value.putInt("id", i);
            LOGGER.debug("id = {}", i);
            value.putString("name", "Thread" + i);
            result.put("partition" + i, value);
        }
        return result;
    }
}
