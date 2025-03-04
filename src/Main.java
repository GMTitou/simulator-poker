import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        System.out.println("Bienvenue dans le simulateur de poker!");

        List<Card> cards = new ArrayList<Card>();

        cards = choisirCartesEnUneFois();

        System.out.println("\nVoici les cartes que vous avez choisies:");
        for (Card card : cards) {
            System.out.println(card.getNumber() + " de " + card.getColor());
        }
    }

    public static List<Card> choisirCartesEnUneFois() {
        List<Card> cartesChoisies = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez choisir 5 cartes en une seule saisie.");
        System.out.println("Format: [numero][couleur] [numero][couleur] ...");
        System.out.println("Numéro: 1 (As) à 13 (Roi)");
        System.out.println("Couleur: COEUR, CARREAU, PIQUE, TREFLE");
        System.out.println("Exemple: 1COEUR 10PIQUE 2CARREAU 5TREFLE 13COEUR");

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

        scanner.close();
        return cartesChoisies;
    }


    List<Card> cards = new ArrayList<Card>();
}