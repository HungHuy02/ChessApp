//
// Created by HungHuy02 on 5/1/2025.
//

#ifndef BITBOARD_H
#define BITBOARD_H

#include <iostream>
#include <cstring>
#include <vector>
#include <random>
#include <map>
#include <unordered_map>

using namespace std;

struct MoveResult {
    string notation;
    int diffMove;
};

MoveResult makeMove(int source, char sourcePiece, int target, char targetPiece, char toPiece, bool isPuzzle);
vector<int> getLegalMoves(int encodeSquare);
bool parseFen(const char *fen);
void initAll();
int hasOneLegalMove();
string fenOtherPart();

#endif

