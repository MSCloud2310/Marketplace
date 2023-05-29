package javeriana.ms.services.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.ArrayList;
import java.util.List;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfycaRHxhdb9zURb2rf8e7Ud";

    public static List<String> getClaims(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build().parseClaimsJws(token).getBody();
        List<String> claimsArray = new ArrayList<String>();
        claimsArray.add(claims.getSubject());
        claimsArray.add((String) claims.get("role"));
        claimsArray.add((String) claims.get("id").toString());

        return claimsArray;
    }


}