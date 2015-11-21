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

    public CPU(){
        A = new Register8();
        B = new Register8();
        PC = new Register8();
        MBR = new Register8();
        OUT = new Register8();
    }

    public static void main(String[] args){
        CPU mic8 = new CPU();
    }
}
