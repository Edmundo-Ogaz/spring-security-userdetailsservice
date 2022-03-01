package com.example.spring.security.userdetailsservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.spring.security.userdetailsservice.SecurityConstants.SECRET;
import static com.example.spring.security.userdetailsservice.SecurityConstants.TOKEN_PREFIX;

@RestController
public class TokenController {

    @GetMapping("/verifyToken")
    public String verifyToken(@RequestParam(value = "token", defaultValue = "") String token) {
        System.out.println("UsuarioController verifyToken");

        String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();

        /*if (!token) {
            return res.status(400).json({
                    error: true,
                    message: "Token is required."
    });
        }
        // check token that was passed by decoding token using secret
        jwt.verify(token, process.env.JWT_SECRET, function (err, user) {
            if (err) return res.status(401).json({
                    error: true,
                    message: "Invalid token."
    });

            // return 401 status if the userId does not match.
            if (user.userId !== userData.userId) {
                return res.status(401).json({
                        error: true,
                        message: "Invalid user."
      });
            }*/

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("user", user);


        return jsonObject.toString();
    }
}
