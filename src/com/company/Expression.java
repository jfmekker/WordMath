package com.company;

import static com.company.Number.integerToFormattedString;
import static com.company.Number.parseInteger;

public class Expression {

    static long parseEquation(String str, boolean print) throws Exception {
        String[] ops;

        if (str.contains(" plus ")){
            ops = str.split(" plus ");
            if (ops.length != 2) throw new Exception("Invalid Equation Format");
            long op1 = parseInteger(ops[0]);
            long op2 = parseInteger(ops[1]);
            long out = op1 + op2;
            if (print) System.out.println("" + integerToFormattedString(op1)
                    + " + " + integerToFormattedString(op2) + " = " + integerToFormattedString(out));
            return out;
        }
        else if (str.contains(" minus ")){
            ops = str.split(" minus ");
            if (ops.length != 2) throw new Exception("Invalid Equation Format");
            long op1 = parseInteger(ops[0]);
            long op2 = parseInteger(ops[1]);
            long out = op1 - op2;
            if (print) System.out.println("" + integerToFormattedString(op1)
                    + " - " + integerToFormattedString(op2) + " = " + integerToFormattedString(out));
            return out;
        }
        else if (str.contains(" times ")){
            ops = str.split(" times ");
            if (ops.length != 2) throw new Exception("Invalid Equation Format");
            long op1 = parseInteger(ops[0]);
            long op2 = parseInteger(ops[1]);
            long out = op1 * op2;
            if (print) System.out.println("" + integerToFormattedString(op1)
                    + " * " + integerToFormattedString(op2) + " = " + integerToFormattedString(out));
            return out;
        }
        else if (str.contains(" divided by ")){
            ops = str.split(" divided by ");
            if (ops.length != 2) throw new Exception("Invalid Equation Format");
            long op1 = parseInteger(ops[0]);
            long op2 = parseInteger(ops[1]);
            long out = op1 / op2;
            if (print) System.out.println("" + integerToFormattedString(op1)
                    + " / " + integerToFormattedString(op2) + " = " + integerToFormattedString(out));
            return out;

        }
        else if (str.contains(" modulus ")){
            ops = str.split(" modulus ");
            if (ops.length != 2) throw new Exception("Invalid Equation Format");
            long op1 = parseInteger(ops[0]);
            long op2 = parseInteger(ops[1]);
            long out = op1 % op2;
            if (print) System.out.println("" + integerToFormattedString(op1)
                    + " % " + integerToFormattedString(op2) + " = " + integerToFormattedString(out));
            return out;
        }
        else {
            long op1 = parseInteger(str);
            if (print) System.out.println("= " + integerToFormattedString(op1));
            return  op1;
        }
    }

}
