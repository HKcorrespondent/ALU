
import java.util.Arrays;

/**
 * ģ��ALU���������͸���������������
 * @author 161250170_������
 *
 */

public class XALU {


	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
	 */
	public String integerRepresentation (String number, int length) {
		String ret;
		if(number.charAt(0)!='-'){
			ret=integerToBinary(number);
			if(number.equals("0")){
				char[] zero=new char[length];
				Arrays.fill(zero,'0');
				ret=new String(zero);
			}
			if(ret.length()<length){
				int bias=length-ret.length();
				char[] zero=new char[bias];
				Arrays.fill(zero,'0');
				ret=new String(zero)+ret;
			}
		}else {
			//number < 0
			number=number.replace("-","");
			String temp=sub(power2(String.valueOf(length)),number);
			if(temp.equals("00")){
				char[] overflow=new char[length];
				Arrays.fill(overflow,'0');
				overflow[0]='1';
				ret=new String(overflow);
			}else {
				ret = integerToBinary(temp);
			}
		}
		return ret;
	}


	/**
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		char[] ret=new char[eLength+sLength+1];
		Arrays.fill(ret,'0');
		//deal with inf
		if(number.equals("+Inf")){
			for(int i=0;i<eLength;i++){
				ret[i+1]='1';
			}
			return new String(ret);
		}else if(number.equals("-Inf")){
			for(int i=0;i<=eLength;i++){
				ret[i]='1';
			}
			return new String(ret);
		}

		char[] srcInt=number.split("\\.")[0].toCharArray();
		char[] srcDec=number.split("\\.")[1].toCharArray();
		boolean sig=true;//   + -->true     - -->false;
		//determine the signal of result
		if(srcInt[0]=='-'){
			ret[0]='1';
			sig=false;
			srcInt=Arrays.copyOfRange(srcInt,1,srcInt.length);
		}else {
			ret[0]='0';
		}
        int eMax=(1<<(eLength-1))-1;
		char[] srcBint=integerToBinary(new String(srcInt)).toCharArray();
		char[] srcBdec=decimal2Binary(new String(srcDec),sLength+5).toCharArray();

        //deal with too big number

        if(srcBint.length-1>eMax){
            return sig?floatRepresentation("+Inf",eLength,sLength):floatRepresentation("-Inf",eLength,sLength);
        }
        boolean zf=true;
        //deal pure decimal
        if(srcBint.length==0){
            srcBdec=decimal2Binary(new String(srcDec),sLength+5).toCharArray();
            int cnt=0;
			for (char aSrcBdec : srcBdec) {
				if (aSrcBdec == '1') {
					cnt++;
					zf=false;
					break;
				}
				cnt++;
			}
			//fix a bug
			if(zf){
				Arrays.fill(ret,1,ret.length,'0');
				return new String(ret);
			}
            if(cnt>=eMax){
				//exp-underflow
				char[] retDec;
                char[] zero=new char[cnt-eMax];
                Arrays.fill(zero,'0');
                retDec=(new String(zero)+new String(srcBdec)).toCharArray();
                for(int i=0;i<eLength;i++){
                	ret[1+i]='0';
				}
				for(int i=0;i<sLength;i++){
                	ret[1+eLength+i]=retDec[i];
				}
				return new String(ret);
            }else {
            	//formal
            	char[] exp=integerToBinary(String.valueOf(eMax-cnt)).toCharArray();
            	for(int i=0;i<exp.length;i++){
            		ret[1+eLength-1-i]=exp[exp.length-1-i];
				}
				for(int i=exp.length;i<eLength;i++){
            		ret[1+eLength-1-i]='0';
				}
				System.arraycopy(srcBdec,cnt,ret,1+eLength,sLength);
//				for(int i=0;i<sLength;i++){
//					ret[1+eLength+i]=srcBdec[i];
//				}
				return new String(ret);
			}
		}

		//formal
		int cnt=srcBint.length-1;
//		for(int i=0;;i++){
//			cnt++;
//			if(srcBint[i]=='1'){
//				break;
//			}
//		}
		char[] srcB=(new String(srcBint)+new String(srcBdec)).toCharArray();
		char[] eTemp=integerToBinary(String.valueOf(eMax+cnt)).toCharArray();
		if(eTemp.length<eLength){
			ret[1]='0';
			System.arraycopy(eTemp, 0, ret, 2, eLength - 1);
		}else {
			System.arraycopy(eTemp,0,ret,1,eLength);
		}

		System.arraycopy(srcB,1,ret,1+eLength,sLength);


		return new String(ret);
	}
	
	/**
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String ieee754 (String number, int length) {
	    String ret=null;
	    if(length==32){
	        ret=floatRepresentation(number,8,23);
        }else if(length==64){
	        ret=floatRepresentation(number,11,52);
        }
		return ret;
	}
	
	/**
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 */
	public String integerTrueValue (String operand) {
        String ret;
        String temp="0";
        char[] src=operand.toCharArray();
        int length=operand.length();
        for(int i=length-1;i>=0;i--){
            if(src[i]=='1'){
                temp=add(temp,power2(String.valueOf(length-1-i)));
            }
        }
        if(src[0]=='1'){
            ret=sub(power2(String.valueOf(length)),temp).replaceFirst("0","");
            ret="-"+ret;
        }else {
            ret=temp.replaceFirst("0","");
            if(ret.length()==0){
                ret="0";
            }
        }

        if(length==1){
            if(src[0]=='1'){
                ret="-1";
            }else {
                ret="0";
            }
        }
		return ret;
	}
	
