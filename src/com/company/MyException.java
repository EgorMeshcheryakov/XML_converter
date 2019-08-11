package com.company;

import org.w3c.dom.Node;

public class MyException extends Exception {

    private Node node;
    public Node getNode() {
        return node;
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Node node) {
        super(message);
        this.node = node;
    }
}
