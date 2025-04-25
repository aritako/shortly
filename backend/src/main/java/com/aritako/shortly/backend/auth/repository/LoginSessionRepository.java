package com.aritako.shortly.backend.auth.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aritako.shortly.backend.auth.model.LoginSession;

public interface LoginSessionRepository extends JpaRepository<LoginSession, Long> {
  Optional<LoginSession> findByRefreshToken(String refreshToken);
}
