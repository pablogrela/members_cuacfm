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

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.Mongo;

@Configuration
class MongoConfig {

   @SuppressWarnings("deprecation")
   @Bean
   public MongoDbFactory mongoDbFactory() throws UnknownHostException {
      return new SimpleMongoDbFactory(new Mongo(), "nue");
   }

   @Bean
   public MongoTemplate mongoTemplate() throws UnknownHostException {
      return new MongoTemplate(mongoDbFactory(), mongoConverter());
   }

   @Bean
   public MongoTypeMapper mongoTypeMapper() {
      return new DefaultMongoTypeMapper(null);
   }

   @Bean
   public MongoMappingContext mongoMappingContext() {
      return new MongoMappingContext();
   }

   @Bean
   public MappingMongoConverter mongoConverter() throws UnknownHostException {
      @SuppressWarnings("deprecation")
      MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory(),
            mongoMappingContext());
      converter.setTypeMapper(mongoTypeMapper());
      return converter;
   }
}
