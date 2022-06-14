package com.upgrad.notificationservice.service;

import com.upgrad.notificationservice.dto.AppointmentDto;
import com.upgrad.notificationservice.dto.DoctorDto;
import com.upgrad.notificationservice.dto.PrescriptionDto;
import com.upgrad.notificationservice.dto.UserDto;
import com.upgrad.notificationservice.enumeration.RegistrationStatus;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private SesClient client;
    private final FreeMarkerConfigurer freeMarkerConfigurer;
    final String fromEmail = "genuineroshan095@gmail.com";

    @PostConstruct
    public void init(){
        /**
         * Credentials of the IAM user with SES full access.
         */
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create("AKIARPE32VMFSI7N63WT",
                        "SWFs0SVf4oEq5YZ9LqD8ApmZ0k0q4YgzAoxgZ81E")
        );

        client = SesClient.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.US_EAST_1)
                .build();
    }

    public void verifyEmail(String emailId){
        client.verifyEmailAddress(req -> req.emailAddress(emailId));
    }

    public void sendEmailToDoctor(DoctorDto doctor) throws IOException, TemplateException, MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("user", doctor);
        Template freemarkerTemplate = this.getTemplateForDoctor(doctor);
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        SendSimpleEmail(doctor.getEmailId(), "Registration", htmlBody);
    }

    public void sendEmailToUser(UserDto user) throws IOException, TemplateException, MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("user", user);
        Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration().getTemplate("userregistration.ftl");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        SendSimpleEmail(user.getEmailId(), "Registration", htmlBody);
    }

    public void sendPrescription(PrescriptionDto prescription) throws IOException, TemplateException, MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("prescription", prescription);
        Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration().getTemplate("doctorprescription.ftl");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        SendSimpleEmail(prescription.getUserEmailId(), "Prescription", htmlBody);
    }

    public void sendAppointment(AppointmentDto appointment) throws IOException, TemplateException, MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("appointment", appointment);
        Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration().getTemplate("doctorappointment.ftl");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        SendSimpleEmail(appointment.getUserEmailId(), "Appointment", htmlBody);
    }

    private void SendSimpleEmail(String toEmail, String subject, String body) throws MessagingException {
        Properties properties = System.getProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = javax.mail.Session.getDefaultInstance(properties);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(fromEmail);
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        msg.setSubject(subject);
        msg.setContent(body, "text/html");
        /**
         * Credentials of the SMTP user.
         */
        try (Transport transport = session.getTransport()) {
            transport.connect("email-smtp.us-east-1.amazonaws.com", "AKIARPE32VMF37DGPUUR",
                    "BGnr2NZssbBq2/9FqzdBoc6LO0U7HFFt2tKeFRfSAive");
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Template getTemplateForDoctor(DoctorDto doctorDto) throws IOException {
        switch (Enum.valueOf(RegistrationStatus.class, doctorDto.getStatus())){
            case ACTIVE:
                return freeMarkerConfigurer.getConfiguration().getTemplate("doctorregistrationverficationsuccessfull.ftl");
            case REJECTED:
                return freeMarkerConfigurer.getConfiguration().getTemplate("doctorregistrationverficationfailure.ftl");
            case PENDING:
                return freeMarkerConfigurer.getConfiguration().getTemplate("doctorregistration.ftl");
            default:
                throw new IllegalStateException("Unexpected value: " + Enum.valueOf(RegistrationStatus.class, doctorDto.getStatus()));
        }
    }
}
