import java.io.File;
import java.util.Scanner;


public class stringSplit {
	 public static void main(String[] args) {

	        try {
	            File file = new File("/cise/homes/rrohit/workspace/petriNet/src/instructions.txt");
	            Scanner input = new Scanner(file);
	            while (input.hasNextLine()) {
	                String line = input.nextLine();
	                line= line.substring(1, line.length()-1);
	                String[] parts= line.split(",");
	                System.out.println(parts[0]+"   "+parts[3]);
	            }
	            input.close();

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
}
