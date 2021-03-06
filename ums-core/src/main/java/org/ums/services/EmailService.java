package org.ums.services;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.ums.academic.model.PersistentUser;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.dto.ResetPasswordEmailDto;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.readOnly.User;

@Component("emailService")
public class EmailService  {

  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private PasswordService mPasswordService;
  @Autowired
  private VelocityEngine velocityEngine;

  private User user;

  public void setUser(User user){
    this.user=user;
  }

  public void sendEmail(final String toEmailAddresses, final String fromEmailAddress,
                        final String subject) {
    sendEmail(toEmailAddresses, fromEmailAddress, subject, null, null);
  }

  public void sendEmailWithAttachment(final String toEmailAddresses, final String fromEmailAddress,
                                      final String subject, final String attachmentPath,
                                      final String attachmentName) {
    sendEmail(toEmailAddresses, fromEmailAddress, subject, attachmentPath, attachmentName);
  }

  private void sendEmail(final String toEmailAddresses, final String fromEmailAddress,
                         final String subject, final String attachmentPath,
                         final String attachmentName) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        message.setTo(toEmailAddresses);
        message.setFrom(new InternetAddress(fromEmailAddress,"IUMS"));
        message.setSubject(subject);


        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy  HH:mm");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);

        ResetPasswordEmailDto others = new ResetPasswordEmailDto();
        others.setUmsRootUrl("https://localhost/ums-web/login");
        others.setUmsForgotPasswordUrl("https://localhost/ums-web/login/?fogot-password.ums");
        String abc="https://localhost/ums-web/login/reset-password.html?pr_token=$$TOKEN$$&uid=$$USER_ID$$";
        abc=abc.replace("$$TOKEN$$",user.getPasswordResetToken());
        abc=abc.replace("$$USER_ID$$",user.getId());

        others.setUmsResetPasswordUrl(abc);
        others.setForgotPasswordRequestDateTime(strDate);


        Map model = new HashMap();
        model.put("user",user );
        model.put("others",others );

        String body = VelocityEngineUtils.mergeTemplateIntoString(
            velocityEngine, "email-templates/password-reset.vm", "UTF-8", model);
        message.setText(body, true);
        if (!StringUtils.isBlank(attachmentPath)) {
          FileSystemResource file = new FileSystemResource(attachmentPath);
          message.addAttachment(attachmentName, file);
        }
      }
    };
    this.mailSender.send(preparator);
  }

}
