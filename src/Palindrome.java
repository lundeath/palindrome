import java.util.BitSet;

public class Palindrome {
    private static final int LIMIT = 99999;

    public static void main(String[] args) {
        System.out.println("Processing...");

        long start = System.nanoTime();
        BitSet primes = getPrimesUpTo(LIMIT);
        getPalindrome(primes);
        long finish = System.nanoTime();
        double timeConsumed = (finish-start)/1000000000;
        System.out.println("Time consumed: " + timeConsumed + " seconds");
    }

    //Функция нахождения наибольшего палиндрома
    private static int[] getPalindrome(BitSet primes) {
        long palindrome = 0;
        long multiplier1 = 0;
        long multiplier2 = 0;
        for (int j = 2; j <= LIMIT; j++) {
            for (int k = 2; k <= LIMIT; k++) {
                if (primes.get(j) && primes.get(k)) {
                    long i = (long) j * (long) k;

                    if (palindromeCheck(i)) {
                        if (i > palindrome) {
                            palindrome = i;
                            multiplier1 = j;
                            multiplier2 = k;
                        }
                    }
                }
            }
        }
        System.out.println("palindrome = " + palindrome
                + "\nmultiplier1 = " + multiplier1
                + "\nmultiplier2 = " + multiplier2);
        return null;
    }

    //Проверка на то, является ли число палиндромом
    private static boolean palindromeCheck(long i) {
        char[] palindrome = String.valueOf(i).toCharArray();
        int fromBegin = 0;
        int fromEnd = palindrome.length - 1;
        while (fromBegin < fromEnd) {
            if (palindrome[fromBegin] == palindrome[fromEnd]) {
                fromBegin++;
                fromEnd--;
            } else return false;
        }
        return true;
    }

    //Решето Аткина для нахождения простых чисел
        private static BitSet getPrimesUpTo (int limit) {
            BitSet sieve = new BitSet();
            // Предварительное просеивание
            for (long x2 = 1L, dx2 = 3L; x2 < limit; x2 += dx2, dx2 += 2L)
                for (long y2 = 1L, dy2 = 3L, n; y2 < limit; y2 += dy2, dy2 += 2L) {
                    // n = 4x² + y²
                    n = (x2 << 2L) + y2;
                    if (n <= limit && (n % 12L == 1L || n % 12L == 5L))
                        sieve.flip((int)n);
                    // n = 3x² + y²
                    n -= x2;
                    if (n <= limit && n % 12L == 7L)
                        sieve.flip((int)n);
                    // n = 3x² - y² (при x > y)
                    if (x2 > y2) {
                        n -= y2 << 1L;
                        if (n <= limit && n % 12L == 11L)
                            sieve.flip((int)n);
                    }
                }
            // Все числа, кратные квадратам, помечаются как составные
            int r = 5;
            for (long r2 = r * r, dr2 = (r << 1L) + 1L; r2 < limit; ++r, r2 += dr2, dr2 += 2L)
                if (sieve.get(r))
                    for (long mr2 = r2; mr2 < limit; mr2 += r2)
                        sieve.set((int)mr2, false);
            // Числа 2 и 3 — заведомо простые
            if (limit > 2)
                sieve.set(2, true);
            if (limit > 3)
                sieve.set(3, true);
            return sieve;
    }
}
