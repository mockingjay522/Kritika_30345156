package com.example.kritika_30345156.web;

import com.example.kritika_30345156.entities.Salesman;
import com.example.kritika_30345156.repositories.SalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class AppControllerTest {

    Salesman salesman;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    SalesRepository salesRepository;

    @Mock
    View mockView;

    @InjectMocks
    AppController appController;

    @BeforeEach
    void setup() throws ParseException {
        salesman = new Salesman();
        salesman.setId(1);
        salesman.setName("John");
        String sdate1 = "2022/12/10";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sdate1);
        salesman.setDot(date1);
        salesman.setAmount(2000);
        salesman.setItem("Washing Machine");

        MockitoAnnotations.openMocks(this);

        mockMvc = standaloneSetup(appController).setSingleView(mockView).build();
    }

    @Test
    public void findAll() throws Exception{
        List<Salesman> list = new ArrayList<Salesman>();
        list.add(salesman);
        list.add(salesman);

        when(salesRepository.findAll()).thenReturn(list);
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listTransactions",list))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("listTransactions", hasSize(2)));

        verify(salesRepository, times(1)).findAll();
        verifyNoMoreInteractions(salesRepository);
    }

    @Test
    void index() {
    }

    @Test
    void save() throws Exception{
        when(salesRepository.save(salesman)).thenReturn(salesman);
        salesRepository.save(salesman);
        verify(salesRepository, times(1)).save(salesman);
    }

    @Test
    void delete() {
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(salesRepository).deleteById(idCapture.capture());
        salesRepository.deleteById(1L);
        assertEquals(1, idCapture.getValue());
        verify(salesRepository, times(1)).deleteById(1L);
    }

    @Test
    void editTransaction() {
    }
}