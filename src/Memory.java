import java.util.Arrays;

/**
 * Project: MIC8-AssemblerIDE
 * Created by calin on 11/21/15.
 */
public class Memory {
    int[] memory;
    private int size;

    public Memory(int addressSize){
        /**
         * Generates a 2^addressSize values memory.
         */
        size = (int)Math.pow(2, addressSize);
        memory = new int[size];
        _initializeMemory();
    }

    public Memory(){
        /**
         * Generates a 2^8 values memory.
         */
        this(8); // 256 Bytes
    }

    public void setByte(int address, int value){
        memory[address] = value;
    }

    public int getByte(int address){
        return memory[address];
    }

    public int getSize(){
        /**
         * Returns the size of the memory i.e. how many values it can store.
         */
        return size;
    }

    public void fetch(Register8 MBR, Register8 PC){
        /**
         * Fetches into the MBR the OP-code at address PC
         */
        MBR.setValue(memory[PC.getValue()]);
    }

    public void writeMemory(int[] data){
        /**
         * Writes a program or data to memory. Max 256 bytes
         */
        for (int i = 0; i < data.length; i++)
            memory[i] = data[i];
    }

    @Override
    public String toString() {
        return "Memory{" +
                "memory=" + Arrays.toString(memory) +
                '}';
    }

    private void _initializeMemory(){
        for (int i = 0; i < size; i++)
            memory[i] = 0;

    }
}
