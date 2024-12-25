import java.awt.print.PrinterIOException;
import java.io.Serializable;
import java.sql.SQLOutput;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class NutSort implements Serializable {
    public static String Success = "Success";
    public static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) throws InterruptedException {
        runGame();
        System.out.println(stringBuilder);
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

        return PipesArray;
    }

    public static ArrayList<Pipe> LoadGameSimple() {
        int SlotSetup = 4;

        Character[] setup1 = {'R', 'B', 'R', 'B'};
        Character[] setup2 = {'B', 'R', 'R', 'B'};

        Pipe pipe1 = new Pipe(SlotSetup, setup1);
        Pipe pipe2 = new Pipe(SlotSetup, setup2);
        Pipe pipe3 = new Pipe(SlotSetup, null);
        Pipe pipe4 = new Pipe(SlotSetup, null);


        ArrayList<Pipe> PipesArray = new ArrayList<>();
        PipesArray.add(pipe1);
        PipesArray.add(pipe2);
        PipesArray.add(pipe3);
        PipesArray.add(pipe4);


        return PipesArray;
    }


    public static ArrayList<Pipe> LoadClone(ArrayList<Pipe> pipes) {
        return DeepCloneUtil.deepClone(pipes);
    }

    public static boolean runGame() throws InterruptedException {
        int counter = 0;
        boolean run = true;
        while (run) {
            ArrayList<Pipe> PipesArray = LoadGame();
            System.out.println("Moves: " + counter);
            stringBuilder = new StringBuilder();
            counter = 0;
            System.out.println("Load Game");
            Random random = new Random();
            int num1 = 0;
            int num2 = 0;

            do {
                ArrayList<int[]> moves = getValidMoves(PipesArray);

                if (moves.size() == 0) {
                    System.out.println("No moves");
                    break;
                }

                if (isRepetitiveMove(PipesArray)) {
                    System.out.println("Repetitive moves");
                    break;
                }

                num1 = random.nextInt(moves.size());
                int[] arr = moves.get(num1);
                PipesArray.get(arr[0]).Put(PipesArray.get(arr[1]));
                printOrAppend(PipesArray,true);
                stringBuilder.append(arr[0]).append(" ").append(arr[1]);
                counter++;

                if (winGame(PipesArray)) {
                    System.out.println("Win game");
                    return true;
                }

            } while (true);
        }
        return true;
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
                    String check = alterPipes.get(i).canAccept(alterPipes.get(j));
                    if (check.equals(Success)) {
                        from = i;
                        to = j;
                        int[] arr = {from, to};
                        ret.add(arr);
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

    public static String[] encodeState(ArrayList<Pipe> screws) {
        ArrayList<String> inputStrings = new ArrayList<>();
        for (Pipe pipeItem : screws) {
            inputStrings.add(pipeItem.getPipeString());
        }
        return inputStrings.toArray(new String[0]);
    }


    private static boolean isRepetitiveMove(ArrayList<Pipe> pipes) {
        ArrayList<Pipe> clonePipes = LoadClone(pipes);
        ArrayList<int[]> moves = getValidMoves(clonePipes);

        for (int[] move : moves) {
            int from = move[0];
            int to = move[1];
            ArrayList<Pipe> tempPipes = LoadClone(clonePipes);
            tempPipes.get(from).Put(tempPipes.get(to));
            tempPipes.get(to).Put(tempPipes.get(from));

            if (!(Arrays.equals(encodeState(tempPipes), encodeState(clonePipes)))) {
                return false;
            }
        }

        return true;
    }

}
