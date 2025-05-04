//
// Created by HungHuy02 on 5/1/2025.
//
#include <iostream>
#include <cstring>
#include <vector>
#include <random>
#include <map>
#include <unordered_map>
#include "bitboard.h"
#include <android/log.h>
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "MyNative", __VA_ARGS__)


using namespace std;

enum {
    a8, b8, c8, d8, e8, f8, g8, h8,
    a7, b7, c7, d7, e7, f7, g7, h7,
    a6, b6, c6, d6, e6, f6, g6, h6,
    a5, b5, c5, d5, e5, f5, g5, h5,
    a4, b4, c4, d4, e4, f4, g4, h4,
    a3, b3, c3, d3, e3, f3, g3, h3,
    a2, b2, c2, d2, e2, f2, g2, h2,
    a1, b1, c1, d1, e1, f1, g1, h1, no_sq
};

const char *squareToCoordinate[] = {
        "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
        "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
        "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
        "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
        "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
        "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
        "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
        "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1",
};

enum { P, N, B, R, Q, K, p, n, b, r, q, k };

enum { white, black, both };

enum { rook, bishop };

enum { KS = 1, QS = 2, ks = 4, qs = 8 };

enum { all_moves, only_captures };

enum { only_one, column, row, all };

const int castlingRights[64] = {
        7, 15, 15, 15,  3, 15, 15, 11,
        15, 15, 15, 15, 15, 15, 15, 15,
        15, 15, 15, 15, 15, 15, 15, 15,
        15, 15, 15, 15, 15, 15, 15, 15,
        15, 15, 15, 15, 15, 15, 15, 15,
        15, 15, 15, 15, 15, 15, 15, 15,
        15, 15, 15, 15, 15, 15, 15, 15,
        13, 15, 15, 15, 12, 15, 15, 14
};

const char* asciiPieces = "PNBRQKpnbrqk";

int charPieces[128] = {};

void initCharPieces() {
    charPieces['P'] = P;
    charPieces['N'] = N;
    charPieces['B'] = B;
    charPieces['R'] = R;
    charPieces['Q'] = Q;
    charPieces['K'] = K;
    charPieces['p'] = p;
    charPieces['n'] = n;
    charPieces['b'] = b;
    charPieces['r'] = r;
    charPieces['q'] = q;
    charPieces['k'] = k;
}

/*
          binary move bits                               hexidecimal constants

    0000 0000 0000 0000 0011 1111    source square       0x3f
    0000 0000 0000 0011 1100 0000    target square       0xfc0
    0000 0000 1111 0000 0000 0000    piece               0xf000
    0000 1111 0000 0000 0000 0000    promoted piece      0xf0000
    0001 0000 0000 0000 0000 0000    capture flag        0x100000
    0010 0000 0000 0000 0000 0000    double push flag    0x200000
    0100 0000 0000 0000 0000 0000    enpassant flag      0x400000
    1000 0000 0000 0000 0000 0000    castling flag       0x800000

          binary square bits
    0000 0011 1111                   square              0x3f
    0011 1100 0000                   piece               0x3C0
*/

#define ENCODE_MOVE(source, target, piece, promoted, capture, double, enpassant, castling) \
    ((source) |          \
    (target << 6) |     \
    (piece << 12) |     \
    (promoted << 16) |  \
    (capture << 20) |   \
    (double << 21) |    \
    (enpassant << 22) | \
    (castling << 23))


#define ENCODE_SQUARE(square, piece) \
    ((square) |          \
    (piece << 6))

#define GET_MOVE_SOURCE(move) (move & 0x3f)
#define GET_MOVE_TARGET(move) ((move & 0xfc0) >> 6)
#define GET_MOVE_PIECE(move) ((move & 0xf000) >> 12)
#define GET_MOVE_PROMOTED(move) ((move & 0xf0000) >> 16)
#define GET_MOVE_CAPTURE(move) (move & 0x100000)
#define GET_MOVE_DOUBLE(move) (move & 0x200000)
#define GET_MOVE_ENPASSANT(move) (move & 0x400000)
#define GET_MOVE_CASTLING(move) (move & 0x800000)

#define GET_SQUARE(square) (square & 0x3f)
#define GET_PIECE(square) ((square & 0x3C0) >> 6)

#define U64 unsigned long long
#define SET_BIT(board, square) (board |= (1ULL << (square)))
#define GET_BIT(board, square) (board & (1ULL << square))
#define POP_BIT(board, square) (GET_BIT(board, square) ? board ^= (1ULL << square) : 0)

U64 bitboards[12];
U64 occupancies[3];
int side;
int enpassant = no_sq;
int castle;


U64 zobristTable[12][64];
static unordered_map<U64, int> history;

#define COPY_BOARD()                                                      \
    U64 bitboards_copy[12], occupancies_copy[3];                          \
    int side_copy, enpassant_copy, castle_copy;                           \
    memcpy(bitboards_copy, bitboards, 96);                                \
    memcpy(occupancies_copy, occupancies, 24);                            \
    side_copy = side, enpassant_copy = enpassant, castle_copy = castle;

#define TAKE_BACK()                                                       \
    memcpy(bitboards, bitboards_copy, 96);                                \
    memcpy(occupancies, occupancies_copy, 24);                            \
    side = side_copy, enpassant = enpassant_copy, castle = castle_copy;

/*
8   0 1 1 1 1 1 1 1
7   0 1 1 1 1 1 1 1
6   0 1 1 1 1 1 1 1
5   0 1 1 1 1 1 1 1
4   0 1 1 1 1 1 1 1
3   0 1 1 1 1 1 1 1
2   0 1 1 1 1 1 1 1
1   0 1 1 1 1 1 1 1

    a b c d e f g h
*/
const U64 NOT_A_FILE = 18374403900871474942ULL;
/*
8   1 1 1 1 1 1 1 0
7   1 1 1 1 1 1 1 0
6   1 1 1 1 1 1 1 0
5   1 1 1 1 1 1 1 0
4   1 1 1 1 1 1 1 0
3   1 1 1 1 1 1 1 0
2   1 1 1 1 1 1 1 0
1   1 1 1 1 1 1 1 0

    a b c d e f g h
*/
const U64 NOT_H_FILE = 9187201950435737471ULL;
/*
8   0 0 1 1 1 1 1 1
7   0 0 1 1 1 1 1 1
6   0 0 1 1 1 1 1 1
5   0 0 1 1 1 1 1 1
4   0 0 1 1 1 1 1 1
3   0 0 1 1 1 1 1 1
2   0 0 1 1 1 1 1 1
1   0 0 1 1 1 1 1 1

    a b c d e f g h
*/
const U64 NOT_AB_FILE = 18229723555195321596ULL;
/*
8   1 1 1 1 1 1 0 0
7   1 1 1 1 1 1 0 0
6   1 1 1 1 1 1 0 0
5   1 1 1 1 1 1 0 0
4   1 1 1 1 1 1 0 0
3   1 1 1 1 1 1 0 0
2   1 1 1 1 1 1 0 0
1   1 1 1 1 1 1 0 0

    a b c d e f g h
*/
const U64 NOT_HG_FILE = 4557430888798830399ULL;

const int BISHOP_RELEVANTS_BITS[64] = {
        6, 5, 5, 5, 5, 5, 5, 6,
        5, 5, 5, 5, 5, 5, 5, 5,
        5, 5, 7, 7, 7, 7, 5, 5,
        5, 5, 7, 9, 9, 7, 5, 5,
        5, 5, 7, 9, 9, 7, 5, 5,
        5, 5, 7, 7, 7, 7, 5, 5,
        5, 5, 5, 5, 5, 5, 5, 5,
        6, 5, 5, 5, 5, 5, 5, 6
};

