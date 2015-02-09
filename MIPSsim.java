/*
 * "I have neither given nor received any unauthorized aid on this assignment"
 */

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

class Queue {
	private LinkedList<instruction> list = new LinkedList<instruction>();
	private int size = 8; // Restrict the instruction tokens

	public void put(instruction v) {
		if (size > 0) {
			list.addFirst(v);
			--size;
		} else {
			System.out.println("Only 8 instruction tokens are allowed");
		}
	}

	public instruction get() {
		++size;
		return list.removeLast();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

}

class register {
	int value;
	boolean avail;

	public register(int b) {
		this.value = b;
		this.avail = true;
	}

	public void triggerAvail() {
		this.avail = !this.avail;
	}

	public boolean getAvail() {
		return this.avail;
	}

	public void setValue(int val) {
		this.value = val;
	}

	public int getValue() {
		return this.value;
	}

}

class instruction {
	String opcode = null;
	String desReg = null;
	String sourceReg1 = null;
	String sourceReg2 = null;
	Integer imm = Integer.MIN_VALUE;

	public instruction(String s) {
		String[] parts = s.split(",");
		if (parts[0].equals("LD")) {
			opcode = parts[0];
			desReg = parts[1];
			sourceReg1 = parts[2];
			imm = Integer.parseInt(parts[3]);
		} else {
			opcode = parts[0];
			desReg = parts[1];
			sourceReg1 = parts[2];
			sourceReg2 = parts[3];
		}
	}

	void print() {
		if (this.opcode.equals("LD")) {
			System.out.println(opcode + " " + desReg + " " + sourceReg1 + " "+ imm);
		} else {
			System.out.println(opcode + " " + desReg + " " + sourceReg1 + " "+ sourceReg2);
		}
	}

}

public class MIPSsim {

	public static Map<String, register> RGF = new HashMap<String, register>(8); 
	public static Map<Integer, Integer> DAM = new HashMap<Integer, Integer>(8); 
	public static Queue INM = new Queue();

	public static void readFile() {
		File file = null;
		Scanner input = null;
		/* instruction file */
		try {
			file = new File(
					"/cise/homes/rrohit/workspace/petriNet/src/instructions.txt");
			input = new Scanner(file);
			while (input.hasNextLine()) {
				String line = input.nextLine();
				line = line.substring(1, line.length() - 1);
				instruction temp = new instruction(line);
				INM.put(temp);
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/* Register file */
		try {
			file = new File(
					"/cise/homes/rrohit/workspace/petriNet/src/registers.txt");
			input = new Scanner(file);
			while (input.hasNextLine()) {
				String line = input.nextLine();
				line = line.substring(1, line.length() - 1);
				String[] parts = line.split(",");
				int x = Integer.parseInt(parts[1]);
				register temp = new register(x);
				RGF.put(parts[0], temp);
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/* Data access memory */
		try {
			file = new File(
					"/cise/homes/rrohit/workspace/petriNet/src/datamemory.txt");
			input = new Scanner(file);
			while (input.hasNextLine()) {
				String line = input.nextLine();
				line = line.substring(1, line.length() - 1);
				String[] parts = line.split(",");
				int x = Integer.parseInt(parts[0]);
				int y = Integer.parseInt(parts[1]);
				DAM.put(x, y);
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void testInput() {
		// Instruction file
		while (!INM.isEmpty()) {
			INM.get().print();
		}
		// Registers
		Iterator<Entry<String, register>> it = RGF.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, register> pairs = it.next();
			register k = (register) pairs.getValue();
			System.out.println(pairs.getKey() + " = " + k.getValue());
			it.remove(); 
		}
		// Data access memory
		Iterator<Entry<Integer, Integer>> it2 = DAM.entrySet().iterator();
		while (it2.hasNext()) {
			Entry<Integer, Integer> pairs = it2.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
			it2.remove(); 
		}


	}

	public static void main(String[] args) {

		MIPSsim.readFile();
		MIPSsim.testInput();

	}

}
