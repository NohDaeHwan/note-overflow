package com.note.noteoverflow.domain;

import com.note.noteoverflow.dto.security.NotePrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.isAuthenticated()) {
            return null;
        }
        NotePrincipal principal = (NotePrincipal) authentication.getPrincipal();
        return Optional.of(principal.getUsername());
    }

}