	/**
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		char[] src=operand.toCharArray();
		double result=0.0D;
		String ret;
		char sig=src[0];
		char[] exponent=Arrays.copyOfRange(src,1,1+eLength);
		char[] significand=Arrays.copyOfRange(src,1+eLength,sLength+eLength+1);

		//Judge for zero,inf and NaN
		boolean eZFlag = true, eMFlag = true, sZFlag = true;
		for (int i = 0; i < eLength; i++) {
			if ('0' != exponent[i]) {
				eZFlag = false;
			}
			if ('1' != exponent[i]) {
				eMFlag = false;
			}
		}
		for (int i = 0; i < sLength; i++) {
			if ('0' != significand[i]) {
				sZFlag = false;
			}
		}

		if (eMFlag && sZFlag) {
			ret = "Inf";
		}else if (eMFlag && !sZFlag) {
			ret = "NaN";
		}else if (eZFlag && sZFlag) {
			ret = "0.0";
		}else if(eZFlag){
			//deal with underflow
			double base=Double.parseDouble(power2(sub(power2(String.valueOf(eLength-1)),"2")));
			base=1.0D/base;
			double offset=1.0D;
			for(int i=0;i<sLength;i++){
				if(significand[i]=='1'){
					result+=offset*base;
				}
				base*=0.5D;
			}
			ret=String.valueOf(result);
		}else {
			//normal cases
			double base;
			double offset=1.0D;
			int e=Integer.parseInt(integerTrueValue(("0"+new String(exponent))));
			significand=("1"+new String(significand)).toCharArray();
			e=e-(Integer.parseInt(power2(String.valueOf(eLength-1)))-1);
			if(e==0){
				base=1.0D;
			}else if(e>0){
				base=Double.parseDouble(power2(String.valueOf(e)));
			}else {
				base=Double.parseDouble(power2(String.valueOf(-e)));
				base=1.0D/base;
			}

			for(int i=0;i<=sLength;i++){
				if(significand[i]=='1'){
					result+=offset*base;
				}
				base*=0.5D;
			}

			ret=String.valueOf(result);
			if(ret=="0"){
				ret="0.0";
			}
		}
		if(sig=='1'){
			ret="-"+ret;
		}
		return ret;
	}
	
	/**
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
	 */
	public String negation (String operand) {
		StringBuffer buffer=new StringBuffer();
		char temp;
		for(int i=0,len=operand.length();i<len;i++){
			temp=operand.charAt(i);
			if(temp=='0'){
				buffer.append('1');
			}else {
				buffer.append('0');
			}
		}
		return new String(buffer);
	}
	
	/**
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
	 */
	public String leftShift (String operand, int n) {
		StringBuffer zerobuf=new StringBuffer();
		for(int i=1;i<=n;i++){
			zerobuf.append('0');
		}
		operand=operand+new String(zerobuf);

		return operand.substring(n);
	}
	
