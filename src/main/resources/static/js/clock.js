document.addEventListener('DOMContentLoaded', function() {
    const display = document.getElementById('display');
    const startBtn = document.getElementById('startBtn');
    const resetBtn = document.getElementById('resetBtn');
    const stopBtn = document.getElementById('stopBtn');
    const countdownBtn = document.getElementById('countdownbtn');
    const timerBtn = document.getElementById('timerbtn');
    const timeInput = document.getElementById('timeInput');

    let intervalId;
    let totalSeconds = 0;
    let isCountdown = true;
    let isRunning = false;


    function updateDisplay(seconds) {
        const minutes = Math.floor(seconds / 60);
        const remainingSeconds = seconds % 60;
        display.textContent = `${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}`;
    }
    function stop(){
        clearInterval(intervalId);
        isRunning=false;
    }

    function startTimer() {
        if (isRunning) return;
        isRunning = true;
        clearInterval(intervalId);
        intervalId = setInterval(() => {
            if (isCountdown) {
                if (totalSeconds > 0) {
                    totalSeconds--;
                    updateDisplay(totalSeconds);
                } else {
                    clearInterval(intervalId);
                    isRunning = false;
                    alert('Countdown finished!');
                }
            } else {
                totalSeconds++;
                updateDisplay(totalSeconds);
            }
        }, 1000);
    }

    function resetTimer() {
        clearInterval(intervalId);
        totalSeconds = 0;
        timeInput.value = ""
        isRunning = false;
        updateDisplay(totalSeconds);
    }
    function highlightButton(){
        if(isCountdown === true){
            countdownBtn.style.background = '#527bd5';
            timerBtn.style.background = 'white'
        } else{
            timerBtn.style.background = '#527bd5'
            countdownBtn.style.background = 'white';
        }
    }

    startBtn.addEventListener('click', () => {
        if (totalSeconds === 0 && timeInput.value) {
            const [minutes, seconds] = timeInput.value.split(':').map(Number);
            totalSeconds = minutes * 60 + seconds;
        }
        startTimer();
    });
    stopBtn.addEventListener('click', stop);

    resetBtn.addEventListener('click', resetTimer);

    countdownBtn.addEventListener('click', () => {
        isCountdown = true;
        highlightButton()
        resetTimer();
        timeInput.value = "";
    });

    timerBtn.addEventListener('click', () => {
        isCountdown = false;
        highlightButton()
        resetTimer();
        timeInput.value = "";
    });

    timeInput.addEventListener('keyup', (event) => {
        if (event.key === 'Enter') {
            const [minutes, seconds] = timeInput.value.split(':').map(Number);
            totalSeconds = minutes * 60 + seconds;
            updateDisplay(totalSeconds);
        }
    });
});