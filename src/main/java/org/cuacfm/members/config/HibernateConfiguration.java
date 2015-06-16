/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
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

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.websystique.springmvc.configuration" })
@PropertySource(value = { "classpath:persistence.properties" })
public class HibernateConfiguration {

   @Autowired
   private Environment environment;

   @Bean
   public LocalSessionFactoryBean sessionFactory() {
      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
      sessionFactory.setDataSource(dataSource());
      sessionFactory.setPackagesToScan(new String[] { "com.websystique.springmvc.model" });
      sessionFactory.setHibernateProperties(hibernateProperties());
      return sessionFactory;
   }

   @Bean
   public DataSource dataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
      dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
      dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
      dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
      return dataSource;
   }

   private Properties hibernateProperties() {
      Properties properties = new Properties();
      properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
      properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
      properties.put("hibernate.format_sql",
            environment.getRequiredProperty("hibernate.format_sql"));
      return properties;
   }

   @Bean
   @Autowired
   public HibernateTransactionManager transactionManager(SessionFactory s) {
      HibernateTransactionManager txManager = new HibernateTransactionManager();
      txManager.setSessionFactory(s);
      return txManager;
   }
}
