package com.brunoandreotti.springbatchestudo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

  @Bean
  public Job helloJob(JobRepository jobRepository, Step step) {

    //Inicia um Job com o nome e o repository
    return new JobBuilder("HelloJob", jobRepository) // JobRepository é o repository que salva os meta dados para que o Job orquestre a sua execução
        .start(step) //O job é composto de "steps" (etapas) de execução, neste caso será somente um
        .next(step)
        .build();
  }

  @Qualifier("1")
  @Bean
  public Step helloStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

      //Inicia o step com seu nome e o job repository
    return new StepBuilder("helloStep", jobRepository)

        // tasklet é usado quando o step executará uma tarefa simples
        .tasklet((StepContribution contribution, ChunkContext chunckContext) -> {
          System.out.println("Hello Job 1");

          //Informa o fim da tasklet
          return RepeatStatus.FINISHED;
        }, transactionManager)
        .build();
  }


}
