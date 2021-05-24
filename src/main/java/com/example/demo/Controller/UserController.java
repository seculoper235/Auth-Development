package com.example.demo.Controller;

import com.example.demo.Entity.UserDto;
import com.example.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
// type-safe 하도록 consumes와 produces를 json으로 설정해준다.
@RequestMapping(value = "/users",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> selectUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.selectUser(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        userDto.builder()
                .userId(id)
                .build();
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
