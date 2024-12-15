public class temp3 {
    public static void main(String[] args) {
        int SlotSetup = 4;
        Character[] setup1 = {'B', 'Y', 'I', 'G'};
        Character[] setup2 = {'b', 'g', 'R', };

        Pipe pipe1 = new Pipe(SlotSetup, setup1);
        Pipe pipe2 = new Pipe(SlotSetup, setup2);

        System.out.println(pipe1.pipeStack.peekFirst());
        System.out.println(pipe2.pipeStack.peekFirst());

        System.out.println(pipe2.pipeStack.peekFirst() == pipe1.pipeStack.peekFirst());


        pipe1.Put(pipe2);

    }
}
