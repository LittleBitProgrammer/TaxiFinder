package com.robertovecchio.model.graph;

import com.robertovecchio.model.graph.edge.observer.Edge;
import com.robertovecchio.model.graph.node.Node;

import java.util.List;

public interface Graphable {
    List<Node> getVertexes();
    List<Edge> getEdges();
    int getOrder();
    int getLength();
    boolean contains(Node node);
    boolean contains(Edge edge);
    void printGraph();
}
