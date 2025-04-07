package com.cordingrecipe.scheduleclone.user.controller;

import com.cordingrecipe.scheduleclone.user.dto.request.UserSaveRequestDto;
import com.cordingrecipe.scheduleclone.user.dto.response.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<UserResponseDto> signup(@Valid @RequestBody UserSaveRequestDto dto){
        return ResponseEntity.ok(userService.save(dto));
    }
}
