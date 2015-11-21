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

            case 0x17: {  // IFEQ
                int difference = A.getValue() - B.getValue();
                int nextAddress = programMemory.getByte(PC.getValue());

                if (difference == 0)
                    Operations.jump(PC, nextAddress);
                else
                    PC.increment();  // execute next instruction, after address

                clock += 4;
                break;
            }

            case 0x1B: {  // IFL
                int difference = A.getValue() - B.getValue();
                int nextAddress = programMemory.getByte(PC.getValue());

                if (difference < 0)  // A < B
                    Operations.jump(PC, nextAddress);
                else
                    PC.increment();  // execute next instruction, after address

                clock += 4;
                break;
            }

            case 0x19: {  // IFZ
                int nextAddress = programMemory.getByte(PC.getValue());

                if (A.getValue() == 0)
                    Operations.jump(PC, nextAddress);
                else
                    PC.increment();

                clock+=4;
                break;
            }

            case 0x1F:  // halt
                running = false;
                // Sau PC.decrement() ca sa ramana in acelasi loc + MBR = halt
                break;

            default:
                break;

        }
    }

    public static void main(String[] args){
        CPU mic8 = new CPU();

        int[] program = {0x0B, 0xFE, 0x11, 0x0B, 0x00, 0x17, 0x0A, 0x0F, 0x1D, 0x05, 0x1F};
        for (int i = 0; i < program.length; i++)
            mic8.programMemory.setByte(i, program[i]);
        //System.out.println(mic8.programMemory);
        while (mic8.isRunning())
            mic8.runOnce();

        System.out.println("A: " + mic8.A);

    }
}
