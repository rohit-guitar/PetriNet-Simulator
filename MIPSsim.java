/*
 * “ On my honor, I have neither given nor received unauthorized aid on this assignment ”.
 *Authored By :- Rohit Garg
 *UFID:- 17622194
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
	public static int step=-1;
	public static StringBuilder simout = null;
	public static PrintWriter printWriter = null;
	
	
	public static void readFile() {
		File file = null;
		Scanner input = null;
		/* instruction file */
		try {
			file = new File(
					"instructions.txt");
			input = new Scanner(file);
			while (input.hasNextLine()) {
				String line = input.nextLine();
				INM.add(line);
			}
			input.close();
		} catch (Exception ex) {
			System.out.println("Instructions file is not fond");
			ex.printStackTrace();
		}
		/* Register file */
		try {
			file = new File(
					"registers.txt");
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
			System.out.println("Register file is not fond");
			ex.printStackTrace();
		}
		/* Data access memory */
		try {
			file = new File(
					"datamemory.txt");
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
			System.out.println("Data memory file is not fond");
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
			simout.append("\n");  
		}
		else{
			for(int i=0;i<(k.size()-1);i++){
				System.out.print(k.get(i)+",");
				simout.append(k.get(i)+","); 
			}
			System.out.println(k.get(k.size()-1));
			simout.append(k.get(k.size()-1));
			simout.append("\n");
		}
	}

	public static void simPrint(){
		simout = new StringBuilder();
		System.out.println("STEP "+step+":"); 
		simout.append("STEP "+step+":"+"\n"); 
		System.out.print("INM:");
		simout.append("INM:"); 
		printHelp(INM);
		System.out.print("INB:");
		simout.append("INB:");
		printHelp(INB);
		System.out.print("AIB:");
		simout.append("AIB:");
		printHelp(AIB);
		System.out.print("LIB:");
		simout.append("LIB:");
		printHelp(LIB);
		System.out.print("ADB:");
		simout.append("ADB:");
		printHelp(ADB);
		System.out.print("REB:");
		simout.append("REB:");
		printHelp(REB);
		System.out.print("RGF:");
		simout.append("RGF:");
		List<String> keys = new ArrayList<String>(RGF.keySet());
		Collections.sort(keys);
		int size1=keys.size();
		for(int i=0;i<size1-1;i++){
			register temp= RGF.get(keys.get(i));
			if(!(temp.getValue()==Integer.MIN_VALUE)){
				System.out.print("<"+keys.get(i)+","+temp.getValue()+">"+",");
				simout.append("<"+keys.get(i)+","+temp.getValue()+">"+",");
			}
		}
		register temp= RGF.get(keys.get(size1-1));
		System.out.println("<"+keys.get(size1-1)+","+temp.getValue()+">");
		simout.append("<"+keys.get(size1-1)+","+temp.getValue()+">"+"\n");
		
		System.out.print("DAM:");
		simout.append("DAM:");
		List<Integer> keys1 = new ArrayList<Integer>(DAM.keySet());
		Collections.sort(keys1);
		int size2=keys1.size();
		for(int i=0;i<size2-1;i++){
			Integer temp1= DAM.get(keys1.get(i));
			System.out.print("<"+keys1.get(i)+","+temp1.toString()+">"+",");
			simout.append("<"+keys1.get(i)+","+temp1.toString()+">"+",");
		}
		Integer temp1= DAM.get(keys1.get(size2-1));
		System.out.println("<"+keys1.get(size2-1)+","+temp1.toString()+">");
		simout.append("<"+keys1.get(size2-1)+","+temp1.toString()+">"+"\n");
		System.out.println();
		//simout.append("\n");
		
	}

	public static void main(String[] args) throws FileNotFoundException {
		MIPSsim.readFile();
		printWriter = new PrintWriter("simulation.txt");
		boolean done= INM.isEmpty() && INB.isEmpty() && AIB.isEmpty() && LIB.isEmpty() && ADB.isEmpty() && REB.isEmpty();
		while(!done)
		{	
			done= INM.isEmpty() && INB.isEmpty() && AIB.isEmpty() && LIB.isEmpty() && ADB.isEmpty() && REB.isEmpty();
			step++;
			MIPSsim.simPrint();
			printWriter.println(simout.toString());
			write();
			load();
			asu();
			addr();
			issue();
			decode();		
		}
		printWriter.close();
	}
	
	public static void decode(){
		if(INM.isEmpty()){return;}
		else{
			String input =  INM.remove(0);
			input=input.substring(1, input.length()-1);
			String[] ins = input.split(",");
			if(!RGF.containsKey(ins[1])){
				RGF.put(ins[1], new register(Integer.MIN_VALUE));
			}
				if(ins[0].equals("LD")){
					register src = RGF.get(ins[2]);
					register des= RGF.get(ins[1]);
						if(src.getAvail()){
							String next="<LD,"+ins[1]+","+src.getValue()+","+ins[3]+">";
							INB.add(next);
							des.triggerAvail();
						}
						else{return;}
				}
				else{
					register des = RGF.get(ins[1]);
					register src1 = RGF.get(ins[2]);
					register src2 = RGF.get(ins[3]);
						if(src1.getAvail() && src2.getAvail()){
							String next= "<"+ins[0]+","+ins[1]+","+src1.getValue()+","+src2.getValue()+">";
							INB.add(next);
							des.triggerAvail();
						}
						else{return;}
				}
			
		}
	}
	
	public static void issue(){
		if(INB.isEmpty()){return;}
		else{
			String input =  INB.remove(0);
			String input2=input.substring(1, input.length()-1);
			String[] ins = input2.split(",");
				if(ins[0].equals("LD")){
					LIB.add(input);
				}
				else{
					AIB.add(input);
				}
			
		}
		
	}
	
	public static void addr(){
		if(LIB.isEmpty()){return;}
		else{
			String input =  LIB.remove(0);
			String input2=input.substring(1, input.length()-1);
			String[] ins = input2.split(",");
			int add= Integer.parseInt(ins[2])+Integer.parseInt(ins[3]);
			String next = "<"+ins[1]+","+add+">";
			ADB.add(next);
		}
	}
	
	public static void asu(){
		if(AIB.isEmpty()){return;}
		else{
			String input =  AIB.remove(0);
			String input2=input.substring(1, input.length()-1);
			String[] ins = input2.split(",");
				if(ins[0].equals("ADD")){
					int add= Integer.parseInt(ins[2])+Integer.parseInt(ins[3]);
					String next = "<"+ins[1]+","+add+">";
					REB.add(next);
				}
				else{
					int add= Integer.parseInt(ins[2])-Integer.parseInt(ins[3]);
					String next = "<"+ins[1]+","+add+">";
					REB.add(next);
				}	
		}
	}
	
	public static void load(){
		if(ADB.isEmpty()){return;}
		else{
			String input =  ADB.remove(0);
			String input2=input.substring(1, input.length()-1);
			String[] ins = input2.split(",");
			int value = DAM.get(Integer.parseInt(ins[1]));
			String next = "<"+ins[0]+","+value+">";
			REB.add(next);
		}
	}
	public static void write(){
		if(REB.isEmpty()){return;}
		else{
			String input =  REB.remove(0);
			String input2=input.substring(1, input.length()-1);
			String[] ins = input2.split(",");
			int newValue= Integer.parseInt(ins[1]);
			register des=RGF.get(ins[0]);
			des.setValue(newValue);
			des.triggerAvail();
		}
	}

}
