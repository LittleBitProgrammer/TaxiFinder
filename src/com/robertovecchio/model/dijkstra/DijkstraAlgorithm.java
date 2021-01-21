package com.robertovecchio.model.dijkstra;

import com.robertovecchio.model.graph.Graphable;
import com.robertovecchio.model.graph.edge.observer.Edge;
import com.robertovecchio.model.graph.node.Node;
import java.util.*;

/**
 * L'algoritmo di Dijkstra trova lo shortest path da una sorgente su tutte le destinazioni in un grafo direzionale.
 * L'idea di Dijkstra è semplice. Partiziona i nodi in due differenti set:
 *
 * - settled
 * - unsettled
 *
 * Inizialmente tutti i nodi sono di tipo unsettled perchè devono essere ancora valutati. Un nodo si muove nei settled
 * set se uno shortest path dalla sorgente a questo nodo è stato trovato.
 *
 * Inizialmente la distanza di ogni nodo alla sorgente è impostata ad un numero molto alto.
 *
 * Inoltre all'inizio solo la sorgente è nel set dei nodi unsettled. L'algoritmo esegue fin tanto che gli unsettled
 * node sono vuoti. In ogni iterazione viene selezionato il nondo con distanza minore dalla sorgente al di fuori degli
 * unsettled. Viene letto ogni nodo che va verso l'esterno dalla sorgente e valuta per ogni nodo di destinazione, negli
 * edge in cui non sono ancora settled, se la distanza conosciuta dalla sorgente a questo nodo può essere ridotta
 * utilizzando quell'edge. Se ciò risultasse vero allora la distanza viene aggiornata e il nodo viene aggiunto ai nodi
 * che hanno bisogno di essere valutati.
 *
 * Di seguito il pseudo codice dell'algoritmo il quale stiamo atraendo neella seguente classe:
 *
 * foreach node set distance[node] = HIGH
 *
 * settledNodes = empty
 * unsettledNodes =empty
 *
 * add sourceNode to unsettledNodes
 * distance[sourceNode] = 0
 *
 * while(unsettled is not empty){
 *     evaluationNode = getNodeWithLowestDistance(unsettledNodes)
 *     remove evaluationNode from unsettledNodes
 *     add evaluationNode to settledNodes
 *     evaluatesNeighbors(evaluationNode)
 * }
 *
 * getNodeWithLowestDistance(unsettledNodes){
 *     find the node with the lowest distane and return it
 * }
 *
 * evaluatesNeighbors(evaluationNode){
 *     foreach destinationNode that can be reached via an edge from evaluationNode AND is not in settledNodes{
 *         edgeDistance = getDistance(edge(evaluationNode, destinationNode))
 *         newDistance = distance [evaluationNode] + edgeDistance
 *         if(distance[destinationNode] > new distance){
 *             distance[destinationNode] = newDistance
 *             add destinationNode to unsettledNodes
 *         }
 *     }
 * }*/
public class DijkstraAlgorithm {

    //==================================================
    //               Variabili d'istanza
    //==================================================

    /**
     * Lista di nodi presenti nel grafo
     * @see List
     * @see Node
     * */
    private final List<Node> nodes;
    /**
     * Lista dei collegamenti presenti nel grafo
     * @see List
     * @see Edge
     */
    private final List<Edge> edges;
    /**
     * Set dei settled nodes dichiarati per seguire l'algoritmo
     * @see Set
     * @see Node
     */
    private Set<Node> settledNode;
    /**
     * Set degli unsettledNode dichiarati per seguire l'algoritmo
     * @see Set
     * @see Node
     */
    private Set<Node> unsettledNode;
    /**
     * Map dei predecessori dichiarati per seguire l'algoritmo
     * @see Map
     * @see Node
     * */
    private Map<Node, Node> predecessors;
    /**
     * Map per associare una distanza ad un nodo "destinazione"
     * @see Map
     * @see Node
     * @see Integer
     */
    private Map<Node, Double> distance;

    //==================================================
    //                 Costruttori
    //==================================================

    /**
     * Metodo Costruttore dell'algorimo di Dijkstra
     * @param graphable Grafo da utilizzare per l'algoritmo
     * @see Graphable
     */
    public DijkstraAlgorithm(Graphable graphable){
        // Creiamo una copia dell'array così possiamo operare su questi array
        this.nodes = new ArrayList<>(graphable.getVertexes());
        this.edges = new ArrayList<>(graphable.getEdges());
    }

    //==================================================
    //                    Metodi
    //==================================================

    /**
     * Metodo che si occupa di inizializzare il tutto partendo da un'origine
     * @param source Nodo sorgente
     * @see Node
     */
    public void execute(Node source){
        // Inizializziamo diverse collections
        settledNode = new HashSet<>();
        unsettledNode = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();

        // Inizializziamo la distanza dalla sorgente a 0 come descritto nell'algoritmo
        distance.put(source,0D);

        // Inseriamo la sorgente negli unsettled come da algoritmo
        unsettledNode.add(source);

        // come da algoritmo avviamo un ciclo iterativo che continuerà a ciclare fintanto che la size degli unsettled
        // è maggiore di 0
        while (unsettledNode.size() > 0){
            // Trova il nodo minimo tra tutti i nodi di tipo unsettled
            Node node = getMinimum(unsettledNode);

            // Aggiungiamo ai node settled il nodo minimo trovato
            settledNode.add(node);

            // Rimuoviamo dai node unsettled il nodo minimo trovato
            unsettledNode.remove(node);

            //Trova le distanze minime
            findMinimalDistanceNode(node);
        }
    }

