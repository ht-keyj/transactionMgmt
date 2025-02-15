package com.example.demo.service;


import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

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

        // 模拟 repository 行为
        when(transactionRepository.findAll()).thenReturn(transactions);

        // 调用方法
        List<Transaction> result = transactionService.getAllTransactions();

        // 验证结果
        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testCreateTransaction() {
        // 模拟数据
        Transaction transaction = new Transaction("deposit", 100.0);

        // 模拟 repository 行为
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // 调用方法
        Transaction result = transactionService.createTransaction(transaction);

        // 验证结果
        assertNotNull(result);
        assertEquals("deposit", result.getType());
        assertEquals(100.0, result.getAmount());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testUpdateTransaction() {
        // 模拟数据
        Long id = 1L;
        Transaction existingTransaction = new Transaction("deposit", 100.0);
        Transaction updatedTransaction = new Transaction("withdrawal", 50.0);

        // 模拟 repository 行为
        when(transactionRepository.findById(id)).thenReturn(Optional.of(existingTransaction));
        when(transactionRepository.save(existingTransaction)).thenReturn(existingTransaction);

        // 调用方法
        boolean result = transactionService.updateTransaction(id, updatedTransaction);

        // 验证结果
        assertTrue(result);
        assertEquals("withdrawal", existingTransaction.getType());
        assertEquals(50.0, existingTransaction.getAmount());
        verify(transactionRepository, times(1)).findById(id);
        verify(transactionRepository, times(1)).save(existingTransaction);
    }

    @Test
    void testDeleteTransaction() {
        // 模拟数据
        Long id = 1L;

        // 模拟 repository 行为
        when(transactionRepository.existsById(id)).thenReturn(true);

        // 调用方法
        boolean result = transactionService.deleteTransaction(id);

        // 验证结果
        assertTrue(result);
        verify(transactionRepository, times(1)).existsById(id);
        verify(transactionRepository, times(1)).deleteById(id);
    }
}