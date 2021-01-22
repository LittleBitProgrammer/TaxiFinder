package com.robertovecchio.model.graph;

import com.robertovecchio.model.graph.edge.observer.Edge;
import com.robertovecchio.model.graph.node.Node;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Questa classe ha il semplice scopo di rappresentare un grafo (nel nostro caso pesato e direzionale). Un grafo
 * è composto da nodi e linee direzionali che definiscono una connessione tra un nodo e l'altro. La descrizione
 * matematica per un grafo è G={V,E}. L'ordine di un grafo è il numero di nodi, mentre la sua grandezza rappresenta
 * il numero di linee.
 *
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2020
 */
public class WeightedGraph implements Serializable, Graphable, Cloneable {

    //==================================================
    //               Attributi statici
    //==================================================

    /**Tipo numerico Long utile alla serializzazione*/
    @Serial
    private final static long serialVersionUID = 6L;

    //==================================================
    //               Variabili d'istanza
    //==================================================

    /**
     * Un Grafo è un aggregato di nodi
     * @see List
     * @see Node*/
    private final List<Node> vertexes;
    /**
     * Un Grafo è un aggregato di edge
     * @see List
     * @see Edge*/
    private final List<Edge> edges;

    //==================================================
    //                 Costruttori
    //==================================================

    /**
     * Costruttore di un Grafo
     * @param vertexes Lista dei nodi presenti un grafo
     * @param edges Lista dei collegamenti in un grafo
     * @see List
     * @see Node
     * @see Edge
     */
    public WeightedGraph(List<Node> vertexes, List<Edge> edges){
        this.vertexes = vertexes;
        this.edges = edges;
    }

    //==================================================
    //                   Getter
    //==================================================

    @Override
    public List<Node> getVertexes() {
        return vertexes;
    }

    @Override
    public List<Edge> getEdges() {
        return edges;
    }


    //==================================================
    //                  Metodi
    //==================================================

    @Override
    public int getOrder(){
        return this.vertexes.size();
    }

    @Override
    public int getLength(){
        return this.edges.size();
    }

    @Override
    public boolean contains(Node node){
        return this.vertexes.contains(node);
    }

    @Override
    public boolean contains(Edge edge){
        return this.edges.contains(edge);
    }

    @Override
    public void printGraph(){
        System.out.println("Grafo: \n" + "Vertici:\n" + this.vertexes + "\nCollegamenti:\n" + this.edges);
    }

    @Override
    public WeightedGraph clone() throws CloneNotSupportedException {
        return (WeightedGraph)super.clone();
    }
}
