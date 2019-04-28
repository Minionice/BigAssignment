/* This class is responsible for accepting a string input,
** encrypting it using the RSA algorithm, and then decrypting it.
** Upon encrypting the message input by the user, it displays the
** message both before and after encryption. It then displays the
** message after it has been decrypted.
**
** Authors: Sean McTiernan and Mario Roani
** April 27, 2019
*/

import java.util.Scanner;
import java.math.BigInteger;

public class RSA2 {

    // INSTANCE VARIABLES
    // ==================

    public static BigInteger n, e;
    private static BigInteger p, q, d, phi_n;
    public static final BigInteger ONE = new BigInteger("1");
    public static final BigInteger MINUS_ONE = new BigInteger("-1");
    
    // CONSTRUCTOR
    // ===========
    
    /* An instance of this class initializes two numbers p and q 
    ** to be 200 digit primes. It then calculates all necessary
    ** values to perform RSA encryption (and decryption).
    */
    public RSA2() {
        p = new BigInteger("58021664585639791181184025950440248398226136069516938232493687505822471836536824298822733710342250697"
        + "739996825938232641940670857624514103125986134050997697160127301547995788468137887651823707102007839");
        q = new BigInteger("154748112064865871932586905016824046263613417566588942019082941536260807826937770030225669967357969832"
        + "39343580281979005677758015801189957392350213806122307985157041153484138150252828152419133170303749");
        phi_n = p.subtract(ONE).multiply(q.subtract(ONE));
        e = new BigInteger("9007");
        d = e.modPow(MINUS_ONE, phi_n);
        n = p.multiply(q);
    }

    public static void main(String[] args) {
        String encryptedMsg, decryptedMsg;
        String QUIT = "QUIT";
        Scanner in = new Scanner(System.in);
        String message = in.nextLine();
        RSA2 rsa = new RSA2();
        
        /* Here is an infinite loop so that a user may enter as many 
        ** messages as his/her heart desires. The loop is terminated 
        ** upon the user entering "QUIT".
        */
        while (message.compareTo(QUIT) != 0) {
            if (message.compareTo("") != 0) {
                encryptedMsg = encrypt(message, rsa.e, rsa.n);
                BigInteger mToPowE = new BigInteger(encryptedMsg);
                decryptedMsg = decrypt(mToPowE, rsa.d, rsa.n);

                System.out.println("\nMessage:\n" + message + "\n");
                System.out.println("Encrypted message:\n" + encryptedMsg + "\n");
                System.out.println("Decrypted message: \n" + decryptedMsg + "\n");
                System.out.println("\n------------------------------------------------------------------------------------------------\n\n");
            }
            message = in.nextLine();
        }
        System.out.println("Goodbye.");
    }

    /* A method used to encrypt a desired message 'M', by first converting
    ** the message into relevant numbers based upon the ASCII table (with
    ** slight modifications). The number is then raised to the power 'E'
    ** modulo 'N', as per the RSA algorithm.
    */
    public static String encrypt(String M, BigInteger E, BigInteger N) {
        String msg = convertMessageToNum(M);
        BigInteger msgNum = new BigInteger(msg);
        BigInteger M_ToPow_E = msgNum.modPow(E, N);
        return M_ToPow_E.toString();
    }

    /* This method is responsible for taking the input String 'str' and
    ** converting each character of 'str' into it's ASCII value minus 31.
    ** The subtraction is to account for the characters in the ASCII table 
    ** which would not be valid characters. Characters with ASCII values 
    ** (minus 31) less than 10 have a '0' prepended to their modified ASCII
    ** value (this is to aid in the decryption process).
    */
    public static String convertMessageToNum(String str) {
        String result = "";
        String tmp;
        int a;
        for(int i = 0; i < str.length(); i++) {
            a = str.charAt(i);
            a = a - 31;
            if (a < 10) {
               tmp = "0" + Integer.toString(a);
            }
            else { tmp = Integer.toString(a); }
            result = result + tmp;
        }
        return result;
    }

    /* This method handles the decryption process. Using the RSA algorithm,
    ** the number 'c' is raised to the power 'D' (D is chosen such that 
    ** D*e = 1 (mod phi(n))). The "firstResult" string is used to store the
    ** numeric value of the decrypted message. Then "firstResult" is used in
    ** getting "endResult" by parsing through "firstResult" in sets of two
    ** integers in order to obtain one two-digit number, and then add 31 to
    ** it to get the character matching this ASCII value. 
    */
    public static String decrypt(BigInteger c, BigInteger D, BigInteger N) {
        String firstResult = "";
        String endResult = "";
        String checker = "";
        int test;
        char mar;
        BigInteger c_ToPow_d = c.modPow(D, N);
        String temp = c_ToPow_d.toString();
        
        /* Prepends a "0" to the string if the string has odd length.
        ** This is to aid in the decryption process as each integer pair
        ** must have two digits in order to be correctly converted
        ** back into a character (an odd number length would result
        ** in one value being a single integer as opposed to a pair).
        */
        if((temp.length() % 2) != 0) {
            firstResult = "0" + temp;
        }
        else { firstResult = temp; }
        int j = 1;
        for(int i = 0; i < firstResult.length(); i += 2) {
            checker = firstResult.substring(i, i+2);
            test = Integer.parseInt(checker) + 31;
            mar = (char)test;
            endResult = endResult + mar;
        }
        return endResult;
    }
}