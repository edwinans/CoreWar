package core;

import java.util.ArrayList;

public class Interpreter {

	public Interpreter() {

	}

	// la valeur de retour et l'indice du prochaine cmd q'on doit executer:
	// par example si JMP , ce n'est plus index+1 !
	public static ArrayList<Integer> execute(int[] core, int index) throws CoreException {
		ArrayList<Integer> indexArray = new ArrayList<Integer>();
		index %= 4096;
		int aOperand, bOperand;
		int aAddress = 0, bAddress = 0;
		int nextIndex = (index + 1) % 4096;
		Instruction ins = new Instruction(core[index]);

		// set operand_a:
		if (ins.getAMode() == 0) {
			aAddress = (index + ins.getAField()) % 4096;
			aOperand = core[aAddress];
		} else if (ins.getAMode() == 2) {
			int i = (index + ins.getAField()) % 4096;
			aAddress = (i + core[i]) % 4096;
			aOperand = core[aAddress];
		} else
			aOperand = ins.getAField();

		// set operand_b:
		if (ins.getBMode() == 0) {
			bAddress = (index + ins.getBField()) % 4096;
			bOperand = core[bAddress];
		} else if (ins.getBMode() == 2) {
			int i = (index + ins.getBField()) % 4096;
			bAddress = (i + core[i]) % 4096;
			bOperand = core[bAddress];
		} else
			bOperand = ins.getBField();//

		switch (ins.getOpCode()) {
		case 0:// DAT
			throw new CoreException("Le processus est mort, vous avez perdu!");
		case 1:// MOV
			if (ins.getBMode() == 1)
				throw new CoreException("Le mode d'addressage est incorrect!");
			core[bAddress] = aOperand;
			indexArray.add(bAddress);
			break;
		case 2:// ADD
			if (ins.getBMode() == 1)
				throw new CoreException("Le mode d'addressage est incorrect!");
			int ans = aOperand + bOperand;
			core[bAddress] = ans;
			indexArray.add(bAddress);
			break;
		case 3:// SUB
			if (ins.getBMode() == 1)
				throw new CoreException("Le mode d'addressage est incorrect!");
			int ans1 = aOperand - bOperand;
			core[bAddress] = ans1;
			indexArray.add(bAddress);
			break;
		case 4:// JMP
			nextIndex = aAddress;
			break;
		case 5:// JMZ
			if (aOperand == 0)
				nextIndex = bAddress;
			break;
		case 6:// DJZ
			int newAns = aOperand - 1;
			core[aAddress] = newAns;
			if (newAns == 0)
				nextIndex = bAddress;
			break;
		case 7:// CMP
			nextIndex++;
			break;
		case 8:// MUL
			if (ins.getBMode() == 1)
				throw new CoreException("Le mode d'addressage est incorrect!");
			int ans2 = aOperand * bOperand;
			core[bAddress] = ans2;
			indexArray.add(bAddress);
			break;
		case 9:// DIV
			if (ins.getBMode() == 1)
				throw new CoreException("Le mode d'addressage est incorrect!");
			int ans3 = aOperand / bOperand;
			core[bAddress] = ans3;
			indexArray.add(bAddress);
			break;
		case 10:// MOD
			if (ins.getBMode() == 1)
				throw new CoreException("Le mode d'addressage est incorrect!");
			int ans4 = aOperand % bOperand;
			core[bAddress] = ans4;
			indexArray.add(bAddress);
			break;
		case 11:// NOP
			break;
		case 12:// JMN
			if (aOperand != 0)
				nextIndex = bAddress;
			break;
		case 13:// DJN
			int newAns1 = aOperand - 1;
			core[aAddress] = newAns1;
			if (newAns1 == 0)
				nextIndex = bAddress;
			break;
		case 14:// SEQ
			if (aOperand == bOperand)
				nextIndex++;
			break;
		case 15:// SNE
			if (aOperand != bOperand)
				nextIndex++;
			break;
		default:
			throw new CoreException("Le commande n'existe pas!");
		}

		indexArray.add(0, nextIndex);
		return indexArray;
	}

}