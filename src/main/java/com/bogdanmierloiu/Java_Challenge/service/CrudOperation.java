package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.exception.NotEnoughTokens;

import java.util.List;

public interface CrudOperation<T, K> {

    K add(T request);

    List<K> getAll();

    K findById(Long id);

    K update(T request) throws NotEnoughTokens;

    void delete(Long id);
}
