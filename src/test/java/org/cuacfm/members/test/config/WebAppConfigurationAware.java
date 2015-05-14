package org.cuacfm.members.test.config;

import org.cuacfm.members.config.WebMvcConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/** The Class WebAppConfigurationAware.*/
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = {
        ApplicationConfigTest.class,
        EmbeddedDataSourceConfig.class,
        JpaConfigTest.class,
        NoCsrfSecurityConfig.class,
        WebMvcConfig.class
})
public abstract class WebAppConfigurationAware {

    /** The wac. */
    @Inject
    protected WebApplicationContext wac;
    
    /** The mock mvc. */
    protected MockMvc mockMvc;

    /**
     * Before.
     */
    @Before
    public void before() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

}
