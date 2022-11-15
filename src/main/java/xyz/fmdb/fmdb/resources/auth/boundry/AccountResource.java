package xyz.fmdb.fmdb.resources.auth.boundry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.fmdb.fmdb.resources.auth.control.AccountControl;
import xyz.fmdb.fmdb.resources.auth.entity.AccountRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AccountResource {

    private AccountControl accountControl;

    @Autowired
    public AccountResource(AccountControl accountControl) {
        this.accountControl = accountControl;
    }



    @RequestMapping(method = RequestMethod.POST,value = "/register")
    public ResponseEntity register(@Valid @RequestBody AccountRequest accountRequest){
        return accountControl.registerController(accountRequest);

    }

    @RequestMapping(method = RequestMethod.POST,value = "/login")
    public ResponseEntity login(@Valid @RequestBody AccountRequest accountRequest){
        return accountControl.loginController(accountRequest);
    }
}
