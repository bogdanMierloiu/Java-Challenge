package com.bogdanmierloiu.Java_Challenge.controller.rest;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudRequest<T, K> {

    ResponseEntity<?> add(T request);

    ResponseEntity<List<K>> getAll();

    ResponseEntity<K> findById(Long id);

    ResponseEntity<K> update(T request);

    ResponseEntity<?> delete(Long id);
}
