# TaxiFinder
## Corso di Programmazione 3
### Progetto Esame

Docente: Proff. Angelo Ciaramella e Raffaele Montella

A.A 2020/2021

### Studente
* **Cognome**: Vecchio

* **Nome**: Roberto

* **Matricola**: 124/1871

## Traccia - Taxi
Si vuole sviluppare un'**applicazione per la gestione di Taxi**. Si suppone di avere **una flotta di automobili** posizionate in appositi **parcheggi distribuiti in città. Un cliente può prenotare un Taxi (via sms o e-mail)** e **attendere presso** una **opportuna postazione** non necessariamente corrispondente a quella dove sostano i Taxi. **Il Taxi** che deve effettuare la corsa **viene scelto tra quelli liberi ed è quello che garantisce il percorso più breve per raggiungere la postazione** (usare **l'algoritmo di Dijkstra**, vedi sotto).

**Il Taxi può scegliere il percorso per raggiungere la destinazione secondo due strategie** (Usare l'algoritmo di Dijkstra, vedi sotto):
* **percorso con meno traffico.** Su ogni strada sono previsti due sensori che calcolano il tempo impiegato da un'auto per percorrere quel tratto;
* **percorso più breve.**
Implementare l'applicazione garantendo le **opportune interfacce grafiche.**

## Algoritmo di Dijkstra
L'algoritmo di Dijkstra è un algoritmo utilizzato per cercare i cammini minimi (o Shortest Paths) in un grafo con o senza ordinamento, ciclico e con pesi non negativi sugli archi. Per ulteriori dettagli sull'implementazione vedere:
[Algoritmo di Dijkstra](https://it.wikipedia.org/wiki/Algoritmo_di_Dijkstra)

## Note di sviluppo
La prova d'esame richiede la progettazione e lo sviluppo della traccia proposta. Lo studente può scegliere di sviluppare il progetto nelle due modalità: **Applicazione Web** o **Programma standalone con supporto grafico**.

Il progetto deve essere sviluppato secondo le seguenti linee:
* usare almeno **due pattern** (almeno **uno** per chi sceglie la **modalità web application**) tra i design pattern noti;
* attenersi ai principi della programmazione **SOLID**;
* usare il linguaggio **Java**;
* inserire sufficienti **commenti (anche per Javadoc)** e ** annotazioni;
* gestione delle **eccezioni**;
* usare i **file** o **database**;


Lo studente deve presentare una relazione sintetica
