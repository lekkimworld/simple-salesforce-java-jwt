package com.lekkimworld.apps;

import java.io.FileInputStream;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Properties;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args )
    {
        try {
            // read props
            Properties props = new Properties();
            props.load(new FileInputStream(
                System.getProperty("user.dir") + 
                System.getProperty("file.separator") + 
                "env.properties"));

            // JWT - probably read from Authorization header (i.e. Bearer xyz123)
            final String token = props.getProperty("JWT");

            // decode JWT and get keyid specified in the token
            DecodedJWT jwt = JWT.decode(token);
            final String keyid = jwt.getKeyId();

            // read JWKS from endpoint and get appropriate key
            JwkProvider provider = new UrlJwkProvider(new URL(props.getProperty("JWKS_URL")));
            Jwk jwk = provider.get(keyid);

            // verify signature
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            algorithm.verify(jwt);

            // verify audience, issuer, that it hasn't expired etc....

            // show result
            System.out.println("Verified JWT");
            System.out.println("\tSubject : " + jwt.getSubject());
            System.out.println("\tAudience: " + jwt.getAudience().get(0));
            System.out.println("\tIssuer  : " + jwt.getIssuer());
            System.out.println("\tKey ID  : " + jwt.getKeyId());
            System.out.println("\tExpire  : " + SimpleDateFormat.getInstance().format(jwt.getExpiresAt()));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
