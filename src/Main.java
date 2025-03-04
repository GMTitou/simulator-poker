import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le simulatreur de poker!");
        Scanner scanner = new Scanner(System.in);
        List<Card> cardsFirstJoueur = new ArrayList<Card>();
        List<Card> cardsSecondsJoueur = new ArrayList<Card>();
        System.out.println("Veuillez choisir les carte du Joueur 1");
        cardsFirstJoueur = choisirCartesEnUneFois(scanner, null);
        System.out.println("\nVoici les cartes que vous avez choisies:");
        for (Card card : cardsFirstJoueur) {
            System.out.println(card.getNumber() + " de " + card.getColor());
        }
        System.out.println("\nVeuillez maintenant choisir les carte du Joueur 2");
        cardsSecondsJoueur = choisirCartesEnUneFois(scanner, cardsFirstJoueur);
        System.out.println("\nVoici les cartes que vous avez choisies:");
        for (Card card : cardsSecondsJoueur) {
            System.out.println(card.getNumber() + " de " + card.getColor());
        }
        scanner.close();

        calculatorPoint(cardsFirstJoueur, cardsSecondsJoueur);
    }

    public static void calculatorPoint(List<Card> cardsFirstJoueur, List<Card> cardsSecondJoueur) {
        AtomicInteger pointsFirstJoueur = new AtomicInteger();
        AtomicInteger pointsSecondJoueur = new AtomicInteger();

        Card highestCardFirstJoueur = cardsFirstJoueur.stream()
                .max(Comparator.comparingInt(Card::getNumber))
                .orElse(null);

        Card highestCardSecondJoueur = cardsSecondJoueur.stream()
                .max(Comparator.comparingInt(Card::getNumber))
                .orElse(null);

        if (highestCardFirstJoueur.getNumber() > highestCardSecondJoueur.getNumber()) {
            pointsFirstJoueur.set(1);
            System.out.println("Premier joueur a la carte la plus haute: " + highestCardFirstJoueur.getNumber());
        } else if (highestCardFirstJoueur.getNumber() < highestCardSecondJoueur.getNumber()) {
            pointsSecondJoueur.set(1);
            System.out.println("Deuxième joueur a la carte la plus haute: " + highestCardSecondJoueur.getNumber());
        }

        Map<Integer, Long> countFirstJoueur = cardsFirstJoueur.stream()
                .collect(Collectors.groupingBy(Card::getNumber, Collectors.counting()));

        Map<Integer, Long> countSecondJoueur = cardsSecondJoueur.stream()
                .collect(Collectors.groupingBy(Card::getNumber, Collectors.counting()));

        List<Integer> triplesFirstJoueur = countFirstJoueur.entrySet().stream()
                .filter(entry -> entry.getValue() == 3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Integer> triplesSecondJoueur = countSecondJoueur.entrySet().stream()
                .filter(entry -> entry.getValue() == 3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Integer> carreFirstJoueur = countFirstJoueur.entrySet().stream()
                .filter(entry -> entry.getValue() == 4)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Integer> carreSecondJoueur = countSecondJoueur.entrySet().stream()
                .filter(entry -> entry.getValue() == 4)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Integer> pairsFirstJoueur = countFirstJoueur.entrySet().stream()
                .filter(entry -> entry.getValue() == 2 && !triplesFirstJoueur.contains(entry.getKey()) && !carreFirstJoueur.contains(entry.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Integer> pairsSecondJoueur = countSecondJoueur.entrySet().stream()
                .filter(entry -> entry.getValue() == 2 && !triplesSecondJoueur.contains(entry.getKey()) && !carreSecondJoueur.contains(entry.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        boolean isSameColorFirstJoueur = cardsFirstJoueur.stream()
                .allMatch(card -> card.getColor() == cardsFirstJoueur.get(0).getColor());

        boolean isSameColorSecondJoueur = cardsSecondJoueur.stream()
                .allMatch(card -> card.getColor() == cardsSecondJoueur.get(0).getColor());

        boolean isStraightFlushFirstJoueur = isSameColorFirstJoueur && isSequential(cardsFirstJoueur);
        boolean isStraightFlushSecondJoueur = isSameColorSecondJoueur && isSequential(cardsSecondJoueur);

        boolean isRoyalFlushFirstJoueur = isRoyalFlush(cardsFirstJoueur);
        boolean isRoyalFlushSecondJoueur = isRoyalFlush(cardsSecondJoueur);

        if (isRoyalFlushFirstJoueur) {
            pointsFirstJoueur.set(10);
            System.out.println("Premier joueur a une quinte flush royale: " + cardsFirstJoueur);
        }

        if (isRoyalFlushSecondJoueur) {
            pointsSecondJoueur.set(10);
            System.out.println("Deuxième joueur a une quinte flush royale: " + cardsSecondJoueur);
        }

        if (isStraightFlushFirstJoueur && !isRoyalFlushFirstJoueur) {
            pointsFirstJoueur.set(9);
            System.out.println("Premier joueur a une quinte flush: " + cardsFirstJoueur);
        }

        if (isStraightFlushSecondJoueur && !isRoyalFlushSecondJoueur) {
            pointsSecondJoueur.set(9);
            System.out.println("Deuxième joueur a une quinte flush: " + cardsSecondJoueur);
        }


        if (!isStraightFlushFirstJoueur && isSequential(cardsFirstJoueur)) {
            pointsFirstJoueur.set(8);
            System.out.println("Premier joueur a une suite (quinte): " + cardsFirstJoueur);
        }

        if (!isStraightFlushSecondJoueur && isSequential(cardsSecondJoueur)) {
            pointsSecondJoueur.set(8);
            System.out.println("Deuxième joueur a une suite (quinte): " + cardsSecondJoueur);
        }


        if (!carreFirstJoueur.isEmpty()) {
            pointsFirstJoueur.set(7);
            System.out.println("Premier joueur possède un carré: " + carreFirstJoueur);
        }

        if (!carreSecondJoueur.isEmpty()) {
            pointsSecondJoueur.set(7);
            System.out.println("Deuxième joueur possède un carré: " + carreSecondJoueur);
        }


        if (pairsFirstJoueur.size() == 2) {
            pointsFirstJoueur.set(4);
            System.out.println("Premier joueur possède deux paires: " + pairsFirstJoueur);
        } else if (!pairsFirstJoueur.isEmpty()) {
            pointsFirstJoueur.set(2);
            System.out.println("Premier joueur possède une paire: " + pairsFirstJoueur);
        }

        if (pairsSecondJoueur.size() == 2) {
            pointsSecondJoueur.set(4);
            System.out.println("Deuxième joueur possède deux paires: " + pairsSecondJoueur);
        } else if (!pairsSecondJoueur.isEmpty()) {
            pointsSecondJoueur.set(2);
            System.out.println("Deuxième joueur possède une paire: " + pairsSecondJoueur);
        }

        if (!triplesFirstJoueur.isEmpty()) {
            pointsFirstJoueur.set(3);
            System.out.println("Premier joueur possède un triple: " + triplesFirstJoueur);
        }

        if (!triplesSecondJoueur.isEmpty()) {
            pointsSecondJoueur.set(3);
            System.out.println("Deuxième joueur possède un triple: " + triplesSecondJoueur);
        }


        if (isSameColorFirstJoueur && !isStraightFlushFirstJoueur) {
            pointsFirstJoueur.set(5);
            System.out.println("Premier joueur a une couleur: " + cardsFirstJoueur.get(0).getColor());
        }

        if (isSameColorSecondJoueur && !isStraightFlushSecondJoueur) {
            pointsSecondJoueur.set(5);
            System.out.println("Deuxième joueur a une couleur: " + cardsSecondJoueur.get(0).getColor());
        }


        System.out.println("Points du premier joueur: " + pointsFirstJoueur);
        System.out.println("Points du deuxième joueur: " + pointsSecondJoueur);
    }

    private static boolean isRoyalFlush(List<Card> cards) {
        List<Integer> royalFlushSequence = Arrays.asList(10, 11, 12, 13, 1);
        List<Integer> sortedCards = cards.stream()
                .map(Card::getNumber)
                .sorted()
                .toList();

        if (sortedCards.size() != 5) {
            return false;
        }

        return sortedCards.containsAll(royalFlushSequence);
    }


    private static boolean isSequential(List<Card> cards) {
        List<Integer> sortedCards = cards.stream()
                .map(Card::getNumber)
                .sorted()
                .collect(Collectors.toList());

        for (int i = 1; i < sortedCards.size(); i++) {
            if (sortedCards.get(i) != sortedCards.get(i - 1) + 1) {
                return false;
            }
        }
        return true;
    }

    public static List<Card> choisirCartesEnUneFois(Scanner scanner, List<Card> cartesAEviter) {
    List<Card> cartesChoisies = new ArrayList<>();

    System.out.println("Veuillez choisir 5 cartes en une seule saisie.");
    System.out.println("Format: [numero][couleur] [numero][couleur] ...");
    System.out.println("Numéro: 1 (As) à 13 (Roi)");
    System.out.println("Couleur: COEUR, CARREAU, PIQUE, TREFLE");
    System.out.println("Exemple: 1COEUR 10PIQUE 2CARREAU 5TREFLE 13COEUR");

    if (cartesAEviter != null && !cartesAEviter.isEmpty()) {
        System.out.println("\nAttention: Les cartes suivantes sont déjà prises et ne peuvent pas être choisies:");
        for (Card card : cartesAEviter) {
            System.out.println("- " + card.getNumber() + " de " + card.getColor());
        }
    }

    boolean saisieValide = false;

    while (!saisieValide) {
        System.out.print("Votre main: ");
        String input = scanner.nextLine().trim();
        String[] cartesStr = input.split("\\s+");

        if (cartesStr.length != 5) {
            System.out.println("Erreur: Vous devez saisir exactement 5 cartes.");
            continue;
        }

        List<Card> cartesTmp = new ArrayList<>();
        boolean erreurDansMain = false;

        for (String carteStr : cartesStr) {
            try {
                int i = 0;
                while (i < carteStr.length() && Character.isDigit(carteStr.charAt(i))) {
                    i++;
                }

                if (i == 0 || i == carteStr.length()) {
                    throw new IllegalArgumentException("Format incorrect pour " + carteStr);
                }

                int number = Integer.parseInt(carteStr.substring(0, i));
                String colorStr = carteStr.substring(i);

                if (number < 1 || number > 13) {
                    throw new IllegalArgumentException("Le numéro doit être entre 1 (As) et 13 (Roi)");
                }

                Color color;
                try {
                    color = Color.valueOf(colorStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Couleur invalide: " + colorStr);
                }

                Card nouvelleCarte = new Card(number, color);

                for (Card c : cartesTmp) {
                    if (c.getNumber() == number && c.getColor() == color) {
                        throw new IllegalArgumentException("Carte en double: " + nouvelleCarte);
                    }
                }

                if (cartesAEviter != null) {
                    for (Card c : cartesAEviter) {
                        if (c.getNumber() == number && c.getColor() == color) {
                            throw new IllegalArgumentException("Cette carte est déjà prise par un autre joueur: " + nouvelleCarte);
                        }
                    }
                }

                cartesTmp.add(nouvelleCarte);

            } catch (NumberFormatException e) {
                System.out.println("Erreur: Numéro de carte invalide dans " + carteStr);
                erreurDansMain = true;
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage());
                erreurDansMain = true;
                break;
            }
        }

        if (!erreurDansMain) {
            cartesChoisies = cartesTmp;
            saisieValide = true;
        }
    }

    return cartesChoisies;
    }
}