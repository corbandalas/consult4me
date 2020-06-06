package studio.secretingredients.consult4me.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.service.PropertyService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine; // From Thymeleaf

    @Autowired
    private PropertyService propertyService;


    public void sendCustomerRegistration(String toEmail, String customerName, String customerPassword) {

        Map<String, String> data = new HashMap<>();

        data.put("name", customerName);
        data.put("password", customerPassword);

        initiateEmailSend(toEmail, "Успешная регистрация", "customerRegistration", data);
    }

    public void sendSpecialistRegistrationToAdmin(String specialistEmail, String specialistName, Set<Specialisation> specialisations) {

        Map<String, String> data = new HashMap<>();

        data.put("name", specialistName);
        data.put("email", specialistEmail);
        data.put("specialisation", specialisations.toString());

        initiateEmailSend(propertyService.findPropertyByKey("studio.secretingredients.info.email").getValue(), "Новая регистрация специалиста",
                "specialistRegistrationAdmin", data);
    }

    public void sendSpecialistRegistration(String toEmail, String customerName) {

        Map<String, String> data = new HashMap<>();

        data.put("name", customerName);

        initiateEmailSend(toEmail, "Успешная регистрация", "specialistRegistration", data);
    }

    private void initiateEmailSend(String toEmail, String subject, String templateName, Map<String, String> templateValues) {
        String processedHTMLTemplate = this.constructHTMLTemplate(templateName, templateValues);

        // Start preparing the email
        MimeMessagePreparator preparator = message -> {
             MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
             helper.setFrom(propertyService.findPropertyByKey("studio.secretingredients.info.email").getValue());
             helper.setTo(toEmail);
             helper.setSubject(subject);
             helper.setText(processedHTMLTemplate, true);
        };

        mailSender.send(preparator); //send the email
    }

    // Fills up the HTML file
    private String constructHTMLTemplate(String templateName, Map<String, String> templateValues) {
        Context context = new Context();
        for (String key: templateValues.keySet()) {
            context.setVariable(key, templateValues.get(key));
        }

        return templateEngine.process(templateName, context);
    }
}