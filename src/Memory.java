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

    private void _initializeMemory(){
        for (int i = 0; i < size; i++)
            memory[i] = 0;

    }
}