const int ROOK_RELEVENTS_BITS[64] = {
        12, 11, 11, 11, 11, 11, 11, 12,
        11, 10, 10, 10, 10, 10, 10, 11,
        11, 10, 10, 10, 10, 10, 10, 11,
        11, 10, 10, 10, 10, 10, 10, 11,
        11, 10, 10, 10, 10, 10, 10, 11,
        11, 10, 10, 10, 10, 10, 10, 11,
        11, 10, 10, 10, 10, 10, 10, 11,
        12, 11, 11, 11, 11, 11, 11, 12
};

const U64 rookMagicNumbers[64] = {
        0x8a80104000800020ULL,
        0x140002000100040ULL,
        0x2801880a0017001ULL,
        0x100081001000420ULL,
        0x200020010080420ULL,
        0x3001c0002010008ULL,
        0x8480008002000100ULL,
        0x2080088004402900ULL,
        0x800098204000ULL,
        0x2024401000200040ULL,
        0x100802000801000ULL,
        0x120800800801000ULL,
        0x208808088000400ULL,
        0x2802200800400ULL,
        0x2200800100020080ULL,
        0x801000060821100ULL,
        0x80044006422000ULL,
        0x100808020004000ULL,
        0x12108a0010204200ULL,
        0x140848010000802ULL,
        0x481828014002800ULL,
        0x8094004002004100ULL,
        0x4010040010010802ULL,
        0x20008806104ULL,
        0x100400080208000ULL,
        0x2040002120081000ULL,
        0x21200680100081ULL,
        0x20100080080080ULL,
        0x2000a00200410ULL,
        0x20080800400ULL,
        0x80088400100102ULL,
        0x80004600042881ULL,
        0x4040008040800020ULL,
        0x440003000200801ULL,
        0x4200011004500ULL,
        0x188020010100100ULL,
        0x14800401802800ULL,
        0x2080040080800200ULL,
        0x124080204001001ULL,
        0x200046502000484ULL,
        0x480400080088020ULL,
        0x1000422010034000ULL,
        0x30200100110040ULL,
        0x100021010009ULL,
        0x2002080100110004ULL,
        0x202008004008002ULL,
        0x20020004010100ULL,
        0x2048440040820001ULL,
        0x101002200408200ULL,
        0x40802000401080ULL,
        0x4008142004410100ULL,
        0x2060820c0120200ULL,
        0x1001004080100ULL,
        0x20c020080040080ULL,
        0x2935610830022400ULL,
        0x44440041009200ULL,
        0x280001040802101ULL,
        0x2100190040002085ULL,
        0x80c0084100102001ULL,
        0x4024081001000421ULL,
        0x20030a0244872ULL,
        0x12001008414402ULL,
        0x2006104900a0804ULL,
        0x1004081002402ULL
};

const U64 bishopMagicNumbers[64] = {
        0x40040844404084ULL,
        0x2004208a004208ULL,
        0x10190041080202ULL,
        0x108060845042010ULL,
        0x581104180800210ULL,
        0x2112080446200010ULL,
        0x1080820820060210ULL,
        0x3c0808410220200ULL,
        0x4050404440404ULL,
        0x21001420088ULL,
        0x24d0080801082102ULL,
        0x1020a0a020400ULL,
        0x40308200402ULL,
        0x4011002100800ULL,
        0x401484104104005ULL,
        0x801010402020200ULL,
        0x400210c3880100ULL,
        0x404022024108200ULL,
        0x810018200204102ULL,
        0x4002801a02003ULL,
        0x85040820080400ULL,
        0x810102c808880400ULL,
        0xe900410884800ULL,
        0x8002020480840102ULL,
        0x220200865090201ULL,
        0x2010100a02021202ULL,
        0x152048408022401ULL,
        0x20080002081110ULL,
        0x4001001021004000ULL,
        0x800040400a011002ULL,
        0xe4004081011002ULL,
        0x1c004001012080ULL,
        0x8004200962a00220ULL,
        0x8422100208500202ULL,
        0x2000402200300c08ULL,
        0x8646020080080080ULL,
        0x80020a0200100808ULL,
        0x2010004880111000ULL,
        0x623000a080011400ULL,
        0x42008c0340209202ULL,
        0x209188240001000ULL,
        0x400408a884001800ULL,
        0x110400a6080400ULL,
        0x1840060a44020800ULL,
        0x90080104000041ULL,
        0x201011000808101ULL,
        0x1a2208080504f080ULL,
        0x8012020600211212ULL,
        0x500861011240000ULL,
        0x180806108200800ULL,
        0x4000020e01040044ULL,
        0x300000261044000aULL,
        0x802241102020002ULL,
        0x20906061210001ULL,
        0x5a84841004010310ULL,
        0x4010801011c04ULL,
        0xa010109502200ULL,
        0x4a02012000ULL,
        0x500201010098b028ULL,
        0x8040002811040900ULL,
        0x28000010020204ULL,
        0x6000020202d0240ULL,
        0x8918844842082200ULL,
        0x4010011029020020ULL
};

U64 pawnAttacks[2][64];
U64 knightAttacks[64];
U64 kingAttacks[64];

U64 bishopMasks[64];
U64 rookMasks[64];
U64 bishopAttacks[64][512];
U64 rookAttacks[64][4096];

static inline int countBit(U64 board) {
    int count = 0;
    while(board) {
        count++;
        board &= board - 1;
    }
    return count;
}

static inline int getLastSignificant1stBit(U64 board) {
    if (board) {
        return countBit((board & -board) - 1);
    }
    return -1;
}

void printBoard(U64 board) {
    for(int rank = 0; rank < 8; rank++) {
        for(int file = 0; file < 8; file++) {
            int square = rank * 8 + file;
            if(!file) {
                cout << 8 - rank << "  ";
            }
            cout << " " << (GET_BIT(board, square) ? 1 : 0);
        }
        cout << "\n";
    }
    cout << "\n    a b c d e f g h\n\n";
    cout << board << "ULL\n";
}

unsigned int randomState = 1804289383;

unsigned int getRandomU32Number() {
    unsigned int number = randomState;

    number ^= number << 13;
    number ^= number >> 17;
    number ^= number << 5;

    randomState = number;

    return number;
}

U64 getRandomU64Number() {
    U64 n1, n2, n3, n4;
    n1 = ((U64) getRandomU32Number()) & 0xFFFF;
    n2 = ((U64) getRandomU32Number()) & 0xFFFF;
    n3 = ((U64) getRandomU32Number()) & 0xFFFF;
    n4 = ((U64) getRandomU32Number()) & 0xFFFF;
    return n1 | n2 << 16 | n3 << 32 | n4 << 48;
}

U64 generateMagicNumber() {
    return getRandomU64Number() & getRandomU64Number() & getRandomU64Number();
}

U64 computeZobristHash() {
    U64 hash = 0;
    for (int piece = P; piece <= k; ++piece) {
        U64 bb = bitboards[piece];
        while (bb) {
            int square = __builtin_ctzll(bb);
            hash ^= zobristTable[piece][square];
            bb &= bb - 1;
        }
    }
    return hash;
}

void recordPosition(U64 hash) {
    history[hash]++;
}

bool isThreefoldRepetition(U64 hash) {
    return history[hash] >= 3;
}

