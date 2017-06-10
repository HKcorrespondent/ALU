import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.javafx.runtime.async.AsyncOperationListener;

/**
 * ģ��ALU���������͸���������������
 * @author [�����_161250169]
 *�����_161250169
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
	//�����õ�ȫ1��0����Ϊlength���ַ���
	private static String turn(String ost, int Length){
			String s = "";
			while(Length>0){
				s=s+ost;
				Length--;
			}
			return s;
			
		}
	
	
	
	/**
	 * 1.����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
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
	private String getInf(double value, int eLength, int sLength){
		String stringELength=turn("1",eLength);
		String stringSLength=turn("0",sLength);

		if(value>0){

		return plus0+stringELength+stringSLength;
		
		}else{
			
		return minus1+stringELength+stringSLength;
		}
		
	}
	/**
	 * 
	 * 2.����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		

		//	һ����LOW�ķ���
		// TODO YOUR CODE HERE.
		
		String stringELength="";
		String stringSLength="";
	
		String floatRepresentation="";
		switch(number){
		case "+Inf"	:
			
			floatRepresentation=getInf(1,  eLength,  sLength);
			break;
		case "-Inf"	:

			floatRepresentation=getInf(-1,  eLength,  sLength);
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
			//�ǹ��
				double[] numbers = null;
				try {
					numbers = splitNumber2doubleIntegerAndDecimal(number);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "error";
				}
			
				
				double numof2=Math.abs(numbers[1]);
				int j=2*sLength+2;
				String stringOf2="";
				while(numof2!=0&&j>0){
				
					
					j--;
					if(numof2*2>=1){
						numof2=numof2*2-1.0D;
						stringOf2=stringOf2+"1";
					}else{
						numof2=numof2*2;
						stringOf2=stringOf2+"0";
					}
					
				}
				
				stringOf2=leftShift(stringOf2,Math.abs((1-(int)pianyi)) );
				
				return floatRepresentation+turn("0", eLength)+stringOf2.substring(0, sLength);

			}else{
			//���
				if(Double.parseDouble(number)<=-(2*Math.pow(2, Math.pow(2, eLength)-2))){
					return getInf(-1, eLength, sLength);
				}
				if(Double.parseDouble(number)>=(2*Math.pow(2, Math.pow(2, eLength)-2))){
					return getInf(1, eLength, sLength);
				}
				
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
				double numof1=Math.abs(numbers[0]);
				double numof2=Math.abs(numbers[1]);
//				System.out.println();
//				System.out.println(numof1);
//				System.out.println(numof2);
//				System.out.println();
//				System.out.println(numof1+" num2 "+numof2);
				int i=0;
				for( i =0;i<1000;i++){
					if(Math.pow(2, i)>numof1){
						break;
					}
				}

				boolean ident = false;
				while(i>=0||numof1!=0){
					if(numof1>=Math.pow(2, i)){
						ident=true;
						numof1=numof1-Math.pow(2, i);
						stringOf1=stringOf1+"1";
					}else{
						if(ident){
							stringOf1=stringOf1+"0";
						}
					}
					i--;
				}
				
				
				
				

//				System.out.println(stringOf1);
				
	
				int j=2*sLength;

				while(numof2!=0&&j>0){
					
					j--;
					if(numof2*2>=1){
						numof2=numof2*2-1.0D;
						stringOf2=stringOf2+"1";
					}else{
						numof2=numof2*2;
						stringOf2=stringOf2+"0";
					}
				}
				

				if(stringOf1.length()<1){
					stringOf1="0";
				}
				if(stringOf2.length()<1){
					stringOf2="0";
				}

				stringOf2=stringOf2+turn("0", sLength);
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
		if(stringOf1.equals("0")){
			if(!stringOf2.equals(fullBit("0", stringOf2.length()))){
				int i=0;
				out:for( i=0;i<stringOf2.length();i++){
					if(stringOf2.charAt(i)=='1'){

						 jiema-=i+1;
						 stringOf2=stringOf2.substring(i+1);
						 
						 stringOf2=stringOf2+fullBit("0", i+1);
						 
					
						 break out;
					}
				}
			}
		}
		
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
	 * 3.����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String ieee754 (String number, int length) {
		if(length==32){
			return floatRepresentation(number, 8, 23) ;
		}else{
			return floatRepresentation(number, 11, 52) ;
		}
	
		
	}
	
	/**
	 * 4.��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
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
	 * 6.���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		
		if(operand.substring(1).equals(turn("0", operand.substring(1).length()))){
			return ""+0;
		}
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
			// �ǹ��
			
			int yiwei=-(power(2, eLength-1)-1);
			
			return ret+denormalizedFloatTrue(sString, yiwei);
			
			
			
		}else {
			//���
//			System.out.println(integerTrueValue("0"+eString));
//			
			int yiwei=Integer.parseInt(integerTrueValue("0"+eString))-(power(2, eLength-1)-1);
			//get previous value of sLength
		
			while(("1"+sString).length()<yiwei+3){
				sString=sString+"0";
			}
			return ret+floatTrue(sString, yiwei);
//			
//			String integerString;
//			String decimalString;
//			if(yiwei>=0){
//
//			integerString = ("1"+sString).substring(0, yiwei+1);
//			decimalString = ("1"+sString).substring(yiwei+1);
//
//			}else{
//				integerString ="0";
//				decimalString=fullBit("0", (-yiwei)-1)+("1"+sString);
//			}
//			double integerDouble= 0;
//			for(int i=integerString.length()-1;i>=0;i--){
//				if(integerString.charAt(i)=='1'){
//					integerDouble+=Math.pow(2, (integerString.length()-1)-i);
//				}else{
//					
//				}
//			}
//
//			double decimalDouble =0;
//			for(int i =0;i<decimalString.length();i++){
//				if(decimalString.charAt(i)=='1')
//					decimalDouble+=Math.pow(2, -(i+1));
//			}
//			double retd = integerDouble+decimalDouble;
//			return ret+retd;
////			System.out.println(ret+integerLong+String.valueOf(decimalDouble).substring(1));
////			return ret+integerLong+String.valueOf(decimalDouble).substring(1);
		}
		
		
		
	}
	private double floatTrue(String sString,int yiwei){
		double pianyiliang = Math.pow(2, yiwei);
		double decimalDouble =0;
		for(int i =0;i<sString.length();i++){
			if(sString.charAt(i)=='1')
				decimalDouble+=Math.pow(2, -(i+1));
		}
		decimalDouble+=1.0;
		
		return decimalDouble*pianyiliang;
		
		
	}
	private double denormalizedFloatTrue(String sString,int yiwei){
	
		double pianyiliang = Math.pow(2, yiwei);
		double decimalDouble =0;
		for(int i =0;i<sString.length();i++){
			if(sString.charAt(i)=='1')
				decimalDouble+=Math.pow(2, -(i+1));
		}
//		decimalDouble+=1.0;
		
		return decimalDouble*pianyiliang*2;
		
		
	}
	
	
	
	
	/**
	 * 7.��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
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
	 * 8.���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		int StringBit=operand.length();
//		n = n%(StringBit);
		if(n>StringBit){n=StringBit;}
		operand = operand.substring(n);
		operand+=ALU.turn("0", n);
		return operand;
	}
	
	/**
	 * 9.�߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		int StringBit=operand.length();
		if(n>StringBit){
			n=StringBit;
		}
//		String symbol=""+operand.charAt(0);
		operand = operand.substring(0,(StringBit-n));
		operand=ALU.turn("0", n)+operand;
		return operand;
	}
	
	/**
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		int StringBit=operand.length();
		if(n>StringBit){n=StringBit;}
		String symbol=""+operand.charAt(0);
		operand = operand.substring(0,(StringBit-n));
		operand=ALU.turn(symbol, n)+operand;
		return operand;
	
	}
	
	/**
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
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
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
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
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
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
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
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
	/*
	 * ��������operand������չ��lengthλ����
	 */
	
	
	private String fullBit(String operand,int length){
		char c=operand.charAt(0);
		while(operand.length()<length){
			operand=c+operand;
		}
		return operand;
		
	}
	
	/**
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
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
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
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
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		String syb ="0";
		// TODO YOUR CODE HERE.
		operand1=fullBit(operand1, length);
		operand2=fullBit(operand2, length);
		String reg=fullBit(operand1, length*2);

		String R=reg.substring(0, length);
		
		String Q=reg.substring(length);
		
		if(operand2.equals(fullBit("0", operand2.length()))){
			System.out.println("zhe li 1");
			syb="1";
		}
		if(operand1.equals(fullBit("0", operand2.length()))){
			return "NaN";
			
		}
		R=adder(reg.substring(0,length), SwitchDiv(reg.substring(length), operand2), '0', operand2.length()).substring(1);
		//panduan yi chu
		

		
		
		
		
		reg=R+Q;
		
		String Qone=SwitchDivQ(R, operand2);
		if((operand1.charAt(0)==operand2.charAt(0)&&Qone.equals("1"))||(operand1.charAt(0)!=operand2.charAt(0)&&Qone.equals("0"))){
			System.out.println("zhe li 2    "+Qone);
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
		
		System.out.println(Q); 
		System.out.println(R);
		if(R.equals(operand2)){
			Q=oneAdder(Q).substring(1);
			R=turn("0", R.length());
		}
		if(R.equals(oneAdder(negation(operand2)).substring(1))){
			Q=adder(Q, turn("1", Q.length()), '0', Q.length()).substring(1);
			R=turn("0", R.length());
		}
		
		
		
		return syb+Q+R;
	}
	private String SwitchDivQ(String R,String operand){
		if(R.charAt(0)==operand.charAt(0)){

			return "1";
			
		}else{

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
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		//���Ƚ�operand������ȡ����
		char signOfOperand1 = operand1.charAt(0);
		char signOfOperand2 = operand2.charAt(0);
		
		//��������ȡ����ֵ����䵽length����
		operand1 = fullBit("0"+operand1.substring(1), length);
		operand2 = fullBit("0"+operand2.substring(1), length);
		//�ж�֮�����ӷ����Ǽ���

		String retStirng="";
		if(signOfOperand1==signOfOperand2){
			String adderValue=adder(operand1, operand2, '0', length+4).substring(1);

		
				retStirng=""+adderValue.charAt(3)+signOfOperand1+adderValue.substring(4);
				
		
		}else{
			String ret = adder("0"+operand1,"0"+negation(operand2) , '1', length+4);
			
			ret=ret.substring(4);
	
			if(ret.charAt(0)=='1'){
				retStirng= "0"+ signOfOperand1+ret.substring(1);
			}else{
				retStirng="0"+negation(""+signOfOperand1)+oneAdder(negation(ret.substring(1))).substring(1);
			}
			
//			String retSign="";
//			if(firstOperandIsBigger(operand1, operand2)){
//				
//				
//				
//				String ret = adder(operand1,negation(operand2) , '1', length);
//				retStirng=ret.charAt(0)+signOfOperand1
//			}else{
//				String ret = adder(operand2,negation(operand1) , '1', length);
//				
//			}
			
		}
		//do it
		
		
		
		
		
		return retStirng;
	}
	/*
	 * �Ƚ�ԭ���С,��һ����ͷ���true,���򷵻�false,
	 */
	private boolean firstOperandIsBigger(String firstOperand,String secondOperand){
		if(firstOperand.length()!=secondOperand.length()){
			int length=(firstOperand.length()>secondOperand.length())?firstOperand.length():secondOperand.length();
			firstOperand=fullBit("0"+firstOperand, length);
			secondOperand=fullBit("0"+secondOperand, length);
		}
		for(int i=0;i<firstOperand.length();i++){
			
			if(firstOperand.charAt(i)!=secondOperand.charAt(i)){
				if(firstOperand.charAt(i)=='1'&&secondOperand.charAt(i)=='0'){
					return true;
				}else if(firstOperand.charAt(i)=='0'&&secondOperand.charAt(i)=='1'){
					return false;
				}else{
					return false;
				}
			}
		}
		
		return false;
		
	}
	
	
	

	/**
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		//assertEquals("000111111101110000",alu.floatAddition("00111111010100000", "00111111001000000", 8, 8, 4));
		if(operand1.substring(1).equals(turn("0", operand1.length()-1))){
			return "0"+operand2;
		}
		if(operand2.substring(1).equals(turn("0", operand2.length()-1))){
			return "0"+operand1;
		}
		
		floatNumber float1 =new floatNumber(operand1, eLength, sLength);
		floatNumber float2 =new floatNumber(operand2, eLength, sLength);
		
		System.out.println(float1.expoent);
		String retExpoent="";
		String retSign="";
		int yiwei;
		if(firstOperandIsBigger(float1.expoent, float2.expoent)){
			retSign=float1.sign;
			yiwei=Integer.parseInt(integerTrueValue(float1.expoent))-Integer.parseInt(integerTrueValue(float2.expoent));
			retExpoent=float1.expoent;
			float2.rightShift(yiwei, float1.expoent);
			float1.significand=float1.significand+turn("0", yiwei);
		}else{
			yiwei=Integer.parseInt(integerTrueValue(float2.expoent))-Integer.parseInt(integerTrueValue(float1.expoent));
			if(!float2.expoent.equals(float1.expoent)){
				retSign=float2.sign;
			}
			retExpoent=float2.expoent;
			float1.rightShift(yiwei, float2.expoent);
			float2.significand=float2.significand+turn("0", yiwei);
		}
		System.out.println("yiwei:"+yiwei);
		
		
		
		//β���Ӽ�
		int signedAddLength = 4;
		while(signedAddLength<float1.significand.length()+1){
			signedAddLength+=4;
		}
		int addLength=signedAddLength-float1.significand.length();
		System.out.println();
		System.out.println(float1.significand);
		System.out.println();
		System.out.println((float1.sign+float1.significand+" "+float2.sign+float2.significand+" "+ signedAddLength));
		String retSignificand = signedAddition(float1.sign+float1.significand,float2.sign+float2.significand, signedAddLength);
		//β�����
		
		System.out.println("guigehua  "+retSignificand);
		if(retSign.equals("")){
		retSign=""+retSignificand.charAt(1);
		}
		retSignificand=retSignificand.substring(2+addLength);
		System.out.println(retSign);
		
		System.out.println("guigehua  "+retSignificand);
		if(retSignificand.charAt(0)=='1'){
			retExpoent=oneAdder(retExpoent);
			if(retExpoent.charAt(0)=='1'||retExpoent.substring(1).equals(turn("1", retExpoent.length()-1))){
				//shang yi
				
			}
			retExpoent=retExpoent.substring(1);
			
			return "0"+retSign+retExpoent+retSignificand.substring(1, sLength+1);
			
			
			
		}else{
			if(retSignificand.charAt(1)=='1'){
				return "0"+retSign+retExpoent+retSignificand.substring(2,sLength+2);
			}else{
				//gui ge hua
				System.out.println("here");
				System.out.println(retSignificand);
				retSignificand=retSignificand.substring(1);
				int iLength=retSignificand.length();
				int jiemayidong=0;
				for(int i=0;i<iLength;i++){
					
					if(retSignificand.charAt(0)=='1'){
						break;
					}
					//jiema jian yi
					jiemayidong++;
					//zuo yi
					retSignificand=retSignificand.substring(1);
				}
				System.out.println(retSignificand);
				int ExpoentLength = 4;
				while(ExpoentLength<retExpoent.length()+1){
					ExpoentLength+=4;
				}
				if(retSignificand.length()==0){
					return "0"+retSign+turn("0", sLength+eLength);
				}
				String s=(signedAddition(retExpoent, "1"+integerRepresentation(""+jiemayidong, ExpoentLength-1), ExpoentLength));
				System.out.println("s:"+s);
				System.out.println("i'm "+retSignificand);
				retExpoent=s.substring(s.length()-retExpoent.length(), s.length());
				System.out.println(retSignificand);
				System.out.println(retSignificand);
				return "0"+retSign+retExpoent+(retSignificand+turn("0", sLength+1)).substring(1, sLength+1);	
				
				
				
				
			}
			
		}
		
		
		//����жϲ��������մ�
		
		
	}
	
	/**
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.

		//
		
		
		
		if(operand2.charAt(0)=='0'){
			operand2="1"+operand2.substring(1);
		}else{
			operand2="0"+operand2.substring(1);
		}
		if(operand2.substring(1).equals(turn("0", operand2.length()-1))){
			return "0"+operand1;
		}
			
		
		return floatAddition(operand1, operand2, eLength, sLength, gLength);
	}
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		if(operand1.substring(1).equals(turn("0", operand1.length()-1))){
			return turn("0", 2+eLength+sLength);
		}

		if(operand2.substring(1).equals(turn("0", operand2.length()-1))){
			return turn("0", 2+eLength+sLength);
		}
		floatNumber number1 =new floatNumber(operand1, eLength, sLength);
		floatNumber number2 =new floatNumber(operand2, eLength, sLength);
		String overflow="0";
		String sign=(number1.sign.equals(number2.sign))?"0":"1";
		String retSignificand="";
		String retExpoent;

		int  expoent=Integer.parseInt(integerTrueValue(number1.expoent))+Integer.parseInt(integerTrueValue(number2.expoent))-(int)(Math.pow(2, eLength-1)-1);
		int multiplicationLength = 4;
		while(multiplicationLength<1+2*(number1.significand.length()-1)){
			multiplicationLength+=4;
		}
		
		retSignificand=integerMultiplication(number1.significand,number2.significand, multiplicationLength);
		retSignificand=retSignificand.substring(retSignificand.length()-(2*eLength+2),retSignificand.length());
		
		if(retSignificand.charAt(0)=='0'){
			retSignificand=retSignificand.substring(2,2+eLength);
		}else{
			expoent++;
			retSignificand=retSignificand.substring(1,1+eLength);
		}
		System.out.println("retSignificand: "+retSignificand);
		//yi chu
		if(expoent>=(int)(Math.pow(2, eLength))){
			return "1"+sign+turn("1", eLength)+turn("0", sLength);
			
		}
		if(expoent<Math.pow(2, 1-(int)Math.pow(2, eLength-1))){
			return "�ǹ�񻯻���0";
		}
		
		
		
		retExpoent=integerRepresentation(""+expoent, eLength+1);
		retExpoent=retExpoent.substring(retExpoent.length()-eLength,retExpoent.length());

		
		
		return overflow+sign+retExpoent+retSignificand;
	}
	
	/**
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		
		floatNumber number1 =new floatNumber(operand1, eLength, sLength);
		floatNumber number2 =new floatNumber(operand2, eLength, sLength);
		if(operand1.substring(1).equals(turn("0", operand1.length()-1))){
			return turn("0", 2+eLength+sLength);
		}

		if(operand2.substring(1).equals(turn("0", operand2.length()-1))){
			return "0"+((number1.sign.equals(number2.sign))?"0":"1")+turn("1", eLength)+turn("0",sLength);
		}

		String overflow="0";
		String sign=(number1.sign.equals(number2.sign))?"0":"1";
		String retSignificand="";
		String retExpoent;
		int  expoent=Integer.parseInt(integerTrueValue(number1.expoent))-Integer.parseInt(integerTrueValue(number2.expoent))+(int)(Math.pow(2, eLength-1)-1);
		System.out.println("expoent:"+expoent);
		int divisionLength = 4;
		int gLength=sLength+1;
		
		
		
		while(divisionLength<
				number1.significand.length()+gLength
//				1+2*(number1.significand.length())
				){
			divisionLength+=4;
		}
		System.out.println(number1.significand+" "+number2.significand);
		retSignificand=integerDivision(number1.significand
				+turn("0", gLength)
				,number2.significand
//				+turn("0", number2.significand.length()-1)
				, divisionLength);
		System.out.println("retSignificand: "+retSignificand);
		retSignificand=retSignificand.substring(1,1+divisionLength);
		retSignificand=retSignificand.substring(retSignificand.length()-(gLength+1),retSignificand.length());
		System.out.println("retSignificand: "+retSignificand);
		while(retSignificand.length()>0){
			if(retSignificand.charAt(0)=='1'){
				retSignificand=retSignificand.substring(1);
				break;
			}
			retSignificand=retSignificand.substring(1);
			expoent--;
			//yichupanduan
			
		}
		
		retExpoent=integerRepresentation(""+expoent, eLength+1);
		retExpoent=retExpoent.substring(retExpoent.length()-eLength,retExpoent.length());
		
		
		return overflow+sign+retExpoent+(retSignificand+turn("0", sLength)).substring(0, sLength);
	}
	private class floatNumber{
		
		String sign;
		
		String expoent;
//		int expoentvalue;

		String significand;
		
		public void rightShift(int value,String expoent){
			this.expoent=expoent;
			significand=turn("0", value)+significand;
		}
		floatNumber(String operand, int eLength, int sLength){
			sign=""+operand.charAt(0);
			expoent=operand.substring(1,eLength+1);
			if(expoent!=turn("0", eLength)){
				significand="01"+operand.substring(eLength+1);
			}else{
				significand="00"+operand.substring(eLength+1);
			}
			
			
		}
	}
}
