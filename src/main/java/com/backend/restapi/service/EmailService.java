package com.backend.restapi.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final OtpService otpService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, OtpService otpService) {
        this.javaMailSender = javaMailSender;
        this.otpService = otpService;
    }
    public void sendOtpEmailRegister(String email, String username) {
        String otp = otpService.generateOTP(email);
        String recipientEmail = email;
        String subject = "Xác thực OTP";
        String text = "Xin chào " + username + ",\n\n"
                + "Chúng tôi đã nhận được yêu cầu đăng ký tài khoản của bạn tại trang safamo.com .\n"
                + "Vui lòng sử dụng mã OTP sau để xác nhận tài khoản của bạn:\n\n"
                + "Mã OTP: "+ otp +"\n\n"
                + "Cảm ơn bạn đã đăng ký tài khoản!";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
    
    public void sendOtpEmailChangePassword(String email) {
        String otp = otpService.generateOTP(email);
        String recipientEmail = email;
        String subject = "Xác thực OTP thay đổi mật khẩu";
        String text ="Chúng tôi đã nhận được yêu cầu đăng thay đổi mật khẩu của bạn tại trang safamo.com .\n"
                + "Vui lòng sử dụng mã OTP sau để xác nhận tài khoản của bạn:\n\n"
                + "Mã OTP: "+ otp +"\n\n"
                + "Cảm ơn bạn đã tin tưởng!";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}

