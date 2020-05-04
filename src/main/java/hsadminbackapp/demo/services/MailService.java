package hsadminbackapp.demo.services;

public interface MailService {
    void sendEmail(String sendTo, String subject, String content);
}
