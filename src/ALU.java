import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.javafx.runtime.async.AsyncOperationListener;

/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author [徐晟韬_161250169]
 *徐晟韬_161250169
 */

public class ALU {
	public static final String plus0 = "0";
	public static final String minus1 = "1";
	
	public static void main(String[] s){
		
		ALU alu =new ALU();
		System.out.println(""+(char)(~'0'));
		
		
//			
////		System.out.println(alu.floatTrueValue("01000001001101100000", 8, 11));
////		System.out.println(alu.floatRepresentation("123453122222222222222222542111111115444444444532111111.25"
////				, 8, 11));
//		System.out.println(alu.integerRepresentation("-2147483648",32));
//
	}
	
	
	
	
	private static int power(int base,int power){
		double ret=1;
		while(power>0){
			ret=base*ret;
			power--;
			
		}
		return (int)ret;
	}
//	private int stringtoint(String s){
//		int sum=0;
//		for(int i=s.length();i>0;i--){
//			sum=sum+chartoint(s.charAt(s.length()-i))*power(10, i-1);
//		}
//		return sum;
//		
//	}
	private  int chartoint(char c){return (int)c-48;}
	//用来得到全1或0长度为length的字符串
	private static String turn(String ost, int Length){
			String s = "";
			while(Length>0){
				s=s+ost;
				Length--;
			}
			return s;
			
		}
	
	
	
