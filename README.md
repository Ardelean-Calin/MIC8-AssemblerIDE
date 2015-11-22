# MIC8-AssemblerIDE
This project aims to build a complete IDE to program and simulate a simple 8-bit processor in order to help people understand
how a microprocessor works from the ground up. 

These are the OP-codes implemented by the microprocessor:

| Command         | Op-code (hexa)  | Description |
| :-----:         | :-----:         | ------------
| `NOP`           | 00              |No operation
| `INPUT`         | 05              |Load whatever is at the input into A
| `OUTPUT`        | 07              |Write A to the output register
|`LOAD A,#<value>`| 0b              |Load the value specified after the op-code into A (Eg. `0b 1f` loads 1f into A
|`INC A`          | 0f              |Increment the value of A
|`MOV B,A`        | 11              |Copy A into B
|`SUB A,B`        | 13              |Subtract A and B. Store result in A
|`ADD A,B`        | 15              |Add A and B. Store result in A
|`JUMP #<addr>`   | 1d              |Jump to the instruction pointed by the address specified after the op-code
|`IFEQ`           | 17              |If A==B, execute the jump instruction (so you need an address after this)
|`IFZ`            | 19              |If A==0, execute the jump instruction (so you need an address after this)
|`IFL`            | 1b              |If A<=B, execute the jump instruction (so you need an address after this)
|`PUSH A`         | 22              |Push A into stack
|`POP A`          | 24              |Pop from stack into A
|`HALT`           | 1f              |Halt processor

Using these op-codes one can implement a variety of programs. If you want to simulate this CPU in [Logisim Evolution](https://github.com/reds-heig/logisim-evolution), download the whole Logisim folder and open **CPU.circ**. There are some test programs in there but you can write your own using the commands above. Just write the corresponding op-codes into the external memory present in *Test-CPU* and press Ctrl+K in order to run the clock.
