package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/wallet")
@SessionAttributes("player")
@RequiredArgsConstructor
public class WalletMVCController {


}