	/**
	 * 1.生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
		Long num=Long.parseLong(number);
		String retString ="";
		length+=2;
		if(num>=0){
		
		for(int i =length;i>0;i--){
			retString=retString+num/(long)(Math.pow(2, i-1));
			num=num%(ALU.power(2, i-1));
		}
		}else{
			num=Math.abs(num)-1;
			for(int i =length;i>0;i--){
				retString=retString+num/(long)(Math.pow(2, i-1));
				num=num%((long)(Math.pow(2, i-1)));
			}
			retString=this.negation(retString);
		}
		return retString.substring(2);
	}
	
	/**
	 * 
	 * 2.生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		

		//	一个很LOW的方案
		// TODO YOUR CODE HERE.
		
		String stringELength="";
		String stringSLength="";
	
		String floatRepresentation="";
		switch(number){
		case "+Inf"	:
			stringELength=turn("1",eLength);
			stringSLength=turn("0",sLength);
	
			floatRepresentation=plus0+stringELength+stringSLength;
			break;
		case "-Inf"	:
			stringELength=turn("1",eLength);
			stringSLength=turn("0",sLength);
			
			floatRepresentation=minus1+stringELength+stringSLength;
			break;
		case "NaN"	:
			stringELength=turn("1",eLength);
			stringSLength=turn("1",sLength);
			
			floatRepresentation=plus0+stringELength+stringSLength;
			break;
		case "0"	:
			stringELength=turn("0",eLength);
			stringSLength=turn("0",sLength);
			
			floatRepresentation=plus0+stringELength+stringSLength;
			break;
		case "-0"	:
			stringELength=turn("0",eLength);
			stringSLength=turn("0",sLength);
			
			floatRepresentation=minus1+stringELength+stringSLength;
			break;
		default:{
			double num = Double.parseDouble(number);
			double pianyi =   (Math.pow(2, eLength-1)-1);
			
			if(num==0){
				stringELength=turn("0",eLength);
				stringSLength=turn("0",sLength);
				
				floatRepresentation=plus0+stringELength+stringSLength;
				return floatRepresentation;
			}
			if(num<0){
				floatRepresentation=minus1;
				num=Math.abs(num);
			}else{
				floatRepresentation=plus0;
			}
//			System.out.println(num);
//			System.out.println(Math.pow(2, (Math.pow(2, 0)-pianyi)));
			if(num!=0&&num<Math.pow(2, (Math.pow(2, 0)-pianyi))){
			//非规格化
			System.out.println("no to");
			}else{
			//规格化
			
				double[] numbers = null;
				try {
					numbers = splitNumber2doubleIntegerAndDecimal(number);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "error";
				}
				
					
					
					
					
					
					
				int jiema=0;
				String stringOf1="";
				String stringOf2="";
				double numof1=numbers[0];
				double numof2=numbers[1];

				
				{
					int idtfy=0;
					double i = power(2,eLength)/2;
					
					while(numof1>0||i>=0){
						if(numof1>=Math.pow(2, i)){
							idtfy=1;
							numof1=numof1-Math.pow(2, i);
					
							 stringOf1= stringOf1+"1";
						}else{
							if( idtfy==1)
								stringOf1= stringOf1+"0";
						}
						i--;
					}
				}
				if(stringOf1.length()<1){
					stringOf1="0";
				}
				System.out.println(stringOf1);
				
				{	
					
						for(int i = 1;i<sLength+10;i++){
							if(numof2>=Math.pow(0.5, i)){
								stringOf2=stringOf2+"1";
								numof2=numof2-Math.pow(0.5, i);
							}else{
								stringOf2=stringOf2+"0";
							}
						}
						
						
				}

				
				System.out.println(jiema+","+pianyi+","+ stringOf1+","+stringOf2+","+eLength+","+sLength);
				
				
				
				floatRepresentation=floatRepresentation+getFinalFloatString(jiema, pianyi, stringOf1, stringOf2, eLength, sLength);
				
				
				
				
			}	
			
			}
		
		}
		return floatRepresentation;
//		return "";
	}
	private double[] splitNumber2doubleIntegerAndDecimal(String number)throws Exception{

		String[] numbers =number.split("\\.");
		double[] retNumbers=new double[]{0,0}; 

		if(numbers.length==2){
			retNumbers[0]=Double.parseDouble(numbers[0]);
			retNumbers[1]=Double.parseDouble("0."+numbers[1]);
		}else if(numbers.length==1){
			retNumbers[0]=0;
			retNumbers[1]=Double.parseDouble("0."+numbers[1]);
		}else{
			throw new Exception();
		}
		return retNumbers;
	}
	
	
	
	private String getFinalFloatString(int jiema, double pianyi,String stringOf1, String stringOf2,  int eLength, int sLength){
		String floatRepresentation="";
		 jiema=jiema+ ((stringOf1.length()-1)+(int)(pianyi));
			
			
			String jiemastring="";
			for(int i = eLength-1;i>=0;i--){
				if(jiema>=Math.pow(2, i)){
					jiemastring=jiemastring+"1";
					jiema=jiema-(power(2, i));
				}else{
					jiemastring=jiemastring+"0";
				}
			}
			
			floatRepresentation=floatRepresentation+(jiemastring+stringOf1.substring(1)+stringOf2).substring(0, eLength+sLength);
			
			return floatRepresentation;
	}
	
	
	
	
	
	/**
	 * 3.生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		if(length==32){
			return floatRepresentation(number, 8, 23) ;
		}else{
			return floatRepresentation(number, 11, 52) ;
		}
	
		
	}
	
	/**
	 * 4.计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
		// TODO YOUR CODE HERE.
		String ret = "";
		Long sum=(long) 0;
		if(operand.charAt(0)=='1'){
			ret = "-";
			
		}
		operand = operand.substring(1);
		for(int i = 0;i<operand.length();i++){
			if(operand.charAt(i)=='1'){
			sum=sum+power(2, operand.length()-i-1);
			}else{
				
			}
		}
		
		if(ret.equals(""))
		ret=ret+sum;
		else{
		sum=power(2, operand.length())-sum;
		ret=ret+sum;
		}
		return ret;
	}
	
	/**
	 * 6.计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String ret = "";
		int sum=0;
		if(operand.charAt(0)=='1'){
			ret = "-";
			
		}
		String eString=operand.substring(1, eLength+1);
		String sString=operand.substring(eLength+1);
		
		if(eString.equals(turn("1", eLength))&&sString.equals(turn("0", sLength))){
			if(ret.equals("-"))
				return "-Inf";
			else
				return "+Inf";
		}else if(eString.equals(turn("1", eLength))&&false==sString.equals(turn("0", sLength)))
			return "NaN";
		else if(eString.equals(turn("0", eLength))){
			// 非规格化
			
			
			
			return "非规格化";
			
			
			
		}else {
			//规格化
			int yiwei=Integer.parseInt(integerTrueValue("0"+eString))-(power(2, eLength-1)-1);
			//get previous value of sLength
		
			while(("1"+sString).length()<yiwei+3){
				sString=sString+"0";
			}
			String integerString = ("1"+sString).substring(0, yiwei+1);
			String decimalString = ("1"+sString).substring(yiwei+1);
			System.out.println(integerString);
			System.out.println(decimalString);
			long integerLong= Long.parseLong(integerTrueValue("0"+integerString));
			double decimalDouble =0;
			for(int i =0;i<decimalString.length();i++){
				if(decimalString.charAt(i)=='1')
					decimalDouble+=Math.pow(2, -(i+1));
			}
			
			return ret+integerLong+String.valueOf(decimalDouble).substring(1);
		}
		
		
		
	}
	
	/**
	 * 7.按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		// TODO YOUR CODE HERE.
		String negation="";
		for(int i =0 ;i<operand.length();i++ ){
			if(operand.charAt(i)=='0'){
				negation=negation+"1";
			}else{
				negation=negation+"0";
			}
		}
		
		return negation;
	}
	
	/**
	 * 8.左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		int StringBit=operand.length();
		n = n%(StringBit);
		operand = operand.substring(n);
		operand+=ALU.turn("0", n);
		return operand;
	}
	
	/**
	 * 9.逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		int StringBit=operand.length();
		n = n%(StringBit);
//		String symbol=""+operand.charAt(0);
		operand = operand.substring(0,(StringBit-n));
		operand=ALU.turn("0", n)+operand;
		return operand;
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		int StringBit=operand.length();
		n = n%(StringBit);
		String symbol=""+operand.charAt(0);
		operand = operand.substring(0,(StringBit-n));
		operand=ALU.turn(symbol, n)+operand;
		return operand;
	
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		
		
		                
				
		long num = Stream.of((Character)x,(Character)y,(Character)c)
				.filter(xas -> xas.equals('1'))
				.count();

		String ret ="";
		switch((int)num){
		case 0:
			ret="00";
			break;
		case 1:
			ret="01";
			break;
		case 2:
			ret="10";
			break;
		case 3:
			ret="11";
			break;
		}
		return ret;
	}
	
	/**
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		
		int[] ret = new int[operand1.length()+1];
		int[] bit1 = new int[operand1.length()];
		int[] bit2 = new int[operand2.length()];
		int[] pi   = new int[operand1.length()];
		int[] gi   = new int[operand1.length()];
		int[] cbit	   =new int[operand1.length()+1];
		
		cbit[0]=Integer.parseInt(""+c);
		
		for(int i =0;i<operand1.length();i++){
			bit1[i]=Integer.parseInt(""+operand1.charAt(i));
		}
		for(int i =0;i<operand2.length();i++){
			bit2[i]=Integer.parseInt(""+operand2.charAt(i));
		}
		for(int i =0;i<operand1.length();i++){
			pi[i]=bit1[i]|bit2[i];
		}
		for(int i =0;i<operand1.length();i++){
			gi[i]=bit1[i]&bit2[i];
		}
		
		cbit[4]=gi[3]|(pi[3]&cbit[0]);
		cbit[3]=gi[2]|(pi[2]&gi[3])|(pi[2]&pi[3]&cbit[0]);
		cbit[2]=gi[1]|(pi[1]&gi[2])|(pi[1]&pi[2]&gi[3])|(pi[1]&pi[2]&pi[3]&cbit[0]);
		cbit[1]=gi[0]|(pi[0]&gi[1])|(pi[0]&pi[1]&gi[2])|(pi[0]&pi[1]&pi[2]&gi[3])|(pi[0]&pi[1]&pi[2]&pi[3]&cbit[0]);
		
			
			
		
		
		
		
		return ""+cbit[1]+(cbit[2]^bit1[0]^bit2[0])+(cbit[3]^bit1[1]^bit2[1])+(cbit[4]^bit1[2]^bit2[2])+(cbit[0]^bit1[3]^bit2[3]);
	}
	
	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String operand) {
		String ret="";
		// TODO YOUR CODE HERE.
		if(operand.charAt(0)=='0'){
			operand=operand.substring(1);
			int[] bit = new int[operand.length()];
			int[] retbit = new int[operand.length()+1];
			for(int i =0;i<bit.length;i++){
				bit[i]=Integer.parseInt(""+operand.charAt(i));
			}
			int c =1;
			for(int i=bit.length-1;i>=0;i--){
				retbit[i+1]=bit[i]^c;
				c=bit[i]&c;
			}
			
			retbit[0]=c;
			ret+=retbit[0]+""+c;
			
			for(int i=1;i<retbit.length;i++){
				ret+=retbit[i];
			}
		}else{
			int[] bit = new int[operand.length()];
			int[] retbit = new int[operand.length()+1];
			for(int i =0;i<bit.length;i++){
				bit[i]=Integer.parseInt(""+operand.charAt(i));
			}
			int c =1;
			for(int i=bit.length-1;i>=0;i--){
				retbit[i+1]=bit[i]^c;
				c=bit[i]&c;
			}
			c=0;
			retbit[0]=c;
			
			
			for(int i=0;i<retbit.length;i++){
				ret+=retbit[i];
			}
		}
		return ret;
	}
	
	
	
	
	
	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		// TODO YOUR CODE HERE.
		//first full the bit
		int OF=0;
		char mark='0';
		String ret ="";
		if(operand1.charAt(0)==operand2.charAt(0)&&operand1.charAt(0)=='0'){
			OF=1;
			mark='0';
			
		}
		if(operand1.charAt(0)==operand2.charAt(0)&&operand1.charAt(0)=='1'){
			OF=1;
			mark='1';
		}
		
		operand1=fullBit(operand1, length);
		operand2=fullBit(operand2, length);
		
		String midString ="";
		for(int i=0;i<length/4;i++){
			midString=claAdder(operand1.substring(length-(i+1)*4, length-(i)*4), operand2.substring(length-(i+1)*4, length-(i)*4), c);
	
			c=midString.charAt(0);
			ret=midString.substring(1)+ret;
		}
		if(OF==1){
			OF=((int)ret.charAt(0)-48)^((int)mark-48);
		}
		
	
		
		
				return OF+ret;
	}
	private String fullBit(String operand,int length){
		char c=operand.charAt(0);
		while(operand.length()<length){
			operand=c+operand;
		}
		return operand;
		
	}
	
	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		operand1=fullBit(operand1, length);
		operand2=fullBit(operand2, length);
	
		operand2=negation(operand2);
		

		String ret = adder(operand1, operand2, '1', length);
		
		
		
		
		
		return ret;
	}
//	private int complement2int(String operand){
//		int ret=0;
//		if(operand.charAt(0)=='0'){
//			
//		}
//		
//		return ret;
//		
//	}
//	private int binary2int(String operand){
//		int ret=0;
//		for(int i=operand.length()-1;i>=0;i--){
//			if(operand.charAt(i)==1){
//				
//			}
//		}
//		return ret;
//		
//	}
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		
		length+=4;
		// TODO YOUR CODE HERE.
		operand1=fullBit(operand1, length);
		operand2=fullBit(operand2, length);
		String reg =new String();
		String addString = null;
		String add = null;
		reg=turn("0", length)+operand2+'0';
		for(int i=0;i<length;i++){
		
			
			addString=SwitchMul(reg.substring(reg.length()-2,reg.length()), operand1);
			add=integerAddition(addString,reg.substring(0,addString.length()), addString.length());
		
			reg=add.substring(1)+reg.substring(addString.length());
			reg=ariRightShift(reg, 1);

		}
//			reg=add.substring(1)+reg.substring(addString.length());
//			System.out.println(reg);
		System.out.println(reg);
			if(reg.substring(0, (reg.length()-1)/2+4).equals(fullBit(""+reg.charAt(length+4), reg.substring(0, (reg.length()-1)/2+4).length()))){
				return "0"+reg.substring(length+4,length*2);
			}
			else{
				return "1"+reg.substring(length+4,length*2);
			}
	}
	private String SwitchMul(String string,String operand1){
		switch(string){
		case "11":
		case "00":
			return turn("0",operand1.length());
		case "10":
			return oneAdder(negation(operand1)).substring(1);
		case "01":
			return operand1;
		}
		return null;
	}
	
	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		String syb ="0";
		// TODO YOUR CODE HERE.
		operand1=fullBit(operand1, length);
		operand2=fullBit(operand2, length);
		String reg=fullBit(operand1, length*2);
		System.out.println(reg);
		String R=reg.substring(0, length);
		
		String Q=reg.substring(length);
		
		if(operand2.equals(fullBit("0", operand2.length()))){
			syb="1";
		}
		
		R=adder(reg.substring(0,length), SwitchDiv(reg.substring(length), operand2), '0', operand2.length()).substring(1);
		//panduan yi chu
		
		System.out.println(reg.substring(0,length));
		System.out.println(SwitchDiv(reg.substring(length), operand2));
		System.out.println(R);
		
		
		
		
		
		
		reg=R+Q;
		
		String Qone=SwitchDivQ(R, operand2);
		if((operand1.charAt(0)==operand2.charAt(0)&&Qone.equals("1"))||(operand1.charAt(0)!=operand2.charAt(0)&&Qone.equals("0"))){
			syb="1";
		}
		
		for(int i=0;i<length;i++){
			reg=reg.substring(1)+Qone;
			Q=reg.substring(length);
			R=adder(reg.substring(0,length), SwitchDiv(reg, operand2), '0', operand2.length()).substring(1);
			Qone=SwitchDivQ(R, operand2);
			reg=R+Q;
			
		}
		Q=Q.substring(1)+Qone;
		
		System.out.println(R);
		System.out.println(Q);
		if(operand1.charAt(0)!=operand2.charAt(0)){
			Q=oneAdder(Q).substring(1);
		}
		if(R.charAt(0)!=operand1.charAt(0)){
			if(operand1.charAt(0)==operand2.charAt(0)){
				R=adder(R, operand2, '0', R.length()).substring(1);
			}else{
				R=adder(R, oneAdder(negation(operand2)).substring(1), '0', R.length()).substring(1);
			}
		}
		
		
		
		return syb+Q+R;
	}
	private String SwitchDivQ(String R,String operand){
		if(R.charAt(0)==operand.charAt(0)){
			System.out.println(1);
			return "1";
			
		}else{
			System.out.println(0);
			return "0";
		}
	}
	private String SwitchDiv(String XfirstChar,String operand){
		if(XfirstChar.charAt(0)==operand.charAt(0)){
			return oneAdder(negation(operand)).substring(1);
		}else{
			return operand;
		}
		
	}
	
	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		return null;
	}
	
	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
}
