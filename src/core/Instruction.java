package core;

public class Instruction {
	private int opCode;
	private int aMode;
	private int aField;
	private int bMode;
	private int bField;

	public Instruction(int opCode, int aMode, int aField, int bMode, int bField) {
		this.opCode = opCode;
		this.aMode = aMode;
		this.aField = aField;
		this.bMode = bMode;
		this.bField = bField;
	}

	public Instruction(String code) throws CoreException {
		String[] tokens = code.split(" ");
		if (tokens.length < 2 || tokens.length > 3)
			throw new CoreException("Le nombre de paramètres est incorrect");

		opCode = redCodeToInt(tokens[0]);

		String first = tokens[1];
		aMode = hasMode(first);
		first = (aMode == 0) ? first : first.substring(1);
		aField = (Integer.valueOf(first) + 4096) % 4096;

		if (tokens.length == 3) {
			String second = tokens[2];
			bMode = hasMode(second);
			second = (bMode == 0) ? second : second.substring(1);
			bField = (Integer.valueOf(second) + 4096) % 4096;
		}
	}

	public Instruction(int code) {

		bField = code % ((int) Math.pow(2, 12));

		if (bField >= 2048)// if negative number
			bField -= 4096;

		code >>= 12;
		bMode = code % 4;

		code >>= 2;
		aField = code % ((int) Math.pow(2, 12));

		if (aField >= 2048) // if negative number
			aField -= 4096;

		code >>= 12;
		aMode = code % 4;

		code >>= 2;
		opCode = code;
	}

	public int getOpCode() {
		return opCode;
	}

	public int getAMode() {
		return aMode;
	}

	public int getAField() {
		return aField;
	}

	public int getBMode() {
		return bMode;
	}

	public int getBField() {
		return bField;
	}

	public String toString() {
		String str = "";
		str += intToRedCode(this.opCode) + " ";

		if (aMode == 1)
			str += "#";
		else if (aMode == 2)
			str += "@";
		str += aField + " ";

		if (bMode == 1)
			str += "#";
		else if (bMode == 2)
			str += "@";
		str += bField;

		return str;
	}

	public static void main(String[] args) throws CoreException {
		// method main juste pour tester les conversion entre (instruction <--> int )
		Instruction i1 = new Instruction("CMP #12 @321");
		System.out.println(i1.toInt());
		System.out.println(i1);
		Instruction i1Copy = new Instruction(i1.toInt());
		System.out.println(i1Copy.toInt());
		System.out.println(i1Copy);

	}

	/*
	 * format int gauche a droite: 4 premier bit: opCode 2 prochain : mode Afield 12
	 * prochain: Afield 2 porchain: mode Bfield 12 prochain" Bfield
	 */

	public int toInt() {
		return ((opCode << 28) + (aMode << 26) + (aField << 14) + (bMode << 12) + (bField));
	}

	public int hasMode(String str) {
		if (str.length() >= 2) {
			if (str.charAt(0) == '#')
				return 1;
			else if (str.charAt(0) == '@')
				return 2;
		}
		return 0;
	}

	public static int redCodeToInt(String redCode) throws CoreException {
		int op = 0;
		switch (redCode) {
		case "DAT":
			op = 0;
			break;
		case "MOV":
			op = 1;
			break;
		case "ADD":
			op = 2;
			break;
		case "SUB":
			op = 3;
			break;
		case "JMP":
			op = 4;
			break;
		case "JMZ":
			op = 5;
			break;
		case "DJZ":
			op = 6;
			break;
		case "CMP":
			op = 7;
			break;
		case "MUL":
			op = 8;
			break;
		case "DIV":
			op = 9;
			break;
		case "MOD":
			op = 10;
			break;
		case "NOP":
			op = 11;
			break;
		case "JMN":
			op = 12;
			break;
		case "DJN":
			op = 13;
			break;
		case "SEQ":
			op = 14;
			break;
		case "SNE":
			op = 15;
			break;
		default:
			throw new CoreException("Opération illegale!");
		}
		return op;
	}

	public static String intToRedCode(int opcode) {
		String res = "";

		switch (opcode) {
		case (0):
			res = "DAT";
			break;
		case (1):
			res = "MOV";
			break;
		case (2):
			res = "ADD";
			break;
		case (3):
			res = "SUB";
			break;
		case (4):
			res = "JMP";
			break;
		case (5):
			res = "JMZ";
			break;
		case (6):
			res = "DJZ";
			break;
		case (7):
			res = "CMP";
			break;
		case (8):
			res = "MUL";
			break;
		case (9):
			res = "DIV";
			break;
		case (10):
			res = "MOD";
			break;
		case (11):
			res = "NOP";
			break;
		case (12):
			res = "JMN";
			break;
		case (13):
			res = "DJN";
			break;
		case (14):
			res = "SEQ";
			break;
		case (15):
			res = "SNE";
			break;
		}
		return res;
	}

	/*
	 * DAT = 0 MOV = 1 ADD = 2 SUB = 3 JMP = 4 JMZ = 5 DJZ = 6 CMP = 7 MUL = 8 DIV =
	 * 9 MOD = 10 NOP = 11 JMN = 12 DJN = 13 SEQ = 14 SNE = 15
	 * 
	 * modes: Blank = 0 , # = 1 , @ = 2
	 */}
