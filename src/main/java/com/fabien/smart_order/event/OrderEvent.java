package com.fabien.smart_order.event;

import com.fabien.smart_order.model.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Représente un événement lié à la création d'une commande. Permet de transporter l'objet Order aux observateurs.
 */
@Getter
@RequiredArgsConstructor
public class OrderEvent {

    private final Order order;

}
