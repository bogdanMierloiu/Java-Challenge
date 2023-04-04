package com.bogdanmierloiu.Java_Challenge.controller.rest;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.exception.DuplicatePlayerException;
import com.bogdanmierloiu.Java_Challenge.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController implements CrudRequest<PlayerRequest, PlayerResponse> {
    private final PlayerService playerService;

    @Override
    @PostMapping
    public ResponseEntity<?> add(@RequestBody PlayerRequest request) {
        try {
            return new ResponseEntity<>(playerService.add(request), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Player name cannot be empty.");
        } catch (DuplicatePlayerException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This name is not available! Try another one!");
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PlayerResponse>> getAll() {
        return new ResponseEntity<>(playerService.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(playerService.findById(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The player with id " + id + " not found!");
        }
    }

    @Override
    public ResponseEntity<PlayerResponse> update(PlayerRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return null;
    }
}
