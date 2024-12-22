import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

public class NutSort implements Serializable {
    public static int ITR_INNER = 10000;
    public static int ITR_OUTER = 100000;
    public static String LockedPipe = "Pipe is locked";
    public static String StackFull = "Stack is full";
    public static String EmptyPipe = "Cannot put from empty Pipe";
    public static String ReachedCap = "Target stack reached maximum capacity";
    public static String Success = "Success";
    public static ArrayList<Pipe> STATE;
    public static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) throws InterruptedException {
        runGame();
    }


    public static ArrayList<Pipe> LoadGame() {
        int SlotSetup = 4;

        Character[] setup1 = {'B', 'Y', 'I', 'G'};
        Character[] setup2 = {'b', 'g', 'R', 'P'};
        Character[] setup3 = {'p', 'p', 'D', 'b'};
        Character[] setup4 = {'b', 'p', 'P', 'A'};

        Character[] setup5 = {'D', 'B', 'D', 'R'};
        Character[] setup6 = {'b', 'R', 'Y', 'D'};
        Character[] setup7 = {'P', 'A', 'O', 'I'};
        Character[] setup8 = {'g', 'O', 'I', 'g'};

        Character[] setup9 = {'A', 'P', 'R', 'B'};
        Character[] setup10 = {'G', 'O', 'p', 'A'};
        Character[] setup11 = {'G', 'Y', 'O', 'G'};
        Character[] setup12 = {'g', 'Y', 'I', 'B'};

        Pipe pipe1 = new Pipe(SlotSetup, setup1);
        Pipe pipe2 = new Pipe(SlotSetup, setup2);
        Pipe pipe3 = new Pipe(SlotSetup, setup3);
        Pipe pipe4 = new Pipe(SlotSetup, setup4);

        Pipe pipe5 = new Pipe(SlotSetup, setup5);
        Pipe pipe6 = new Pipe(SlotSetup, setup6);
        Pipe pipe7 = new Pipe(SlotSetup, setup7);
        Pipe pipe8 = new Pipe(SlotSetup, setup8);

        Pipe pipe9 = new Pipe(SlotSetup, setup9);
        Pipe pipe10 = new Pipe(SlotSetup, setup10);
        Pipe pipe11 = new Pipe(SlotSetup, setup11);
        Pipe pipe12 = new Pipe(SlotSetup, setup12);

        Pipe pipe13 = new Pipe(SlotSetup, null);
        Pipe pipe14 = new Pipe(SlotSetup, null);
        Pipe pipe15 = new Pipe(SlotSetup, null);
        Pipe pipe16 = new Pipe(SlotSetup, null);


        ArrayList<Pipe> PipesArray = new ArrayList<>();
        PipesArray.add(pipe1);
        PipesArray.add(pipe2);
        PipesArray.add(pipe3);
        PipesArray.add(pipe4);
        PipesArray.add(pipe5);
        PipesArray.add(pipe6);
        PipesArray.add(pipe7);
        PipesArray.add(pipe8);
        PipesArray.add(pipe9);
        PipesArray.add(pipe10);
        PipesArray.add(pipe11);
        PipesArray.add(pipe12);
        PipesArray.add(pipe13);
        PipesArray.add(pipe14);
        //PipesArray.add(pipe15);
        //PipesArray.add(pipe16);
        return PipesArray;
    }

    public static boolean runGame() throws InterruptedException {
        ArrayList<Pipe> PipesArray = LoadGame();

        PipesArray.get(0).Put(PipesArray.get(13));
        ArrayList<Pipe> PipesArray2 = DeepCloneUtil.deepClone(PipesArray);

        ArrayList<int[]> validMoves = getValidMoves(PipesArray2);
        System.out.println("Valid moves:");
        for (int[] arr : validMoves) {
            System.out.println("from " + arr[0] + " to " + arr[1]);
        }

        printOrAppend(PipesArray2, false);

        printOrAppend(PipesArray, false);


        if (winGame(PipesArray)) {
            System.out.println("GAME IS WON!");
            return true;
        }

        return false;

        //ArrayList<int[]> validMoves = getValidMoves(cp);
        //System.out.println("Valid moves:");
        //for (int[] arr : validMoves) {
        //    System.out.println("from " + arr[0] + " to " + arr[1]);
        //}
    }


    public static String mergeStrings(String[] inputStrings) {
        String[][] splitLines = new String[inputStrings.length][];
        int maxLines = 0;

        for (int i = 0; i < inputStrings.length; i++) {
            splitLines[i] = inputStrings[i].split("\n");
            maxLines = Math.max(maxLines, splitLines[i].length);
        }

        StringBuilder combined = new StringBuilder();

        for (int i = 0; i < maxLines; i++) {
            for (String[] splitLine : splitLines) {
                if (i < splitLine.length) {
                    combined.append(splitLine[i]).append(" ");
                }
            }
            combined.append("\n");
        }

        return combined.toString().trim();
    }

    public static boolean winGame(ArrayList<Pipe> pipeList) {
        for (Pipe pipe : pipeList) {
            if (!pipe.pipeStack.isEmpty()) {
                if (!pipe.isLocked()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printOrAppend(ArrayList<Pipe> pipes, boolean append) {
        ArrayList<String> inputStrings = new ArrayList<>();
        for (Pipe pipeItem : pipes) {
            inputStrings.add(pipeItem.getPipeString());
        }
        String[] stringArray = inputStrings.toArray(new String[0]);

        if (append) {
            stringBuilder.append(mergeStrings(stringArray));
            stringBuilder.append("\n");
            stringBuilder.append("{'0'} {'1'} {'2'} {'3'} {'4'} {'5'} {'6'} {'7'} {'8'} {'9'} {'10'} {'11'} {'12'} {'13'}");
            stringBuilder.append("\n");
            stringBuilder.append("------------------------------------------------------------------------------------");
            stringBuilder.append("\n");
        } else {
            System.out.println(mergeStrings(stringArray));
            System.out.println("{'0'} {'1'} {'2'} {'3'} {'4'} {'5'} {'6'} {'7'} {'8'} {'9'} {'10'} {'11'} {'12'} {'13'}");
            System.out.println("------------------------------------------------------------------------------------");
        }
    }

    public static ArrayList<int[]> getValidMoves(ArrayList<Pipe> pipes) {

        ArrayList<Pipe> alterPipes = pipes;
        ArrayList<int[]> ret = new ArrayList<>();
        int from;
        int to;

        for (int i = 0; i < alterPipes.size(); i++) {
            for (int j = 0; j < alterPipes.size(); j++) {
                if (i != j) {
                    String check = alterPipes.get(i).Put(alterPipes.get(j));
                    if (check.equals(Success)) {
                        from = i;
                        to = j;
                        int[] arr = {from, to};
                        ret.add(arr);
                        alterPipes = pipes;
                    }
                }
            }
        }
        return ret;
    }

    public static void printLocks(ArrayList<Pipe> pipeList) {
        for (Pipe pipe : pipeList) {
            System.out.println(pipe.isLocked());
        }
    }
    
}
