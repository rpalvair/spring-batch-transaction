package com.palvair.spring.batch.transaction;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Job job(@Qualifier("step1Master") Step step1Master) {
        return jobs.get("myJob").start(step1Master).build();
    }

    @Bean
    public Step step1Master(final Step step1,
                            final ThreadLoadRangePartionner threadLoadRangePartionner,
                            final TaskExecutor jobTaskExecutor,
                            final StateListener stateListener) {
        return steps.get("step1.master")
                .partitioner("step1", threadLoadRangePartionner)
                .step(step1)
                .gridSize(4)
                .taskExecutor(jobTaskExecutor)
                .listener(stateListener)
                .build();
    }

    @Bean
    protected Step step1(ItemReader<User> reader,
                         ItemProcessor<User, User> processor,
                         CompositeWriter writer,
                         SkipPolicy customSkipPolicy) {
        return steps.get("step1")
                .<User, User>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(customSkipPolicy)
                .build();
    }

    @Bean
    public TaskExecutor jobTaskExecutor() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(4);
        threadPoolTaskExecutor.setMaxPoolSize(4);
        threadPoolTaskExecutor.setQueueCapacity(50);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskExecutor;
    }
}
