/**
 * Project: MIC8-AssemblerIDE
 * Created by calin on 11/24/15.
 */
public class Stack {
    int stackMemory[];
    int stackPointer;

    // size is the number of bytes
    public Stack(int size){
        stackMemory = new int[size];
        stackPointer = 0;
    }

    public Stack(){
        this(16);
    }

    public void push(Register8 A) throws StackOverFlowException{
        /**
         * Stores an item in the stack. Throws StackOverFlowException
         * if the stack is full
         */
        if(stackPointer == stackMemory.length)
            throw new StackOverFlowException("Stack overflow.");

        stackMemory[stackPointer] = A.getValue();
        stackPointer++;
    }

    public void pop(Register8 A) throws StackUnderFlowException{
        /**
         * Retrieve an item from the stack. Throws StackUnderFlowException
         * if the stack is
         */
        if(stackPointer == 0)
            throw new StackUnderFlowException("Stack underflow");

        stackPointer--;
        A.setValue(stackMemory[stackPointer]);
    }

    @Override
    public String toString(){
        StringBuilder myString = new StringBuilder();

        for (int i = 0; i < stackMemory.length; i++) {
            if(i % 16 == 0)
                myString.append(String.format("\n%02x: %02x", i, stackMemory[i]));
            else
                myString.append(String.format("\t%02x", stackMemory[i]));
        }

        return myString.toString();
    }
}
