public class Card {

    int number;
    Color color;

    public Card(int number, Color color) {
        this.number = number;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        String nom;
        switch (number) {
            case 1:
                nom = "As";
                break;
            case 11:
                nom = "Valet";
                break;
            case 12:
                nom = "Dame";
                break;
            case 13:
                nom = "Roi";
                break;
            default:
                nom = String.valueOf(number);
        }

        return nom + " de " + color;
    }
}
