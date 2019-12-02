package com.nearsoft.training.library.service.impl;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.nearsoft.training.library.model.User;
import com.nearsoft.training.library.repository.BooksByUserRepository;
import com.nearsoft.training.library.repository.UserRepository;

public class UserServiceImplTest {

    @Test
    public void givenAnUserAndAListOfBooks_whenRegisterReturn_ThenBooksAreUnassignedFromUser(){
        // Given:
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BooksByUserRepository booksByUserRepository = Mockito.mock(BooksByUserRepository.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, booksByUserRepository);
        User user = new User();
        String[] isbnList = new String[]{"abc", "bcd"};
        String curp = "12345";

        user.setCurp(curp);

        Mockito.when(userRepository.findById(curp)).thenReturn(Optional.of(user));

        // When:
        userService.registerReturn(user, isbnList);

        // Then:
        Mockito.verify(userRepository).findById(curp);
        Mockito.verify(booksByUserRepository).deleteByCurpAndIsbnIn(curp, isbnList);

        Mockito.verifyNoMoreInteractions(userRepository, booksByUserRepository);
    }

    @Test
    public void givenAnNonExistentUserAndAListOfBooks_whenRegisterReturn_ThenBooksAreUnassignedFromUser(){
        // Given:
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BooksByUserRepository booksByUserRepository = Mockito.mock(BooksByUserRepository.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, booksByUserRepository);
        User user = new User();
        String[] isbnList = new String[]{"abc", "bcd"};
        String curp = "12345";

        user.setCurp(curp);

        Mockito.when(userRepository.findById(curp)).thenReturn(Optional.empty());

        // When:
        userService.registerReturn(user, isbnList);

        // Then:
        Mockito.verify(userRepository).findById(curp);
        Mockito.verify(userRepository).save(user);
        Mockito.verify(booksByUserRepository).deleteByCurpAndIsbnIn(curp, isbnList);

        Mockito.verifyNoMoreInteractions(userRepository, booksByUserRepository);
    }


}
