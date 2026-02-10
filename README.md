# Resource Booking System — Module 1

## Panoramica del progetto
Il progetto **Resource Booking System** è una web application sviluppata in Java che consente la **gestione e la prenotazione di risorse condivise** (ad esempio aule, sale riunioni, laboratori o attrezzature) da parte di utenti registrati.

L’obiettivo del progetto è realizzare un sistema semplice ma strutturato che permetta di:
- creare e gestire risorse;
- creare e gestire utenti;
- effettuare prenotazioni su intervalli temporali definiti;
- prevenire conflitti di prenotazione;
- offrire sia un’interfaccia web sia un accesso tramite API REST.

Il progetto rappresenta la **versione richiesta per il Modulo 1**, focalizzata sulla modellazione del dominio, sulla logica applicativa e sulla persistenza dei dati.

---

## Funzionalità principali

### Gestione delle risorse
- Creazione di risorse con nome, tipo, location e descrizione opzionale.
- Gestione dello stato della risorsa (attiva / non attiva).
- Visualizzazione dell’elenco delle risorse disponibili.

### Gestione degli utenti
- Creazione di utenti identificati da username.
- Visualizzazione dell’elenco degli utenti registrati.

### Gestione delle prenotazioni
- Creazione di prenotazioni indicando risorsa, utente, data/ora di inizio e fine.
- Validazioni:
  - la data di fine deve essere successiva alla data di inizio;
  - la risorsa deve essere attiva;
  - una risorsa non può essere prenotata se già occupata nello stesso intervallo.
- Visualizzazione e annullamento delle prenotazioni.

---

## Interfaccia web e API REST

Il sistema offre due modalità di utilizzo.

**Interfaccia Web (UI server-side)**  
È disponibile una UI minimale basata su Thymeleaf che permette la gestione completa di risorse, utenti e prenotazioni tramite form e tabelle.

**API REST**  
Sono disponibili API REST sotto il prefisso `/api/*` per la gestione programmatica delle risorse, degli utenti e delle prenotazioni, utilizzando metodi HTTP standard e codici di stato significativi (ad esempio 409 in caso di conflitto).

---

## Architettura dell’applicazione
L’architettura del progetto è organizzata a strati:
- Presentation layer: controller Spring MVC e REST
- Service layer: logica di business e regole di prenotazione
- Persistence layer: JPA/Hibernate e repository

---

## Tecnologie utilizzate
- Java 21
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Data JPA / Hibernate
- PostgreSQL
- Gradle

---

## Avvio dell’applicazione
Dopo aver configurato un database PostgreSQL locale, l’applicazione può essere avviata eseguendo Gradle e sarà accessibile all’indirizzo http://localhost:8080.

---

## Riferimento Git
La versione relativa al **Modulo 1** è identificata dal tag **module1-final**.  
Questa versione rappresenta la consegna del Modulo 1, priva di estensioni cloud-native o containerizzazione.
