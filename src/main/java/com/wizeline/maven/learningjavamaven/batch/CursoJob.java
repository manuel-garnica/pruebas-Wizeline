package com.wizeline.maven.learningjavamaven.batch;

import com.wizeline.maven.learningjavamaven.model.CursoDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
    public class CursoJob {

        @Autowired
        private JobBuilderFactory jobBuilderFactory;

        @Autowired
        private StepBuilderFactory stepBuilderFactory;

        @Value("${archivo.input}")
        private String archivoInput;

        @Value("${archivo.output}")
        private String archivoOutput;

        @Bean
        public CursoReader cursoReader() {
            return new CursoReader(archivoInput);
        }

        @Bean
        public CursoProcessor cursoProcessor() {
            return new CursoProcessor();
        }

        @Bean
        public CursoWriter cursoWriter() {
            return new CursoWriter(archivoOutput);
        }

        @Bean
        public Step step1() {
            return stepBuilderFactory.get("step1")
                    .<CursoDTO, CursoDTO>chunk(1)
                    .reader(cursoReader())
                    .processor(cursoProcessor())
                    .writer(cursoWriter())
                    .build();
        }

        @Bean
        public Job cursoJobCurso() {
            return jobBuilderFactory.get("cursoJobCurso")
                    .incrementer(new RunIdIncrementer())
                    .start(step1())
                    .build();
        }
    }

