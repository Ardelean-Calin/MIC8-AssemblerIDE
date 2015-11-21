/**
 * Project: MIC8-AssemblerIDE
 * Created by calin on 11/21/15.
 */
public class Register8 {
    int storedValue;

    public Register8(){
        clear();
    }

    public Register8(int value){
        setValue(value);
    }

    public void setValue(int value){
        storedValue = value;
    }

    public int getValue(){
        return storedValue;
    }

    public void clear(){
        setValue(0);
    }
}
