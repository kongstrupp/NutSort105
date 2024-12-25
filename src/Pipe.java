import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.*;

public class Pipe implements Serializable {

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

    public String Put(Pipe target) {
        String retString = null;
        if (isLocked()) {
            return "Pipe is locked";
        } else {
            if (target.numberOfNuts >= target.numberOfSlots) {
                return "Stack is full";
            }

            if (pipeStack.isEmpty()){
                return "Cannot put from empty Pipe";
            }

            if (target.pipeStack.isEmpty() || pipeStack.peekFirst() == target.pipeStack.peekFirst()) {
                boolean partialFailure = false;
                while (!pipeStack.isEmpty()
                        && (target.pipeStack.isEmpty() || pipeStack.peekFirst() == target.pipeStack.peekFirst())) {
                    if (target.numberOfNuts >= target.numberOfSlots) {
                        partialFailure = true; // Mark this iteration as a failure but continue
                        break;
                    }
                    target.pipeStack.push(pipeStack.pop());
                    numberOfNuts--;
                    target.numberOfNuts++;
                    retString = "Success";
                }
                if (partialFailure) {
                    retString = "Success";
                }
                return retString;
            }
        }
        return "No Operation";
    }

    public String canAccept(Pipe target) {
        String retString = null;
        if (isLocked()) {
            return "Pipe is locked";
        } else {
            if (target.numberOfNuts >= target.numberOfSlots) {
                return "Stack is full";
            }

            if (pipeStack.isEmpty()){
                return "Cannot put from empty Pipe";
            }

            if (target.pipeStack.isEmpty() || pipeStack.peekFirst() == target.pipeStack.peekFirst()) {
                retString = "Success";
                return retString;
            }
        }
        return "No Operation";
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

