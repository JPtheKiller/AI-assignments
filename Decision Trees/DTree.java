import java.io.File;
import java.util.*;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileNotFoundException;
import java.lang.Math;

class Atributo{

    String Tag;
    LinkedList<String> Values;

    Atributo(String A){
            Tag = A;
            Values = new LinkedList<String>();
    }

    public void addvalue(String V){
            Values.add(V);
    }
}

class Example{

    Atributo Atribute;
    LinkedList<String> info;
    double entropy;


    Example(LinkedList<Atributo> Atributos,String Tag){
            info = new LinkedList<String>();

            Atributo ToAdd = null;
            for(Atributo A : Atributos)
                    if(A.Tag.equals(Tag)){
                            ToAdd = A;
                            break;
                    }

            if(ToAdd == null)
                    System.out.println("Atribute " + Tag + " not found.");

            Atribute = ToAdd;
            entropy = 1000; //subject to change might be bigger no clue, MAXINTEGER for whoever wants to do that
    }

    Example(Atributo A){
            info = new LinkedList<String>();
            Atribute = A;
            entropy = 1000;
    }

    public void add(String Valor){

            if(!(Atribute.Values.contains(Valor)))
                    Atribute.Values.add(Valor);

            info.add(Valor);
    }
}

class Tleaf{
	String Class;
	int Counter;
	
	Tleaf(String C,int num){
		Class = C;
		Counter = num;
	}
	
}

class Tree{
	
	Atributo Atribute;
	Hashtable<String, Tree> Branches; //Branches.put("one", T);
	Tleaf IsLeaf;
	
	Tree(Atributo A){
		Atribute = A;
		Branches = new Hashtable<String, Tree>();
		IsLeaf = null;
	}
	
	Tree(String C,int Counter,Atributo Target_Atribute){
		Atribute = Target_Atribute;
		IsLeaf = new Tleaf(C,Counter);
		Branches = null;
	}
	
	public void AddBranch(String Tag,Tree SubTree) {	
		Branches.put(Tag, SubTree);
	}
	
	public void Print() {
		DoPrint(this,0);
	}
	
	private void DoPrint(Tree T,int depth) {		
		
		if(T.IsLeaf != null) {
			System.out.println(T.IsLeaf.Class + " (" + T.IsLeaf.Counter + ")");
		}
		else {
			
			if(depth != 0) {
				System.out.println();
				
				for(int i = 1; i<depth;i++)
					System.out.print("\t");
				
				System.out.println("  <" + T.Atribute.Tag + ">");
			}
			else 
				System.out.println("<" + T.Atribute.Tag + ">");
			for(String Value : T.Atribute.Values) {
				
				for(int i = 1; i<=depth;i++)
					System.out.print("\t");
				
				System.out.print(Value + ": ");
				
				DoPrint(T.Branches.get(Value),depth+1);
				
			}
			
		}
		
	}
}

class DTree{
	/*
    static LinkedList<Atributo> Atributes = new LinkedList<Atributo>();
    static LinkedList<Example> Examples = new LinkedList<Example>();
    static Atributo ClassAtribute;
    static LinkedList<Example> test = new LinkedList<Example>();
    */
	
    static LinkedList<Atributo> Atributes = null;
    static LinkedList<Example> Examples = null;
    static Atributo ClassAtribute = null;
    static LinkedList<Example> test = null;
    static Tree CurrentTree = null;
    
    
    //Returns Atribute with Tag T
    public static Atributo FindAtribute(LinkedList<Atributo> LA,String T) {
    	
    	Atributo out = null;
    	
    	for(Atributo A: LA)
    		if(A.Tag.equals(T)) {
    			out = A;
    			break;
    		}
    	
    	
    	return out;

    }
    
    //Returns Example of a certain attribute from example list
    public static Example AtributeExample(LinkedList<Example> LE,Atributo A) {
    	
    	Example out = null;
    	
    	for(Example E: LE)
    		if(E.Atribute == A) {
    			out = E;
    			break;
    		}
    	
    	
    	return out;
    }
    
    //Clones Attribute list without an element
    public static LinkedList<Atributo> CloneRmvAtributes(LinkedList<Atributo> LA,Atributo A){
    	
    	LinkedList<Atributo> out = new LinkedList<Atributo>();
    	
    	for(Atributo I :LA)
    		if(I != A)
    			out.add(I);
    	
    	return out;
    }

    //Clones Example list
    public static LinkedList<Example> CloneExamples(LinkedList<Example> LE){
    	
    	LinkedList<Example> out = new LinkedList<Example>();
    	
    	for(Example E : LE) {
    		
    		Example temp = new Example(E.Atribute);
    		
    		for(String Value : E.info)
    			temp.info.add(Value);
    		
    		out.add(temp);
    	}
    	
    	
    	return out;	
    }
    
