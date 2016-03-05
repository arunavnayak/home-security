package com.oberasoftware.home.security.token;

import com.oberasoftware.home.security.common.api.UserService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Renze de Vries
 */
@Component
public class DefaultUserLoader {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultUserLoader.class);

    @Autowired
    private UserService userService;

    @PostConstruct
    public void ensureDefaultUsers() {
        InputStream stream = this.getClass().getResourceAsStream("/default-users.json");

        LOG.debug("Creating default users and roles if not present");
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(stream);
            JsonNode userNode = rootNode.get("users");


            userNode.getElements().forEachRemaining(u -> {
                String userName = u.get("userName").asText();
                String userMail = u.get("userEmail").asText();
                String userPassword = u.get("userPassword").asText();

                List<String> roles = new ArrayList<>();
                u.get("roles").getElements().forEachRemaining(r -> roles.add(r.asText()));


                if(!userService.findUser(userName).isPresent()) {
                    LOG.info("Default user with username: {} mail: {} with roles: {} was created", userName, userMail, roles);
                    userService.createUser(userName, userPassword, userMail, roles);
                } else {
                    LOG.debug("Default user: {} already exists, not re-creating", userName);
                }
            });
        } catch (IOException e) {
            LOG.error("Could not create default users", e);
        }
        LOG.info("Finished creating default users and roles");
    }
}
