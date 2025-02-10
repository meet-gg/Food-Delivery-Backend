package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.Entity.Session;
import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import com.Online.Food.Delivery.System.Online.Food.Repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServicesImpl implements SessionServices {
    private final SessionRepository sessionRepo;
    private final int SESSION_LIMIT=2;
    @Override
    public void generateNewSession(User user, String refreshToken) {
        List<Session> userSessions=sessionRepo.findByUser(user);
        if (userSessions.size()==SESSION_LIMIT){
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));
            Session lastRecentlyUsedSession = userSessions.getFirst();
            sessionRepo.delete(lastRecentlyUsedSession);
        }
        Session session=Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepo.save(session);
    }

    @Override
    public void validateSession(String refreshToken) {
        Session session=sessionRepo.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Session not found for refresh token"+refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepo.save(session);
    }
}
