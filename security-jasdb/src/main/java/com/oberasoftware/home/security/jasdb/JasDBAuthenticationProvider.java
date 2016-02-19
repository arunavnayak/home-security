package com.oberasoftware.home.security.jasdb;

import com.oberasoftware.home.security.common.api.AuthenticatedUser;
import com.oberasoftware.home.security.common.api.AuthenticationProvider;
import com.oberasoftware.home.security.common.model.LocalUser;
import com.oberasoftware.jasdb.api.entitymapper.EntityManager;
import nl.renarj.jasdb.api.DBSession;
import nl.renarj.jasdb.core.crypto.CryptoEngine;
import nl.renarj.jasdb.core.crypto.CryptoFactory;
import nl.renarj.jasdb.core.exceptions.JasDBStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.renarj.jasdb.api.query.QueryBuilder.createBuilder;

/**
 * @author Renze de Vries
 */
@Component
public class JasDBAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(JasDBAuthenticationProvider.class);
    private static final int SINGLE_USER = 1;

    @Autowired
    private JasDBSessionFactory sessionFactory;

    @Override
    public Optional<AuthenticatedUser> authenticate(String clientId, String password) {
        try {
            DBSession session = sessionFactory.createSession();
            EntityManager entityManager = session.getEntityManager();
            List<LocalUser> users = entityManager.findEntities(LocalUser.class,
                    createBuilder().field("userName").value(clientId));

            if(users.size() == SINGLE_USER) {
                LocalUser user = users.get(0);
                String hash = user.getPasswordHash();
                String salt = user.getSalt();

                CryptoEngine cryptoEngine = CryptoFactory.getEngine();
                boolean authenticated = cryptoEngine.hash(salt, password).equals(hash);
                LOG.debug("User: {} was successfully authenticated: {}", clientId, authenticated);

                if(authenticated) {
                    return Optional.of(new AuthenticatedUserImpl(user, new ArrayList<>()));
                }
            }
        } catch(JasDBStorageException e) {
            LOG.error("Could not query JasDB User store", e);
        }

        return Optional.empty();
    }
}
