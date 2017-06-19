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

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService {

	private static final Logger logger = LoggerFactory.getLogger(FirebaseUtils.class);

	@Value("${email.from}")
	private String from;

	@Value("${email.host}")
	private String host;

	@Value("${email.path}")
	private String path;

	@Value("${email.port}")
	private String port;

	@Value("${email.password}")
	private String password;

	@Value("${email.username}")
	private String username;

	public void sendMail(String email, String subject, String body) {

		try {

			// Add properties to email config
			Properties props = System.getProperties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", "true"); // added this line  
			props.put("mail.smtp.starttls.required", "true"); // added this line

			SMTPAuthentication auth = new SMTPAuthentication(username, password);

			//Get sesión
			Session session = Session.getDefaultInstance(props, auth);

			//Definition of the message: to whom it is directed and its content
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject(subject);
			message.setText(body);

			// Create the mail root multipart
			MimeMultipart mpRoot = new MimeMultipart("mixed");

			// Create the content multipart (for text and HTML)
			MimeMultipart mpContent = new MimeMultipart("alternative");

			// Create a body part to house the multipart/alternative Part
			MimeBodyPart contentPartRoot = new MimeBodyPart();
			contentPartRoot.setContent(mpContent);

			// Add the root body part to the root multipart
			mpRoot.addBodyPart(contentPartRoot);

			// Add text
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(body, "ISO-8859-1");
			mpContent.addBodyPart(mbp1);

			// Add html
			body = body.replaceAll("\n", "<br>");
			body = "<html><body><p>" + body + "</p></body></html>";

			MimeBodyPart mbp2 = new MimeBodyPart();
			mbp2.setContent(body, "text/html");
			mpContent.addBodyPart(mbp2);

			// Disable attachment		
			//			//Recovery attachment
			//			List<File> ficheros = new ArrayList<>();
			//			File dir = new File(path);
			//			String[] archivos = dir.list();		
			//			//agregamos cada fichero en una lista
			//			if (archivos != null) {
			//				for (int i = 0; i < archivos.length; i++) {
			//					File fichero = new File(path + File.separator + archivos[i]);
			//					ficheros.add(fichero);
			//				}
			//			}
			//			
			//			for (File fichero: ficheros) {
			//				MimeBodyPart mbp3 = new MimeBodyPart();
			//				DataSource source = new FileDataSource(fichero);
			//				mbp3.setDisposition(Part.ATTACHMENT);
			//				mbp3.setDataHandler(new DataHandler(source));
			//				mbp3.setFileName(fichero.getName());
			//				mpRoot.addBodyPart(mbp3);
			//			}
			message.setContent(mpRoot);

			Transport.send(message);
		} catch (Exception e) {
			logger.error("sendMail: ", e);
		}
	}
}

// Connection definition and authentication
class SMTPAuthentication extends Authenticator {

	private String username;
	private String password;

	public SMTPAuthentication(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

}
