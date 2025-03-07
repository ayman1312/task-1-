import java.util.*;

public class PlayfairCipher {
    private static final int SIZE = 5;
    private static char[][] playfairMatrix;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the keyword: ");
            String keyword = scanner.nextLine().toUpperCase().replaceAll("J", "I");
            
            generatePlayfairMatrix(keyword);
            displayMatrix();
            
            System.out.print("Enter plaintext to encrypt: ");
            String plaintext = scanner.nextLine().toUpperCase().replaceAll("J", "I");
            String encryptedText = encrypt(plaintext);
            System.out.println("Encrypted text: " + encryptedText);
            
            System.out.print("Enter ciphertext to decrypt: ");
            String ciphertext = scanner.nextLine().toUpperCase();
            String decryptedText = decrypt(ciphertext);
            System.out.println("Decrypted text: " + decryptedText);
        }
    }
    
    private static void generatePlayfairMatrix(String keyword) {
        String keyString = keyword + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        LinkedHashSet<Character> charSet = new LinkedHashSet<>();
        for (char c : keyString.toCharArray()) {
            if (Character.isLetter(c)) {
                charSet.add(c);
            }
        }
        
        Iterator<Character> iterator = charSet.iterator();
        playfairMatrix = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                playfairMatrix[i][j] = iterator.next();
            }
        }
    }
    
    private static void displayMatrix() {
        System.out.println("Playfair Matrix:");
        for (char[] row : playfairMatrix) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
    
    private static String encrypt(String text) {
        return processText(text, true);
    }
    
    private static String decrypt(String text) {
        return processText(text, false);
    }
    
    private static String processText(String text, boolean encrypt) {
        text = prepareText(text);
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);
            
            if (posA[0] == posB[0]) { 
                result.append(playfairMatrix[posA[0]][(posA[1] + (encrypt ? 1 : 4)) % SIZE]);
                result.append(playfairMatrix[posB[0]][(posB[1] + (encrypt ? 1 : 4)) % SIZE]);
            } else if (posA[1] == posB[1]) { 
                result.append(playfairMatrix[(posA[0] + (encrypt ? 1 : 4)) % SIZE][posA[1]]);
                result.append(playfairMatrix[(posB[0] + (encrypt ? 1 : 4)) % SIZE][posB[1]]);
            } else { 
                result.append(playfairMatrix[posA[0]][posB[1]]);
                result.append(playfairMatrix[posB[0]][posA[1]]);
            }
        }
        return result.toString();
    }
    
    private static String prepareText(String text) {
        text = text.replaceAll("[^A-Z]", "");
        StringBuilder prepared = new StringBuilder();
        
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (prepared.length() % 2 == 1 && prepared.charAt(prepared.length() - 1) == c) {
                prepared.append('X'); 
            }
            prepared.append(c);
        }
        if (prepared.length() % 2 == 1) {
            prepared.append('X'); 
        }
        return prepared.toString();
    }
    
    private static int[] findPosition(char c) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (playfairMatrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}