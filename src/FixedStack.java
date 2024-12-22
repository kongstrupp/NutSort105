import java.io.Serializable;

public class FixedStack<T> implements Serializable {
    private final T[] stack;
    private final int size;
    private int top;

    @SuppressWarnings("unchecked")
    public FixedStack(int size) {
        this.stack = (T[]) new Object[size];
        this.top = -1;
        this.size = size;
    }

    public void push(T obj) {
        if (top >= size - 1)
            throw new IndexOutOfBoundsException("Stack size = " + size);
        stack[++top] = obj;
    }

    public T pop() {
        if (top < 0) throw new IndexOutOfBoundsException();
        T obj = stack[top--];
        stack[top + 1] = null;
        return obj;
    }

    public T peekFirst() {
        if (top < 0) throw new IndexOutOfBoundsException("Stack is empty");
        return stack[top];
    }


    public int size() {
        return size;
    }

    public int elements() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public String getStackString() {
        StringBuilder output = new StringBuilder();
        for (int i = size - 1; i >= 0; i--) {
            if (i > top) {
                output.append("[' ']");
            } else {
                output.append("['").append(stack[i]).append("']");
            }
            output.append("\n");
        }
        return output.toString();
    }
}
