package com.robertovecchio.model.graph.node;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Questa classe ha la responsabilità di astrarre un nodo generico, quindi astratto, il quale non potrà essere
 * istanziato ma utile a definire attributi comuni alle classi che erediteranno da quest'ultima, andando a definire
 * di fatto un nodo di un grafo, ovvero una posizione discreta.
 * @author robertovecchio
 * @version 1.0
 * @since 07/01/2021
 * */
public abstract class Node implements Serializable {

    /**
     * Numero seriale utile ai fini della memorizzazione
     */
    @Serial
    private final static long serialVersionUID = 9L;

    //==================================================
    //               Variabili d'istanza
    //==================================================

    /**@see Coordinates*/
    private Coordinates coordinates; // Cordinate di un nodo

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Costruttore di Node
     * @param coordinates Coordinate, rappresentate da latitudine e longitudine
     * */
    public Node(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    //==================================================
    //                      Setter
    //==================================================

    /**
     * Setter delle coordinate
     * @param coordinates Coordinate espresse in latitudine e longitudine
     * @see Coordinates
     */
    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    //==================================================
    //                      Getter
    //==================================================

    /**
     * Getter delle coordinate
     * @return Le cordinate espresse in latitudine e longitudine
     * @see Coordinates
     * */
    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    //==================================================
    //                      Metodi
    //==================================================

    /**
     * Override del metodo equals atto a constatare l'uguaglianza di due oggetti di tipo Node
     * @return se i due oggetti sono uguali ritorna true, altrimenti false
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return coordinates.equals(node.coordinates);
    }

    /**
     * Override del metodo hascode
     * @return il valore intero rappresentato dall'oggetto
     * */
    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    /**
     * Override del metodo toString atto a generare una stringa dato un oggetto del tipo Node
     * @return Stringa dell'oggetto di tipo Node
     * */
    @Override
    public String toString() {
        return "Node{" +
                "coordinates=" + coordinates +
                '}';
    }

}