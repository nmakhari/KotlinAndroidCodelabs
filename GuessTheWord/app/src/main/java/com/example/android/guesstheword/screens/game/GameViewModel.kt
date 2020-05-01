package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.lang.Math.random

class GameViewModel:ViewModel() {

    companion object{//timer constants
        private const val DONE = 0L
        private const val SECOND = 1000L
        private const val START_TIME = 60000L
    }

    //Creating and encapsulaitng the liveDatas
    private var _word = MutableLiveData<String>()
    //backing property
    val word: LiveData<String>
        get()= _word

    private var _score = MutableLiveData<Int>()

    val score: LiveData<Int>
        get() = _score

    private var _eventGameFinished = MutableLiveData<Boolean>()

    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    private var _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeSting = Transformations.map(currentTime){ time ->
        DateUtils.formatElapsedTime(time)
    }

    val hint = Transformations.map(word){ current ->
        val randomPosition = (1..current.length).random()
        "The current word has ${current.length} letters\n" +
                "and the letter in position $randomPosition \n is ${current.get(randomPosition-1).toUpperCase()}"
    }

    private lateinit var wordList: MutableList<String>

    private val timer: CountDownTimer


    init{
        Log.i("GameViewModel", "GameViewModel initialized")
        _score.value = 0
        _word.value = ""
        timer = object: CountDownTimer(START_TIME, SECOND){
            override fun onTick(p0: Long) {
                _currentTime.value = p0/SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinished()
            }
        }
        timer.start()
        resetList()
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "onCleared() called back")
        //makes sure to stop the timer
        timer.cancel()
    }

    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    private fun nextWord() {
        if(wordList.isEmpty()){
            //onGameFinished()
            resetList()
        }else {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }
    }

     fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

     fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    fun onGameFinished(){
        _eventGameFinished.value = true
    }

    fun onGameFinishedComplete(){
        _eventGameFinished.value = false
    }
}