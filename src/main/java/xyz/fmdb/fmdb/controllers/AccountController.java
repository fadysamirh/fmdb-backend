package xyz.fmdb.fmdb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import xyz.fmdb.fmdb.models.Account;
import xyz.fmdb.fmdb.repositories.AccountRepository;
import xyz.fmdb.fmdb.response.Response;
import xyz.fmdb.fmdb.util.JwtUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    JwtUtils jwtUtils;

    @RequestMapping(method = RequestMethod.POST,value = "/register")
    public ResponseEntity register(@Valid @RequestBody Account account){

        Account foundAccount=accountRepository.findOneByUsername(account.getUsername());
        if(foundAccount!=null){
            return new ResponseEntity("User already exists",HttpStatus.FORBIDDEN);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPassword=bCryptPasswordEncoder.encode(account.getPassword());
        accountRepository.save(new Account(account.getUsername(), hashPassword));
        return new ResponseEntity("Successful",HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/login")
    public ResponseEntity login(@Valid @RequestBody Account account){
        Account foundAccount=accountRepository.findOneByUsername(account.getUsername());
        if(foundAccount==null){
            return new ResponseEntity<>("Username doesn't exist",HttpStatus.FORBIDDEN);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(account.getPassword(), foundAccount.getPassword())){
            return new ResponseEntity(new Response("Successful",jwtUtils.generateJwtToken(foundAccount)),HttpStatus.OK);
        }else{
            return new ResponseEntity("Wrong credntials",HttpStatus.FORBIDDEN);
        }


    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlePasswordValidationException(MethodArgumentNotValidException e) {

        //Returning password error message as a response.
        return new ResponseEntity<String>(String.join(",", e.getBindingResult().getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
    }
}
