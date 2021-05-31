package com.changgou.token;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/*****
 * @Author: www.itheima
 * @Date: 2019/7/7 13:48
 * @Description: com.changgou.token
 *  使用公钥解密令牌数据
 ****/
public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJpdGhlaW1hIiwiaWQiOiIxIn0.aW7hzbFwTfnKJuR33feBF4P54yS5TCLLmGDqMybI_VAAkRLTHMwmq4KX23Dy-h32FRKMpqCl6LUPE-vUU_nT0f44Nihm3LgRVE2Aq8Ed-WNQPKO60vaC79mfhAaLfJvZ_IbKmv8R7SRYnEq1HBYCXwuivk3rEizB_jUPb7MVHm6xWFQlo5X6nReakNFqoG2GoTJroLVL99Et-iFeoCCg4egfIseyWUxCK8FLc4hDirTpQTBEOmN04zOI0XG7r0ZWJaoNbVaknKDX5dtEdLfzBhibIFmTQsfJ9fLYQYq-8ry21PXrA6VpPfYZyuTnD2GftysRLNqyBFvhGhyrGGF1Pg";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiOIU0bVJDaVxXj1ArmvcSAiAFBWrSkGkEpAsTyk1nD1Z+vMoFjEeh2jRbb0SJQnnHOrELnobsWnc71hcjWTJfPqjpVootWXDhKC2eYjoWgTMFy+2lejN0KV0sUhLb1gKAUA6q+BYtvR9Xfar2vBgWuyj/5WqrJbwfrGgnu1YS9c96U9S2SMxSM8V5yo+RtRgJPmCwZljQk2G7hqt6rHFF48oN+KPR5+cA39FPJh+0hKxOZThNOzYltYY5PqWsxzxG67097wrgNeDpi5FjSVQuZJJgzJ0qUHcsK+ajP4YQKbplTgU/49o719D5PZ7YffB9JfnYWX9V+j20tPID3/71QIDAQAB-----END PUBLIC KEY-----";

        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容 载荷
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
