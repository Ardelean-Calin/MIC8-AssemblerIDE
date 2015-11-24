/**
 * Project: MIC8-AssemblerIDE
 * Created by calin on 11/21/15.
 */
public class Operations {
    public static void move(Register8 B, Register8 A){
        /**
         * Copies the value from register A into register B.
         */
        B.setValue(A.getValue());
    }

    public static void output(Register8 A, Register8 out){
        /**
         * Copies the value of A register into the Ouput register.
         */
        out.setValue(A.getValue());
    }

    public static void increment(Register8 A){
        /**
         * Increments the value stored in register A.
         */
        A.setValue(A.getValue() + 1);
    }

    public static void add(Register8 A, Register8 B){
        /**
         * Stores in A the sum of register A and register B.
         */
        A.setValue(A.getValue() + B.getValue());
    }

    public static void subtract(Register8 A, Register8 B){
        /**
         * Stores in A the difference of register A and register B.
         */
        A.setValue(A.getValue() - B.getValue());
    }

    public static void load(Register8 A, int value){
        /**
         * Loads into register A the given value.
         */
        A.setValue(value);
    }

    public static void jump(Register8 PC, int address){
        /**
         * Stores the given address into the PC. This way, the next instruction will
         * be the instruction at the given address.
         */
        PC.setValue(address);
    }

    public static void input(Register8 input, Register8 A){
        /**
         * Retrieves into A the value of the input register.
         */
        A.setValue(input.getValue());
    }

    public static void jumpIfEqual(Memory programMemory, Register8 PC, Register8 A, Register8 B){
        /**
         * Extracts the address as being the next op-code/byte and jumps
         * to the address in Program Memory indicated by that byte only
         * if A == B;
         */
        int difference = A.getValue() - B.getValue();
        int nextAddress = programMemory.getByte(PC.getValue());

        if (difference == 0)
            jump(PC, nextAddress);
        else
            PC.increment();  // execute next instruction, after address
    }

    public static void jumpIfLess(Memory programMemory, Register8 PC, Register8 A, Register8 B){
        /**
         * Extracts the address as being the next op-code/byte and jumps
         * to the address in Program Memory indicated by that byte only
         * if A <= B;
         */
        int difference = A.getValue() - B.getValue();
        int nextAddress = programMemory.getByte(PC.getValue());

        if (difference <= 0)  // A <= B
            jump(PC, nextAddress);
        else
            PC.increment();  // execute next instruction, after address
    }

    public static void jumpIfZero(Memory programMemory, Register8 PC, Register8 A){
        /**
         * Extracts the address as being the next op-code/byte and jumps
         * to the address in Program Memory indicated by that byte only
         * if A == 0;
         */
        int nextAddress = programMemory.getByte(PC.getValue());

        if (A.getValue() == 0)
            jump(PC, nextAddress);
        else
            PC.increment();
    }

    public static void push(Register8 A, Stack stack) throws StackOverFlowException {
        /**
         * Stores A into the stack.
         */
        stack.push(A);
    }

    public static void pop(Register8 A, Stack stack) throws StackUnderFlowException {
        /**
         * Retrieves A from stack.
         */
        stack.pop(A);
    }
}
