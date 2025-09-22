package sys.pro;

import java.io.IOException;
import java.util.Scanner;

/** Blackjack implementation. */
public class Game {
    private static final int SCORE_LIMIT = 21;
    private static final int DEALER_TARGET_SCORE = 17;

    /** Interactive blackjack. */
    public static void main(String[] args) throws IOException, NullPointerException {
        ResettingDeck deck = new ResettingDeck<DefaultDeck>(DefaultDeck::new);
        int roundsWonByPlayer = 0;
        int totalRounds = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            totalRounds += 1;
            System.out.println("Round " + totalRounds);
            GameResult result = round(scanner, deck);

            switch (result) {
                case PlayerWon:
                    roundsWonByPlayer += 1;
                    System.out.print("You won! ");
                    break;
                case PlayerLost:
                    System.out.print("You lost. ");
                    break;
                case Draw:
                    System.out.print("Draw. ");
                    break;
                default:
                    throw new NullPointerException();
            }

            int roundsWonByDealer = totalRounds - roundsWonByPlayer;
            System.out.println(
                    "You won "
                            + roundsWonByPlayer
                            + " rounds and lost "
                            + roundsWonByDealer
                            + " rounds.\n");
        }
    }

    /** Simulate a round of the game. */
    public static GameResult round(Scanner input, ResettingDeck deck) throws IOException {
        Hand dealerHand = new Hand();
        Hand playerHand = new Hand();

        playerHand.addCard(deck.nextCard());
        playerHand.addCard(deck.nextCard());

        dealerHand.addCard(deck.nextCard());
        dealerHand.addCard(deck.nextCard().flip());

        printHands(playerHand, dealerHand);

        if (playerHand.containsBlackjack()) {
            return GameResult.PlayerWon;
        }

        System.out.println("Your turn.");
        System.out.println("Type `hit` to take another card, type `stand` to end the turn.");

        for (; ; ) {
            String s;
            do {
                s = input.nextLine().strip();
            } while (!s.equals("hit") && !s.equals("stand"));

            if (s.equals("stand")) {
                break;
            }

            Card card = deck.nextCard();
            playerHand.addCard(card);

            System.out.println("You got " + card + ".");

            printHands(playerHand, dealerHand);

            if (playerHand.chips() > SCORE_LIMIT) {
                return GameResult.PlayerLost;
            }
        }

        System.out.println("Dealers turn.");
        dealerHand.get(1).flip();
        printHands(playerHand, dealerHand);

        if (dealerHand.containsBlackjack()) {
            return GameResult.PlayerLost;
        }

        while (dealerHand.chips() < DEALER_TARGET_SCORE) {
            Card card = deck.nextCard();
            System.out.println("Dealer got " + card + ".");

            dealerHand.addCard(card);
            printHands(playerHand, dealerHand);

            if (dealerHand.chips() > SCORE_LIMIT) {
                return GameResult.PlayerWon;
            }
        }

        if (playerHand.chips() > dealerHand.chips()) {
            return GameResult.PlayerWon;
        } else if (playerHand.chips() == dealerHand.chips()) {
            return GameResult.Draw;
        } else {
            return GameResult.PlayerLost;
        }
    }

    private static void printHands(Hand playerHand, Hand dealerHand) {
        System.out.println("Your cards: " + playerHand);
        System.out.println("Dealer's cards: " + dealerHand);
        System.out.println();
    }
}
