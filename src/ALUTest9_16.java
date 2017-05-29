import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ALUTest9_16 {
	ALU alu =new ALU();
	@Test
	public void logRightShift() {
		
		assertEquals("00111101", alu.logRightShift("11110110", 2)
				);

	}
	
	@Test
	public void ariRightShift() {
		
		assertEquals("11111101", alu.ariRightShift("11110110", 2)
				);

	}
	
	@Test
	public void fullAdder() {
		
		assertEquals("10", alu.fullAdder('1', '1', '0')
				);

	}
	
	@Test
	public void claAdder() {
		assertEquals("01100", alu.claAdder("1001", "0011", '0'));
				
		assertEquals("11111", alu.claAdder("1111", "1111", '1'));
				
		assertEquals("01011", alu.claAdder("1001", "0001", '1')
				);

	}
	
	@Test
	public void oneAdder1() {
		assertEquals("010000010", alu.oneAdder("10000001")
				);
		assertEquals("000001010", alu.oneAdder("00001001")
				);

	}	
	@Test
	public void adder() {
		
		assertEquals("000000111", alu.adder("0100", "0011", '0', 8)
				);
		
		assertEquals("100000000", alu.adder("10000000", "10000000", '0', 8));
//		assertEquals("01111111", alu.adder("11100", "01100011", '0', 8)
//				);
		assertEquals("11111", alu.adder("0111", "0111",'1', 4)
				);

	}
	
	@Test
	public void integerAddition() {
		
		assertEquals("000000111", alu.integerAddition("0100", "0011", 8)
				);

	}
	@Test
	public void integerSubtraction() {
		int x =15;int y=-5;
		int length =8;
		assertEquals(0+alu.integerRepresentation(""+(x-y), length), alu.integerSubtraction(alu.integerRepresentation(""+x,length),alu.integerRepresentation(""+y,length) , length));
		
		
		
		assertEquals("000000001", alu.integerSubtraction("0100", "0011", 8)
				);
		assertEquals("11111", alu.integerSubtraction("0111", "1000", 4)
				);

	}
	@Test
	public void integerMultiplication() {
		assertEquals("01000",alu.integerMultiplication("1000", "0001",4 ));
		assertEquals("11110",alu.integerMultiplication("1101", "0110",4 ));
		assertEquals("000001100",alu.integerMultiplication("0100", "0011", 8));
		
	}
	
	
	
	
	
}
