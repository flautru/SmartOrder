# 📦 SmartOrder – API de gestion de commandes (Design Patterns en Java)

**SmartOrder** est une application Spring Boot conçue pour illustrer l’utilisation de plusieurs **design patterns de création** en Java, dans le cadre d’une API REST simulant un système de gestion de commandes e-commerce.

---

## 🧠 Objectifs pédagogiques

- Mettre en pratique les **design patterns de création** :
  - `Builder` : création d’objets `Order` complexes
  - `Factory Method` : instanciation dynamique de services de livraison et paiement
  - `Prototype` : clonage d’une commande existante
  - `Singleton` : configuration globale accessible partout

- Structurer une application Spring Boot modulaire et maintenable

---

## 🛠️ Stack technique

- Java 21  
- Spring Boot (Web)  
- Maven  
- Lombok *(optionnel)*  
- REST API  
- JUnit *(tests à venir)*

---

## 📁 Architecture

```text
src/
├── config/            → Gestion de la configuration globale (singleton)
├── controller/        → Exposition des endpoints REST
├── delivery/          → Implémentation du pattern Factory Method pour delivery
├── dto/               → Implémentation du pattern Data Transfer Object
├── mapper/            → Implémentation des mappers
├── model/             → Objets métiers : Order, Product, Customer, etc.
├── payment/           → Implémentation du pattern Factory Method pour payment
├── repository/        → Interface Jpa avec la base de donnée H2
├── service/           → Logique applicative
└── SmartOrderApplication.java
```

## 📌 Design Patterns utilisés

- Builder :	Construire une commande complète étape par étape
- Factory :	Créer dynamiquement des services de paiement/livraison
- Singleton :	Fournir une configuration globale unique à l'app
- Prototype :	Cloner facilement une commande existante

## 📲 Endpoints disponibles
```text
Méthode	URI	Description
GET	/api/products	Obtenir la liste des produits
POST	/api/orders	Créer une nouvelle commande
POST	/api/orders/{id}/clone	Cloner une commande existante
GET	/api/config	Afficher la configuration système
```

## 🧭 Roadmap Design Patterns (en cours d'intégration)

Ce projet a pour objectif d’illustrer l'utilisation professionnelle des Design Patterns en Java/Spring Boot. Voici les évolutions prévues :

| Design Pattern | Description | Statut |
|----------------|-------------|--------|
| 🟢 Observer | Notifications client par email ou SMS lorsque la commande est créée ou mise à jour. | À faire |
| 🟢 Strategy | Stratégie de livraison interchangeable : Colissimo, Chronopost, Relais colis. | À faire |
| 🟢 Template Method | Structure générique des notifications : entête, contenu, signature. | À faire |
| 🟢 Specification | Validation métier des commandes : montant minimum, produits présents, etc. | À faire |
| 🟢 State | Cycle de vie de la commande : Pending → Paid → Shipped → Delivered. | À faire |
| 🟢 Decorator | Ajout dynamique d’options à la commande (emballage cadeau, assurance…). | À faire |
| 🟢 Chain of Responsibility | Traitement du paiement en plusieurs étapes (validation → fraude → débit). | À faire |

> 🛠️ Chaque pattern est intégré progressivement dans un esprit de clean architecture et testabilité. Suivez le Kanban GitHub pour suivre l’avancement.

https://github.com/users/flautru/projects/2/views/1
