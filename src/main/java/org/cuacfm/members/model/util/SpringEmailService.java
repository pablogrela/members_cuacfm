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
package org.cuacfm.members.model.util;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("springEmailService")
public class SpringEmailService {

	@Value("${path}${email.path}")
	private String path;

	@Value("${email.from}")
	private String from;

	private static final Logger logger = LoggerFactory.getLogger(FirebaseUtils.class);

	@Autowired
	public JavaMailSender javaMailSender;

	public boolean sendMail(String to, String subject, String msg) {

		try {
			SimpleMailMessage message = new SimpleMailMessage();

			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(msg);

			javaMailSender.send(message);
			return true;

		} catch (Exception e) {
			logger.error("sendMail: ", e);
			return false;
		}
	}

	public boolean sendMailWithAttachment(String to, String subject, String msg, String fileName) {

		try {

			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(msg);

			FileUtils.createFolderIfNoExist(path);
			FileSystemResource file = new FileSystemResource(new File(path + fileName));
			helper.addAttachment("Invoice", file);

			javaMailSender.send(message);
			return true;

		} catch (Exception e) {
			logger.error("sendMailWithAttachment: ", e);
			return false;
		}
	}
}
