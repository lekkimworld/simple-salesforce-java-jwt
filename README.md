# simple-salesforce-java-jwt

Simple Java application showing how to verify a JWT using the Auth0 JWT and JWKS libraries reading the JWKS from a Salesforce org instead of manually loading the certificate used to verify.

## Building and Running

Create a `env.properties` file with a `JWT` and `JWKS_URL` property. Then install dependencies with Maven using `mvn install`.
