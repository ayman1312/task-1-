package monoalphabetic.cipher;

import java.util.*;

public class MonoalphabeticBruteForce {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public static void main(String[] args) {
        String encryptedMessage;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the encrypted message: ");
            encryptedMessage = scanner.nextLine().toUpperCase();
        }
        
        System.out.println("Attempting to brute-force decrypt...");
        bruteForceDecrypt(encryptedMessage);
    }

    private static void bruteForceDecrypt(String encryptedMessage) {
        List<String> permutations = generatePermutations(ALPHABET);
        
        permutations.forEach((key) -> {
            String decrypted = decryptWithKey(encryptedMessage, key);
            System.out.println("Key: " + key + " => " + decrypted);
        });
    }

    private static List<String> generatePermutations(String str) {
        List<String> permutations = new ArrayList<>();
        permute(str.toCharArray(), 0, permutations);
        return permutations;
    }

    private static void permute(char[] arr, int k, List<String> permutations) {
        if (k == arr.length) {
            permutations.add(new String(arr));
        } else {
            for (int i = k; i < arr.length; i++) {
                swap(arr, i, k);
                permute(arr, k + 1, permutations);
                swap(arr, i, k);
            }
        }
    }

    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static String decryptWithKey(String encryptedMessage, String key) {
        Map<Character, Character> substitutionMap = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            substitutionMap.put(key.charAt(i), ALPHABET.charAt(i));
        }
        
        StringBuilder decryptedMessage = new StringBuilder();
        for (char c : encryptedMessage.toCharArray()) {
            decryptedMessage.append(substitutionMap.getOrDefault(c, c));
        }
        return decryptedMessage.toString();
    }
}

