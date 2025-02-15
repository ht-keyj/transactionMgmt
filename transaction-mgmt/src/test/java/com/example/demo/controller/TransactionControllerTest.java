package com.example.demo.controller;


import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 初始化 Mockito 注解
    }

    @Test
    void testGetAllTransactions() {
        // 模拟数据
        Transaction transaction1 = new Transaction("deposit", 100.0);
        Transaction transaction2 = new Transaction("withdrawal", 50.0);
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        // 模拟 service 行为
        when(transactionService.getAllTransactions()).thenReturn(transactions);

        // 调用方法
        List<Transaction> result = transactionController.getAllTransactions();

        // 验证结果
        assertEquals(2, result.size());
        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    void testCreateTransaction() {
        // 模拟数据
        Transaction transaction = new Transaction("deposit", 100.0);

        // 模拟 service 行为
        when(transactionService.createTransaction(transaction)).thenReturn(transaction);

        // 调用方法
        Transaction result = transactionController.createTransaction(transaction);

        // 验证结果
        assertNotNull(result);
        assertEquals("deposit", result.getType());
        assertEquals(100.0, result.getAmount());
        verify(transactionService, times(1)).createTransaction(transaction);
    }

    @Test
    void testUpdateTransaction() {
        // 模拟数据
        Long id = 1L;
        Transaction transaction = new Transaction("withdrawal", 50.0);

        // 模拟 service 行为
        when(transactionService.updateTransaction(id, transaction)).thenReturn(true);

        // 调用方法
        ResponseEntity<?> result = transactionController.updateTransaction(String.valueOf(id), transaction);

        // 验证结果
        assertEquals(200, result.getStatusCodeValue());
        verify(transactionService, times(1)).updateTransaction(id, transaction);
    }

    @Test
    void testDeleteTransaction() {
        // 模拟数据
        Long id = 1L;

        // 模拟 service 行为
        when(transactionService.deleteTransaction(id)).thenReturn(true);

        // 调用方法
        ResponseEntity<?> result = transactionController.deleteTransaction(String.valueOf(id));

        // 验证结果
        assertEquals(200, result.getStatusCodeValue());
        verify(transactionService, times(1)).deleteTransaction(id);
    }
}