void initZobrist() {
    mt19937_64 rng(random_device{}());
    uniform_int_distribution<U64> dist(0, ~0ULL);
    for (int square = 0; square < 64; square++) {
        for (int piece = P; piece <= k; piece++) {
            zobristTable[piece][square] = dist(rng);
        }
    }
}

void parseFen(const char *fen) {
    memset(bitboards, 0ULL, sizeof(bitboards));
    memset(occupancies, 0ULL, sizeof(occupancies));
    side = 0;
    enpassant = no_sq;
    castle = 0;
    for(int rank = 0; rank < 8; rank++) {
        for (int file = 0; file < 8; file++) {
            int square = rank * 8 + file;
            if((*fen >= 'a' && *fen <= 'z') || (*fen >= 'A' && *fen <= 'Z')) {
                int piece = charPieces[*fen];
                SET_BIT(bitboards[piece], square);
                fen++;
                file++;
            }

            if(*fen >= '1' && *fen <= '8') {
                int offset = *fen - '1';
                file += offset;
                fen++;
                file++;
            }

            if(*fen == '/') fen++;
            file--;
        }
    }

    fen++;
    (*fen == 'w') ? (side = white) : (side = black);
    fen += 2;

    while(*fen != ' ') {
        switch(*fen) {
            case 'K': castle |= KS; break;
            case 'Q': castle |= QS; break;
            case 'k': castle |= ks; break;
            case 'q': castle |= qs; break;
            case '-': break;
        }
        fen++;
    }

    fen++;
    if(*fen != '-') {
        int file = fen[0] - 'a';
        int rank = 8 - (fen[1] - '0');
        enpassant = rank * 8 + file;
    } else
        enpassant = no_sq;

    for(int piece = P; piece <= K; piece++) {
        occupancies[white] |= bitboards[piece];
    }
    for (int piece = p; piece <= k; piece++) {
        occupancies[black] |= bitboards[piece];
    }
    occupancies[both] |= occupancies[white];
    occupancies[both] |= occupancies[black];
    recordPosition(computeZobristHash());
}

U64 maskPawnAttacks(int side, int square) {
    U64 attacks = 0ULL;
    U64 board = 0ULL;
    SET_BIT(board, square);
    if (!side) {
        if((board >> 7) & NOT_A_FILE) attacks |= board >> 7;
        if((board >> 9) & NOT_H_FILE) attacks |= board >> 9;
    } else {
        if((board << 7) & NOT_H_FILE) attacks |= board << 7;
        if((board << 9) & NOT_A_FILE) attacks |= board << 9;
    }
    return attacks;
}

U64 maskKnightAttacks(int square) {
    U64 attacks = 0ULL;
    U64 board = 0ULL;
    SET_BIT(board, square);
    if ((board >> 6) & NOT_AB_FILE) attacks |= board >> 6;
    if ((board >> 15) & NOT_A_FILE) attacks |= board >> 15;
    if ((board >> 17) & NOT_H_FILE) attacks |= board >> 17;
    if ((board >> 10) & NOT_HG_FILE) attacks |= board >> 10;
    if ((board << 6) & NOT_HG_FILE) attacks |= board << 6;
    if ((board << 15) & NOT_H_FILE) attacks |= board << 15;
    if ((board << 17) & NOT_A_FILE) attacks |= board << 17;
    if ((board << 10) & NOT_AB_FILE) attacks |= board << 10;
    return attacks;
}

U64 maskKingAttacks(int square) {
    U64 attacks = 0ULL;
    U64 board = 0ULL;
    SET_BIT(board, square);
    if ((board >> 7) & NOT_A_FILE) attacks |= board >> 7;
    if (board >> 8) attacks |= board >> 8;
    if ((board >> 9) & NOT_H_FILE) attacks |= board >> 9;
    if ((board >> 1) & NOT_H_FILE) attacks |= board >> 1;
    if ((board << 7) & NOT_H_FILE) attacks |= board << 7;
    if (board << 8) attacks |= board << 8;
    if ((board << 9) & NOT_A_FILE) attacks |= board << 9;
    if ((board << 1) & NOT_A_FILE) attacks |= board << 1;
    return attacks;
}

U64 maskBishopRelevantOccupancyBits(int square) {
    U64 relevantOccupancyBits = 0ULL;
    int r, f;
    int tr = square / 8;
    int tf = square % 8;
    for(r = tr + 1, f = tf + 1; r < 7 && f < 7; r++, f++) relevantOccupancyBits |= (1ULL << (r * 8 + f));
    for(r = tr + 1, f = tf - 1; r < 7 && f > 0; r++, f--) relevantOccupancyBits |= (1ULL << (r * 8 + f));
    for(r = tr - 1, f = tf + 1; r > 0 && f < 7; r--, f++) relevantOccupancyBits |= (1ULL << (r * 8 + f));
    for(r = tr - 1, f = tf - 1; r > 0 && f > 0; r--, f--) relevantOccupancyBits |= (1ULL << (r * 8 + f));
    return relevantOccupancyBits;
}

U64 bishopAttacksOnTheFly(int square, U64 block) {
    U64 attacks = 0ULL;
    int r, f;
    int tr = square / 8;
    int tf = square % 8;
    for (r = tr + 1, f = tf + 1; r < 8 && f < 8; r++, f++) {
        attacks |= (1ULL << (r * 8 + f));
        if ((1ULL << (r * 8 + f)) & block) break;
    }
    for (r = tr - 1, f = tf + 1; r >= 0 && f < 8; r--, f++) {
        attacks |= (1ULL << (r * 8 + f));
        if ((1ULL << (r * 8 + f)) & block) break;
    }
    for (r = tr + 1, f = tf - 1; r < 8 && f >= 0; r++, f--) {
        attacks |= (1ULL << (r * 8 + f));
        if ((1ULL << (r * 8 + f)) & block) break;
    }
    for (r = tr - 1, f = tf - 1; r >= 0 && f >= 0; r--, f--) {
        attacks |= (1ULL << (r * 8 + f));
        if ((1ULL << (r * 8 + f)) & block) break;
    }
    return attacks;
}

U64 maskRookRelevantOccupancyBits(int square) {
    U64 relevantOccupancyBits = 0ULL;
    int r, f;
    int tr = square / 8;
    int tf = square % 8;
    for (r = tr + 1; r < 7; r++) relevantOccupancyBits |= (1ULL << (r * 8 + tf));
    for (r = tr - 1; r > 0; r--) relevantOccupancyBits |= (1ULL << (r * 8 + tf));
    for (f = tf + 1; f < 7; f++) relevantOccupancyBits |= (1ULL << (tr * 8 + f));
    for (f = tf - 1; f > 0; f--) relevantOccupancyBits |= (1ULL << (tr * 8 + f));
    return relevantOccupancyBits;
}

U64 rookAttacksOnTheFly(int square, U64 block) {
    U64 attacks = 0ULL;
    int r, f;
    int tr = square / 8;
    int tf = square % 8;
    for (r = tr + 1; r < 8; r++) {
        attacks |= (1ULL << (r * 8 + tf));
        if ((1ULL << (r * 8 + tf)) & block) break;
    }
    for (r = tr - 1; r >= 0; r--) {
        attacks |= (1ULL << (r * 8 + tf));
        if ((1ULL << (r * 8 + tf)) & block) break;
    }
    for (f = tf + 1; f < 8; f++) {
        attacks |= (1ULL << (tr * 8 + f));
        if ((1ULL << (tr * 8 + f)) & block) break;
    }
    for (f = tf - 1; f >= 0; f--) {
        attacks |= (1ULL << (tr * 8 + f));
        if ((1ULL << (tr * 8 + f)) & block) break;
    }
    return attacks;
}

