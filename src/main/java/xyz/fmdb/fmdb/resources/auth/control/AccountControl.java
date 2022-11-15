package xyz.fmdb.fmdb.resources.auth.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.fmdb.fmdb.common.response.Response;
import xyz.fmdb.fmdb.common.utils.JwtUtil;
import xyz.fmdb.fmdb.repositories.auth.boundary.AccountRepository;
import xyz.fmdb.fmdb.repositories.auth.entity.Account;
import xyz.fmdb.fmdb.resources.auth.entity.AccountRequest;

@Component
public class AccountControl {

    private AccountRepository accountRepository;
    private JwtUtil jwtUtil;

    @Autowired
    public AccountControl(AccountRepository accountRepository, JwtUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity registerController(AccountRequest accountRequest){
        Account toBeSaved=new Account();
        toBeSaved.setUsername(accountRequest.getUsername().toLowerCase());
        Account foundAccount=accountRepository.findOneByUsername(accountRequest.getUsername());
        if(foundAccount!=null){
            return new ResponseEntity("User already exists", HttpStatus.FORBIDDEN);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPassword=bCryptPasswordEncoder.encode(accountRequest.getPassword());
        toBeSaved.setPassword(hashPassword);
        accountRepository.save(toBeSaved);
        return new ResponseEntity("Successful",HttpStatus.CREATED);

    }

    public ResponseEntity loginController(AccountRequest accountRequest){
    Account foundAccount=accountRepository.findOneByUsername(accountRequest.getUsername());
        if(foundAccount==null){
        return new ResponseEntity<>("Username doesn't exist",HttpStatus.FORBIDDEN);
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(accountRequest.getPassword(), foundAccount.getPassword())){
        return new ResponseEntity(new Response("Successful", jwtUtil.generateJwtToken(foundAccount.getId())),HttpStatus.OK);
    }else{
        return new ResponseEntity("Wrong credntials",HttpStatus.FORBIDDEN);
    }
    }
}
