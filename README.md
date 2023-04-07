# JAVA CHALLENGE

Această aplicație este scrisă în limbajul de programare Java și utilizează biblioteca Jakarta Persistence (JPA) pentru a
interacționa cu o bază de date relațională. Ea definește patru clase de entități: Player, Reputation, Wallet și Nft.

Clasa Player definește o entitate care reprezintă un jucător în joc. Un jucător are un nume și o reputație, care este o
altă entitate. Un jucător are, de asemenea, un portofel, care este și el o entitate.

Clasa Reputation definește o entitate care reprezintă reputația unui jucător. O reputație are un tip și poate fi
asociată cu mai mulți jucători.

Clasa Wallet definește o entitate care reprezintă portofelul unui jucător. Un portofel are un număr de token-uri și
poate conține mai multe obiecte colecționabile, reprezentate prin entitatea Nft. Un portofel poate fi asociat cu mai
mulți jucători.

Clasa Nft definește o entitate care reprezintă un obiect colecționabil, sau un NFT (non-fungible token). Un NFT are un
tip și poate fi conținut în mai multe portofele.

Aceste clase de entități sunt definite utilizând notarea JPA, ceea ce înseamnă că ele pot fi mapate direct într-o bază
de date relațională. Clasa Player este o entitate de tipul rădăcină, care este utilizată pentru a accesa celelalte
entități prin intermediul relațiilor de asociere. De exemplu, relația dintre un jucător și reputația sa este definită
prin intermediul unei relații de mulți-la-unu, adică un jucător poate avea o singură reputație, dar o reputație poate fi
asociată cu mai mulți jucători.

În ansamblu, această aplicație este concepută pentru a stoca informații despre jucători și obiectele colecționabile pe
care le dețin, precum și despre reputațiile lor în joc. Ea oferă o modalitate de a gestiona aceste date și de a le
accesa prin intermediul relațiilor dintre entități.

# Relațiile dintre aceste entități sunt:

Utilizatorul este înrudit cu Rank într-o relație Multi-la-Unu. Fiecare jucător are exact o reputație, dar fiecare
reputație poate
au mai mulți utilizatori.
Utilizatorul este înrudit cu Wallet într-o relație Multi-la-Unu. Fiecare jucător are exact un portofel, dar fiecare
portofel poate avea
utilizatori multipli.
Portofelul este legat de Insigna într-o relație Multi-la-Mulți. Fiecare portofel poate avea mai multe insigne și fiecare
nft poate fi
asociate cu mai multe portofele.
Rang este legat de Utilizatorul într-o relație Unu-la-Mulți. Fiecare reputație poate avea mai mulți utilizatori, dar
fiecare jucător are
exact o reputație.