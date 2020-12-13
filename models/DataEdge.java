package models;

public class DataEdge<T> implements Edge{
    private T data;

    DataEdge(T data){
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
