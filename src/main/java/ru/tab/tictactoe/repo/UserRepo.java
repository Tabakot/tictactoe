package ru.tab.tictactoe.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.tab.tictactoe.model.User;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByEmail(String email);
    Page<User> findAll(Pageable pageable);
}
