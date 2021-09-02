package com.davisdabols.shotsandbeer.ui

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.davisdabols.shotsandbeer.App
import com.davisdabols.shotsandbeer.common.*
import com.davisdabols.shotsandbeer.repository.GameRepository
import com.davisdabols.shotsandbeer.repository.models.GamePiece
import com.davisdabols.shotsandbeer.repository.models.HighScoreModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.util.*
import javax.inject.Inject

class GameViewModel : ViewModel() {

    @Inject
    lateinit var repository: GameRepository

    private var timer = object : CountDownTimer(MAX_GAME_TIME, 10){
        override fun onTick(elapsedTime: Long) {
            val date = Date(MAX_GAME_TIME - elapsedTime)
            _gameTimer.tryEmit(date.time.toTimeString())
        }

        override fun onFinish() {
            onGameOver()
        }
    }

    private val _highScores = MutableSharedFlow<List<HighScoreModel>>(replay = 1)
    private val _gamePieces = MutableSharedFlow<List<GamePiece>>(replay = 1)
    private val _guesses = MutableSharedFlow<List<GamePiece>>(replay = 1)
    private val _gameTimer = MutableSharedFlow<String>(replay = 1)
    private val _onGameOver = MutableSharedFlow<String?>(replay = 1)

    val highScores: SharedFlow<List<HighScoreModel>> = _highScores
    val gamePieces: SharedFlow<List<GamePiece>> = _gamePieces
    val guesses: SharedFlow<List<GamePiece>> = _guesses
    val gameTimer: SharedFlow<String> = _gameTimer
    val onGameOver: SharedFlow<String?> = _onGameOver

    init {
        App.component.inject(this)
        launchIO {
            repository.highScores.collect { notes ->
                _highScores.tryEmit(notes)
            }
        }
    }

    fun startGame() {
        _onGameOver.tryEmit(null)

        val pieces = mutableListOf<GamePiece>()
        (1..POSSIBLE_NUMBERS).forEach { number ->
            pieces.add(GamePiece(number, number))
        }
        pieces.shuffle()
        pieces.shuffle()

        pieces.drop(6)

        _gamePieces.tryEmit(pieces)
        timer.start()
    }

    fun checkInput(inputValue: String) {
        val guesses = _guesses.replayCache[0].toMutableList()
        val pieces = _gamePieces.replayCache[0].toMutableList()

        (1..PIECE_COUNT).forEach { index ->
            (1..PIECE_COUNT).forEach { indexTwo ->
                val inputDigit = inputValue[indexTwo].digitToInt()
                guesses.add(GamePiece(guesses.last().ID + 1, inputDigit))

                var piecesFoundThisAttempt = 0

                if(pieces[index].value == inputDigit) {
                    guesses[indexTwo].isFound = true
                    piecesFoundThisAttempt += 1
                } else if(pieces.any { it.value == inputDigit }) {
                    guesses[indexTwo].wrongLocation = true
                }

                if(piecesFoundThisAttempt == 4) {
                    onGameOver()
                }
            }
        }

        _guesses.tryEmit(guesses)
    }

    private fun onGameOver() {
        timer.cancel()
        val score = _gameTimer.replayCache[0]
        _onGameOver.tryEmit(_gameTimer.replayCache[0])

        launchIO {
            val id = _highScores.replayCache.lastOrNull()?.maxOfOrNull { it.id }?.plus(1) ?: 0
            repository.insertHighScore(HighScoreModel(id, Date().time.toDateString(), score))
        }
    }

}