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

    const popup = document.createElement('div');
    popup.id = 'popup';
    popup.style.display = 'none';
    document.body.appendChild(popup);


    function updateDisplay(seconds) {
        const minutes = Math.floor(seconds / 60);
        const remainingSeconds = seconds % 60;
        display.textContent = `${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}`;
    }
    function showPopup(message, isFinish=true){
        popup.textContent = message;
        popup.className = isFinish ? "success" : "error";
        popup.style.display = 'block';
        setTimeout(() =>{
            popup.style.display ='none';
        },10000);
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
                    showPopup('Time is up', true);
                    isRunning=false;
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
        isRunning=false;
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
        countdownBtn.style.background = '#527bd5';
        timerBtn.style.background = 'white'
        resetTimer();
        timeInput.value = "";
    });

    timerBtn.addEventListener('click', () => {
        isCountdown = false;
        timerBtn.style.background = '#527bd5'
        countdownBtn.style.background = 'white';
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