package org.cuacfm.members.web.home;

import java.security.Principal;

import org.cuacfm.members.model.accountService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class HomeController.*/
@Controller
public class HomeController {
	
    /** The account service. */
    @Autowired
    private AccountService accountService;
    
    
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
    public String index(Principal principal, Model model, RedirectAttributes ra) {

        if (principal != null) {
            return "home/homeSignedIn";
        } else {
            return "home/homeNotSignedIn";
        }
    }
}
