package com.unzilemedet.service;


import com.unzilemedet.dto.ForgotPasswordMailResponseDto;
import com.unzilemedet.rabbitmq.model.RegisterMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendMail(RegisterMailModel registerMailModel){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("bilgeadamum@gmail.com");
        mailMessage.setTo(registerMailModel.getEmail());
        mailMessage.setSubject("AKTIVASYON KODU");
        mailMessage.setText(
                registerMailModel.getUsername() + " başarıyla kayıt oldunuz.\n" +
                "Aktivasyon Kodu: " + registerMailModel.getActivationCode()
        );
        javaMailSender.send(mailMessage);
    }

    public Boolean sendMailForgotPassword(ForgotPasswordMailResponseDto dto){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("${spring.mail.username}");
            mailMessage.setTo(dto.getEmail());
            mailMessage.setSubject("SIFRE SIFIRLAMA E-POSTASI");
            mailMessage.setText("Yeni şifreniz : " + dto.getPassword() +
                    "\nGiriş yaptıktan sonra güvenlik nedeniyle şifrenizi değiştiriniz.");
            javaMailSender.send(mailMessage);
        }catch (Exception e){
            e.getMessage();
        }
        return true;
    }
}