    /**
     * Metodo per trovare il nodo con distanza minima
     * @param vertexes Set di nodi tra cui vogliamo trovare il minimo
     * @return Il nodo con distanza minima
     * @see Set
     * @see Node
     */
    private Node getMinimum(Set<Node> vertexes){
        // Inizializiamo un nodo minimo impostato con valore nullo
        Node minimum = null;

        // Per ogni nodo tra i nodi passati (unsettled)
        for (Node node : vertexes){
            // Se il minimo è ancora nullo inizializiamolo con l'istanza della prima occorrenza
            if (minimum == null){
                minimum = node;
            }else {
                // Altrimenti se il reperimento della distanza più corta del nodo è minore a quella del minimo
                if (getShortestDistance(node) < getShortestDistance(minimum)){
                    // Allora minimum = node
                    minimum = node;
                }
            }
        }

        return minimum;
    }

    /**
     * Metodo che ha lo scopo di ritornare la distanza dal nodo di destinazione
     * @param destination Nodo di destinazione
     * @return distanza dal nodo
     * @see Node
     */
    private double getShortestDistance(Node destination){
        // Inizializziamo d con la distanza presa dal valore della chiave presente nell'hashmap distance
        Double d = distance.get(destination);

        // Se D è nullo ritorna "infinito"
        if (d == null){
            return Double.MAX_VALUE;
        }else {
            // Altrimenti ritorna il valore inizializzato precedentemente
            return d;
        }
    }

    /**
     * Metodo che si occupa di trovare le distanze minime tra i vari nodi adiacenti
     * @param node Nodo di cui vogliamo trovare le varie distanze minime
     * @see Node
     */
    private void findMinimalDistanceNode(Node node){
        // inizializziamo una lista di nodi, il quale rappresenterà tutti i nodi adiacenti a quello passsato
        // come parametro di input
        List<Node> adjacentNodes = getNeighbors(node);

        // Per ogni nodo analizzato nella lista dei nodi vicini
        for (Node target : adjacentNodes){
            // Se la distanza reperita dal target è maggiore della distanza del nodo + la distanza tra nodo e target
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)){
                // Inseriamo nell'Hashmap distance il nodo e la relativa distanza
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                // Inserisci nella lista dei predecessori, utile a ricavare un path come risultato
                predecessors.put(target, node);
                // Inserisci nella lista degli unsettledNodes il target
                unsettledNode.add(target);
            }
        }
    }

    /**
     * Metodo che ha lo scopo di trovare tutti i nodi adiacenti
     * @param node Nodo di cui vogliamo trovare i nodi adiacenti
     * @return La lista dei nodi adiacenti
     * @see Node
     * @see List
     */
    private List<Node> getNeighbors(Node node){
        // Inizializziamo un array list vuoto che rappresenterà i nostri nodi adiacenti
        List<Node> neighbors = new ArrayList<>();

        // Per ogni edge nella lista degli edge
        for (Edge edge : edges){
            // Se la sorgente del nodo coincide con il nodo passato come parametro di input
            // e il nodo destinazione dell'edge preso in considerazione non fa parte dell'Arraylist settledNoded,
            // vuol dire che dobbiamo reperire informazioni inerenti a quel determinato edge
            if (edge.getSource().equals(node) && !isSettled(edge.getDestination())){
                // Allora aggiungiamo all'array dei nodi vicini il nodo destinazione dell'edge
                neighbors.add(edge.getDestination());
            }
        }

        return neighbors;
    }

    /**
     * Metodo che ha lo scopo di capire se un nodo appartiene ai settledNodes
     * @param node Nodo di cui vogliamo constatare la presenza nei settledNodes
     * @return Ritorna true se il nodo è presente nei settledNodes, altrimenti false
     * @see Node
     */
    private boolean isSettled(Node node){
        return settledNode.contains(node);
    }

    /**
     * Ritorna la distanza tra due nodi
     * @param node Nodo sorgente
     * @param target Nodo Destinazione
     * @return la distanza tra i due  nodi
     */
    private double getDistance(Node node, Node target){
        // Per ogni edge nella lista degli edge
        for (Edge edge : edges){
            // Se sorgente e destinazione coincidono
            if (edge.getSource().equals(node) && edge.getDestination().equals(target)){
                // Allora ritorna il peso dell'edge
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Non dovrebbe accadere");
    }

    /**
     * Metodo per ritornare il path dato un nodo target
     * @param target nodo di cui vogliamo reperire un path
     * @return Un path che parte dall'origine e va verso la destinazione
     * @see Node
     */
    public LinkedList<Node> getPath(Node target){
        // Inizializziamo una LinkedList che si occuperà di memorizzare il nostro path
        LinkedList<Node> path = new LinkedList<>();
        Node step = target;

        // Controlliamo se un path esiste
        if (predecessors.get(step) == null){
            return null;
        }

        // Aggiungiamo al path il nostro target
        path.add(target);

        // fintanto che lo step riesce a reperire un nodo precedente
        while (predecessors.get(step) != null){
            // Lo step è uguale al suo predecessore
            step = predecessors.get(step);
            // Aggiungiamo al path il nuovo step
            path.add(step);
        }

        // capovolgiamo l'array
        Collections.reverse(path);

        // ritorniamo il path
        return path;
    }
}