void precomputePieceAttacks() {
    for(int square = 0; square < 64; square++) {
        pawnAttacks[white][square] = maskPawnAttacks(white, square);
        pawnAttacks[black][square] = maskPawnAttacks(black, square);

        knightAttacks[square] = maskKnightAttacks(square);

        kingAttacks[square] = maskKingAttacks(square);
    }
}

U64 setOccupancy(int index, int bitsInMask, U64 attackMask) {
    U64 occupancy = 0ULL;
    for(int count = 0; count < bitsInMask; count++) {
        int square = getLastSignificant1stBit(attackMask);
        POP_BIT(attackMask, square);
        if(index & (1 << count)) {
            occupancy |= 1ULL << square;
        }
    }
    return occupancy;
}

U64 findMagicNumber(int square, int relevantBits, int bishop) {
    U64 occupancies[4096], attacks[4096], usedAttacks[4096];
    U64 attackMask = bishop ? maskBishopRelevantOccupancyBits(square) : maskRookRelevantOccupancyBits(square);
    int occupancyIndices = 1 << relevantBits;
    for(int index = 0; index < occupancyIndices; index++) {
        occupancies[index] = setOccupancy(index, relevantBits, attackMask);
        attacks[index] = bishop ? bishopAttacksOnTheFly(square, occupancies[index]) :
                         rookAttacksOnTheFly(square, occupancies[index]);
    }

    for(int randomCount = 0; randomCount < 100000000; randomCount++) {
        U64 magicNumber = generateMagicNumber();
        if (countBit((attackMask * magicNumber) & 0xFF00000000000000) < 6) continue;
        memset(usedAttacks, 0ULL, sizeof(usedAttacks));
        int index, fail;
        for(index = 0, fail = 0; !fail && index < occupancyIndices; index++) {
            int magicIndex = (int)((occupancies[index] * magicNumber) >> (64 - relevantBits));
            if(usedAttacks[magicIndex] == 0ULL) {
                usedAttacks[magicIndex] = attacks[index];
            } else if(usedAttacks[magicIndex] != attacks[index]) {
                fail = 1;
            }
        }
        if(!fail) {
            return magicNumber;
        }
    }
    cout << "Magic number fails!\n";
    return 0ULL;
}

void initMagicNumbers() {
    for(int square = 0; square < 64; square++) {
        // rookMagicNumbers[square] = findMagicNumber(square, ROOK_RELEVENTS_BITS[square], rook);
    }
    cout << "\n";
    for(int square = 0; square < 64; square++) {
        // bishopMagicNumbers[square] = findMagicNumber(square, ROOK_RELEVENTS_BITS[square], bishop);
    }
}

void initSlidersAttacks(int bishop) {
    for(int square = 0; square < 64; square++) {
        bishopMasks[square] = maskBishopRelevantOccupancyBits(square);
        rookMasks[square] = maskRookRelevantOccupancyBits(square);
        U64 attackMash = bishop ? bishopMasks[square] : rookMasks[square];
        int relevantBitsCount = countBit(attackMash);
        int occupancyIndicies = 1 << relevantBitsCount;
        for(int index = 0; index < occupancyIndicies; index++) {
            if(bishop) {
                U64 occupancy = setOccupancy(index, relevantBitsCount, attackMash);
                int magicIndex = occupancy * bishopMagicNumbers[square] >> (64 - BISHOP_RELEVANTS_BITS[square]);
                bishopAttacks[square][magicIndex] = bishopAttacksOnTheFly(square, occupancy);
            } else {
                U64 occupancy = setOccupancy(index, relevantBitsCount, attackMash);
                int magicIndex = occupancy * rookMagicNumbers[square] >> (64 - ROOK_RELEVENTS_BITS[square]);
                rookAttacks[square][magicIndex] = rookAttacksOnTheFly(square, occupancy);
            }
        }
    }
}

static inline U64 getBishopAttacks(int square, U64 occupancy) {
    occupancy &= bishopMasks[square];
    occupancy *= bishopMagicNumbers[square];
    occupancy >>= 64 - BISHOP_RELEVANTS_BITS[square];
    return bishopAttacks[square][occupancy];
}

static inline U64 getRookAttacks(int square, U64 occupancy) {
    occupancy &= rookMasks[square];
    occupancy *= rookMagicNumbers[square];
    occupancy >>= (64 - ROOK_RELEVENTS_BITS[square]);
    return rookAttacks[square][occupancy];
}

static inline U64 getQueenAttacks(int square, U64 occupancy) {
    return getBishopAttacks(square, occupancy) | getRookAttacks(square, occupancy);
}

static inline int isSquareAttacked(int square, int side) {
    if ((side == white) && (pawnAttacks[black][square] & bitboards[P])) return 1;
    if ((side == black) && (pawnAttacks[white][square] & bitboards[p])) return 1;
    if (knightAttacks[square] & ((side == white) ? bitboards[N] : bitboards[n])) return 1;
    if (getBishopAttacks(square, occupancies[both]) & ((side == white) ? bitboards[B] : bitboards[b])) return 1;
    if (getRookAttacks(square, occupancies[both]) & ((side == white) ? bitboards[R] : bitboards[r])) return 1;
    if (getQueenAttacks(square, occupancies[both]) & ((side == white) ? bitboards[Q] : bitboards[q])) return 1;
    if (kingAttacks[square] & ((side == white) ? bitboards[K] : bitboards[k])) return 1;
    return 0;
}

static inline int makeMove(int move, int move_flag) {
    if (move_flag == all_moves) {
        COPY_BOARD()

        int sourceSquare = GET_MOVE_SOURCE(move);
        int targetSquare = GET_MOVE_TARGET(move);
        int piece = GET_MOVE_PIECE(move);
        int promoted_piece = GET_MOVE_PROMOTED(move);
        int capture = GET_MOVE_CAPTURE(move);
        int double_push = GET_MOVE_DOUBLE(move);
        int enpass = GET_MOVE_ENPASSANT(move);
        int castling = GET_MOVE_CASTLING(move);

        POP_BIT(bitboards[piece], sourceSquare);
        SET_BIT(bitboards[piece], targetSquare);

        if (capture) {
            int start_piece, end_piece;
            if (side == white) {
                start_piece = p;
                end_piece = k;
            } else {
                start_piece = P;
                end_piece = K;
            }

            for (int bb_piece = start_piece; bb_piece <= end_piece; bb_piece++) {
                if (GET_BIT(bitboards[bb_piece], targetSquare)) {
                    POP_BIT(bitboards[bb_piece], targetSquare);
                    break;
                }
            }
        }

        if (promoted_piece) {
            POP_BIT(bitboards[(side == white) ? P : p], targetSquare);
            SET_BIT(bitboards[promoted_piece], targetSquare);
        }

        if (enpass) {
            (side == white) ? POP_BIT(bitboards[p], (targetSquare + 8)) :
            POP_BIT(bitboards[P], (targetSquare - 8));
        }

        enpassant = no_sq;
        if (double_push) {
            (side == white) ? (enpassant = targetSquare + 8) :
            (enpassant = targetSquare - 8);
        }

        if (castling) {
            switch (targetSquare) {
                case (g1):
                    POP_BIT(bitboards[R], h1);
                    SET_BIT(bitboards[R], f1);
                    break;
                case (c1):
                    POP_BIT(bitboards[R], a1);
                    SET_BIT(bitboards[R], d1);
                    break;
                case (g8):
                    POP_BIT(bitboards[r], h8);
                    SET_BIT(bitboards[r], f8);
                    break;
                case (c8):
                    POP_BIT(bitboards[r], a8);
                    SET_BIT(bitboards[r], d8);
                    break;
            }
        }

        castle &= castlingRights[sourceSquare];
        castle &= castlingRights[targetSquare];
        memset(occupancies, 0ULL, sizeof(occupancies));

        for (int bb_piece = P; bb_piece <= K; bb_piece++)
            occupancies[white] |= bitboards[bb_piece];

        for (int bb_piece = p; bb_piece <= k; bb_piece++)
            occupancies[black] |= bitboards[bb_piece];

        occupancies[both] |= occupancies[white];
        occupancies[both] |= occupancies[black];

        side ^= 1;
        if (isSquareAttacked((side == white) ? getLastSignificant1stBit(bitboards[k]) : getLastSignificant1stBit(bitboards[K]), side)) {
            TAKE_BACK()
            return 0;
        } else
            return 1;
    } else {
        if (GET_MOVE_CAPTURE(move))
            makeMove(move, all_moves);
        else
            return 0;
    }
    return 0;
}

