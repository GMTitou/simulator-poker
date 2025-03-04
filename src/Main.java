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

        List<Integer> pairsFirstJoueur = countFirstJoueur.entrySet().stream()
                .filter(entry -> entry.getValue() == 2 && !triplesFirstJoueur.contains(entry.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Integer> pairsSecondJoueur = countSecondJoueur.entrySet().stream()
                .filter(entry -> entry.getValue() == 2 && !triplesSecondJoueur.contains(entry.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (pairsFirstJoueur.size() == 2) {
            pointsFirstJoueur.set(4);
            System.out.println("Premier joueur possède deux paires: " + pairsFirstJoueur);
        } else if (!pairsFirstJoueur.isEmpty()) {
            pointsFirstJoueur.set(2);
            System.out.println("Premier joueur possède une paire: " + pairsFirstJoueur);
        } else {
            System.out.println("Premier joueur ne possède pas de paire.");
        }

        if (pairsSecondJoueur.size() == 2) {
            pointsSecondJoueur.set(4);
            System.out.println("Deuxième joueur possède deux paires: " + pairsSecondJoueur);
        } else if (!pairsSecondJoueur.isEmpty()) {
            pointsSecondJoueur.set(2);
            System.out.println("Deuxième joueur possède une paire: " + pairsSecondJoueur);
        } else {
            System.out.println("Deuxième joueur ne possède pas de paire.");
        }

        if (!triplesFirstJoueur.isEmpty()) {
            pointsFirstJoueur.set(3);
            System.out.println("Premier joueur possède un triple: " + triplesFirstJoueur);
        }

        if (!triplesSecondJoueur.isEmpty()) {
            pointsSecondJoueur.set(3);
            System.out.println("Deuxième joueur possède un triple: " + triplesSecondJoueur);
        }

        System.out.println("Points du premier joueur: " + pointsFirstJoueur);
        System.out.println("Points du deuxième joueur: " + pointsSecondJoueur);
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