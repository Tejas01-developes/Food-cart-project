package com.foodcart.project3.refreshtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class refreshtoken {
    @Value("${accesssecret.key}")
    private String accesskey;

    @Value("${refreshsecret.key}")
    private String refreshkey;

    public String accesstoken(String email){
    return     Jwts
                .builder()
                .setSubject(email)
            .claim("type","access")
                .signWith(SignatureAlgorithm.HS256,accesskey.getBytes())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .compact();
    }

    public String refreshtoken(String email){
        return     Jwts
                    .builder()
                    .setSubject(email)
                .claim("type","refresh")
                    .signWith(SignatureAlgorithm.HS256,refreshkey.getBytes())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                    .compact();


    }

    private Claims getaccessClaims(String token){
        return Jwts.parser()
                .setSigningKey(accesskey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims getrefreshClaims(String token){
        return Jwts.parser()
                .setSigningKey(refreshkey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }





    public boolean verifyaccesstokens(String token) {
        try {
          Claims c=getaccessClaims(token);
          return "access".equals(c.get("type").toString());
        } catch (Exception e) {
            return false;
        }
    }


    public boolean verifyrefreshtokens(String token) {
        try {
            Claims c=getrefreshClaims(token);
            return "refresh".equals(c.get("type").toString());
        } catch (Exception e) {
            return false;
        }
    }







    public String extractemail(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(accesskey.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }




    public String extractemails(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(refreshkey.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }













}