	/**
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
	 */
	public String logRightShift (String operand, int n) {
		int len=operand.length();
		StringBuffer zerobuf=new StringBuffer();
		for(int i=1;i<=n;i++){
			zerobuf.append('0');
		}
		operand=new String(zerobuf)+operand;
		return operand.substring(0,len);
	}
	
	/**
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public String ariRightShift (String operand, int n) {
		char first=operand.charAt(0);
		if(first=='1'){
			int len=operand.length();
			StringBuffer sigbuffer=new StringBuffer();
			for(int i=1;i<=n;i++){
				sigbuffer.append('1');
			}
			operand=new String(sigbuffer)+operand;
			return operand.substring(0,len);
		}else {
			return logRightShift(operand,n);
		}
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
		char first=(char)(((x&y)|(c&(x^y)))&0x0f+0x30);
		char second=(char)(x^y^c);
		return ""+first+second;
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
		char[] num1=operand1.toCharArray();
		char[] num2=operand2.toCharArray();
		char[] g=new char[4];
		char[] p=new char[4];
		char[] carry=new char[5];
		char[] ret=new char[5];
		carry[4]=c;
		for(int i=0;i<4;i++){
			p[3-i]=(char)(((num1[i]|num2[i])&0xf)|0x30);
			g[3-i]=(char)((num1[i]&num2[i]&0xf)|0x30);
		}
		carry[3]=(char)(g[0]|(p[0]&c));
		carry[2]=(char)(g[1]|(p[1]&g[0])|(p[1]&p[0]&c));
		carry[1]=(char)(g[2]|(p[2]&g[1])|(p[2]&p[1]&g[0])|(p[2]&p[1]&p[0]&c));
		carry[0]=(char)(g[3]|(p[3]&g[2])|(p[3]&p[2]&g[1])|(p[3]&p[2]&p[1]&g[0])|(p[3]&p[2]&p[1]&p[0]&c));

		ret[0]=carry[0];
		for(int i=1;i<5;i++){
			ret[i]=fullAdder(num1[i-1],num2[i-1],carry[i]).charAt(1);
		}
		return new String(ret);
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
		int len=operand.length();
		char[] ret=new char[len+1];
		char[] num1=operand.toCharArray();
		char[] num2=new char[len+1];
		Arrays.fill(num2,'0');
		num2[len]='1';
		char[] carry=new char[len+1];
		Arrays.fill(carry,'0');
		for(int i=len-1;i>=0;i--){
			ret[i+1]=(char)(num1[i]^num2[i+1]^carry[i+1]);
			carry[i]=(char)((num1[i]&carry[i+1])|(num2[i+1]&carry[i+1])|(num1[i]&num2[i+1]));
		}
		ret[0]=(char)((carry[1]^carry[0])+'0');
		return new String(ret);
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
		char[] num1=operand1.toCharArray();
		char[] num2=operand2.toCharArray();

		//������չ
		StringBuffer sigbuffer=new StringBuffer();
		if(num1.length<length){
			int bias=length-num1.length;
			char sig=num1[0];
			for(int i=bias;i>0;i--){
				sigbuffer.append(sig);
			}
			num1=(new String(sigbuffer)+operand1).toCharArray();
			sigbuffer.setLength(0);
		}
		if (num2.length<length){
			int bias=length-num2.length;
			char sig=num2[0];
			for(int i=bias;i>0;i--){
				sigbuffer.append(sig);
			}
			num2=(new String(sigbuffer)+operand2).toCharArray();
		}
		//��������Ա��ж����
		char sig1=num1[0];
		char sig2=num2[0];
		int time=length/4;
		char[] carry=new char[time+1];
		carry[time]=c;
		//������ÿ��λ���ӷ�
		String[] result=new String[time];
		StringBuffer resultSrc=new StringBuffer();
		StringBuffer buffer1=new StringBuffer();
		StringBuffer buffer2=new StringBuffer();
		for(int i=0;i<time;i++){
			buffer1.setLength(0);
			buffer2.setLength(0);

			buffer1.append(num1[length-4-4*i]);
			buffer1.append(num1[length-3-4*i]);
			buffer1.append(num1[length-2-4*i]);
			buffer1.append(num1[length-1-4*i]);
			buffer2.append(num2[length-4-4*i]);
			buffer2.append(num2[length-3-4*i]);
			buffer2.append(num2[length-2-4*i]);
			buffer2.append(num2[length-1-4*i]);

			result[time-1-i]=claAdder(new String(buffer1),new String(buffer2),carry[time-i]);
			carry[time-1-i]=result[time-1-i].charAt(0);
			result[time-1-i]=result[time-1-i].substring(1);
			resultSrc=resultSrc.insert(0,result[time-1-i]);
		}
		if((sig1==sig2)&&(sig1!=result[0].charAt(0))){
			resultSrc.insert(0,'1');
		}else {
			resultSrc.insert(0,'0');
		}

		return new String(resultSrc);
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

		return adder(operand1,operand2,'0',length);
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
		operand1=adder(operand1,"00",'0',length).substring(1);
		operand2=adder(operand2,"00",'0',length).substring(1);
		operand2=negation(operand2);
		return adder(operand1,operand2,'1',length);
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		//sig-extend
		String num1=adder(operand1,"00",'0',length).substring(1);
		String num2=adder(operand2,"00",'0',length).substring(1);
		String neg_num1=adder(negation(operand1),"01",'0',length).substring(1);

		char[] p=new char[length];
		char y_1='0';
		Arrays.fill(p,'0');
		String py=new String(p)+num2;
		StringBuffer temp=new StringBuffer();
		int lastIndex=py.length()-1;
		for(int i=0;i<length;i++){
			temp.setLength(0);
			temp.append(py.charAt(lastIndex));
			temp.append(y_1);
			switch (new String(temp)){
				case "00": {
					y_1='0';
					break;
				}
				case "01":{
					y_1='0';
					py=adder(py.substring(0,length),num1,'0',length).substring(1)+py.substring(length,lastIndex+1);
					break;
				}
				case "11":{
					y_1='1';
					break;
				}
				case "10":{
					y_1='1';
					py=adder(py.substring(0,length),neg_num1,'0',length).substring(1)+py.substring(length,lastIndex+1);
					break;
				}
			}
			py=ariRightShift(py,1);
		}
		char[] py_array=py.toCharArray();
		int tmp=0;
		for(int i=0;i<length;i++){
			tmp+=(py_array[i]^py_array[i+1])&0xf;
		}
		if(tmp!=0){
			py='1'+py;
		}else {
			py='0'+py;
		}
		return py.charAt(0)+py.substring(length+1);
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
		String num1=adder(operand1,"0",'0',2*length).substring(1);
		String num2=adder(operand2,"0",'0',length).substring(1);
		String num2_neg=negation(num2);
		char of='0';
		num2_neg=adder(num2_neg,"01",'0',length).substring(1);
		char[] r;
		char[] q;
		q=num1.substring(length,2*length).toCharArray();
		if(num1.charAt(0)==num2.charAt(0)){
			r=adder(num1.substring(0,length),num2_neg,'0',length).substring(1).toCharArray();
			if(r[0]==num2.charAt(0)){
				q[length-1]='1';
				of='1';
			}else {
				q[length-1]='0';
			}

		}else{
			r=adder(num1.substring(0,length),num2,'0',length).substring(1).toCharArray();
			if(r[0]==num2.charAt(0)){
				q[length-1]='1';
			}else {
				q[length-1]='0';
				of='1';
			}
		}
		char temp;
		for(int i=0;i<length;i++){
			if(r[0]==num2.charAt(0)){
				temp=q[0];
				q=leftShift(new String(q),1).toCharArray();
				q[length-1]='1';
				r=leftShift(new String(r),1).toCharArray();
				r[length-1]=temp;
				r=adder(new String(r),num2_neg,'0',length).substring(1).toCharArray();
			}else {
				temp=q[0];
				q=leftShift(new String(q),1).toCharArray();
				q[length-1]='0';
				r=leftShift(new String(r),1).toCharArray();
				r[length-1]=temp;
				r=adder(new String(r),num2,'0',length).substring(1).toCharArray();
			}
		}

		//deal with q
		if(r[0]==num2.charAt(0)){
			q=leftShift(new String(q),1).toCharArray();
			q[length-1]='1';
		}else {
			q=leftShift(new String(q),1).toCharArray();
			q[length-1]='0';
		}
		if(num1.charAt(0)!=num2.charAt(0)){
			q=adder(new String(q),"01",'0',length).substring(1).toCharArray();
		}

		//deal with r
		if(r[0]!=num1.charAt(0)){
			if(num1.charAt(0)==num2.charAt(0)){
				r=adder(new String(r),num2,'0',length).substring(1).toCharArray();
			}else {
				r=adder(new String(r),num2_neg,'0',length).substring(1).toCharArray();
			}
		}

		return of+new String(q)+new String(r);
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
		char sign1=operand1.charAt(0),sign2=operand2.charAt(0);
		StringBuffer zerobuffer1=new StringBuffer(),zeroBuffer2=new StringBuffer();
		int bias1=length-operand1.length()+1;
		int bias2=length-operand2.length()+1;
		while(bias1!=0){
			zerobuffer1.append('0');
			bias1--;
		}
		while(bias2!=0){
			zeroBuffer2.append('0');
			bias2--;
		}
		char[] num1=(zerobuffer1+operand1.substring(1)).toCharArray(),num2=(zeroBuffer2+operand2.substring(1)).toCharArray();
		char of='0';
		String ret;
		if(sign1==sign2){
			ret=adder(new String(num1),new String(num2),'0',length);
			if(ret.charAt(0)=='1'){
				of='1';
			}
			ret=sign1+ret.substring(1);
		}else {
			num2=negation(new String(num2)).toCharArray();
			num2=adder(new String(num2),"01",'0',length).substring(1).toCharArray();
			ret=adder(new String(num1),new String(num2),'0',length*2);
			if(ret.charAt(length)=='1'){
				ret=sign2+ret.substring(length+1);
			}else {
				ret=ret.substring(length+1);
				ret=negation(ret);
				ret=adder(ret,"01",'0',length).substring(1);
				ret=(sign1=='1')?("0"+ret):("1"+ret);
			}

		}

		return of+ret;
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
		return null;
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
		return null;
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
		char sign1=operand1.charAt(0),sign2=operand2.charAt(0);
		String exp1=operand1.substring(1,1+eLength);
		String exp2=operand2.substring(1,1+eLength);
		String sig1=getSignificand(operand1,eLength,sLength),sig2=getSignificand(operand2,eLength,sLength);
		int emax=Integer.parseInt(power2(String.valueOf(eLength-1)))-1;
		int exponent1=Integer.parseInt(integerTrueValue("0"+exp1))-emax;
		int exponent2=Integer.parseInt(integerTrueValue("0"+exp2))-emax;
		if(exponent1>exponent2){
			int deltaE=exponent1-exponent2;
			StringBuffer buffer=new StringBuffer(sig2);
			for(int i=0;i<deltaE;i++){
				buffer.insert(0,'0');
			}
			sig2=new String(buffer).substring(0,sLength+1);
			int align4=((sLength+2)/4+1)*4-sLength-1;
			String retSignificand=integerMultiplication("0"+sig1,"0"+sig2,((sLength+2)/4+1)*4).substring(1+1+align4);
			retSignificand=retSignificand.substring(0,sLength+1);
		}
		return null;
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
		return null;
	}




	//tools I write

//	should not be called directly

	private  String getSignificand(String src,int eLength,int sLength){
		char sign=src.charAt(0);
		String exponent=src.substring(1,eLength+1);
		String signif=src.substring(eLength+1);
		String ret;
		if(Integer.parseInt(integerTrueValue("0"+exponent))==0){
			ret="0"+signif;
		}else {
			ret="1"+signif;
		}
		return ret;
	}
	//return exponent:significand
	private String setSignificand(String significand, int eLength,int sLength){
		String retE;
		String retS;
		char[] sig=significand.toCharArray();
		int emax=Integer.parseInt(power2(String.valueOf(eLength-1)))-1;
		int exponent=0;
		for(int i=0;i<sLength;i++){
			if(sig[i]=='1'){
				break;
			}
			exponent++;
		}

		if(exponent==sLength){
			char[] zero=new char[eLength];
			Arrays.fill(zero,'0');
			retE=new String(zero);
			zero=new char[sLength];
			Arrays.fill(zero,'0');
			retS=new String(zero);
		}else{
//			if()
		}
		return null;//retE+":"+retS;
	}


	private static StringBuffer cut(char[] ret){
		for(int i=0,length=ret.length;i<length-2;i++){
			if(ret[i]=='0'&&ret[i+1]=='0'){
				ret[i]=(char)0;
			}else {
				break;
			}
		}
		StringBuffer buffer=new StringBuffer();
		for(int i=0,length=ret.length;i<length;i++){
			if(ret[i]!=(char)0){
				buffer.append(ret[i]);
			}
		}
		return buffer;
	}

	//convert a decimal number to a Binary number
	private static String integerToBinary(String denumber){
		StringBuffer buffer=new StringBuffer();
		String[] tmp;
		while(!"0".equals(denumber)&&!"00".equals(denumber)){
			tmp=divid2(denumber).split(":");
			denumber=new String(cut(tmp[0].toCharArray()));
			buffer.append(tmp[1]);
		}

		//reverse
		char[] temp=new String(buffer).toCharArray();
		buffer.setLength(0);
		for(int i=temp.length-1;i>=0;i--){
			buffer.append(temp[i]);
		}
		return  new String(buffer);
	}

	//convert a decimal decimal to Binary decimal
	private static String decimal2Binary(String decimal,int length){
		char[] ret=new char[length];
		char[] temp;
		Arrays.fill(ret,(char)0);
		temp=decimal.toCharArray();
		for(int i=0;ret[length-1]==0;i++){
			temp=mult2(new String(temp)).toCharArray();
			ret[i]=temp[0];
			if(temp.length>1) {
				temp = Arrays.copyOfRange(temp, 1, temp.length);
			}else {
				temp=new char[1];
				temp[0]='0';
			}
		}
		return new String(ret);
	}

	private static String power2(String n){
		String ret="1";
		while(!"0".equals(n)&&!"00".equals(n)){
			ret=mult2(ret);
			n=sub(n,"1");
		}
		return ret;
	}

	private static String mult2(String s1){
		String s="0"+s1;
		char[] num=s.toCharArray();
		char[] carry=new char[num.length];
		Arrays.fill(carry,'0');
		char[] ret=Arrays.copyOf(carry,num.length);
		int temp;
		for(int i=num.length-1;i>0;i--){
			temp=(num[i]-'0')<<1;
			temp+=carry[i]-'0';
			if(temp>9){
				carry[i-1]='1';
				ret[i]=(char)(temp-10+'0');
			}else{
				ret[i]=(char)(temp+'0');
			}
		}
		ret[0]=(char)(carry[0]+((num[0]-'0')<<1));

//        cut 00000abcd-->0abcd
		StringBuffer buffer=cut(ret);
		return new String(buffer);
	}

	private static String sub(String s1,String s2){
		char[] num1=s1.toCharArray();
		char[] num2=s2.toCharArray();
		char[] temp1;

		//form the correct format to add

		if(num1.length>num2.length){
			int bias=num1.length-num2.length;
			temp1= Arrays.copyOf(num2,num2.length+bias+1);
			//xxxxxxx1234
			while(temp1[temp1.length-1]=='\u0000'){
				for(int i=temp1.length-1;i>0;i--) {
					temp1[i]=temp1[i-1];
				}
			}
			//00000001234
			for(int i=0;i<bias+1;i++){
				temp1[i]='0';
			}
			//01234123512
			num1=Arrays.copyOf(num1,num1.length+1);
			for(int i=num1.length-1;i>0;i--){
				num1[i]=num1[i-1];
			}

			num2=temp1;
			num1[0]='0';

		}else{
			int bias=num2.length-num1.length;
			temp1= Arrays.copyOf(num1,num1.length+bias+1);
			//xxxxxxx1234
			while(temp1[temp1.length-1]=='\u0000'){
				for(int i=temp1.length-1;i>0;i--) {
					temp1[i]=temp1[i-1];
				}
			}
			//00000001234
			for(int i=0;i<bias+1;i++){
				temp1[i]='0';
			}
			//01234123512
			num2=Arrays.copyOf(num2,num2.length+1);
			for(int i=num2.length-1;i>0;i--){
				num2[i]=num2[i-1];
			}
			num1=temp1;
			num2[0]='0';
		}

		//sub
		char temp;
		char[] carry=new char[num1.length];
		char[] ret=new char[num1.length];
		Arrays.fill(carry,(char)0);
		Arrays.fill(ret,'0');

		for(int i=num1.length-1;i>0;i--){
			temp=(char)(num1[i]-num2[i]-carry[i]+'0');
			if(temp<'0'){
				ret[i]=(char)(temp+10);
				carry[i-1]=1;
			}else{
				ret[i]=temp;
			}
		}

		//cut 00000abcd-->0abcd
		StringBuffer buffer=cut(ret);
		return new String(buffer);
	}
	private static String add(String s1,String s2){
		char[] num1=s1.toCharArray();
		char[] num2=s2.toCharArray();
		char[] temp1;
		if(num1.length>num2.length){
			int bias=num1.length-num2.length;
			temp1= Arrays.copyOf(num2,num2.length+bias+1);
			//xxxxxxx1234
			while(temp1[temp1.length-1]=='\u0000'){
				for(int i=temp1.length-1;i>0;i--) {
					temp1[i]=temp1[i-1];
				}
			}
			//00000001234
			for(int i=0;i<bias+1;i++){
				temp1[i]='0';
			}
			//01234123512
			num1=Arrays.copyOf(num1,num1.length+1);
			for(int i=num1.length-1;i>0;i--){
				num1[i]=num1[i-1];
			}
			num2=temp1;
			num1[0]='0';

		}else{
			int bias=num2.length-num1.length;
			temp1= Arrays.copyOf(num1,num1.length+bias+1);
			//xxxxxxx1234
			while(temp1[temp1.length-1]=='\u0000'){
				for(int i=temp1.length-1;i>0;i--) {
					temp1[i]=temp1[i-1];
				}
			}
			//00000001234
			for(int i=0;i<bias+1;i++){
				temp1[i]='0';
			}
			//01234123512
			num2=Arrays.copyOf(num2,num2.length+1);
			for(int i=num2.length-1;i>0;i--){
				num2[i]=num2[i-1];
			}
			num2[0]='0';
			num1=temp1;
		}
		//add
		char[] carry=new char[num1.length];
		char[] ret=new char[num1.length];
		char temp;
		Arrays.fill(carry,'0');
		Arrays.fill(ret,'0');
		for(int i=num1.length-1;i>0;i--){
			temp=(char)(carry[i]+num1[i]+num2[i]-'0'-'0');
			if(temp>'9'){
				ret[i]=(char)(temp-10);
				carry[i-1]='1';
			}else {
				ret[i]=temp;
			}
		}
		ret[0]=(char)(carry[0]+num1[0]+num2[0]-'0'-'0');

		//cut 00000abcd-->0abcd
		StringBuffer buffer=cut(ret);
		return new String(buffer);
	}

	private static String divid2(String s){
		char[] num=s.toCharArray();
		char a,b;
		int temp;
		char[] ret=new char[num.length];
		int rest=0;
		boolean flag=true;//�жϸ�λ�ǲ����Ѿ���������ˣ�����22��2�У���λû�б������������12��2����λ�������ˣ�
		Arrays.fill(ret,'0');
		for(int i=0,len=num.length;i<len-1;i++){
			a=num[i];
			b=num[i+1];
			temp=a+rest*10-0x30;
			if(temp<2){
				i++;
				a-=0x30;
				b-=0x30;
				rest=a*10+b;
				ret[i]=(char)(rest/2+0x30);
				rest%=2;
				if(i==len-1){
					flag=false;
				}
			}else {
				ret[i]=(char)((temp>>1)+'0');
				rest=temp&1;
			}
		}
		if(flag) {
			temp = num[num.length - 1] + rest * 10 - 0x30;
			ret[num.length - 1] = (char) ((temp >> 1) + '0');
			rest=temp&1;
		}

		StringBuffer buffer=new StringBuffer(new String(ret));
		buffer.append(":");
		buffer.append(rest);




		return new String(buffer);
	}
}