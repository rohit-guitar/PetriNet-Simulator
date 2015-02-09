/*
 * "I have neither given nor received any unauthorized aid on this assignment"
 */

import java.io.File;
import java.util.*;
import java.util.Map.Entry;


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



public class MIPSsim {
	
	public static ArrayList<String> INB = new ArrayList<String>();
	public static ArrayList<String> LIB = new ArrayList<String>();
	public static ArrayList<String> ADB = new ArrayList<String>();
	public static ArrayList<String> AIB = new ArrayList<String>();
	public static ArrayList<String> REB = new ArrayList<String>();
	public static Map<String, register> RGF = new HashMap<String, register>(8); 
	public static Map<Integer, Integer> DAM = new HashMap<Integer, Integer>(8); 
	public static ArrayList<String> INM = new ArrayList<String>();
	public static int step=0;
	
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
				INM.add(line);
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
		for(int i=0;i<INM.size();i++){
			System.out.println(INM.get(i));
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
	
	public static void printHelp(ArrayList<String> k){
		if(k.size()==0){
			System.out.println();
		}
		else{
			for(int i=0;i<(k.size()-1);i++){
				System.out.print(k.get(i)+",");
			}
			System.out.println(k.get(k.size()-1));
		}
	}

	public static void simPrint(){
		System.out.println("STEP "+step+":");
		System.out.print("INM:");
		printHelp(INM);
		System.out.print("INB:");
		printHelp(INB);
		System.out.print("AIB:");
		printHelp(AIB);
		System.out.print("LIB:");
		printHelp(LIB);
		System.out.print("ADB:");
		printHelp(ADB);
		System.out.print("REB:");
		printHelp(REB);
		System.out.print("RGF:");
		Iterator<Entry<String, register>> it = RGF.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, register> pairs = it.next();
			register k = (register) pairs.getValue();
			System.out.print("<"+pairs.getKey() + "," + k.getValue()+">"+",");
			it.remove(); 
		}
		System.out.println();
		System.out.print("DAM:");
		Iterator<Entry<Integer, Integer>> it2 = DAM.entrySet().iterator();
		while (it2.hasNext()) {
			Entry<Integer, Integer> pairs = it2.next();
			System.out.print("<"+pairs.getKey() + "," + pairs.getValue()+">"+",");
			it2.remove(); 
		}

		System.out.println();
		System.out.println();
		
	}

	public static void main(String[] args) {

		MIPSsim.readFile();
		MIPSsim.simPrint();
		

	}

}
