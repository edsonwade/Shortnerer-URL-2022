package mindswap.academy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    static int numberWithMoreOccurency = 0;
    static int maxCount = 0;


    int sum = 0;

    public static void main(String[] args) {
        /* Exercise 1  */
        int numb = 40000;
        int[] sumNumer = new int[]{2, 3, 4, 6, 5, 7, 9, 8};
        List<Integer> integers = Arrays.asList(1, null);
        Map<Integer, Integer> memoizeTable = new HashMap<>(); //

        //     System.out.println("total sum : " + returnTheSumOfParNumber(sumNumer));
//         System.out.println("total sum : " + returnTheSumOfParNumber(integers));
//        System.out.println("total sum using list :" + returnTheSumOfParNumber(integers));

        /*   /* Exercise 2 */
//        int[] number = new int[]{1, 2, 3, 3, 4, 3, 4, 2, 2, 2, 7, 7};
//        System.out.println(returnTheNumberTheOccurMoreTimeInArray(number));
//
        /* Exercixe 3 recursive */
         System.out.println("fibonacci recursive:" + returnTheSumOfAllNumbersFibonacciSequenceRecursive(numb));

//
//        /* Exercixe 3.1 not recursive */
//        System.out.println("Fibonnaci Not Recursive ");
//        for (int i = 1; i <= 10; i++) System.out.print("\t" + returnTheSumOfAllNumbersFibonacciSequenceNotRecursive(i));
    }

    /**
     * exercise 1 ->  faz um metodo que recebe um array de
     * inteiros e retorna a soma dos numeros pares
     */

    public static int returnTheSumOfParNumber(int[] number) {
        int sum = 0;
        for (int i = 0; i < number.length; i++) {
            if ((number[i] % 2) == 0) {
                sum += number[i];
            }
        }
        return sum;
    }

    /**
     * exercise 1.1 ->  faz um metodo que recebe uma lista de
     * * inteiros e retorna a soma dos numeros pares
     */

    public static int returnTheSumOfParNumber(List<Integer> number) {
        return number.stream().filter(numbers -> (numbers % 2) == 0)
                     .mapToInt(numbers -> numbers)
                     .sum();
    }

    /**
     * exercise 2 -> Faz um metodo que recebe
     * uma lista de inteiros e retorna o nº mais frequente
     */
    public static int returnTheNumberTheOccurMoreTimeInArray(int[] number) {
        for (int i = 0; i < number.length; i++) {
            int count = 0;
            for (int j = 0; j < number.length; j++) {
                if (number[i] == number[j]) {
                    count++;
                }
                if (count > maxCount) {
                    numberWithMoreOccurency = number[i];
                    maxCount = count;
                }
            }
        }
        return numberWithMoreOccurency;
    }

    private static Map<Integer,Integer> memo = new HashMap<>();
    /**
     * exercise 3 ->faz um metodo que recebe um inteiro n e
     * retorna a soma de todos os numeros da sequencia
     * de fibonacci ate o numero n (resolver ambas maneiras recursiva e nao recursiva)
     */
    private static int returnTheSumOfAllNumbersFibonacciSequenceRecursive(int numb) {

        if(numb<=1){
            return numb;
        }

        if(memo.containsKey(numb)){
           return  memo.get(numb);
        }

       int result =  returnTheSumOfAllNumbersFibonacciSequenceRecursive(numb -1) + returnTheSumOfAllNumbersFibonacciSequenceRecursive(numb -2);

        memo.put(numb,result);

        System.out.println(numb);

        return result;

    }

    /**
     * exercise 3.1 ->faz um metodo que recebe um inteiro n e
     * retorna a soma de todos os numeros da sequencia
     * de fibonacci ate o numero n (resolver ambas maneiras recursiva e nao recursiva)
     */
    private static int returnTheSumOfAllNumbersFibonacciSequenceNotRecursive(int numb) {
        int nextNumber = 0, previousNumber = 0, previewNumber = 1;

        if (numb == 1) {
            return 0;
        }
        if (numb == 2) {
            return 1;
        }

        for (int i = 3; i <= numb; i++) {
            nextNumber = previousNumber + previewNumber;
            previewNumber = previousNumber;
            previousNumber = nextNumber;
        }
        return nextNumber;
    }


}