package com.foodcart.project3.controller;

import com.foodcart.project3.refreshtoken.refreshtoken;
import com.foodcart.project3.repository.foodrepo;
import com.foodcart.project3.repository.otprepo;
import com.foodcart.project3.repository.tokenrepo;
import com.foodcart.project3.repository.userrepo;
import com.foodcart.project3.schemas.addproductschema;
import com.foodcart.project3.schemas.otpschema;
import com.foodcart.project3.schemas.tokenschema;
import com.foodcart.project3.schemas.userschema;
import com.foodcart.project3.service.email;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/actions")
public class pagecontroller {
    @Autowired
    private otprepo otprepo;
    @Autowired
    private email email;
    @Autowired
    private userrepo userrepo;
    @Autowired
    private refreshtoken refreshtoken;
    @Autowired
    private tokenrepo tokenrepo;
    @Autowired
    private foodrepo foodrepo;

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    public ResponseEntity<?> loginuser(@RequestBody Map<String, String> req) throws MessagingException {

        String emaill = req.get("email");
        String otp = String.format("%06d", new Random().nextInt(999999));

        otpschema old = otprepo.findByemail(emaill);
        if (old != null) {
            otprepo.delete(old);
        }

        otpschema otpschema = new otpschema();
        otpschema.setEmail(emaill);
        otpschema.setOtp(otp);
        otpschema.setCreatedat(LocalDateTime.now());
        otprepo.save(otpschema);

        userschema userschema = new userschema();
        userschema.setEmail(emaill);
        userschema.setCreatedat(LocalDateTime.now());

        email.sendmails(
                emaill,
                "Login OTP",
                "your login OTP IS " + otp
        );


        System.out.println("OTP is send to " + emaill + "=" + otp);
        return ResponseEntity.ok(Map.of("success", true, "message", "otp is send succesfully"));

    }

    @Transactional
    @PostMapping("/verify")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    public ResponseEntity<?> verifyotp(@RequestBody Map<String, String> req) {
        String emaill = req.get("email");
        String uerotp = req.get("otp");

        otpschema otpdata = otprepo.findByemail(emaill);

        if (otpdata == null) {
            return ResponseEntity.badRequest().body(Map.of("success", true, "message", "OTP expired try again"));
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdtime = otpdata.getCreatedat();

        if (createdtime.plusMinutes(1).isBefore(now)) {
            otprepo.deleteByemail(emaill);
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "OTP expired request for new otp"));
        }


        if (!otpdata.getOtp().equals(uerotp)) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invalid otp"));
        }

        otprepo.deleteByemail(emaill);



        Optional<userschema> user = userrepo.findByemail(emaill);
        if (user.isEmpty()) {
            userschema userschema = new userschema();
            userschema.setEmail(emaill);
            userschema.setCreatedat(LocalDateTime.now());
            userrepo.save(userschema);
        }
            String access = refreshtoken.accesstoken(emaill);
            String refreshtokenn = refreshtoken.refreshtoken(emaill);

            Optional<tokenschema> existing=tokenrepo.findByemail(emaill);
            tokenschema token;
            if(existing.isPresent()) {
                token = existing.get();
                token.setRefreshtoken(refreshtokenn);
                token.setCreatedat(LocalDateTime.now());
                token.setExpiry(LocalDateTime.now().plusDays(7));

            }else{
                token =new tokenschema();
                token.setEmail(emaill);
                token.setRefreshtoken(refreshtokenn);
                token.setCreatedat(LocalDateTime.now());
                token.setExpiry(LocalDateTime.now().plusDays(7));

                tokenrepo.save(token);
            }

            ResponseCookie cookie = ResponseCookie.from("refresh", refreshtokenn)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .build();


            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(Map.of("success", true, "message", "Login succesfull","access",access));

        }

        @GetMapping("/getlogged")
            public ResponseEntity<?>getloginuser(HttpServletRequest req,@RequestHeader("Authorization")String authheader){
        if(authheader==null || !authheader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token");
        }

        String token=authheader.substring(7);

        String email=refreshtoken.extractemail(token);

        Optional<userschema> useremail=userrepo.findByemail(email);

        return ResponseEntity.ok(useremail);

        }

@PostMapping("/logout")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    public ResponseEntity<?>logoutuser(HttpServletRequest req,HttpServletResponse resp){
ResponseCookie cookie=ResponseCookie.from("refresh",null)
        .maxAge(0)
        .path("/")
        .secure(true)
        .sameSite("Lax")
        .httpOnly(true)
        .build();
return  ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body("Logged out succesfully");
}


@GetMapping("/iterms/{category}")
    public ResponseEntity<?>getcatagaries(@PathVariable String category){
    List<addproductschema> iterms=foodrepo.findByCategoryIgnoreCase(category);
    return ResponseEntity.ok(Map.of("itermss",iterms));
}








    }
