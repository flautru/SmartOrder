package com.fabien.smart_order.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNotification {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public final boolean send(final String recipient, final String subject, final String content) {
        try {
            logger.info("Envoie {} ver {}", getNotificationType(), recipient);

            String fullMessage = buildFullMessage(subject, content);

            boolean success = doSend(recipient, fullMessage);

            // 3. Log du résultat
            if (success) {
                logger.info("{} envoyé avec succès à {}", getNotificationType(), recipient);
            } else {
                logger.warn("Échec envoi {} à {}", getNotificationType(), recipient);
            }

            return success;
        } catch (Exception e) {
            logger.error("Erreur envoi {} à {} : {}", getNotificationType(), recipient, e.getMessage());
            return false;
        }
    }

    private String buildFullMessage(final String subject, final String content) {
        final StringBuilder message = new StringBuilder();

        message.append(buildHeader(subject));
        message.append(formatContent(content));
        message.append(buildSignature());

        return message.toString();
    }

    protected abstract String getNotificationType();

    protected abstract String buildHeader(String subject);

    protected abstract String formatContent(String content);

    protected abstract String buildSignature();

    protected abstract boolean doSend(String recipient, String message);
}
