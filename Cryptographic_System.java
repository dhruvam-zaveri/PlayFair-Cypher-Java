import java.util.Scanner;
import java.lang.Math;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.io.*;

class Encrypt{
    public String removeWhiteSpace(String plaintext)
    {
        plaintext = plaintext.replaceAll("\\s", "");
        return plaintext;
    }

    public String removeRepeatedCharacter(String input)
    {
        String output = new String();
        int c = 2;
        output = output + input.charAt(0);
        for (int i = 0; i < input.length(); i++) 
        {
            for (int j = 0; j < output.length(); j++) 
            {
                if (input.charAt(i) == output.charAt(j)) 
                {
                    c = 1;
                    break;
                }
                else
                {
                    c = 0;
                }
            }
            if(c == 0)
            {
                output = output + input.charAt(i);
            }
        }
        return output;
    }

    public String playFair(String plaintext)
    {
        String ciphertext = new String();
        Scanner s = new Scanner(System.in);
        String key;

        int i,j,t,c = 2;//loop and conditional variable

        int x,y,z;//temp variables

        char en_block[][] = new char[5][5];//key block
        char bn_block[][] = {{'a','b','c','d','e'},{'f','g','h','i','k'},{'l','m','n','o','p'},{'q','r','s','t','u'},{'v','w','x','y','z'}};
        //abcd block

        char cipher[] = new char[2];

        plaintext = removeWhiteSpace(plaintext);
        plaintext = plaintext.toLowerCase();
        plaintext = plaintext.replaceAll("j","i");

        System.out.println("Enter the key: ");
        key = s.nextLine();

        key = removeWhiteSpace(key);
        key = key.toLowerCase();
        key = removeRepeatedCharacter(key);

        //inserting key in keyblock
        for(i = 0;i < key.length();i++)
        {
            t = i % 5;
            j = i / 5;
            en_block[j][t] = key.charAt(i); 
        }

        //preparing key block after inserting key in block
        z = key.length();
        y = z % 5;
        x = z / 5;
        for(i = 0;i < 5;i++)
        {
            for(j = 0;j < 5;j++)
            {
                c = 1;
                for(t = 0;t < z;t++)
                {
                    if(bn_block[i][j] == key.charAt(t))
                    {
                        c = 0;
                    }
                }
                if(c == 1)
                {
                    en_block[x][y] = bn_block[i][j];
                    y++;
                    x = x + (y / 5);
                    y = y % 5;
                }
            }
        }


        int a = 6,b = 6;//rows and cols for first char initializatin 6 is just for reference
        int e = 6,d = 6;//rows and cols for second char
        for(i = 0;i < plaintext.length();i += 2)
        {
            cipher[0] = plaintext.charAt(i);
            if(i+1 >= plaintext.length())
            {
                plaintext += 'x';
            }
            cipher[1] = plaintext.charAt(i+1);
            
            //taking dummy character for repeating characters
            if(cipher[0] == cipher[1] && cipher[0] != x)
            {
                cipher[1] = 'x';
                i--;
            }

            for(x = 0;x < 5;x++)
            {
                for(y = 0;y < 5;y++)
                {
                    if(cipher[0] == en_block[x][y])
                    {
                        a = x;//rows of 1 ch
                        b = y;//cols of 1 ch
                    }
                }
            }

            for(x = 0;x < 5;x++)
            {
                for(y = 0;y < 5;y++)
                {
                    if(cipher[1] == en_block[x][y])
                    {
                        d = x;//rows of 1 ch
                        e = y;//cols of 1 ch
                    }
                }
            }

            //same row
            if(d == a)
            {
                b = (b + 1) % 5;
                e = (e + 1) % 5;

                //taking next char in same row in cyclic manner
                cipher[0] = en_block[a][b];
                cipher[1] = en_block[d][e];
            }

            //same column
            else if(e == b)
            {
                a = (a + 1) % 5;
                d = (d + 1) % 5;

                //taking next char in same column in cyclic manner
                cipher[0] = en_block[a][b];
                cipher[1] = en_block[d][e];
            }

            else
            {
                //taking char of same row and column of second char in keyblock
                cipher[0] = en_block[a][e];
                cipher[1] = en_block[d][b];
            }

            //inserting pair of ciphered char into string 
            ciphertext = ciphertext +cipher[0];
            ciphertext = ciphertext +cipher[1];
        }

        s.close(); 
        return ciphertext;     
    }
}

class Decrypt
{
    public String removeWhiteSpace(String plaintext)
    {
        plaintext = plaintext.replaceAll("\\s", "");
        return plaintext;
    }

    public String removeRepeatedCharacter(String input)
    {
        String output = new String();
        int c = 2;
        output = output + input.charAt(0);
        for (int i = 0; i < input.length(); i++) 
        {
            for (int j = 0; j < output.length(); j++) 
            {
                if (input.charAt(i) == output.charAt(j)) 
                {
                    c = 1;
                    break;
                }
                else
                {
                    c = 0;
                }
            }
            if(c == 0)
            {
                output = output + input.charAt(i);
            }
        }
        return output;
    }

