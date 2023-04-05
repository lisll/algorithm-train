package com.datapipeline.LinkedList;

/**
 * @author ly
 * @Date Create in 11:16 2021/4/6 0006
 * @Description
 */
public interface Queue<E> {
    int getSize();
    boolean isEmpty();
    void enqueue(E e);
    E dequeue();
    E getFront();
}
