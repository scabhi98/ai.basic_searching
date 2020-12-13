package models;

public interface Node<T> extends GraphElement{
    public T getValue();
    public void setValue(T value);
}
