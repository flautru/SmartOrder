package com.fabien.smart_order.notification.impl;

import com.fabien.smart_order.notification.AbstractNotification;
import org.springframework.stereotype.Component;

@Component
public class EmailNotification extends AbstractNotification {
    @Override
    protected String getNotificationType() {
        return "EMAIL";
    }

    @Override
    protected String buildHeader(final String subject) {
        return String.format("""
            <html>
            <head><title>%s</title></head>
            <body style="font-family: Arial, sans-serif;">
                <h2 style="color: #2c3e50;">SmartOrder - %s</h2>
                <hr>
            """, subject, subject);
    }

    @Override
    protected String formatContent(final String content) {
        return String.format("""
                <div style="margin: 20px 0; line-height: 1.6;">
                    <p>%s</p>
                </div>
            """, content.replace("\n", "<br>"));
    }

    @Override
    protected String buildSignature() {
        return """
                <hr>
                <p style="color: #666; font-size: 12px;">
                    <strong>L'équipe SmartOrder</strong><br>
                    📧 contact@smartorder.com | 📞 01 23 45 67 89
                </p>
            </body>
            </html>
            """;
    }

    @Override
    protected boolean doSend(String recipient, String message) {
        logger.debug("Envoi email HTML ({} caractères)", message.length());

        return true;
    }
}
