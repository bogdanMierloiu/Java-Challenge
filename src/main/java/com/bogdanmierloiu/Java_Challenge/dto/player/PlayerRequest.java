package com.bogdanmierloiu.Java_Challenge.dto.player;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlayerRequest {

    private Long id;

    private String name;


}
