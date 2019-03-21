package core;

public class Interpreter {
	

	public Interpreter() {
		
	}
	
	
	//la valeur de retour et l'indice du prochaine cmd q'on doit executer:
	//par example si JMP , ce n'est plus index+1 !
	public static int execute(int[] core,int index) throws CoreException {
		index%=4096;
		int aOperand , bOperand;
		int aAddress=0 , bAddress = 0;
		int nextIndex=index+1;
		Instruction ins=new Instruction(core[index]);
		
		//set operand_a:
		if(ins.getAMode()==0) {
			aAddress=(index+ins.getAField())%4096;
			aOperand= core[aAddress]; 
		}
		else if(ins.getAMode()==2) {
			int i= (index+ins.getAField())%4096;
			aAddress=(i+core[i])%4096;
			aOperand=core[aAddress];
		}
		else
			aOperand=ins.getAField();//
		
		//set operand_b:
		if(ins.getBMode()==0) {
			bAddress=(index+ins.getBField())%4096;
			bOperand= core[bAddress]; 
		}
		else if(ins.getBMode()==2) {
			int i= (index+ins.getBField())%4096;
			bAddress=(i+core[i])%4096;
			bOperand=core[bAddress];
		}
		else
			bOperand=ins.getBField();//
		
		switch(ins.getOpCode()) {
			case 0:
				throw new CoreException("le processus et mort , bous avais perdu!");
			case 1:
				if(ins.getBField()==1)
					throw new CoreException("le mode d'addressage est incorrect!");
				core[bAddress]=aOperand;
				break;
			case 2:
				if(ins.getBField()==1)
					throw new CoreException("le mode d'addressage est incorrect!");
				int ans= aOperand+bOperand;
				core[bAddress]=ans;
				break;
			case 3:
				if(ins.getBField()==1)
					throw new CoreException("le mode d'addressage est incorrect!");
				int ans1= aOperand-bOperand;
				core[bAddress]=ans1;
				break;
			case 4:
				nextIndex=aOperand;
				break;
			case 5:
				if(aOperand==0)
					nextIndex=bOperand;
				break;
			case 6:
				int newAns=aOperand-1;
				core[aAddress]=newAns;
				if(newAns==0)
					nextIndex=bOperand;
				break;
			case 7:
				if(aOperand==bOperand)
					nextIndex++;
				break;
			case 8:
				if(ins.getBField()==1)
					throw new CoreException("le mode d'addressage est incorrect!");
				int ans2= aOperand*bOperand;
				core[bAddress]=ans2;
				break;
			case 9:
				if(ins.getBField()==1)
					throw new CoreException("le mode d'addressage est incorrect!");
				int ans3= aOperand/bOperand;
				core[bAddress]=ans3;
				break;
			case 10:
				if(ins.getBField()==1)
					throw new CoreException("le mode d'addressage est incorrect!");
				int ans4= aOperand%bOperand;
				core[bAddress]=ans4;
				break;
			case 11:
				break;
			case 12:
				if(aOperand!=0)
					nextIndex=bOperand;
				break;
			case 13:
				int newAns1=aOperand-1;
				core[aAddress]=newAns1;
				if(newAns1==0)
					nextIndex=bOperand;
				break;
			case 14:
				if(aOperand==bOperand)
					nextIndex++;
				break;
			case 15:
				if(aOperand!=bOperand)
					nextIndex++;
				break;
		}
		
		return nextIndex;
	}
	
	
}
