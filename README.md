# JAVA CHALLENGE
The application is designed for software developers. They enter the application, through OAuth2, using the github or google account. At the first login, a wallet is automatically created for them, in which they receive 100 $JAVA tokens and a NFT: "Young Explorer".
The users of the application can post quizzes, in the form of questions, allocating a reward in $JAVA tokens for solving them.
At the same time, they can send answers to the existing, unsolved quizzes.
Validation of an answer is done by the one who asked the question, at which time the amount of $JAVA tokens, allocated as a reward for that question, is withdrawn from his wallet, and the user who gave the answer that was validated, will receive $JAVA tokens in the wallet.
When a user has 3 answers that have been validated as correct, he receives another NFT: "Adventurer NFT", and with 5 valid answers, he receives the NFT: "Cosmonaut NFT". The user's reputation attribute also works on the same principle.
In the application, users can send $JAVA tokens to each other.
The user with the role of admin has access to all the functionalities of the application

To develop the application's functionalities, we used the JAVA programming language, through the Spring Boot MVC framework. We used the MySQL relational database, and the communication between them is done through the Spring Data JPA interface.

The following entities are defined:

PLAYER with the following attributes:
-id: a Long representing the unique identifier of the Player (generated automatically by the database)
-name: a String representing the name of the Player (cannot be null and must be unique)
-isBlocked: a Boolean indicating whether the Player is currently blocked from accessing the system
-reputation: a reference to the Reputation entity representing the reputation of the Player
-wallet: a reference to the Wallet entity representing the virtual wallet of the Player
-answers: a List of Answer entities representing the answers provided by the Player (mapped by the "player" field of the Answer entity)
-questions: a List of Question entities representing the questions asked by the Player (mapped by the "player" field of the Question entity)

WALLET with the following attributes:
-id: a Long representing the unique identifier of the Wallet (generated automatically by the database)
-nrOfTokens: a Long representing the number of tokens stored in the Wallet (cannot be null)
-address: a String representing the address of the Wallet (cannot be null)
-nfts: a List of Nft entities representing the non-fungible tokens (NFTs) owned by the Wallet (mapped by the "wallets" field of the Nft entity)
-players: a List of Player entities representing the players that own the Wallet (mapped by the "wallet" field of the Player entity)
-history: a List of WalletHistory entities representing the transaction history of the Wallet (mapped by the "wallet" field of the WalletHistory entity)

WALLET HISTORY with the following attributes:
-id: a Long representing the unique identifier of the WalletHistory (generated automatically by the database)
-dateTime: a LocalDateTime representing the date and time when the event occurred (can be null)
-event: a String representing the type of event (can be null)
-value: a Long representing the value of the transaction (can be null)
-wallet: a Wallet entity representing the wallet associated with the transaction (mapped by the "history" field of the Wallet entity)

REPUTATION with the following attributes: 
-id: a unique identifier for the Reputation instance, generated automatically by the database.
-type: a string representing the type of reputation, which is a required field and cannot be null.
-players: a list of Player objects associated with the given Reputation instance, with a one-to-many relationship, which is mapped by the reputation field in the Player entity. This list is bidirectional, meaning that changes made to one side of the relationship will automatically be reflected on the other side.

NFT with the following attributes:
-id: a unique identifier for the NFT.
-type: a string representing the type of the NFT.
-wallets: a list of wallets that hold this NFT. It's a many-to-many relationship, which means that an NFT can be held by multiple wallets, and a wallet can hold multiple NFTs.

QUESTION with the following attributes:
-id: a unique identifier for the question (generated automatically by the database)
-text: a string representing the text of the question, with a maximum length of 2000 characters
-rewardTokens: a long integer representing the number of tokens that will be awarded to the player who provides the best answer to this question
-isResolved: a boolean indicating whether the question has been resolved (answered) or not
-answers: a list of Answer entities representing the answers that have been submitted for this question
-player: a Player entity representing the player who submitted this question

ANSWER with the following attributes:
-id: a unique identifier for each answer, of type Long.
-text: a String representing the text of the answer, with a maximum length of 2000 characters.
-isValid: a Boolean indicating whether the answer is considered valid or not, represented as true or false.
-question: a many-to-one relationship with the Question entity, indicating the question that the answer is related to.
-player: a many-to-one relationship with the Player entity, indicating the player who submitted the answer.
