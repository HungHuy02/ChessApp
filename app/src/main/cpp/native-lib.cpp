//
// Created by HungHuy02 on 5/1/2025.
//
#include <jni.h>
#include "bitboard.h"

jobject convertToJavaMoveResult(JNIEnv* env, const MoveResult& result) {
    jclass moveResultClass = env->FindClass("com/huy/chess/data/model/MoveResult");
    if (moveResultClass == nullptr) {
        return nullptr;
    }
    jmethodID constructor = env->GetMethodID(moveResultClass, "<init>", "(Ljava/lang/String;I)V");
    if (constructor == nullptr) {
        return nullptr;
    }
    jstring jNotation = env->NewStringUTF(result.notation.c_str());
    jint jDiffMove = static_cast<jint>(result.diffMove);
    return env->NewObject(moveResultClass, constructor, jNotation, jDiffMove);
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_huy_chess_ui_component_ChessBoardKt_makeMove(JNIEnv *env, jobject thiz, jint source,
                                            jchar source_piece, jint target, jchar target_piece,
                                            jchar to_piece) {
    MoveResult result = makeMove(source, source_piece, target, target_piece, to_piece);
    return convertToJavaMoveResult(env, result);
}

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_huy_chess_ui_component_ChessBoardKt_getLegalMoves(JNIEnv *env, jobject thiz, jint square) {
    vector<int> result = getLegalMoves(square);
    jintArray jresult = env->NewIntArray(result.size());
    env->SetIntArrayRegion(jresult, 0, result.size(), result.data());
    return jresult;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_huy_chess_ui_component_ChessBoardKt_parseFen(JNIEnv *env, jobject thiz, jstring jfen) {
    const char *fen = env->GetStringUTFChars(jfen, 0);
    parseFen(fen);
    env->ReleaseStringUTFChars(jfen, fen);
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_huy_chess_ui_component_ChessBoardKt_hasOneLegalMove(JNIEnv *env, jobject thiz) {
    const bool result = hasOneLegalMove();
    return result ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    initAll();
    return JNI_VERSION_1_6;
}