    //Examples subset that have value vi for attribute A
    public static LinkedList<Example> ExampleSubset(LinkedList<Example> LE,Atributo A, String vi){
    	
		LinkedList<Example> out = CloneExamples(LE);
    	
    	for(Example E : out) 
    		if(E.Atribute == A) {
    			
    			for(String Value : A.Values)
    				if(!(Value.equals(vi))) {
    					
    					int i = E.info.indexOf(Value);
    					
    					while(i!= -1){
    						
    						for(Example Ermv : out)
    							Ermv.info.remove(i);
    						
    						i = E.info.indexOf(Value);
    					}
    					
    				}

    			break;
    		}
    	
    	return out;
    }
    
    //Clones and Removes Atributo from Example list
    public static LinkedList<Example> RmvAtributeLE(LinkedList<Example> LE, Atributo A){
    	LinkedList<Example> out = CloneExamples(LE);
    	
    	for(Example E : out) 
    		if(E.Atribute == A) {
    			out.remove(E);
    			break;
    		}
    	
    	return out;
    }
    
    public static void printAtributo(Atributo A){
            System.out.println("Atributo: " + A.Tag);
            System.out.println("Possible Values:");
            for(String inf : A.Values)
                    System.out.println("\t" + inf);
    }

    public static void printExample(Example E){
            printAtributo(E.Atribute);
            System.out.println("Values:");
            for(String inf : E.info)
                    System.out.println("\t" + inf);
    }

    public static String getFilePath(){
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String files = dialog.getFile();
        return files;
    }

    public static void LineParser(String[] parts,int mode){
			//mode=1 - modo atributo
			//mode=2 - modo exemplos
			//mode=3 - modo atributos test
			//mode=4 - modo exemplos test
			int i=0;
			if(mode==1){
				for(String bv : parts){
					if(i!=0){
						Atributo a=new Atributo(bv.replace("\n", "").replace("\r", ""));
						Atributes.addLast(a);
						Examples.addLast(new Example(a));
					}
					i=1;
				}
			}else if(mode==2){
				i=1;
				for(Example a : Examples){
	                         float lol;
	                         String finale;
	                         finale="";
	                         try{
	                            lol=Float.parseFloat(parts[i]);
	                            finale+=(int) Math.floor(lol);
	                            if(lol>Math.floor(lol))
	                                finale+=".X";
	                            a.add(finale);
	                        }catch(NumberFormatException e){
	                            a.add(parts[i].replace("\n", "").replace("\r", ""));
	                        }
	                        i++;
				}
			}else if(mode==3){
			    //System.out.println(parts[0]);
	            for(String bv : parts){
						Atributo a=FindAtribute(Atributes,bv);
						test.addLast(new Example(a));
				}
			}else if(mode==4){
	            i=0;
				for(Example a : test){
	                         float lol;
	                         String finale;
	                         finale="";
	                         try{
	                            lol=Float.parseFloat(parts[i]);
	                            finale+=(int) Math.floor(lol);
	                            if(lol>Math.floor(lol))
	                                finale+=".X";
	                            a.info.add(finale);
	                        }catch(NumberFormatException e){
	                            a.info.add(parts[i].replace("\n", "").replace("\r", ""));
	                        }
	                        i++;
				}
			}
	}

