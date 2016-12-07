package com.oberasoftware.home.user.service.endpoints;

import com.oberasoftware.home.security.common.api.UserService;
import com.oberasoftware.home.security.common.model.User;
import com.oberasoftware.home.user.service.aspects.OwnerSecured;
import com.oberasoftware.home.user.service.aspects.PrivilegedSecured;
import com.oberasoftware.home.user.service.model.CreateUserModel;
import com.oberasoftware.home.user.service.model.KeyValuePair;
import com.oberasoftware.home.user.service.model.RestUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<?> setUserKey(HttpServletRequest request, @RequestParam KeyValuePair keyValuePair) {
        LOG.debug("Received a user key value pair: {}", keyValuePair);
        String userName = getUserId(request);
        User updateUser = userService.setMetadata(userName, keyValuePair.getKey(), keyValuePair.getValue());

        return ResponseEntity.ok(new RestUser(updateUser.getUserId(), updateUser.getUserName(), updateUser.getUserMail()));
    }

    private String getUserId(HttpServletRequest request) {
        return request.getParameter("client_id");
    }
}
