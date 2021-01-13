package com.robertovecchio.model.graph;

// import
import com.robertovecchio.model.graph.node.Node;
import java.util.*;

/**
 * Classe che astrae una struttura a grafo, permettendone la creazione e la manipolazione, gestendo nodi ed archi,
 * Possiamo utilizzare come nodo, qualsiasi classe erediti da Node, per maggiori dettagli vedere
 * @see Node
 * @author robertovecchio
 * @version 1.0
 * @since 7/01/2021
 * */
public class Graph {

    //==================================================
    //                   Singleton
    //==================================================

    private static Graph instance = createGraph();

    //==================================================
    //               Variabili d'istanza
    //==================================================

    /**@see Map
     * @see Node
     * @see List*/
    private Map<Node, List<Node>> nodes; // lista nodi del grafo e relativi collegamenti

    //==================================================
    //                   Costruttori
    //==================================================
    /**
     * Costruttore del grafo
     * @param nodes nodi sotto forma di Hashmap o affini per effettuare collegamenti a quest'ultimi
     * @see Map
     * @see Node
     * @see List*/
    private Graph(Map<Node,List<Node>> nodes){
        this.nodes = nodes;
    }

    /**
     * Costruttore del grafo che inizializza il grafo con un nuovo HashMp vuoto
     */
    public Graph(){
        this(new HashMap<>());
    }

    //==================================================
    //                      Setter
    //==================================================

    /**
     * Setter dei nodi del grafo con relativi collegamenti
     * @param nodes nodi sotto forma di Hashmap o affini per effettuare collegamenti a quest'ultimi
     * @see Map
     * @see Node
     * @see List
     * */
    public void setNodes(Map<Node, List<Node>> nodes){
        this.nodes = nodes;
    }

    //==================================================
    //                      Getter
    //==================================================

    /**
     * Getter dei nodi del grafo con relativi collegamenti
     * @return nodi sotto forma di Hashmap o affini per effettuare collegamenti a quest'ultimi
     * @see Map
     * @see Node
     * @see List*/
    public Map<Node, List<Node>> getNodes(){
        return this.nodes;
    }

    //==================================================
    //                    Metodi
    //==================================================

    public static Graph getInstance(){
        return instance;
    }

    /**
     * Metodo atto ad aggiungere un nodo
     * @param node Nodo da aggiungere al grafo
     * @see Node
     * */
    public void addNode(Node node){
        // aggiungi se assente un nodo con valore una nuova ArrayList per gli archi del nodo
        nodes.putIfAbsent(node, new ArrayList<>());
    }

    /**
     * Metodo atto a rimuovere un nodo
     * @param node Nodo da rimuovere dal grafo
     * @see Node
     * */
    public void removeNode(Node node){
        // dai nodi itera solo i valori (quindi esclude le chiavi) e per ognuno rimuovi dalla lista
        // il collegamento al nodo
        nodes.values().forEach(list -> list.remove(node));
    }

    /**
     * Metodo atto ad aggiungere un arco tra due nodi
     * @param nodeOne Primo nodo per il collegamento tra due nodi
     * @param nodeTwo Secondo nodo per il collegamento tra due nodi
     * @see Node
     * */
    public void addEdge(Node nodeOne, Node nodeTwo){
        //aggiunge un collegamento tra nodo uno e due
        nodes.get(nodeOne).add(nodeTwo);
        nodes.get(nodeTwo).add(nodeOne);
    }

    /**
     * Metodo atto a rimuovere un arco tra due nodi
     * @param nodeOne Primo nodo per rimuovere il collegamento tra due nodi
     * @param nodeTwo Secondo nodo per rimuovere il collegamento tra due nodi
     * @see Node
     * */
    public void removeEdge(Node nodeOne, Node nodeTwo){
        nodes.get(nodeOne).remove(nodeTwo);
        nodes.get(nodeTwo).remove(nodeOne);
    }

    /**
     * Metodo atto a reperire i nodi adiacenti ad un nodo dato un grafo
     * @param node nodo di cui si vuole conoscere i nodi adiacenti (collegati)
     * @return Lista di nodi adiacenti
     * @see List
     * @see Node*/
    public List<Node> getAdjacentNodes(Node node){
        return this.nodes.get(node);
    }

    //==================================================
    //                Metodi statici
    //==================================================

    /**
     * Metodo atto a generare un grafo di default
     * @return Un grafo inizializzato con nodi e relativi collegamenti
     * */
    public static Graph createGraph(){
        Graph graph = new Graph();
        //TODO:// creare qui un grafo statico da un insieme di strade e punti di interesse
        return graph;
    }

    //==================================================
    //                Metodi Sovrascritti
    //==================================================

    /**
     * Override del metodo toString atto a generare una stringa dato un oggetto del tipo Graph
     * @return Stringa dell'oggetto di tipo Taxi
     * */
    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }

    /**
     * Override del metodo equals atto a constatare l'uguaglianza di due oggetti di tipo Graph
     * @return se i due oggetti sono uguali ritorna true, altrimenti false
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Graph)) return false;
        Graph graph = (Graph) o;
        return Objects.equals(nodes, graph.nodes);
    }

    /**
     * Override del metodo hascode
     * @return il valore intero rappresentato dall'oggetto
     * */
    @Override
    public int hashCode() {
        return Objects.hash(nodes);
    }
}