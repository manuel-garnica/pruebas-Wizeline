package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.BookBean;
import com.wizeline.maven.learningjavamaven.model.XmlBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JAXBControllerTest {

    @Mock
    private BookBean bookBean;

    @InjectMocks
    private JAXBController jaxbController;

    @Test
    public void DadoserviciogGetXML_CuandoSeejecuta_EntoncesExcribeunarchivoyregresaunStatusok() throws JAXBException, IOException {
        ResponseEntity<XmlBean> responseEntity= jaxbController.getXML();
        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()));
    }


}