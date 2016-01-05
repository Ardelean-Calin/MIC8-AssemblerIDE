import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Vector;

/**
 * Created by calin on 1/5/16.
 */
public class Compiler {
    public Compiler(){

    }

    // returns an int array containing the memory
    public int[] compile(String code){
        int[] memory = new int[256];

        String[] words = code.split("\\b+");
        StringBuilder codeBits = new StringBuilder();

        for (int i = 0; i<words.length; i++){
            if(i%2==0) {
                codeBits.append(words[i] + " ");
            }
        }

        boolean vFlag = false;
        int index = 0;
        for (String s:
             codeBits.toString().split(" ")) {

            // For loop

            String temp = s.toLowerCase();
            // Remove h after hexa
            if(temp.endsWith("h"))
                temp = temp.replace("h", "");

            if(vFlag == true){
                memory[index] = Integer.parseInt(temp, 16);
                vFlag = false;
                index++;
                continue;
            }

            if(temp.equals("nop")){
                memory[index] = 0;
            }
            else if(temp.equals("input")){
                memory[index] = 5;
            }
            else if(temp.equals("output")){
                memory[index] = 7;
            }
            else if(temp.equals("load")){
                memory[index] = 11;
                vFlag = true;
            }
            else if(temp.equals("inc")){
                memory[index] = 15;
            }
            else if(temp.equals("mov")){
                memory[index] = 17;
            }
            else if(temp.equals("sub")){
                memory[index] = 19;
            }
            else if(temp.equals("add")){
                memory[index] = 21;
            }
            else if(temp.equals("jump")){
                memory[index] = 29;
                vFlag = true;
            }
            else if(temp.equals("ifeq")){
                memory[index] = 23;
                vFlag = true;
            }
            else if(temp.equals("ifz")){
                memory[index] = 25;
                vFlag = true;
            }
            else if(temp.equals("ifl")){
                memory[index] = 27;
                vFlag = true;
            }
            else if(temp.equals("push")){
                memory[index] = 34;
            }
            else if(temp.equals("pop")){
                memory[index] = 36;
            }
            else if(temp.equals("halt")){
                memory[index] = 31;
            }

            index++;

        }
        return memory;
    }

    public void toFile(File fileName, int[] memory){
        try {
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);

            for (int i=0; i<memory.length; i++){
                if(i%8==0 && i!=0)
                    out.write("\n");
                out.write(Integer.toHexString(memory[i])+" ");
            }

            out.close();
            fstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
