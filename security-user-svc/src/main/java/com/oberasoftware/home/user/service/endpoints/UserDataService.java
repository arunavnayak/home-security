package com.oberasoftware.home.user.service.endpoints;

import com.oberasoftware.home.security.common.api.UserService;
import com.oberasoftware.home.security.common.model.User;
import com.oberasoftware.home.user.service.aspects.OwnerSecured;
import com.oberasoftware.home.user.service.aspects.PrivilegedSecured;
import com.oberasoftware.home.user.service.model.ControllerResourceModel;
import com.oberasoftware.home.user.service.model.CreateUserModel;
import com.oberasoftware.home.user.service.model.RestUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author Renze de Vries
 */
@RestController
@RequestMapping("/users")
public class UserDataService {
    private static final Logger LOG = LoggerFactory.getLogger(UserDataService.class);

    @Autowired
    private UserService userService;

    @PrivilegedSecured(allowedRoles = {"trustedResource", "admin"})
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createUser(HttpServletRequest request, @RequestBody CreateUserModel userModel) {
        LOG.debug("Received user creation command for userName: {} and email: {}", userModel.getUserName(), userModel.getUserPassword());
        User createdUser = userService.createUser(userModel.getUserName(), userModel.getUserPassword(), userModel.getUserEmail(), userModel.getRoles());

        return ResponseEntity.ok(new RestUser(createdUser.getUserId(), createdUser.getUserName(),
                createdUser.getUserMail()));
    }

    @OwnerSecured
    @RequestMapping(value = "/controller", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addController(HttpServletRequest request, @RequestBody ControllerResourceModel controllerResource) {
        LOG.debug("Received a controller creation request: {}", controllerResource.getControllerId());
        String randomPassword = UUID.randomUUID().toString();
        return ResponseEntity.ok(userService.createControllerUser(getUserId(request), controllerResource.getControllerId(),
                randomPassword));
    }

    private String getUserId(HttpServletRequest request) {
        return request.getParameter("client_id");
    }
}