// 0 -> normal, 1 -> check, 2 -> checkmate, 3 -> stalemate, 4 -> 3-fold rule
// 5 -> 50-move rule, 6 -> insufficant material,
int hasOneLegalMove() {
    LOGI("check has one legal move");
    U64 hash = computeZobristHash();
    recordPosition(hash);
    if(isThreefoldRepetition(hash)) {
        return 4;
    }
    bool isKingChecked = isSquareAttacked((side == white) ? getLastSignificant1stBit(bitboards[K]) : getLastSignificant1stBit(bitboards[k]), side);
    int sourceSquare, targetSquare;
    U64 bitboard, attacks;
    COPY_BOARD()
    int move;

    for (int piece = P; piece <= k; piece++) {
        bitboard = bitboards[piece];
        if (side == white) {
            if (piece == P) {
                while (bitboard) {
                    sourceSquare = getLastSignificant1stBit(bitboard);
                    targetSquare = sourceSquare - 8;

                    if (targetSquare >= a8 && !GET_BIT(occupancies[both], targetSquare)) {
                        if (sourceSquare >= a7 && sourceSquare <= h7) {
                            move = ENCODE_MOVE(sourceSquare, targetSquare, piece, Q, 0, 0, 0, 0);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            }
                        } else {
                            int move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 0, 0, 0, 0);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            } else {
                                if ((sourceSquare >= a2 && sourceSquare <= h2) && !GET_BIT(occupancies[both], (targetSquare - 8)))
                                    move = ENCODE_MOVE(sourceSquare, (targetSquare - 8), piece, 0, 0, 1, 0, 0);
                                if(makeMove(move, all_moves)) {
                                    TAKE_BACK()
                                    return isKingChecked ? 1 : 0;
                                }
                            }
                        }
                    }

                    attacks = pawnAttacks[side][sourceSquare] & occupancies[black];
                    while (attacks) {
                        targetSquare = getLastSignificant1stBit(attacks);
                        if (sourceSquare >= a7 && sourceSquare <= h7) {
                            move = ENCODE_MOVE(sourceSquare, targetSquare, piece, Q, 1, 0, 0, 0);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            }
                        } else {
                            move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 1, 0, 0, 0);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            }
                        }
                        POP_BIT(attacks, targetSquare);
                    }

                    if (enpassant != no_sq) {
                        U64 enpassantAttacks = pawnAttacks[side][sourceSquare] & (1ULL << enpassant);
                        if (enpassantAttacks) {
                            int targetEnpassant = getLastSignificant1stBit(enpassantAttacks);
                            move = ENCODE_MOVE(sourceSquare, targetEnpassant, piece, 0, 1, 0, 1, 0);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            }
                        }
                    }
                    POP_BIT(bitboard, sourceSquare);
                }
            }

            if (piece == K) {
                if (castle & ks)  {
                    if (!GET_BIT(occupancies[both], f1) && !GET_BIT(occupancies[both], g1)) {
                        if (!isSquareAttacked(e1, black) && !isSquareAttacked(f1, black))
                            return isKingChecked ? 1 : 0;
                    }
                }

                if (castle & qs) {
                    if (!GET_BIT(occupancies[both], d1) && !GET_BIT(occupancies[both], c1) && !GET_BIT(occupancies[both], b1)) {
                        if (!isSquareAttacked(e1, black) && !isSquareAttacked(d1, black))
                            return isKingChecked ? 1 : 0;
                    }
                }
            }
        } else {
            if (piece == p) {
                while (bitboard) {
                    sourceSquare = getLastSignificant1stBit(bitboard);
                    targetSquare = sourceSquare + 8;

                    if (targetSquare <= h1 && !GET_BIT(occupancies[both], targetSquare)) {
                        if (sourceSquare >= a2 && sourceSquare <= h2) {
                            move = ENCODE_MOVE(sourceSquare, targetSquare, piece, q, 0, 0, 0, 0);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            }
                        } else {
                            move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 0, 0, 0, 0);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            } else if ((sourceSquare >= a7 && sourceSquare <= h7) && !GET_BIT(occupancies[both], (targetSquare + 8))) {
                                move = ENCODE_MOVE(sourceSquare, (targetSquare + 8), piece, 0, 0, 1, 0, 0);
                                if(makeMove(move, all_moves)) {
                                    TAKE_BACK()
                                    return isKingChecked ? 1 : 0;
                                }
                            }
                        }
                    }

                    attacks = pawnAttacks[side][sourceSquare] & occupancies[white];
                    while (attacks) {
                        targetSquare = getLastSignificant1stBit(attacks);

                        if (sourceSquare >= a2 && sourceSquare <= h2) {
                            move = ENCODE_MOVE(sourceSquare, targetSquare, piece, q, 1, 0, 0, 0);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            }
                        } else {
                            move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 1, 0, 0, 0);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            }
                        }
                        POP_BIT(attacks, targetSquare);
                    }

                    if (enpassant != no_sq) {
                        U64 enpassantAttacks = pawnAttacks[side][sourceSquare] & (1ULL << enpassant);

                        if (enpassantAttacks) {
                            int target_enpassant = getLastSignificant1stBit(enpassantAttacks);
                            move = ENCODE_MOVE(sourceSquare, target_enpassant, piece, 0, 1, 0, 1, 0);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            }
                        }
                    }

                    POP_BIT(bitboard, sourceSquare);
                }
            }

            if (piece == k) {
                if (castle & ks) {
                    if (!GET_BIT(occupancies[both], f8) && !GET_BIT(occupancies[both], g8)) {
                        if (!isSquareAttacked(e8, white) && !isSquareAttacked(f8, white)) {
                            move = ENCODE_MOVE(e8, g8, piece, 0, 0, 0, 0, 1);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            }
                        }
                    }
                }

                if (castle & qs) {
                    if (!GET_BIT(occupancies[both], d8) && !GET_BIT(occupancies[both], c8) && !GET_BIT(occupancies[both], b8)) {
                        if (!isSquareAttacked(e8, white) && !isSquareAttacked(d8, white)) {
                            move = ENCODE_MOVE(e8, c8, piece, 0, 0, 0, 0, 1);
                            if(makeMove(move, all_moves)) {
                                TAKE_BACK()
                                return isKingChecked ? 1 : 0;
                            }
                        }
                    }
                }
            }
        }

        if ((side == white) ? piece == N : piece == n) {
            while (bitboard) {
                sourceSquare = getLastSignificant1stBit(bitboard);
                attacks = knightAttacks[sourceSquare] & ((side == white) ? ~occupancies[white] : ~occupancies[black]);

                while (attacks) {
                    targetSquare = getLastSignificant1stBit(attacks);

                    if (!GET_BIT(((side == white) ? occupancies[black] : occupancies[white]), targetSquare)) {
                        move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 0, 0, 0, 0);
                        if(makeMove(move, all_moves)) {
                            TAKE_BACK()
                            return isKingChecked ? 1 : 0;
                        }
                    } else {
                        move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 1, 0, 0, 0);
                        if(makeMove(move, all_moves)) {
                            TAKE_BACK()
                            return isKingChecked ? 1 : 0;
                        }
                    }
                    POP_BIT(attacks, targetSquare);
                }
                POP_BIT(bitboard, sourceSquare);
            }
        }
        if ((side == white) ? piece == B : piece == b) {
            while (bitboard) {
                sourceSquare = getLastSignificant1stBit(bitboard);
                attacks = getBishopAttacks(sourceSquare, occupancies[both]) & ((side == white) ? ~occupancies[white] : ~occupancies[black]);
                while (attacks) {
                    targetSquare = getLastSignificant1stBit(attacks);
                    if (!GET_BIT(((side == white) ? occupancies[black] : occupancies[white]), targetSquare)) {
                        move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 0, 0, 0, 0);
                        if(makeMove(move, all_moves)) {
                            TAKE_BACK()
                            return isKingChecked ? 1 : 0;
                        }
                    } else {
                        move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 1, 0, 0, 0);
                        if(makeMove(move, all_moves)) {
                            TAKE_BACK()
                            return isKingChecked ? 1 : 0;
                        }
                    }
                    POP_BIT(attacks, targetSquare);
                }
                POP_BIT(bitboard, sourceSquare);
            }
        }
        if ((side == white) ? piece == R : piece == r) {
            while (bitboard) {
                sourceSquare = getLastSignificant1stBit(bitboard);
                attacks = getRookAttacks(sourceSquare, occupancies[both]) & ((side == white) ? ~occupancies[white] : ~occupancies[black]);
                while (attacks) {
                    targetSquare = getLastSignificant1stBit(attacks);
                    if (!GET_BIT(((side == white) ? occupancies[black] : occupancies[white]), targetSquare)) {
                        move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 0, 0, 0, 0);
                        if(makeMove(move, all_moves)) {
                            TAKE_BACK()
                            return isKingChecked ? 1 : 0;
                        }
                    } else {
                        move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 1, 0, 0, 0);
                        if(makeMove(move, all_moves)) {
                            TAKE_BACK()
                            return isKingChecked ? 1 : 0;
                        }
                    }
                    POP_BIT(attacks, targetSquare);
                }
                POP_BIT(bitboard, sourceSquare);
            }
        }

        if ((side == white) ? piece == Q : piece == q) {
            while (bitboard) {
                sourceSquare = getLastSignificant1stBit(bitboard);
                attacks = getQueenAttacks(sourceSquare, occupancies[both]) & ((side == white) ? ~occupancies[white] : ~occupancies[black]);

                while (attacks) {
                    targetSquare = getLastSignificant1stBit(attacks);
                    if (!GET_BIT(((side == white) ? occupancies[black] : occupancies[white]), targetSquare)) {
                        move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 0, 0, 0, 0);
                        if(makeMove(move, all_moves)) {
                            TAKE_BACK()
                            return isKingChecked ? 1 : 0;
                        }
                    } else {
                        move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 1, 0, 0, 0);
                        if(makeMove(move, all_moves)) {
                            TAKE_BACK()
                            return isKingChecked ? 1 : 0;
                        }
                    }
                    POP_BIT(attacks, targetSquare);
                }
                POP_BIT(bitboard, sourceSquare);
            }
        }
        if ((side == white) ? piece == K : piece == k) {
            while (bitboard) {
                sourceSquare = getLastSignificant1stBit(bitboard);
                attacks = kingAttacks[sourceSquare] & ((side == white) ? ~occupancies[white] : ~occupancies[black]);

                while (attacks) {
                    targetSquare = getLastSignificant1stBit(attacks);
                    if (!GET_BIT(((side == white) ? occupancies[black] : occupancies[white]), targetSquare)) {
                        move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 0, 0, 0, 0);
                        if(makeMove(move, all_moves)) {
                            TAKE_BACK()
                            return isKingChecked ? 1 : 0;
                        }
                    } else {
                        move = ENCODE_MOVE(sourceSquare, targetSquare, piece, 0, 1, 0, 0, 0);
                        if(makeMove(move, all_moves)) {
                            TAKE_BACK()
                            return isKingChecked ? 1 : 0;
                        }
                    }
                    POP_BIT(attacks, targetSquare);
                }
                POP_BIT(bitboard, sourceSquare);
            }
        }
    }
    return isKingChecked ? 2 : 3;
}

