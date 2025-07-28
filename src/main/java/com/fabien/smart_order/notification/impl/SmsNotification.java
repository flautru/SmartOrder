package com.fabien.smart_order.notification.impl;

import com.fabien.smart_order.notification.AbstractNotification;
import org.springframework.stereotype.Component;

@Component
public class SmsNotification extends AbstractNotification {

    private static final int MAX_SMS_LENGTH = 160;

    @Override
    protected String getNotificationType() {
        return "SMS";
    }

    @Override
    protected String buildHeader(String subject) {
        return String.format("[SmartOrder] %s\n\n", truncate(subject, 30));
    }

    @Override
    protected String formatContent(final String content) {
        final String cleanContent = content.replaceAll("<[^>]*>", "")
            .replaceAll("\\s+", " ")
            .trim();

        return truncate(cleanContent, 100) + "\n\n";
    }

    @Override
    protected String buildSignature() {
        return "-- SmartOrder";
    }

    @Override
    protected boolean doSend(final String recipient, final String message) {
        final String finalMessage = truncate(message, MAX_SMS_LENGTH);

        logger.debug("Envoi SMS ({}/{} caractères)", finalMessage.length(), MAX_SMS_LENGTH);

        return true;
    }

    private String truncate(final String text, final int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}
