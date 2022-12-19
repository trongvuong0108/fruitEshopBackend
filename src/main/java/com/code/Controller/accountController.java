package com.code.Controller;

import com.code.Entity.account;
import com.code.Entity.token;
import com.code.Enum.role;
import com.code.Enum.tokenType;
import com.code.Model.*;
import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import com.code.Service.*;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Properties;

@RestController
@RequestMapping(value = "/account")
public class accountController {
    @Autowired
    private accountService accountService;

    @Autowired
    private tokenService tokenService;

    @Autowired
    private JavaMailSender javaMailSender ;

    public void sendEmail(String toEmail ,
                          String subject,
                          String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ngaitrong0108@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        javaMailSender.send(mailMessage);
    }

    @GetMapping(value = "/get")
    public account get(@RequestParam("username") String username ){
        if(accountService.getByUserName(username) == null) return new account();
        return accountService.getByUserName(username);
    }


    @GetMapping(value = "/save")
    public HttpStatus save(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone
    ){
        Uploader uploader = new Uploader();
        account account = new account();
        account.setUsername(username);
        account.setPassword(password);
        account.setFullName(fullName);
        account.setEmail(email.trim());
        account.setAddress(address);
        account.setPhone(phone);
        account.setEnable(false);
        account.setUserRole(role.CLIENT);
        accountService.save(account);
        token signupToken = new token();
        signupToken.genNewToken();
        signupToken.setAccount(account);
        signupToken.setTokenType(tokenType.SIGNUP);
        tokenService.save(signupToken);
        sendEmail(account.getEmail(),"Token",signupToken.getToken());
        return HttpStatus.OK;
    }

    @GetMapping(value = "/genNewSignupToken")
    public token genNewSignupToken(@RequestParam("username") String username){
        account account = accountService.getByUserName(username);
        token signupToken = new token();
        signupToken.genNewToken();
        signupToken.setAccount(account);
        signupToken.setTokenType(tokenType.SIGNUP);
        tokenService.save(signupToken);
        sendEmail(account.getEmail(),"Token",signupToken.getToken());
        return signupToken;
    }

    @GetMapping(value = "/confirmSignUpToken")
    public String confirmToken(
            @RequestParam("token") String token,
            @RequestParam("username") String username){
        token signinToken = tokenService.findByToken(token);
        if(
                signinToken.getAccount().getUsername().equals(username)&&
                signinToken != null &&
                signinToken.getTokenType() == tokenType.SIGNUP &&
                signinToken.getExpiryAt().isAfter(LocalDateTime.now())
        ){
            account account =signinToken.getAccount() ;
            account.setEnable(true);
            accountService.save(account);
            return "Successful";
        }
        return "Failure";
    }

    @GetMapping(value = "/genNewChangePassToken")
    public token genNewChangePassToken(@RequestParam("username") String username){
        token changePassToken = new token();
        account account = accountService.getByUserName(username);
        changePassToken.genNewToken();
        changePassToken.setAccount(account);
        changePassToken.setTokenType(tokenType.REPASSWORD);
        tokenService.save(changePassToken);
        sendEmail(account.getEmail(),"Token",changePassToken.getToken());
        return changePassToken;
    }

    @GetMapping(value = "/confirmChangePassToken")
    public boolean confirmChangePassToken(@RequestParam("token") String token,
                                        @RequestParam("username") String username){
        token changePassToken = tokenService.findByToken(token);
        if(
                changePassToken.getAccount().getUsername().equals(username)&&
                        changePassToken != null &&
                        changePassToken.getTokenType() == tokenType.REPASSWORD &&
                        changePassToken.getExpiryAt().isAfter(LocalDateTime.now())
        ){
            return true;
        }
        return false;
    }

    @PostMapping(value = "/changePass")
    public void confirmChangePassToken(@RequestBody rePassword rePassword) throws Exception {
        account account = accountService.getByUserName(rePassword.getUsername());
        if(account == null)
            throw new Exception("username not found");
        if(!rePassword.getPassword().equals(account.getPassword()))
            throw new Exception("password not match");
        account.setPassword(rePassword.getNewPassword());
        accountService.save(account);
    }

    @GetMapping(value = "/getUser")
    public Properties getUser(@RequestParam("jwt") String token) throws UnsupportedEncodingException {
        Properties prop = new Properties();
        String[] pieces = token.split("\\.");
        String b64payload = pieces[1];
        String jsonString = new String(Base64.decodeBase64(b64payload), "UTF-8");
        jwtDecodeModel jwtDecodeModel = new Gson().fromJson(jsonString, jwtDecodeModel.class);
        account accountResult = accountService.getByUserName(jwtDecodeModel.sub);
        prop.put("username" , accountResult.getUsername());
        prop.put("fullname" , accountResult.getFullName());
        prop.put("phone" , accountResult.getPhone());
        prop.put("address" , accountResult.getAddress());
        prop.put("email" , accountResult.getEmail());
        return prop ;
    }
}
