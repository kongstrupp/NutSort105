import java.sql.SQLOutput;
import java.util.*;

public class Pipe {

    Character[] pipeSetup;
    FixedStack<Character> pipeStack;
    int numberOfSlots;
    int numberOfNuts;
    boolean lock;

    public Pipe(int slots, Character[] pipeSetup) {
        if (slots <= 0) {
            throw new IndexOutOfBoundsException("Number of Slots cannot be negative or zero");
        }
        if (pipeSetup == null) {
            this.numberOfSlots = slots;
            this.numberOfNuts = 0;
            pipeStack = new FixedStack<>(numberOfSlots);
        } else {
            this.numberOfSlots = slots;
            this.numberOfNuts = pipeSetup.length;
            if (!(pipeSetup.length > slots)) {
                pipeStack = new FixedStack<>(numberOfSlots);
                for (int i = 0; i < numberOfNuts; i++) {
                    pipeStack.push(pipeSetup[i]);
                }
            } else throw new IndexOutOfBoundsException("Setup cannot be larger then slots");
        }
    }

    public void Put(Pipe target) {
        if (isLocked()) {
            System.out.println("Pipe is locked");
        } else {
            if (target.numberOfNuts >= target.numberOfSlots) {
                System.out.println("Stack is full");
                return;
            }

            if (pipeStack.isEmpty()){
                System.out.println("Cannot put from empty Pipe");
                return;
            }

            if (target.pipeStack.isEmpty() || pipeStack.peekFirst() == target.pipeStack.peekFirst()) {
                while (!pipeStack.isEmpty()
                        && (target.pipeStack.isEmpty() || pipeStack.peekFirst() == target.pipeStack.peekFirst())) {
                    if (target.numberOfNuts >= target.numberOfSlots) {
                        System.out.println("Target stack reached maximum capacity");
                        break;
                    }
                    target.pipeStack.push(pipeStack.pop());
                    numberOfNuts--;
                    target.numberOfNuts++;
                }
            }
        }
    }

    public boolean isLocked() {
        if (numberOfNuts < numberOfSlots) {
            return false;
        }

        Object firstItem = pipeStack.peekFirst();
        boolean allSame = true;
        Deque<Object> tempStack = new ArrayDeque<>();

        while (!pipeStack.isEmpty()) {
            Object currentItem = pipeStack.pop();
            tempStack.push(currentItem);
            if (!currentItem.equals(firstItem)) {
                allSame = false;
            }
        }

        while (!tempStack.isEmpty()) {
            pipeStack.push((Character) tempStack.pop());
        }

        return allSame;
    }


    public int getLength() {
        System.out.println(numberOfSlots);
        return numberOfSlots;
    }

    public int getNumberOfNuts() {
        System.out.println(numberOfNuts);
        return numberOfNuts;
    }

    public String getPipeString() {
        return pipeStack.getStackString();
    }

}

