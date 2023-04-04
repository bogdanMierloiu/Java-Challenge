The entities are:

User: An entity representing a player, which has a name, a reputation, and a wallet.
Wallet: An entity representing a wallet, which has an ID, a number of tokens, a list of badges, and a list of users.
Badge: An entity representing a badge, which has an ID, a type, and a list of wallets.
Rank: An entity representing a reputation, which has an ID, a type, and a list of users.

The relationships between these entities are:

User is related to Rank in a Many-to-One relationship. Each player has exactly one reputation, but each reputation may have multiple users.
User is related to Wallet in a Many-to-One relationship. Each player has exactly one wallet, but each wallet may have multiple users.
Wallet is related to Badge in a Many-to-Many relationship. Each wallet may have multiple badges, and each badge may be associated with multiple wallets.
Rank is related to User in a One-to-Many relationship. Each reputation may have multiple users, but each player has exactly one reputation.