static inline vector<int> getOtherSamePieceSquares(U64 bitboard, int square) {
    POP_BIT(bitboard, square);
    vector<int> squares;
    while(bitboard) {
        squares.push_back(getLastSignificant1stBit(bitboard));
        bitboard &= bitboard - 1;
    }
    return squares;
}

int checkSamePieceCanMoveToOneSpot(int source, int piece, int target) {
    vector<U64> attackMask;
    U64 pieceBoard = bitboards[piece];
    int rank = source / 8;
    int file = source % 8;
    bool sameCol = false;
    bool sameRow = false;
    int count = 0;
    vector<int> squares = getOtherSamePieceSquares(pieceBoard, source);
    if(squares.size() > 0) {
        switch (piece) {
            case N:
            case n:
                for(int square: squares) {
                    if(knightAttacks[square] & (1ULL << target)) {
                        count++;
                        if(square % 8 == file) {
                            sameCol = true;
                            continue;
                        }
                        if(square / 8 == rank) {
                            sameRow = true;
                        }
                    }
                }
                break;
            case R:
            case r:
                for(int square: squares) {
                    if(getRookAttacks(source, occupancies[both]) & (1ULL << target)) {
                        count++;
                        if(square % 8 == file) {
                            sameCol = true;
                            continue;
                        }
                        if(square / 8 == rank) {
                            sameRow = true;
                        }
                    }
                }
                break;
            case B:
            case b:
                for(int square: squares) {
                    if(getBishopAttacks(source, occupancies[both]) & (1ULL << target)) {
                        count++;
                        if(square % 8 == file) {
                            sameCol = true;
                            continue;
                        }
                        if(square / 8 == rank) {
                            sameRow = true;
                        }
                    }
                }
                break;
            case Q:
            case q:
                for(int square: squares) {
                    if(getQueenAttacks(source, occupancies[both]) & (1ULL << target)) {
                        count++;
                        if(square % 8 == file) {
                            sameCol = true;
                            continue;
                        }
                        if(square / 8 == rank) {
                            sameRow = true;
                        }
                    }
                }
                break;
        }
        if(sameCol && sameRow) {
            return all;
        } else if(sameCol) {
            return column;
        } else if (sameRow || count > 0) {
            return row;
        }
    }
    return only_one;
}

