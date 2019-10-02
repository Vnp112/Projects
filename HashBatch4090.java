// VASU PATEL cs435 4090 mp

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileInputStream;

public class HashBatch4090{
    static Lexicon newlex;
    public static void main(String[] args) throws IOException
    {
        //new hashbatch instantiation, reading file from arg[0]
        HashBatch(newlex, args[0]);
    }

    public static Lexicon HashCreate (Lexicon L, int m)
    {
        //new hash with T of size m, A of 15m
        Lexicon lexicon1 = new Lexicon(m);
        return lexicon1;
    }
    //Checks if hash is empty
    public boolean HashEmpty (Lexicon L)
    {
        return L.Isempty();
    }

    public boolean HashFull (Lexicon L)
    {
        //Checks if hash is full
        return L.Isfull();
    }

    public static void HashPrint (Lexicon L)
    {
        //Prints out lexicon
        System.out.println(L.toString());
    }

    public static void HashInsert (Lexicon L, String word)
    {
        //inserts a word into the lexicon and T A
        L.Insert(word);
    }

    public static int HashDelete (Lexicon L, String word)
    {

        //deletes word from lexicon, and maybe A
        return L.Delete(word);
    }

    public static int HashSearch (Lexicon L, String word)
    {
        //looks for word in lexicon/T
        return L.Search(word);
    }

    public static void HashBatch (Lexicon L, String filename) throws IOException{
        Scanner scan = new Scanner(new FileReader(filename));
        String[] words = {""};
        words = scan.nextLine().split(" ");
        int m = Integer.parseInt(words[1]);
        newlex = HashCreate(newlex, m);
        while((scan.hasNextLine()))
        {
            String string = scan.nextLine();
            words = string.split(" ");
            switch(words[0])
            {
                case ("10"):
                    HashInsert(newlex, words[1]);
                    break;
                case ("11"):
                    int slotobjdel = HashDelete(newlex, words[1]);
                    System.out.println(words[1] + " \tdeleted from slot " + slotobjdel);
                    break;
                case ("12"):
                    int slotobjfound = HashSearch(newlex, words[1]);
                    if(slotobjfound == -1)
                    {
                        System.out.println(words[1] + " \tnot found");
                    }
                    else
                    {
                        System.out.println(words[1] + " \tfound at slot " + slotobjfound);
                    }
                    break;
                case ("13"):
                    HashPrint(newlex);
                    break;
                case ("14"):
                    HashCreate(newlex, Integer.parseInt(words[1]));
                    break;
                case ("15"):
                    break;
                default:
                    System.out.println("Invalid operation");
                    break;
            }

        }
    }
    public static class Lexicon
    {
        int[] T;
        char[] A;
        int Hashindex;
        int Tlen;
        Lexicon()
        {
            Hashindex = 0;
            Tlen = 0;
        }
        //New lexicon with T of length m and A of 15 times length of m
        public Lexicon(int m)
        {
            T = new int[m];
            A = new char[15*m];

            for(int i=0; i<m; i++)
            {
                T[i] = -100;
            }
        }
        /*

         */
        public int Hash(String m, int n)
        {
            int ascval = 0;
            for(int i=0; i < m.length(); i++)
            {
                ascval += m.charAt(i);
            }
            return (ascval%(T.length) + n*n)%(T.length);
        }

        /*
        Inserts word into the lexicon
         */
        public void Insert(String word)
        {
            for(int i = 0; i < T.length; i++)
            {
                int hashElmt = Hash(word, i);
                if(T[hashElmt] == -100)
                {
                    T[hashElmt] = Hashindex;
                    for(int j=0; j<word.length(); j++)
                    {
                        A[Hashindex] = word.charAt(j);
                        Hashindex++;
                    }
                    Hashindex += 1;
                    A[Hashindex] = 0;
                    Tlen++;
                    break;
                }

            }

        }

        public boolean Isempty()
        {
            return(Tlen == 0);
        }

        public boolean Isfull()
        {
            return(Tlen == T.length);
        }


        public int Search(String word)
        {
            for(int i = 0; i< T.length; i++)
            {
                int ind = Hash(word, i);

                if(T[ind] == -100)
                {
                    continue;
                }
                boolean temp = Arrays.equals(word.toCharArray(),Arrays.copyOfRange(A, T[ind], T[ind]+word.length()));
                boolean equals = temp;
                if(equals)
                {
                    if(A[word.length() + T[ind]] != 0){
                        continue;
                    }
                    return ind;
                }
            }
            return -1;
        }

        public int Delete(String word){

            int wordElem = Search(word);
            int max = word.length() + T[wordElem];
            if(wordElem == -1)
            {
                return -1;
            }

            for(int i = T[wordElem]; i<max; i++)
            {
                A[i] = '*';
            }
            T[wordElem] = -100;
            Tlen--;
            return wordElem;
        }

        public String toString()
        {
            int count = 0;
            String Hashoutput = "";
            Hashoutput += "\tT\t\t\t" + "A: ";
            for(int i = 0; i < A.length; i++)
            {
                if(A[i] == 0)
                {
                    if(A[i-1] == '*')
                    {
                        Hashoutput += "\\";
                        continue;
                    }

                    Hashoutput += "\\";
                    count++;

                    if(count == Tlen)
                    {
                        break;
                    }
                }
                Hashoutput += A[i];

            }
            Hashoutput += "\n";

            for(int i = 0; i< T.length;i++)
            {
                if(T[i] != -100)
                {
                    Hashoutput += i + ": "+T[i] + "\n";
                }
                else
                {
                    Hashoutput += i + ": \n";
                }
            }
            return Hashoutput.toString();
        }
    }

}
