import static org.junit.Assert.*;

import org.junit.Test;

public class ALUTeat1_8 {
	ALU alu =new ALU();
	@Test

	public void integerRepresentationTest1() {
		 assertEquals("11111111",alu.integerRepresentation("-1",8)); 
		 assertEquals("1100",alu.integerRepresentation("-4",4)); 
		 assertEquals("10000000",alu.integerRepresentation("-128",8)); 
		 assertEquals("10000000",alu.integerRepresentation("128",8)); 
		 assertEquals("0000000",alu.integerRepresentation("128",7)); 
		 assertEquals("010000000",alu.integerRepresentation("128",9)); 
		 assertEquals("00000011",alu.integerRepresentation("3",8)); 
		 assertEquals("10000000000000000000000000000000",alu.integerRepresentation("-2147483648",32)); 
		 
		 assertEquals("00001001", alu.integerRepresentation("9", 8)
				);

	}
	
	
	@Test

	public void floatRepresentation() {
//		assertEquals("011000000000000000",alu.floatRepresentation("359538626972463181545861038157804946723595000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000659248448274432.0",12,5));
		assertEquals("01000011101101100100",alu.floatRepresentation("364.5",8, 11));
//		assertEquals("01000001001101100000", alu.floatRepresentation("11.375", 8, 11)
//				);

	}
	@Test

	public void ieee7541() {
		
		assertEquals("01000001001101100000000000000000", alu.ieee754("11.375", 32)
				);

	}
	@Test

	public void integerTrueValue1() {
		
		assertEquals("9", alu.integerTrueValue("00001001")
				);

	}
	@Test
	public void floatTrueValue1() {
	
		assertEquals("11.375", alu.floatTrueValue("01000001001101100000", 8, 11)
			);
		System.out.println(alu.floatTrueValue("01000111101101100100", 8, 11));

	}
	@Test

	public void negation1() {
		
		assertEquals("11110110", alu.negation("00001001")
				);

	}
	
	@Test
	public void leftShift() {
		
		assertEquals("00100100", alu.leftShift("00001001", 2)
				);

	}
	

}
