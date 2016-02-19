package com.oberasoftware.home.security.token;

import com.oberasoftware.home.security.common.api.OAuthException;
import com.oberasoftware.home.security.common.api.TokenManager;
import com.oberasoftware.home.security.common.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import static java.util.Base64.getUrlEncoder;

/**
 * @author Renze de Vries
 */
@Component
public class DefaultTokenManager implements TokenManager {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultTokenManager.class);

    @Value("${expirationTime:600000}")
    private long tokenExpirationTime;

    private static final String ALGORITHM = "HmacSHA256";

    private SecretKey secretKey;

    private static final String TOKEN_PATTERN = "%s:%s:%s";

    private static final Random RND = new Random(System.currentTimeMillis());

    @PostConstruct
    public void initialize() {
        byte[] randomBytes = new byte[128];
        RND.nextBytes(randomBytes);
        LOG.info("Generated a secret: {}", randomBytes);

        secretKey = new SecretKeySpec(randomBytes, ALGORITHM);
    }

    @Override
    public Token generateToken(String clientId) {
        return generateToken(clientId, Token.TOKEN_TYPE.USER_TOKEN);
    }

    @Override
    public Token generateToken(String clientId, Token.TOKEN_TYPE type) {
        String tokenId = UUID.randomUUID().toString();
        String tokenData = String.format(TOKEN_PATTERN, clientId, tokenId, type.name());

        return new Token(tokenId, clientId, toDigest(tokenData), tokenExpirationTime, type);
    }

    private String toDigest(String token) {
        try {
            byte[] tokenData = token.getBytes();

            Mac maCode = Mac.getInstance(secretKey.getAlgorithm());
            maCode.init(secretKey);
            maCode.update(tokenData, 0, tokenData.length);

            return new String(getUrlEncoder().encode(maCode.doFinal()));
        } catch(NoSuchAlgorithmException | InvalidKeyException e) {
            throw new OAuthException("Unable to digest token: " + e.getMessage());
        }
    }
}
