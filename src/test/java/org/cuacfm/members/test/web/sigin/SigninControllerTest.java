package org.cuacfm.members.test.web.sigin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.cuacfm.members.test.config.WebAppConfigurationAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SigninControllerTest extends WebAppConfigurationAware {

    /**
     * Show page sigin
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void displaySignInPage() throws Exception {
        mockMvc.perform(get("/signin")).andExpect(view().name("signin/signin"));
    }
}