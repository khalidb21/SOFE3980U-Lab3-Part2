package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 *
 */
public class Binary
{
	private String number="0";  // string containing the binary value '0' or '1'
	/**
	* A constructor that generates a binary object.
	*
	* @param number a String of the binary values. It should contain only zeros or ones with any length and order. otherwise, the value of "0" will be stored.   Trailing zeros will be excluded and empty string will be considered as zero.
	*/
	public Binary(String number) {
		if (number == null || number.isEmpty()) {
			this.number = "0"; // Default to "0" for null or empty input
			return;
		}
	
		// Validate the binary string (only '0' or '1' allowed)
		for (int i = 0; i < number.length(); i++) {
			char ch = number.charAt(i);
			if (ch != '0' && ch != '1') {
				this.number = "0"; // Default to "0" for invalid input
				return;
			}
		}
	
		// Remove leading zeros
		int beg;
		for (beg = 0; beg < number.length(); beg++) {
			if (number.charAt(beg) != '0') {
				break;
			}
		}
	
		// If all digits are '0', ensure number is "0"
		this.number = (beg == number.length()) ? "0" : number.substring(beg);
	
		// uncomment the following code

		if (this.number.isEmpty()) { // replace empty strings with a single zero
			this.number = "0";
		}
	}
	/**
	* Return the binary value of the variable
	*
	* @return the binary value in a string format.
	*/
	public String getValue()
	{
		return this.number;
	}
	/**
	* Adding two binary variables. For more information, visit <a href="https://www.wikihow.com/Add-Binary-Numbers"> Add-Binary-Numbers </a>.
	*
	* @param num1 The first addend object
	* @param num2 The second addend object
	* @return A binary variable with a value of <i>num1+num2</i>.
	*/
	public static Binary add(Binary num1,Binary num2)
	{
		// the index of the first digit of each number
		int ind1=num1.number.length()-1;
		int ind2=num2.number.length()-1;
		//initial variable
		int carry=0;
		String num3="";  // the binary value of the sum
		while(ind1>=0 ||  ind2>=0 || carry!=0) // loop until all digits are processed
		{
			int sum=carry; // previous carry
			if(ind1>=0){ // if num1 has a digit to add
				sum += (num1.number.charAt(ind1)=='1')? 1:0; // convert the digit to int and add it to sum
				ind1--; // update ind1
			}
			if(ind2>=0){ // if num2 has a digit to add
				sum += (num2.number.charAt(ind2)=='1')? 1:0; // convert the digit to int and add it to sum
				ind2--; //update ind2
			}
			carry=sum/2; // the new carry
			sum=sum%2;  // the resultant digit
			num3 =( (sum==0)? "0":"1")+num3; //convert sum to string and append it to num3
		}
		Binary result=new Binary(num3);  // create a binary object with the calculated value.
		return result;
		
	}

	/**
	* Bitwise Logical OR operation for two binary variables. Peformed for each bit of the two numbers, the result is 1 if at least one of the bits is 1, otherwise it is 0. 
	* For more information, visit <a href="https://www.geeksforgeeks.org/digital-logic/basics-of-boolean-algebra-in-digital-electronics/"> this link </a>.
	*
	* @param num1 The first operand object
	* @param num2 The second operand object	
	* @return A binary variable with a value of <i>num1||num2</i>.
	*/
	public static Binary bitwiseOR(Binary num1,Binary num2) {
		int i = num1.number.length() - 1;
		int j = num2.number.length() - 1;

		String result = "";

		// Process bits from right (LSB) to left (MSB)
		while (i >= 0 || j >= 0)
		{
			// Get current bits, if index is out of range, consider it as '0'
			char bit1 = (i >= 0) ? num1.number.charAt(i) : '0';
			char bit2 = (j >= 0) ? num2.number.charAt(j) : '0';

			// OR operation
			char resultBit = (bit1 == '1' || bit2 == '1') ? '1' : '0';

			// Append to a result string
			result = resultBit + result;

			// Move to the next bit
			i--;
			j--;
		}

    	return new Binary(result);
	}

	/**
	* Bitwise Logical AND operation for two binary variables. Peformed for each bit of the two numbers, the result is 1 if both bits are 1, otherwise it is 0.
	* For more information, visit <a href="https://www.geeksforgeeks.org/digital-logic/basics-of-boolean-algebra-in-digital-electronics/"> this link </a>.
	*
	* @param num1 The first operand object
	* @param num2 The second operand object	
	* @return A binary variable with a value of <i>num1&amp;&amp;num2</i>.
	*/
	public static Binary bitwiseAND(Binary num1,Binary num2) {
		int i = num1.number.length() - 1;
		int j = num2.number.length() - 1;

		String result = "";

		// Process bits from right (LSB) to left (MSB)
		while (i >= 0 || j >= 0)
		{
			// Get current bits, if index is out of range, consider it as '0'
			char bit1 = (i >= 0) ? num1.number.charAt(i) : '0';
			char bit2 = (j >= 0) ? num2.number.charAt(j) : '0';

			// AND operation
			char resultBit = (bit1 == '1' && bit2 == '1') ? '1' : '0';

			// Append to a result string
			result = resultBit + result;
			
			// Move to the next bit 
			i--;
			j--;
		}

		return new Binary(result);
	}

	/**
	* Multiplication operation for two binary variables. Can be performed the same way as multiplication for decimal values, except base is now 2.
	* For more information, visit <a href="https://www.geeksforgeeks.org/maths/binary-multiplication/"> this link </a>.
	*
	* @param num1 The multiplicand object
	* @param num2 The multiplier object
	* @return A binary variable with a value of <i>num1*num2</i>.
	*/
	public static Binary multiply(Binary num1, Binary num2) {
		
		// Variable to store result
		Binary result = new Binary("0");
		
		// use int variable to track digit shift
		int shift = 0;

		// Perform binary multiplication (reference link provided)
		// traverse binary num2 from right to left (LSB to MSB)
		for (int i = num2.number.length() - 1; i >= 0; i--)
		{
			// Check if multiplier digit is 1, if 0 skip
			if (num2.number.charAt(i) == '1')
			{
				// Shift num1 left by appending zeros for the current position of the multiplier digit (2^shift)
				String shifted = num1.number;
				for (int j = 0; j < shift; j++)
				{
					shifted += "0";
				}

				// Uses already implement adding method and add shifted value to result
				result = add(result, new Binary(shifted));
			}
			// increment shift for the next digit in the multiplier
			shift++;
		}

		return result;
	}
}	