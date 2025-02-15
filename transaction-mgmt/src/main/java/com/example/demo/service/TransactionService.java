package com.example.demo.service;



import com.example.demo.model.Transaction;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    private final Cache<Long, Transaction> cache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES) // 缓存过期时间
            .maximumSize(100) // 最大缓存数量
            .build();

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }


    public boolean deleteTransaction(Long id) {
        cache.invalidate(id);
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public Transaction getTransactionById(Long id) {
        // 先从缓存中查找
        Transaction cachedTransaction = cache.getIfPresent(id);
        if (cachedTransaction != null) {
            return cachedTransaction; // 缓存命中，直接返回
        }

        // 缓存未命中，从数据库中查询
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        // 将查询到的数据写入缓存
        cache.put(id, transaction);

        return transaction;
    }

    // 更新交易（先删除缓存，再更新数据库）
    public boolean updateTransaction(Long id, Transaction transaction) {
        // 删除缓存
        cache.invalidate(id);

        // 更新数据库
        Optional<Transaction> transactionOpt = transactionRepository.findById(id);
        if (transactionOpt.isPresent()) {
            Transaction existingTransaction = transactionOpt.get();
            existingTransaction.setType(transaction.getType());
            existingTransaction.setAmount(transaction.getAmount());
            transactionRepository.save(existingTransaction);
            return true;
        }
        return false;
    }

}