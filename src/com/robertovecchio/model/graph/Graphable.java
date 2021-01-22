package com.robertovecchio.model.graph;

import com.robertovecchio.model.graph.edge.observer.Edge;
import com.robertovecchio.model.graph.node.Node;
import java.util.List;

/**
 * Interfaccia che si distacca dall'implementazione di un grafo
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 * */
public interface Graphable {
    /**
     * Metodo per ritornare una lista di nodi da un grafo
     * @return Lista di nodi del grafo
     * @see List
     * @see Node
     */
    List<Node> getVertexes();
    /**
     * Metodo per ritornare una lista di collegamenti da un grafo
     * @return Lista di collegamenti del grafo
     * @see List
     * @see Edge
     */
    List<Edge> getEdges();
    /**
     * Metodo per reperire l'ordine del grafo
     * @return L'ordine del grafo
     */
    int getOrder();
    /**
     * Metodo per reperire la grandezza del grafo
     * @return Grandezza del grafo
     */
    int getLength();
    /**
     * Metodo che constata che un nodo sia presente nel grafo
     * @param node nìNodo di cui si vuole verificare la presenza
     * @return True se il grafo contiene il nodo, altrimenti false
     */
    boolean contains(Node node);
    /**
     * Metodo che constata che un collegamento sia presente nel grafo
     * @param edge Collegamento di cui si vuole verificare la presenza
     * @return True se il grafo contiene il nodo, altrimenti false
     */
    boolean contains(Edge edge);
    /**
     * Metodo utile a stampare il grafo
     */
    void printGraph();
}
