package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.service.UserService;
import com.drunar.coincorner.util.RandomStringGenerator;
import com.drunar.coincorner.util.WebUtility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/forgot")
public class ForgotPasswordController {

    private final JavaMailSender mailSender;
    private final UserService userService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "/forgot/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomStringGenerator.generateRandomString(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = WebUtility.getSiteURL(request) + "/forgot/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "/forgot/forgot_password_form";
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        return userService.getByResetPasswordToken(token)
                .map(user -> {
                    model.addAttribute("token", token);
                    return "forgot/reset_password_form";
                })
                .orElseGet(() -> {
                    model.addAttribute("message", "Invalid Token");
                    return "forgot/reset_password_form";
                });
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        return userService.getByResetPasswordToken(token)
                .map(user -> {
                    model.addAttribute("title", "Reset your password");
                    userService.updatePassword(user, password);
                    redirectAttributes.addFlashAttribute("messagePwdChange", "You have successfully changed your password, please try login..");
                    return "redirect:/login";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("messagePwdChange", "Invalid Token");
                    return "redirect:/login";
                });
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@coincorner.com", "CoinCorner Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}