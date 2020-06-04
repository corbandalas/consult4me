package studio.secretingredients.consult4me.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class EmailSample {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    private TemplateEngine templateEngine; // From Thymeleaf
//
//    public void initiateEmailSend() {
//        String processedHTMLTemplate = this.constructHTMLTemplate();
//
//        // Start preparing the email
//        MimeMessagePreparator preparator = message -> {
//             MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
//             helper.setFrom("Sample <sample@example.com>");
//             helper.setTo("recipient@example.com");
//             helper.setSubject("Sample Subject");
//             helper.setText(processedHTMLTemplate, true);
//        };
//
//        mailSender.send(preparator); //send the email
//    }
//
//    // Fills up the HTML file
//    private String constructHTMLTemplate() {
//        Context context = new Context();
//        context.setVariable("sampleText", "My text sample here");
//        return templateEngine.process("MySampleHTML", context);
//    }
}