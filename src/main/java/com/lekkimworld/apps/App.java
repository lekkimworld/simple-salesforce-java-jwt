package com.lekkimworld.apps;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;

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
            // JWT - probably read from Authorization header (i.e. Bearer xyz123)
            final String token = "eyJraWQiOiJteWNlcnQiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL25hbWVkcHJpbmNpcGFsLmV4YW1wbGUuY29tIiwic3ViIjoiTmFtZWRQcmluY2lwYWwiLCJhdWQiOiJodHRwczovL2xla2tpbS1zaW1wbGUtand0LWRpc3BsYXkuaGVyb2t1YXBwLmNvbSIsIm5iZiI6MTYxNTgxMDU2OCwiaWF0IjoxNjE1ODEwNTY4LCJleHAiOjE2MTU4MTA4Njh9.Ld73YNNuSVn84yFw00er7_370NNWVQyphoBqnrQupEcakdi_Gh2OkBAVgYtXU73vNwMromDqPrQS8rVTCsSEVLtZT-cP0TqAMNq1gVLFfKccPSPemwVapQUH-saJNoCih8QLIaojYsJV4XgNeb4yU5smvBKFS-Nj6Q1Sz7zCBZpck6Bhii1Ohd0vAQhf8KUw4XjX8UOTIeI8GSB5R0Qnw5tALewg6HezC7L0zwkBXOhiDMscDJdH6efGJQ83Ih0W8zntDZz_pTU8HoA6hrVYStEyEKkAhWnne4YfIRvnV7LnKT1uVVfywx0HXdhsSCljeATltdNEvs4xFEWsc1llGg";

            // decode JWT and get keyid specified in the token
            DecodedJWT jwt = JWT.decode(token);
            final String keyid = jwt.getKeyId();

            // read JWKS from endpoint and get appropriate key
            JwkProvider provider = new UrlJwkProvider(new URL("https://lekkim-scratchorg-20210312.my.salesforce.com/id/keys"));
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