    public static void readFile(String filePath) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(filePath));
        sc.useDelimiter("\n");
        if(sc.hasNext()){
            String[] parts= sc.next().split(",");
            LineParser(parts,1);
        }
        while(sc.hasNext()){
            String[] parts= sc.next().split(",");
            LineParser(parts,2);
        }

        ClassAtribute = Examples.peekLast().Atribute;

        sc.close();
    }

    public static void readTestFile(String filePath) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(filePath));
        sc.useDelimiter("\n");
        if(sc.hasNext()){
            String[] parts= sc.next().split(",");
            LineParser(parts,3);
        }
        while(sc.hasNext()){
            String[] parts= sc.next().split(",");
            LineParser(parts,4);
        }

        ClassAtribute = Examples.peekLast().Atribute;

        sc.close();
    }
    
    public static double prob(Example Ex,String PValue,String Endval, LinkedList<Example> LE){
        String c;		
        double npositivos = 0;
        double n = 0;
        int index = 0;

        for(String curr : Ex.info){
            if(curr.equals(PValue)){

                c = AtributeExample(LE,ClassAtribute).info.get(index);
                if(c.equals(Endval))
                        npositivos++;
                n++;

            }
            index++;
        }         
        if(npositivos == 0)
        	return 0;
        return npositivos/n;				
	}

    public static double logab(double a, double b){
        double log;
        double loga = Math.log(a);
        double logb = Math.log(b);
        
        log = loga/logb;
        
        return log;
    }
    
    public static double log2(double a){
    	if(a == 0)
    		return -9999999;
        return logab(a,2);   
    }
    
    public static double normalProb(String P, Example Ex){
        double den = 0, num = 0;        
        for(String curr : Ex.info){            
            if(curr.equals(P))
                num++;
            den++;           
        }
        return num/den;
    }
    
    public static double calcI(Example Ex, LinkedList<Example> LE){
        double I =0;
        for(String curr : ClassAtribute.Values){
            double j = normalProb(curr, AtributeExample(LE,ClassAtribute));
           // System.out.println("Curr: "+curr+" j is " + j);
            I -= ( j * log2(j)); 
        }
        return I;      
    }
    
    public static void entropy(Example Ex, LinkedList<Example> LE){
        
        double ent = calcI(Ex,LE); 
        //System.out.println("I : " + ent);
        double PP;
        double j;
        for(String PValue : Ex.Atribute.Values){
            PP = normalProb(PValue,Ex);
            //System.out.println("PP de " + PValue + " is " + PP);
            for(String endval : ClassAtribute.Values){
                j = prob(Ex,PValue,endval,LE);
                //System.out.println("j de " + endval + " is " +j);
                PP*= -( j * log2(j) );
            }
            ent-=PP;
        }
        Ex.entropy = ent;
    }
    
    public static void CalcEntropy(LinkedList<Example> LE) {
    	for(Example E : LE) {
    			entropy(E,LE);
    	}
    }
    
    public static Atributo BestAtribute(LinkedList<Example> LE) {
		
    	Atributo out = null;
    	
    	CalcEntropy(LE);
    	
    	double ent = -2;
    	
    	for(Example E : LE) {
    		
    		if((E.Atribute != ClassAtribute) && (E.entropy > ent)) {
    			out = E.Atribute;
    			ent = E.entropy;
    		}
    	}
    	return out;
    }
  
    public static String MostCommonValue(Example E,Atributo A) {
    	
    	Hashtable<String, Integer> CountValues = new Hashtable<String,Integer>();
    	
    	for(String Value : A.Values)
    		CountValues.put(Value, 0);
    	
    	for(String Value : E.info)
    		CountValues.put(Value, CountValues.get(Value)+1);
    	
    	int max = -1;
    	String out = null;
    	
    	for(String Value : A.Values)
    		if(CountValues.get(Value) > max) {
    			max = CountValues.get(Value);
    			out = Value;
    		}
    	
    	return out;
    }
    
    public static Tree ID3(LinkedList<Example> LE, Atributo Target_Atribute,LinkedList<Atributo> LA) {
    	
    	Example ClassExample = AtributeExample(LE,Target_Atribute);
    
    	if(LA.size() == 1)
    		return new Tree(MostCommonValue(ClassExample,Target_Atribute),LE.peek().info.size(),Target_Atribute);
    	else {
    	
	    	String PrevValue = ClassExample.info.peek();
	    	Boolean EqualValues = true;
	    	
	    	for(String Value : ClassExample.info) {
	    		if(!(PrevValue.equals(Value)))
	    			EqualValues = false;
	    		
	    		PrevValue = Value;
	    	}
	    	
	    	if(EqualValues)
	    		return new Tree(PrevValue,LE.peek().info.size(),Target_Atribute);
	    	else {
	    		
	    		Atributo BestA = BestAtribute(LE);
	    		
	    		Tree out = new Tree(BestA);
	    		
	    		for(String vi : BestA.Values) {  			
	    			
	    			LinkedList<Example> LEVi = ExampleSubset(LE,BestA,vi);
	    			
	    			if(LEVi.peek().info.size() == 0)
	    				out.AddBranch(vi, new Tree(MostCommonValue(ClassExample,Target_Atribute),0,Target_Atribute));
	    			else
	    				out.AddBranch(vi, ID3(RmvAtributeLE(LEVi,BestA),Target_Atribute,CloneRmvAtributes(LA,BestA)));
	    			
	    		}
	    		
	    		return out;
	    		
	    	}
    	}
    }
    
    public static Example Answer(LinkedList<Example> in,Tree TT) {
    	
    	Example out = new Example(ClassAtribute);
    	for(int i= 0;i<in.peek().info.size();i++) {
    		
    		Tree T = TT;
    		
	    	while(T.IsLeaf == null) {
	    		
	    		String vi = AtributeExample(in,T.Atribute).info.get(i);
	    		
	    		System.out.println(T.Atribute.Tag + " " +vi);
	    		
	    	    if(T.Atribute.Values.contains(vi))
	    	    	T = T.Branches.get(vi);
	    	    else {
	    	    	System.out.println(vi + " is not an known value of "+ T.Atribute.Tag);
	    	    	break;
	    	    }
	    	}
	    	
	    	if(T.IsLeaf != null)
	    		out.info.add(T.IsLeaf.Class);
	    	else
	    		out.info.add(MostCommonValue( AtributeExample(Examples,ClassAtribute),ClassAtribute));
	    	
	    	
	    	
    	}
    	return out;
    }
    
    public static boolean openfile(int mode,Scanner sc) {
    	
    	//mode = 1, normal
    	//mode = 2, test
    	
    	if(mode == 1) {
    		
        	boolean couldopen = false;
        	boolean exit = false;
        	
        	try {
        		
        		readFile(getFilePath());
        		couldopen = true;
        		
        	} 
        	catch (FileNotFoundException e) {
        		System.out.println("File not found error. (Is it in the same folder?)");
        		System.out.println("Do you want to try again?(y/n)");
        		
        		String in = sc.nextLine();
        		while(!(in.equals("y") || in.equals("n")))
        			in = sc.nextLine();
        		
        		if(in.equals("n"))
        			exit = true;
        		
    		}
        	catch(java.lang.NullPointerException e) {
        		System.out.println("File wasn't opened error.");
        		System.out.println("Do you want to try again?(y/n)");
        		
        		String in = sc.nextLine();
        		while(!(in.equals("y") || in.equals("n")))
        			in = sc.nextLine();
        		
        		if(in.equals("n"))
        			exit = true;
        	}
        	
        	if(!couldopen && !exit)
        		return openfile(mode,sc);
        	else
        		return couldopen;
    		
    	}
    	else if(mode == 2) {
    		
    		boolean couldopen = false;
        	boolean exit = false;
        	
        	try {
        		
        		readTestFile(getFilePath());
        		couldopen = true;
        		
        	} 
        	catch (FileNotFoundException e) {
        		System.out.println("File not found error. (Is it in the same folder?)");
        		System.out.println("Do you want to try again?(y/n)");
        		
        		String in = sc.nextLine();
        		while(!(in.equals("y") || in.equals("n")))
        			in = sc.nextLine();
        		
        		if(in.equals("n"))
        			exit = true;
        		
    		}
        	catch(java.lang.NullPointerException e) {
        		System.out.println("File wasn't opened error.");
        		System.out.println("Do you want to try again?(y/n)");
        		
        		String in = sc.nextLine();
        		while(!(in.equals("y") || in.equals("n")))
        			in = sc.nextLine();
        		
        		if(in.equals("n"))
        			exit = true;
        	}
        	
        	if(!couldopen && !exit)
        		return openfile(mode,sc);
        	else
        		return couldopen;
    		
    	}
    	else
    		return false;
    	
    }
    
    public static void menu(Scanner sc) {
    	
    	
    	System.out.println();
	    System.out.println("Escolha a opcao desejada:");
	    System.out.println("1- Criar Decision Tree.");
	    System.out.println("2- Imprimir Decision Tree atual.");
	    System.out.println("3- Ler e imprimir valores decididos de um ficheiro de teste");
	    System.out.println("4- Sair do programa.");
	    
	    
	    int in = sc.nextInt();
		switch (in) {
		 	case 1: 
		 	    	LinkedList<Atributo> BAtributes = Atributes;
		 	    	LinkedList<Example> BExamples = Examples;
		 	    	
		 			Atributes = new LinkedList<Atributo>();
		 			Examples = new LinkedList<Example>();
		 			
		 			if(openfile(1,sc)) {
		 				CurrentTree = ID3(Examples,ClassAtribute,Atributes);
		 				System.out.println("Tree created sucessfully.");
		 			}else {
		 				Atributes = BAtributes;
		 				Examples = BExamples;
		 			}
		 			break;
		 	case 2:
		 			if(CurrentTree != null)
		 				CurrentTree.Print();
		 			else
		 				System.out.println("Tree hasnt been created.");
		 			break;
		 	case 3:
		 			test = new LinkedList<Example>();
		 			
		 			if((openfile(2,sc)) && (CurrentTree != null))
		 				printExample(Answer(test,CurrentTree));
		 			else
		 				System.out.println("Either there was an error opening test file or Tree hasnt been created.");
		 		
		 			break;
		}
		
		if(in != 4)
			menu(sc);
    	
    }
    
    public static void main(String[] args) throws FileNotFoundException{
    	
    	menu(new Scanner(System.in));
    	System.out.println("Exiting");
    	System.exit(0);
    	
    	
    }
}
