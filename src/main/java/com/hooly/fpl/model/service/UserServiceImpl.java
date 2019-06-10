package com.hooly.fpl.model.service;

import com.hooly.fpl.model.entity.User;
import com.hooly.fpl.model.entity.UserRole;
import com.hooly.fpl.model.entity.UserState;
import com.hooly.fpl.model.repository.UserRepository;
import com.hooly.fpl.model.security.UserDetailsImpl;
import com.hooly.fpl.rest.exception.UserAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new UserDetailsImpl(repository.findOneByLogin(s)
                .orElseThrow(IllegalAccessError::new));
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public User save(String login, String password, String firstName, String lastName, String isLearner) {
        User newUser = User.builder().login(login)
                .password(passwordEncoder.encode(password))
                .firstName(firstName)
                .lastName(lastName)
                .userRole(("true".equals(isLearner)) ? UserRole.LEARNER : UserRole.TEACHER)
                .userState(UserState.ACTIVE)
                .build();
        return repository.save(newUser);
    }

    public User getCurrentUser() throws UserAuthenticationException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)){
            throw new UserAuthenticationException("Invalid user credentials. User not found.");
        }
        User user = (User) principal;
        return repository.findOneByLogin(user.getLogin())
                .orElseThrow(() -> new IllegalStateException("Operation requires authentication context!!!"));
    }
}
