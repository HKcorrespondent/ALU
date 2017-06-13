import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ALUTest17_final {
	ALU alu = new ALU();
	@Test
	public void integerDivision() {
		System.out.println("answerQ…ÃR”‡ ˝ "+alu.integerDivision("0111", "0001", 4));
//		assertEquals("001110000", alu.integerDivision("0111", "0001", 4));
//		assertEquals("011101111", alu.integerDivision("1001", "0011", 4));
//		assertEquals("000010001",alu.integerDivision("0100", "0011", 4));
//		assertEquals("00000000100000001",alu.integerDivision("0100", "0011", 8));
	}
	@Test
	public void signedAddition() {
		assertEquals("0100000000",alu.signedAddition("10000", "00000", 8));
		assertEquals("0000000000",alu.signedAddition("00100", "10100", 8));
	}
	@Test
	public void floatAddition() {
		System.out.println("001111010"+"   "+ "001110100");
		assertEquals("0010000000",alu.floatAddition("010000000", "000010000", 4, 4, 4));
	}
	@Test
	public void floatSubtraction() {
		assertEquals("000111110010000000",alu.floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 4));
	}
	public void floatmu() {
//		System.out.println(alu.floatMultiplication("001110000", operand2, eLength, sLength));
	}
}
