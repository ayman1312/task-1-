import java.util.*;

public class MonoalphabeticFrequencyAnalysis {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final double[] ENGLISH_FREQUENCY = {
            8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406,
            6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074
    };

    public static void main(String[] args) {
        String encryptedMessage;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the encrypted message: ");
            encryptedMessage = scanner.nextLine().toUpperCase();
        }
        
        System.out.println("Performing frequency analysis...");
        Map<Character, Double> frequencyMap = analyzeFrequency(encryptedMessage);
        Map<Character, Character> keyMapping = mapFrequencies(frequencyMap);
        String decryptedMessage = decryptWithMapping(encryptedMessage, keyMapping);
        
        System.out.println("Suggested decryption: " + decryptedMessage);
    }
    
    private static Map<Character, Double> analyzeFrequency(String text) {
        Map<Character, Integer> countMap = new HashMap<>();
        int totalLetters = 0;
        
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                countMap.put(c, countMap.getOrDefault(c, 0) + 1);
                totalLetters++;
            }
        }
        
        Map<Character, Double> frequencyMap = new HashMap<>();
        for (char c : countMap.keySet()) {
            frequencyMap.put(c, (countMap.get(c) * 100.0) / totalLetters);
        }
        
        return frequencyMap;
    }
    
    private static Map<Character, Character> mapFrequencies(Map<Character, Double> frequencyMap) {
        List<Character> cipherLetters = new ArrayList<>(frequencyMap.keySet());
        cipherLetters.sort((a, b) -> Double.compare(frequencyMap.get(b), frequencyMap.get(a)));
        
        List<Character> englishLetters = new ArrayList<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            englishLetters.add(ALPHABET.charAt(i));
        }
        englishLetters.sort((a, b) -> Double.compare(ENGLISH_FREQUENCY[ALPHABET.indexOf(b)], ENGLISH_FREQUENCY[ALPHABET.indexOf(a)]));
        
        Map<Character, Character> keyMapping = new HashMap<>();
        for (int i = 0; i < Math.min(cipherLetters.size(), englishLetters.size()); i++) {
            keyMapping.put(cipherLetters.get(i), englishLetters.get(i));
        }
        
        return keyMapping;
    }
    
    private static String decryptWithMapping(String encryptedMessage, Map<Character, Character> keyMapping) {
        StringBuilder decryptedMessage = new StringBuilder();
        for (char c : encryptedMessage.toCharArray()) {
            decryptedMessage.append(keyMapping.getOrDefault(c, c));
        }
        return decryptedMessage.toString();
    }
}
