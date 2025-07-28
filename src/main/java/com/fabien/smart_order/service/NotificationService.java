package com.fabien.smart_order.service;

import com.fabien.smart_order.notification.impl.EmailNotification;
import com.fabien.smart_order.notification.impl.SmsNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailNotification emailNotification;
    private final SmsNotification smsNotification;

    public void sendOrderConfirmation(final String email, final String phone, final Long orderId, final double amount) {
        final String subject = "Confirmation commande #" + orderId;
        final String content = String.format("""
            Votre commande a été confirmée !
            
            Numéro : #%d
            Montant : %.2f €
            
            Merci pour votre confiance.
            """, orderId, amount);

        emailNotification.send(email, subject, content);
        smsNotification.send(phone, subject, content);
    }

    public void sendDeliveryNotification(final String contact, final String trackingCode) {
        final String subject = "Votre commande est expédiée";
        final String content = String.format("""
            Bonne nouvelle ! Votre commande est en route.
            
            Code de suivi : %s
            
            Vous la recevrez sous 24-48h.
            """, trackingCode);

        if (contact.contains("@")) {
            emailNotification.send(contact, subject, content);
        } else {
            smsNotification.send(contact, subject, content);
        }
    }
}
