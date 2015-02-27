package org.cuacfm.members.web.home;

import java.security.Principal;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** The Class HomeController.*/
@Controller
public class HomeController {
	
    /** The account service. */
    @Autowired
    private AccountService accountService;
    
    /** The account. */
    private Account account;
    
    /**  Version preliminar
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal) {
		return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
	}
	**/
	
		
    /**
     * Index.
     *
     * @param principal
     *            the principal
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal, Model model) {

        if (principal != null) {
            account = accountService.findByLogin(principal.getName());
            model.addAttribute("role", account.getRole());
            return "home/homeSignedIn";

        } else {

            return "home/homeNotSignedIn";
        }
    }
}
