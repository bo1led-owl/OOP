package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class GameTest {
    @Test
    void simple() throws IOException {
        Scanner input = new Scanner("stand\n");

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Suit.Spades, Rank.Three));
        cards.add(new Card(Suit.Hearts, Rank.Jack));
        cards.add(new Card(Suit.Diamonds, Rank.Queen));
        cards.add(new Card(Suit.Clubs, Rank.Seven));

        var deck = new ResettingDeck<CustomDeck>(new DeckSupplier(cards));
        GameResult res = Game.round(input, deck);
        assertEquals(res, GameResult.PlayerLost);
    }

    @Test
    void hits() throws IOException {
        Scanner input = new Scanner("hit\nhit\nstand\n");

        ArrayList<Card> cards = new ArrayList<Card>();
        // players initial
        cards.add(new Card(Suit.Spades, Rank.Three));
        cards.add(new Card(Suit.Hearts, Rank.Jack));
        // dealers initial
        cards.add(new Card(Suit.Diamonds, Rank.Two));
        cards.add(new Card(Suit.Clubs, Rank.Seven));
        // player takes two cards
        cards.add(new Card(Suit.Spades, Rank.Four));
        cards.add(new Card(Suit.Hearts, Rank.Four));
        // dealer takes one
        cards.add(new Card(Suit.Spades, Rank.Seven));

        var deck = new ResettingDeck<CustomDeck>(new DeckSupplier(cards));
        GameResult res = Game.round(input, deck);
        assertEquals(res, GameResult.PlayerWon);
    }

    @Test
    void playerGotTooMuch() throws IOException {
        Scanner input = new Scanner("hit\n");

        ArrayList<Card> cards = new ArrayList<Card>();
        // players initial
        cards.add(new Card(Suit.Spades, Rank.Queen));
        cards.add(new Card(Suit.Hearts, Rank.Jack));
        // dealers initial
        cards.add(new Card(Suit.Diamonds, Rank.Two));
        cards.add(new Card(Suit.Clubs, Rank.Seven));
        // player takes a card
        cards.add(new Card(Suit.Spades, Rank.Four));

        var deck = new ResettingDeck<CustomDeck>(new DeckSupplier(cards));
        GameResult res = Game.round(input, deck);
        assertEquals(res, GameResult.PlayerLost);
    }

    @Test
    void dealerGotTooMuch() throws IOException {
        Scanner input = new Scanner("stand\n");

        ArrayList<Card> cards = new ArrayList<Card>();
        // players initial
        cards.add(new Card(Suit.Spades, Rank.Queen));
        cards.add(new Card(Suit.Hearts, Rank.Jack));
        // dealers initial
        cards.add(new Card(Suit.Diamonds, Rank.Jack));
        cards.add(new Card(Suit.Clubs, Rank.Two));
        // players stands
        // dealer takes a card
        cards.add(new Card(Suit.Spades, Rank.Ten));

        var deck = new ResettingDeck<CustomDeck>(new DeckSupplier(cards));
        GameResult res = Game.round(input, deck);
        assertEquals(res, GameResult.PlayerWon);
    }

    @Test
    void blackjack() throws IOException {
        Scanner input = new Scanner("");

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Suit.Spades, Rank.Ace));
        cards.add(new Card(Suit.Hearts, Rank.Jack));
        cards.add(new Card(Suit.Diamonds, Rank.Queen));
        cards.add(new Card(Suit.Clubs, Rank.Seven));

        var deck = new ResettingDeck<CustomDeck>(new DeckSupplier(cards));
        GameResult res = Game.round(input, deck);
        assertEquals(res, GameResult.PlayerWon);
    }
}
