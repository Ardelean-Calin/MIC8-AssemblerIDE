/**
 * Project: MIC8-AssemblerIDE
 * Created by calin on 11/21/15.
 */
public class CPU {
    Register8 A;
    Register8 B;
    Register8 PC;
    Register8 MBR;
    Register8 OUT;
    Register8 IN;
    Stack STACK;
    Memory programMemory;

    int clock;  // Current clock cycle
    boolean running = true;

    public CPU(){
        A = new Register8();
        B = new Register8();
        PC = new Register8();
        MBR = new Register8();
        OUT = new Register8();
        IN = new Register8();
        STACK = new Stack();

        // Create an 256 bytes memory
        programMemory = new Memory();

        clock = 0;
    }

    public void runOnce(){
        /**
         * Runs the CPU main loop exactly once.
         */
        programMemory.fetch(MBR, PC);   // fetch OP-code
        PC.increment(); // get next instruction ready to be fetched
        _executeOpCode();
        clock += 4;
    }

    public boolean isRunning(){
        /**
         * Returns true if no halt instruction was yet detected.
         * False if the CPU has halted.
         */
        return running;
    }

    public void stop(){
        /**
         * Stops the CPU by setting the running flag for which
         * you need to check in the main program.
         */
        running = false;
    }

    private void _executeOpCode(){
        int opCode = MBR.getValue();

        switch (opCode){
            case 0x00:  // main => 4 clk
                runOnce();
                break;

            case 0x04:  // NOP
                clock++;
                runOnce();
                break;

            case 0x05:  // IN
                Operations.input(IN, A);
                clock += 2;
                break;

            case 0x07:  // OUT
                Operations.output(A, OUT);
                clock += 2;
                break;

            case 0x1D:  // JMP addr
                // Format: OP-code, address. Address is stored after the OPCODE
                // but PC was already implemented by the main loop.
                int address = programMemory.getByte(PC.getValue());
                Operations.jump(PC, address);  // now PC is the value of the address
                clock += 2;
                break;

            case 0x0B:  // LOAD A, value
                int value = programMemory.getByte(PC.getValue());
                Operations.load(A, value);
                PC.increment();
                clock += 4;
                break;

            case 0x0F:  // INC A
                Operations.increment(A);
                clock += 2;
                break;

            case 0x11:  // MOV B, A
                Operations.move(B, A);
                clock += 2;
                break;

            case 0x13:  // SUB A, B
                Operations.subtract(A, B);
                clock += 2;
                break;

            case 0x15:  // ADD A, B
                Operations.add(A, B);
                clock += 2;
                break;

            case 0x17:  // IFEQ
                Operations.jumpIfEqual(programMemory, PC, A, B);

                clock += 4;
                break;

            case 0x1B: {  // IFL
                Operations.jumpIfLess(programMemory, PC, A, B);

                clock += 4;
                break;
            }

            case 0x19:   // IFZ
                Operations.jumpIfZero(programMemory, PC, A);

                clock+=4;
                break;

            case 0x22:  // PUSH
                try {
                    Operations.push(A, STACK);
                } catch (StackOverFlowException e) {
                    running = false;
                    System.out.println(e.getMessage());
                }

                clock+=2;
                break;

            case 0x24:  // POP
                try {
                    Operations.pop(A, STACK);
                } catch (StackUnderFlowException e) {
                    running = false;
                    System.out.println(e.getMessage());
                }
                clock+=3;
                break;

            case 0x1F:  // halt
                running = false;
                // Sau PC.decrement() ca sa ramana in acelasi loc + MBR = halt
                break;

            default:
                break;

        }
    }

    @Override
    public String toString(){
        StringBuilder myString = new StringBuilder();

        // Registers
        myString.append(String.format("Registers:\n\tA: %02x\tB: %02x\tOUT: %02x\tPC: %02x\tMBR: %02x\n",
                A.getValue(), B.getValue(), OUT.getValue(), PC.getValue(), MBR.getValue()));

        // Program Memory
        myString.append("Memory:\n");
        myString.append(programMemory);

        // Stack
        myString.append("\nStack:");
        myString.append(STACK.toString());

        return myString.toString();
    }

    public static void main(String[] args){
        CPU mic8 = new CPU();

        int[] program = {0x0B, 0xFE, 0x11, 0x0B, 0x00, 0x17, 0x0A, 0x0F, 0x1D, 0x05, 0x1F};
        for (int i = 0; i < program.length; i++)
            mic8.programMemory.setByte(i, program[i]);

        //System.out.println(mic8.programMemory);
        while (mic8.isRunning())
            mic8.runOnce();

        System.out.println(mic8);

    }
}
