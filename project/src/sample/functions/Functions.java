package sample.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Functions {

    //computation of the "Sieve of Eratosthenes"
    public static ArrayList<Integer> func_1(int n){
        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        //creating the array of boolean values all set to true
        ArrayList<Boolean> arrayBooleanList = new ArrayList<Boolean>(Arrays.asList(new Boolean[n-1]));
        Collections.fill(arrayBooleanList, Boolean.TRUE);

        //modifying the boolean array
        for(int i = 2; i <= (int)Math.sqrt(n); i++){
            if(arrayBooleanList.get(i-2)){
                int j = 0;
                int result = (int) Math.pow(i, 2) + j * i;

                while (result <= n){
                    if (arrayBooleanList.get(result - 2)) {
                        arrayBooleanList.set(result - 2, false);
                    }
                    j++;
                    result = (int) Math.pow(i, 2) + j * i;
                }
            }
        }

        //converting the boolena array to values and adding them to the array of int
        for(int i = 0; i < arrayBooleanList.size(); i++){
            if(arrayBooleanList.get(i)){
                arrayList.add(i+2);
            }
        }

        //returning the result
        return arrayList;
    }


    //computing the GDC with the Euclidean Algorithm
    public static int func_2(int a, int b){
        if(b == 0){
            return a;
        }
        else{
            return func_2(b, a%b);
        }
    }

    //computing the number of prime numbers below the input n, using the previous method func_1 and getting the size
    public static int func_3(int n){
        if(n == 1){
            return 0;
        }else{
            return func_1(n).size();
        }
    }

    //copmuting the totien function using the previous method for GDC
    public static int func_4(int n){
        int totFun = 0;

        for(int i = 0; i < n; i++){
            if(func_2(i,n) == 1){
                totFun++;
            }
        }
        return totFun;
    }

    //compute the prime factorization
    public static ArrayList<Integer> func_5(int n){
        ArrayList<Integer> arrayList = new ArrayList<>();

        int result = n;
        int divisor = 2;

        while(divisor < n){
            if(result%divisor == 0){
                arrayList.add(divisor);
                result = result/divisor;
            }
            if(result%divisor != 0){
                divisor ++;
            }
        }

        //if the input is already prime tell that it is prime
        if(arrayList.size() == 0){
            arrayList.add(n);
        }

        return arrayList;
    }

    //computing the sigma function
    public static int func_6(int x, int n){
        int sigma = 0;

        ArrayList<Integer> arrayDivisorsList = divisors(n);

        for (Integer a: arrayDivisorsList) {
            sigma = sigma + (int) Math.pow(a, x);
        }

        return sigma;
    }

    //computing the first n random numbers using "LCG" algorithm
    public static ArrayList<Integer> func_7(int a, int b, int m, int n){

        ArrayList<Integer> arrayList = new ArrayList<>();
        int seed = 0;

        int output = seed;

        for(int i = 0; i < n; i++){
            output = (output*a + b) % m;
            arrayList.add(output);
        }

        return arrayList;
    }


    public static int func_8(int n){
        int result = 0;

        double a = Math.exp(Math.PI * Math.sqrt(2.0*n/3.0));
        double b = 1/(4*n*Math.sqrt(3.0));
        result = (int) (a * b);

        return result;
        //return result;
    }

    //https://math.stackexchange.com/questions/191263/how-can-i-explain-this-integer-partitions-function-recursion
    public static int func_8_b(int sum, int largest){
        if(largest == 0){
            return 0;
        }
        if(sum == 0){
            return 1;
        }
        if(sum < 0){
            return 0;
        }
        return func_8_b(sum, largest-1) + func_8_b(sum-largest, largest);
    }


    //##############################OTHER METHODS######################################

    //compute all the positive divisor of an integer
    public static ArrayList<Integer> divisors(int n){
        ArrayList<Integer> arrayList = new ArrayList<>();

        for(int i = 1; i <= n; i ++){
            if(n%i == 0){
                arrayList.add(i);
            }
        }
        return arrayList;
    }

}
