package ru.tab.tictactoe.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.tab.tictactoe.model.User;
import ru.tab.tictactoe.repo.UserRepo;

@RestController
@AllArgsConstructor
@RequestMapping("/players")
public class UserController {
    private final UserRepo userRepo;

    @GetMapping
    public Page<User> list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepo.save(user);
    }

    @PutMapping("/{id}")
    public User update(
            @PathVariable("id") User userFromDb,
            @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDb, "id");
        return userRepo.save(userFromDb);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") User user) {
        userRepo.delete(user);
    }
}