string generateAlgebraicNotation(int move) {
    int sourceSquare = GET_MOVE_SOURCE(move);
    int targetSquare = GET_MOVE_TARGET(move);
    int piece = GET_MOVE_PIECE(move);
    int promoted_piece = GET_MOVE_PROMOTED(move);
    int capture = GET_MOVE_CAPTURE(move);
    int double_push = GET_MOVE_DOUBLE(move);
    int enpass = GET_MOVE_ENPASSANT(move);
    int castling = GET_MOVE_CASTLING(move);

    string notation = "";
    if (castling) {
        switch (targetSquare) {
            case (g1):
            case (g8):
                notation = "O-O";
                break;
            case (c1):
            case (c8):
                notation = "O-O-O";
                break;
        }
        return notation;
    }
    switch(piece) {
        case P:
            if(capture) notation = squareToCoordinate[sourceSquare][0];
            break;
        case N:
            notation = "N";
            break;
        case R:
            notation = "R";
            break;
        case B:
            notation = "B";
            break;
        case Q:
            notation = "Q";
            break;
        case K:
            notation = "K";
            break;
        case p:
            if(capture) notation = squareToCoordinate[sourceSquare][0];
            break;
        case n:
            notation = "N";
            break;
        case r:
            notation = "R";
            break;
        case b:
            notation = "B";
            break;
        case q:
            notation = "Q";
            break;
        case k:
            notation = "K";
            break;
    }
    int check = checkSamePieceCanMoveToOneSpot(sourceSquare, piece, targetSquare);
    switch(check) {
        case column:
            notation += squareToCoordinate[sourceSquare][1];
            break;
        case row:
            notation += squareToCoordinate[sourceSquare][0];
            break;
        case all:
            notation += squareToCoordinate[sourceSquare];
            break;
    }
    if(capture) notation += "x";
    notation += squareToCoordinate[targetSquare];
    return notation;
}


MoveResult makeMove(int source, char sP, int target, char tP, char toP) {
    MoveResult moveResult;
    moveResult.diffMove = -1;
    moveResult.notation = "";
    COPY_BOARD()


    int sourcePiece = charPieces[sP];
    int targetPiece = tP == ' ' ? -1 : charPieces[tP];


    if (!GET_BIT(bitboards[sourcePiece], source)) {
        return moveResult;
    }

    if ((side == black && sourcePiece >= P && sourcePiece <= K) ||
        (side == white && sourcePiece >= p && sourcePiece <= k)) {
        return moveResult;
    }

    if (targetPiece != -1 && ((side == white && targetPiece >= P && targetPiece <= K) ||
                              (side == black && targetPiece >= p && targetPiece <= k))) {
        return moveResult;
    }

    U64 attacks;

    switch(sourcePiece) {
        case P:
            if (source >= a7 && source <= h7) {
                if(toP != ' ') {
                    int move = ENCODE_MOVE(source, target, P, charPieces[toP], 0, 0, 0, 0);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = 0;
                        return moveResult;
                    }
                } else {
                    int move = ENCODE_MOVE(source, target, P, q, 0, 0, 0, 0);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = 0;
                        TAKE_BACK()
                        return moveResult;
                    }
                }
            }
            if (!GET_BIT(occupancies[both], (source - 8))) {
                if(target == (source - 8)) {
                    int move = ENCODE_MOVE(source, target, P, 0, 0, 0, 0, 0);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = 65;
                        moveResult.notation = generateAlgebraicNotation(move);
                    }
                }
                if(source >= a2 && source <= h2 && target == (source - 16) && !GET_BIT(occupancies[both], (source - 16))) {
                    int move = ENCODE_MOVE(source, target, P, 0, 0, 1, 0, 0);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = 65;
                        moveResult.notation = generateAlgebraicNotation(move);
                    }
                }
            }
            attacks = pawnAttacks[white][source] & ~occupancies[white] & occupancies[black];
            if(enpassant != no_sq && !GET_BIT(attacks, enpassant) && enpassant == target) {
                int diffSquare = enpassant + 8;
                int move = ENCODE_MOVE(source, target, p, 0, 0, 0, 1, 0);
                if (makeMove(move, all_moves)) {
                    moveResult.diffMove = ENCODE_MOVE(diffSquare, diffSquare, sourcePiece, 0, 0, 0, 0, 0);
                    moveResult.notation = generateAlgebraicNotation(move);
                }
            }
            break;
        case N:
            attacks = knightAttacks[source] & ~occupancies[white];
            break;
        case R:
            attacks = getRookAttacks(source, occupancies[both]) & ~occupancies[white];
            break;
        case B:
            attacks = getBishopAttacks(source, occupancies[both]) & ~occupancies[white];
            break;
        case Q:
            attacks = getQueenAttacks(source, occupancies[both]) & ~occupancies[white];
            break;
        case K:
            if (source == e1) {
                if (target == g1 && (castle & KS)) {
                    int move = ENCODE_MOVE(source, target, sourcePiece, 0, 0, 0, 0, 1);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = ENCODE_MOVE(h1, f1, sourcePiece, 0, 0, 0, 0, 0);
                        moveResult.notation = generateAlgebraicNotation(move);
                    }
                }
                if (target == c1 && (castle & QS)) {
                    int move = ENCODE_MOVE(source, target, sourcePiece, 0, 0, 0, 0, 1);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = ENCODE_MOVE(a1, d1, sourcePiece, 0, 0, 0, 0, 0);
                        moveResult.notation = generateAlgebraicNotation(move);
                    }
                }
            }
            attacks = kingAttacks[source] & ~occupancies[white];
            break;
        case p:
            if (source >= a2 && source <= h2) {
                if(toP != ' ') {
                    int move = ENCODE_MOVE(source, target, P, charPieces[toP], 0, 0, 0, 0);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = 0;
                        TAKE_BACK()
                        return moveResult;
                    }
                } else {
                    int move = ENCODE_MOVE(source, target, P, q, 0, 0, 0, 0);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = 0;
                        TAKE_BACK()
                        return moveResult;
                    }
                }
            }
            if (!GET_BIT(occupancies[both], (source + 8))) {
                if(target == (source + 8)) {
                    int move = ENCODE_MOVE(source, target, p, 0, 0, 0, 0, 0);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = 65;
                        moveResult.notation = generateAlgebraicNotation(move);
                    }
                }
                if(source >= a7 && source <= h7 && target == (source + 16) && !GET_BIT(occupancies[both], (source + 16))) {
                    int move = ENCODE_MOVE(source, target, p, 0, 0, 1, 0, 0);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = 65;
                        moveResult.notation = generateAlgebraicNotation(move);
                    }
                }
            }
            attacks = pawnAttacks[black][source] & ~occupancies[black] & occupancies[white];
            if(enpassant != no_sq && !GET_BIT(attacks, enpassant) && enpassant == target) {
                int diffSquare = enpassant - 8;
                int move = ENCODE_MOVE(source, target, p, 0, 0, 0, 1, 0);
                if (makeMove(move, all_moves)) {
                    moveResult.diffMove = ENCODE_MOVE(diffSquare, diffSquare, sourcePiece, 0, 0, 0, 0, 0);
                    moveResult.notation = generateAlgebraicNotation(move);
                }
            }
            break;
        case n:
            attacks = knightAttacks[source] & ~occupancies[black];
            break;
        case r:
            attacks = getRookAttacks(source, occupancies[both]) & ~occupancies[black];
            break;
        case b:
            attacks = getBishopAttacks(source, occupancies[both]) & ~occupancies[black];
            break;
        case q:
            attacks = getQueenAttacks(source, occupancies[both]) & ~occupancies[black];
            break;
        case k:
            if (source == e8) {
                if (target == g8 && (castle & ks)) {
                    int move = ENCODE_MOVE(source, target, sourcePiece, 0, 0, 0, 0, 1);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = ENCODE_MOVE(h8, f8, sourcePiece, 0, 0, 0, 0, 0);
                        moveResult.notation = generateAlgebraicNotation(move);
                    }
                }
                if (target == c8 && (castle & qs)) {
                    int move = ENCODE_MOVE(source, target, sourcePiece, 0, 0, 0, 0, 1);
                    if (makeMove(move, all_moves)) {
                        moveResult.diffMove = ENCODE_MOVE(a8, d8, sourcePiece, 0, 0, 0, 0, 0);
                        moveResult.notation = generateAlgebraicNotation(move);
                    }
                }
            }
            attacks = kingAttacks[source] & ~occupancies[black];
            break;
        default:
            attacks = 0ULL;
    }

    if (moveResult.diffMove < 65) {
        if((1ULL << target) & attacks) {
            int move = ENCODE_MOVE(source, target, sourcePiece, 0, (targetPiece != -1), 0, 0, 0);
            if (makeMove(move, all_moves)) {
                moveResult.diffMove = 65;
                moveResult.notation = generateAlgebraicNotation(move);
            }
        } else {
            TAKE_BACK()
            return moveResult;
        }
    }
    return moveResult;
}

