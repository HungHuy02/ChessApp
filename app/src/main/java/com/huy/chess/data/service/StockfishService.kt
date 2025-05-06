package com.huy.chess.data.service

import android.content.Context
import android.util.Log
import com.huy.chess.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class StockfishService(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    private lateinit var process: Process
    private var suggestProcess: Process? = null
    private var depth = 0
    private var time = 0

    fun setupNewGame(level: Int, enableSuggestion: Boolean = false) {
        process = setupProcess()
        setupStockfish(level)
        if(enableSuggestion) {
            suggestProcess = setupProcess()
            sendCommand(suggestProcess, "setoption name Skill Level value 20")
        }
    }

    private fun setupProcess(): Process {
        try {
            val process = Runtime.getRuntime()
                .exec(context.applicationInfo.nativeLibraryDir + "/lib_stockfish.so")
            sendCommand(process,"uci")
            return process
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun setupStockfish(level: Int) {
        val skillLevel = when (level) {
            1 -> {
                depth = 5
                time = 50
                -9
            }
            2 -> {
                depth = 5
                time = 100
                -5
            }
            3 -> {
                depth = 5
                time = 150
                -1
            }
            4 -> {
                depth = 5
                time = 200
                3
            }
            5 -> {
                depth = 5
                time = 300
                7
            }
            6 -> {
                depth = 8
                time = 400
                11
            }
            7 -> {
                depth = 13
                time = 500
                16
            }
            8 -> {
                depth = 22
                time = 1000
                20
            }
            else -> throw IllegalStateException("Unexpected value: $level")
        }
        sendCommand(process,"setoption name Skill Level value $skillLevel")
    }

    private fun sendCommand(process: Process?, command: String) {
        try {
            if (process != null) {
                process.outputStream.write("$command\n".toByteArray())
                process.outputStream.flush()
            }
        } catch (e: IOException) {
            throw java.lang.RuntimeException(e)
        }
    }

    suspend fun findBestMove(fen: String?): String {
        sendCommand(process,"position fen $fen")
        sendCommand(process,"go depth $depth movetime $time")
        return getResponse(process)
    }

    private suspend fun getResponse(process: Process?): String = withContext(dispatcher){
        if (process == null) {
            return@withContext ""
        }
        val out = BufferedReader(InputStreamReader(process.inputStream), 16384)
        var data: String
        try {
            var lastReceivedTime = System.currentTimeMillis()
            while (true) {
                while ((out.readLine().also { data = it }) != null) {
                    Log.e("test", data)
                    if (data.startsWith("bestmove")) {
                        val strings =
                            data.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (strings[0] == "bestmove") {
                            return@withContext strings[1]
                        }
                    }
                    lastReceivedTime = System.currentTimeMillis()
                }
                if (System.currentTimeMillis() - lastReceivedTime > 2000) {
                    break
                }
            }
            return@withContext ""
        } catch (e: IOException) {
            throw java.lang.RuntimeException(e)
        }
    }

    fun destroy() {
        sendCommand(process, "quit")
        process.destroy()
        suggestProcess?.let {
            sendCommand(suggestProcess, "quit")
            suggestProcess!!.destroy()
        }
    }
}