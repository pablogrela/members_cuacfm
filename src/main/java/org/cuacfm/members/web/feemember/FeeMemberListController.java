package org.cuacfm.members.web.feemember;

import java.util.List;

import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/** The Class FeeMemberListController. */
@Controller
public class FeeMemberListController {

   /** The Constant FEEMEMBER_VIEW_NAME. */
   private static final String FEEMEMBER_VIEW_NAME = "feemember/feememberlist";

   /** The FeeMemberService. */
   @Autowired
   private FeeMemberService feeMemberService;

   /** The feeMembers. */
   private List<FeeMember> feeMembers;

   /**
    * Instantiates a new fee member List Controller.
    */
   public FeeMemberListController() {
      // Default empty constructor.
   }

   /**
    * List of FeeMember.
    *
    * @return List<FeeMember>
    */
   @ModelAttribute("feeMembers")
   public List<FeeMember> feeMembers() {
      return feeMembers;
   }

   /**
    * Show FeeMember List.
    *
    * @param model
    *           the model
    * @return the string the view
    */

   @RequestMapping(value = "feeMemberList")
   public String feeMembers(Model model) {
      feeMembers = feeMemberService.getFeeMemberList();
      model.addAttribute("feeMembers", feeMembers);
      return FEEMEMBER_VIEW_NAME;
   }

}
