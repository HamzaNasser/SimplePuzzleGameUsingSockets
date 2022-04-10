# SimplePuzzleGameUsingSockets

A simple puzzle game using Sockets: WordNet

Scenario

The server program in java allows a basic client to connect to the server using sockets.  Once connected the server will invite the client to play a game of guess-the-word. The mystery word is always TOPPLE. In this game the server asks the client to enter a guess. Each time that the user makes a guess the client sends it to the server and the server replies by showing them which letters are correct and which are not.

When the client first connects, the server sends the greeting:

Welcome to Wordnet!

The server then will ask for username and password to be submitted separately.  The user is given 5 attempts in total to get these right.  If they fail on the 5th attempt then the server disconnects from the client.

The username must be Sammy (first letter uppercase, remainder lowercase) and the password must be WOOF (all uppercase).

If both the username and password are correctly submitted then the server sends this welcome message to the client:

Welcome Sammy!
Guess the mystery six-letter word

The user should then enter a word, for example:

JOSTLE

The server receives the word, checks for match to the correct word 'TOPPLE' and sends back:

*O**LE

This lets the user know that the letters o, l and e were correct and in the correct places. It does not matter if the user enters upper or lower cases letters. So if the user entered jostle then they would get the same response *O**LE.

If the input is not correctly formatted, such as having too few or too many letters, using spaces or using non-alphabetical characters then the server should send a response that lets the user know:

Oops! You need enter a six letter word containing only alphabetical characters and no spaces.
Try again:

The server should keep accepting guesses until the user guesses correctly, and keep count of the number of turns. If the user guessed correctly in 5 turns then the server should send this response:

You got it in 5 turns - well done and goodbye!