vector<int> getLegalMoves(int encodeSquare) {
    LOGI("parse fen: %llu", occupancies[both]);
    LOGI("getLegalMoves called with sq=%d", encodeSquare);
    int square = GET_SQUARE(encodeSquare);
    LOGI("getLegalMoves called with square=%d", square);
    int piece = GET_PIECE(encodeSquare);
    LOGI("getLegalMoves called with piece=%d", piece);
    vector<int> legalMoves;
    legalMoves.reserve(32);
    U64 attacks = 0ULL;
    COPY_BOARD()

    switch(piece) {
        case P:
            attacks = pawnAttacks[white][square] & ~occupancies[white];
            if(enpassant != no_sq) attacks &= (occupancies[black] |  (1ULL << enpassant));
            else attacks &= occupancies[black];
            if (!GET_BIT(occupancies[both], (square - 8))) {
                int move = ENCODE_MOVE(square, (square - 8), P, 0, 0, 0, 0, 0);
                if (makeMove(move, all_moves)) {
                    legalMoves.push_back(move);
                    TAKE_BACK()
                }
                if(square >= a2 && square <= h2 && !GET_BIT(occupancies[both], (square - 16))) {
                    move = ENCODE_MOVE(square, (square - 16), P, 0, 0, 1, 0, 0);
                    if (makeMove(move, all_moves)) {
                        legalMoves.push_back(move);
                        TAKE_BACK()
                    }
                }
            }
            break;
        case N:
            attacks = knightAttacks[square] & ~occupancies[white];
            LOGI("parse fen: %llu", attacks);
            break;
        case R:
            attacks = getRookAttacks(square, occupancies[both]) & ~occupancies[white];
            break;
        case B:
            attacks = getBishopAttacks(square, occupancies[both]) & ~occupancies[white];
            break;
        case Q:
            attacks = getQueenAttacks(square, occupancies[both]) & ~occupancies[white];
            break;
        case K:
            attacks = kingAttacks[square] & ~occupancies[white];
            if(castle & KS) {
                if(!GET_BIT(occupancies[both], f1) && !GET_BIT(occupancies[both],g1) ) {
                    if(!isSquareAttacked(f1, black) && !isSquareAttacked(g1, black)) {
                        int move = ENCODE_MOVE(square, g1, K, 0, 0, 0, 0, 1);
                        if (makeMove(move, all_moves)) {
                            legalMoves.push_back(move);
                            TAKE_BACK()
                        }
                    }
                }
            }
            if(castle & QS) {
                if(!GET_BIT(occupancies[both], c1) && !GET_BIT(occupancies[both], d1)) {
                    if(!isSquareAttacked(c1, white) && !isSquareAttacked(d1, white)) {
                        int move = ENCODE_MOVE(square, c1, K, 0, 0, 0, 0, 1);
                        if (makeMove(move, all_moves)) {
                            legalMoves.push_back(move);
                            TAKE_BACK()
                        }
                    }
                }
            }
            break;
        case p:
            attacks = pawnAttacks[black][square] & ~occupancies[black];
            if(enpassant != no_sq) attacks &= (occupancies[white] |  (1ULL << enpassant));
            else attacks &= occupancies[white];
            if (!GET_BIT(occupancies[both], (square + 8))) {
                int move = ENCODE_MOVE(square, (square + 8), p, 0, 0, 0, 0, 0);
                if (makeMove(move, all_moves)) {
                    legalMoves.push_back(move);
                    TAKE_BACK()
                }
                if(square >= a7 && square <= h7 && !GET_BIT(occupancies[both], (square + 16))) {
                    move = ENCODE_MOVE(square, (square + 16), p, 0, 0, 1, 0, 0);
                    if (makeMove(move, all_moves)) {
                        legalMoves.push_back(move);
                        TAKE_BACK()
                    }
                }
            }
            break;
        case n:
            attacks = knightAttacks[square] & ~occupancies[black];
            LOGI("parse fen: %llu", attacks);
            break;
        case r:
            attacks = getRookAttacks(square, occupancies[both]) & ~occupancies[black];
            break;
        case b:
            attacks = getBishopAttacks(square, occupancies[both]) & ~occupancies[black];
            break;
        case q:
            attacks = getQueenAttacks(square, occupancies[both]) & ~occupancies[black];
            break;
        case k:
            attacks = kingAttacks[square] & ~occupancies[black];
            if(castle & ks) {
                if(!GET_BIT(occupancies[both], f8) && !GET_BIT(occupancies[both], g8) ) {
                    if(!isSquareAttacked(f8, black) && !isSquareAttacked(g8, black)) {
                        int move = ENCODE_MOVE(square, g8, k, 0, 0, 0, 0, 1);
                        if (makeMove(move, all_moves)) {
                            legalMoves.push_back(move);
                            TAKE_BACK()
                        }
                    }
                }
            }
            if(castle & qs) {
                if(!GET_BIT(occupancies[both], c8) && !GET_BIT(occupancies[both], d8)) {
                    if(!isSquareAttacked(c8, white) && !isSquareAttacked(d8, white)) {
                        int move = ENCODE_MOVE(square, c8, k, 0, 0, 0, 0, 1);
                        if (makeMove(move, all_moves)) {
                            legalMoves.push_back(move);
                            TAKE_BACK()
                        }
                    }
                }
            }
            break;
        default:
            attacks = 0ULL;
    }

    while(attacks) {
        int targetSquare = getLastSignificant1stBit(attacks);
        POP_BIT(attacks, targetSquare);
        int move = ENCODE_MOVE(square, targetSquare, piece, 0, 1, 0, 0, 0);
        LOGI("moves: %d", targetSquare);
        if (makeMove(move, all_moves)) {
            LOGI("target: %d", GET_MOVE_TARGET(move));
            legalMoves.push_back(move);
            TAKE_BACK()
        }
    }

    TAKE_BACK()
    return legalMoves;
}

void initAll() {
    LOGI("init all");
    initCharPieces();
    initZobrist();
    precomputePieceAttacks();
    // initMagicNumbers();
    initSlidersAttacks(rook);
    initSlidersAttacks(bishop);
    initZobrist();
}

//int main() {
//    initAll();
//    return 0;
//}