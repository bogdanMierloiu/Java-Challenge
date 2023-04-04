package com.bogdanmierloiu.Java_Challenge.controller.rest;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.exception.DuplicatePlayerException;
import com.bogdanmierloiu.Java_Challenge.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<PlayerResponse>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<PlayerResponse> findById(Long id) {
        return null;
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
