package school.sptech.zup.dto.obj;

public class PilhaObj<T> {
    private T[] pilha;
    private int topo;
    public PilhaObj(int capacidade) {
        pilha = (T[]) new Object[capacidade];
        topo = -1;
    }
    public PilhaObj() {
    }
    public Boolean isEmpty() {
        return topo == -1;
    }
    public Boolean isFull() {
        return topo >= pilha.length - 1;
    }
    public void push(T info) {
        if (isFull()) {
            throw new IllegalStateException("Pilha cheia!");
        }
       pilha[++topo] = info;
    }
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        return pilha[topo--];
    }
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return pilha[topo];
    }
    public void exibe() {
        if (isEmpty()) {
            System.out.println("Pilha vazia!");
        }
        else {
            System.out.println("\nElementos da pilha:");
            for (int i = topo; i >= 0; i--) {
                System.out.println(pilha[i]);
            }
        }
    }
    public int getTopo() {
        return topo;
    }
}