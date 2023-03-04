package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.batch.UserJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.batch.runtime.BatchStatus;

import java.util.Date;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BatchControllerTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private UserJob userJob;

    @InjectMocks
    private BatchController batchController;

    @Test
    public void DadoServicioBachtStar_Siempre_EjecutaelBatch() throws Exception {
        // Definimos el objeto JobExecution para el job que se ejecutará
        JobExecution jobExecution = new JobExecution(1L) ;

        // Cuando se llama al método run del jobLauncher, retornará el objeto jobExecution definido anteriormente
        when(jobLauncher.run(eq(userJob.printUsersJob()), any())).thenReturn((org.springframework.batch.core.JobExecution) jobExecution);

        // Realizamos una petición HTTP GET al endpoint "/batch/start"
        ResponseEntity<String> response = batchController.startBatch();

        // Verificamos que el código de respuesta sea 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que el cuerpo de la respuesta contenga el texto "Batch Process started!!"
        assertEquals("Batch Process started!!", response.getBody());

        // Verificamos que se haya llamado al método run del jobLauncher con los parámetros correctos
        verify(jobLauncher).run(eq(userJob.printUsersJob()), any(JobParameters.class));
    }

    @Test
    public void testStartBatch2() throws Exception {
        // Definimos el objeto JobExecution para el job que se ejecutará
        JobExecution jobExecution = new JobExecution(1L);

        // Cuando se llama al método run del jobLauncher, retornará el objeto jobExecution definido anteriormente
        when(jobLauncher.run(eq(userJob.printUsersJob()), any())).thenThrow(JobParametersInvalidException.class);

        ResponseEntity<String> response = batchController.startBatch();

        // Verificamos que el código de respuesta sea 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que el cuerpo de la respuesta contenga el texto "Batch Process started!!"
        assertEquals("Batch Process started!!", response.getBody());

    }
}

