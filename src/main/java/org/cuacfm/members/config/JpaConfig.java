/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.cuacfm.members.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * The Class JPAConfig.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = Application.class)
class JpaConfig implements TransactionManagementConfigurer {

   /** The Class DataAccessConfig. */
   @Value("${dataSource.driverClassName}")
   private String driver;

   /** The url. **/
   @Value("${dataSource.url}")
   private String url;

   /** The username. */
   @Value("${dataSource.username}")
   private String username;

   /** The password. */
   @Value("${dataSource.password}")
   private String password;

   /** The ssl. */
   @Value("${dataSource.useSSL}")
   private String useSSL;
   
   /** The dialect. */
   @Value("${hibernate.dialect}")
   private String dialect;

   /** The hbm2ddl auto. */
   //@Value("${hibernate.hbm2ddl.auto}")
   //private String hbm2ddlAuto

   /**
    * Configure data source.
    * 
    * @return the data source
    */
   @Bean
   public DataSource configureDataSource() {
      HikariConfig config = new HikariConfig();
      config.setDriverClassName(driver);
      config.setJdbcUrl(url);
      config.setUsername(username);
      config.setPassword(password);
      config.addDataSourceProperty("useSSL", useSSL);
      config.addDataSourceProperty("cachePrepStmts", "true");
      config.addDataSourceProperty("prepStmtCacheSize", "250");
      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
      config.addDataSourceProperty("useServerPrepStmts", "true");

      return new HikariDataSource(config);
   }

   /**
    * Configure entity manager factory.
    *
    * @return the local container entity manager factory bean
    */
   @Bean
   public LocalContainerEntityManagerFactoryBean configureEntityManagerFactory() {
      LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
      entityManagerFactoryBean.setDataSource(configureDataSource());
      entityManagerFactoryBean.setPackagesToScan("org.cuacfm.members");
      entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

      Properties jpaProperties = new Properties();
      jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
      
      // This property resolve org.hibernate.lazyinitializationexception 
      // Another solution is change @PersistenceContext to @PersistenceContext(type = PersistenceContextType.EXTENDED) in class repository  
      jpaProperties.put("hibernate.enable_lazy_load_no_trans", true);

      // Comentado por que no se utiliza
      // jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2ddlAuto)
      entityManagerFactoryBean.setJpaProperties(jpaProperties);

      return entityManagerFactoryBean;
   }

   /**
    * Configure transaction manager factory.
    *
    * @return the local platfrom manager factory bean
    */
   @Bean
   @Override
   public PlatformTransactionManager annotationDrivenTransactionManager() {
      return new JpaTransactionManager();
   }
}
