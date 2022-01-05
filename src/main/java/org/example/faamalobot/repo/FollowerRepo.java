package org.example.faamalobot.repo;

import org.example.faamalobot.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowerRepo extends JpaRepository<Follower, Long> {
    Optional<Follower> findByChatId(Long chatId);

    boolean existsByChatId(Long chatId);
}
