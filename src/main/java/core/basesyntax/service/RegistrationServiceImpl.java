package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters, " +
                    "not null");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters, " +
                    "and not null");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("User age must be at least 18 years old");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with thid login already exists");
        }

        return storageDao.add(user);
    }
}
