package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userWithShortLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("123456");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithShortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithInvalidAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        User user1 = new User();
        user1.setLogin("userSameLogin");
        user1.setPassword("123456");
        user1.setAge(19);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("userSameLogin");
        user2.setPassword("123456");
        user2.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userWithNullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("superSecretPassword");
        user.setAge(45);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithNullPassword_notOk() {
        User user = new User();
        user.setLogin("validName");
        user.setPassword(null);
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithNullAge_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("collBoi12");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPassword");
        user.setAge(25);
        User registered = registrationService.register(user);
        assertEquals(user.getLogin(), registered.getLogin());
    }
}
