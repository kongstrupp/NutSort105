import java.util.ArrayList;

public class temp2 {


    public static void main(String[] args) {

        // Create an ArrayList of strings
        ArrayList<String> inputStrings = new ArrayList<>();
        inputStrings.add("['R']\n['R']\n['R']\n['R']\n");
        inputStrings.add("['R']\n['R']\n['R']\n['D']\n");

        System.out.println(inputStrings.get(0));

        String[] stringArray = inputStrings.toArray(new String[0]);


        // Call the function and print the result
        String result = mergeStrings(stringArray);
        System.out.println(result);
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
            for (int j = 0; j < splitLines.length; j++) {
                if (i < splitLines[j].length) {
                    combined.append(splitLines[j][i]).append(" ");
                }
            }
            combined.append("\n");
        }

        return combined.toString().trim();
    }

}
