package com.quiz.quizback.controller;

import com.quiz.quizback.entity.User;
import com.quiz.quizback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
    private final OAuth2AuthorizedClientService clientService;

    private final UserRepository userRepository;


    @GetMapping("/login/oauth/code/{provider}")
    public RedirectView loginSuccess(@PathVariable String provider, OAuth2AuthenticationToken authenticationToken) {
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                authenticationToken.getAuthorizedClientRegistrationId(),
                authenticationToken.getName()
        );
        String userEmail = client.getPrincipalName();

        Optional<User> existingUser = userRepository.findByEmail(userEmail);
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setEmail(userEmail);
            user.setProvider(provider);
            userRepository.save(user);
        }

        return new RedirectView("http://localhost:5173/");
    }
}