    public String playFair(String ciphertext)
    {
        String plaintext = new String();
        Scanner s = new Scanner(System.in);
        String key;

        int i,j,t,c = 2;//loop and conditional variable
        int x,y,z;//temp variables
        char en_block[][] = new char[5][5];//key block
        char bn_block[][] = {{'a','b','c','d','e'},{'f','g','h','i','k'},{'l','m','n','o','p'},{'q','r','s','t','u'},{'v','w','x','y','z'}};
        //abcd block

        char plain[] = new char[2];

        System.out.println("Enter the key: ");
        key = s.nextLine();
        key = removeWhiteSpace(key);
        key = key.toLowerCase();
        key = removeRepeatedCharacter(key);

        //inserting key in keyblock
        for(i = 0;i < key.length();i++)
        {
            t = i % 5;
            j = i / 5;
            en_block[j][t] = key.charAt(i); 
        }

        //preparing key block after inserting key in block
        z = key.length();
        y = z % 5;
        x = z / 5;
        for(i = 0;i < 5;i++)
        {
            for(j = 0;j < 5;j++)
            {
                c = 1;
                for(t = 0;t < z;t++)
                {
                    if(bn_block[i][j] == key.charAt(t))
                    {
                        c = 0;
                    }
                }
                if(c == 1)
                {
                    en_block[x][y] = bn_block[i][j];
                    y++;
                    x = x + (y / 5);
                    y = y % 5;
                }
            }
        }

        
        int a = 6,b = 6;//rows and cols for first char initializatin 6 is just for reference
        int e = 6,d = 6;//rows and cols for second char
        for(i = 0;i < ciphertext.length();i += 2)
        {
            plain[0] = ciphertext.charAt(i);
            plain[1] = ciphertext.charAt(i+1);

            for(x = 0;x < 5;x++)
            {
                for(y = 0;y < 5;y++)
                {
                    if(plain[0] == en_block[x][y])
                    {
                        a = x;//rows of 1 ch
                        b = y;//cols of 1 ch
                    }
                }
            }

            for(x = 0;x < 5;x++)
            {
                for(y = 0;y < 5;y++)
                {
                    if(plain[1] == en_block[x][y])
                    {
                        d = x;//rows of 1 ch
                        e = y;//cols of 1 ch
                    }
                }
            }

            //same row
            if(d == a)
            {
                b = (b - 1) % 5;
                e = (e - 1) % 5;

                if(b < 0)
                {
                    b += 5;
                }

                if(e < 0)
                {
                    e += 5;
                }

                //taking next char in same row in cyclic manner
                plain[0] = en_block[a][b];
                plain[1] = en_block[d][e];
            }

            //same column
            else if(e == b)
            {
                a = (a - 1) % 5;
                d = (d - 1) % 5;

                if(a < 0)
                {
                    a += 5;
                }

                if(d < 0)
                {
                    d += 5;
                }
                //taking next char in same column in cyclic manner
                plain[0] = en_block[a][b];
                plain[1] = en_block[d][e];
            }

            else
            {
                //taking char of same row and column of second char in keyblock
                plain[0] = en_block[a][e];
                plain[1] = en_block[d][b];
            }

            //inserting pair of ciphered char into string 
            plaintext = plaintext + plain[0];
            plaintext = plaintext + plain[1];
        }

        s.close(); 
        return plaintext;
    }
}

class Cryptographic_System {
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public static void main(String args[]) {
        String plaintext = new String();
        String ciphertext = new String();

        int option;
        int a;// switching variables

        Encrypt e = new Encrypt();// Objects of diffrent class
        Decrypt d = new Decrypt();
        Scanner s = new Scanner(System.in);

    String s3 = "Murtaza";//required user name
    System.out.print("Enter UserName: ");
    String s2 = s.nextLine();
    while(s2.equals(s3) != true)//loop until valid username is printed
    {
        System.out.println("!!Enter valid UserName!!");
        s2 = s.nextLine();
    }

    File pass = new File("hashValue.txt");//opening file which has hash value of password
    try {
        Scanner sn = new Scanner(pass);
        s3 = sn.nextLine();//scanning hash value of password and storing in string
        sn.close();
    } catch (FileNotFoundException e1) {
        e1.printStackTrace();
    }
    
    System.out.print("\nEnter Password: ");
    String s1 = s.nextLine();//scanning password
    try{
            s1 = toHexString(getSHA(s1));//converting password to hash value
        }
    catch (NoSuchAlgorithmException l) {  
            System.out.println("Exception thrown for incorrect algorithm: " + l);  
        }
        if(s1.equals(s3) != true)
        {
            System.out.println("!!Entered Password is wrong!!");
        } 
    while(s1.equals(s3) != true)
    {
        System.out.println("Enter Password:");
        s1 = s.nextLine();//scanning password
        try{
            s1 = toHexString(getSHA(s1));//converting password to hash value
        }
        catch (NoSuchAlgorithmException l) {  
            System.out.println("Exception thrown for incorrect algorithm: " + l);  
        } 
        if(s1.equals(s3) != true)
        {
            System.out.println("!!Entered Password is wrong!!");
        }
    }


    System.out.println("\nSelect the algortihm:");
    System.out.println("1 - PlayFair Cipher");
    option = s.nextInt();
	
    switch(option){
        case 1:
        System.out.println("\nChoose Option:");
        System.out.println("1 - Encrypt Message");
        System.out.println("2 - Decrypt Message");

        a = s.nextInt();
            switch(a)
            {
                case 1:
                System.out.println("\nEnter your message to encrypt:");
                plaintext = s.nextLine();
                plaintext = s.nextLine();

                ciphertext = e.playFair(plaintext);

                System.out.println(ciphertext);
                break;

                case 2:
                System.out.println("Enter your message to decrypt:");
                ciphertext = s.nextLine();
                ciphertext = s.nextLine();

                plaintext = d.playFair(ciphertext);

                System.out.println(plaintext);
                break;
            }
        break;
    }
    s.close();
    }
}