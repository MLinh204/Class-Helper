document.addEventListener('DOMContentLoaded', function() {
    const cells = document.querySelectorAll('.cell');
    const submitButton = document.getElementById('submitAnswer');
    const selectedPositionInput = document.getElementById('selectedPosition');
    const gameForm = document.getElementById('gameForm');
    const questionModal = document.getElementById('questionModal');
    const questionText = document.getElementById('questionText');
    const optionsContainer = document.getElementById('optionsContainer');
    let selectedCell = null;
    let currentQuestion = null;
    let currentPlayer = 'X'; // This should be dynamically set based on the game state

    // Create popup element
    const popup = document.createElement('div');
    popup.id = 'popup';
    popup.style.display = 'none';
    document.body.appendChild(popup);

    function showPopup(message, isSuccess = true) {
        popup.textContent = message;
        popup.className = isSuccess ? 'success' : 'error';
        popup.style.display = 'block';
        setTimeout(() => {
            popup.style.display = 'none';
        }, 3000);
    }

    function selectCell(cell) {
        if (cell.innerText === '') {
            if (selectedCell) {
                selectedCell.classList.remove('selected');
            }
            selectedCell = cell;
            cell.classList.add('selected');
            if (selectedPositionInput) {
                selectedPositionInput.value = cell.getAttribute('data-index');
            }
            fetchQuestion(cell.getAttribute('data-index'));
        }
    }

    function fetchQuestion(position) {
        const gameId = document.querySelector('input[name="gameId"]').value;
        const quizId = document.querySelector('input[name="quizId"]').value;
        fetch(`/game/${gameId}/question?quizId=${quizId}&position=${position}`)
            .then(response => response.json())
            .then(data => {
                    currentQuestion = data;
                    displayQuestion(data);
            })
            .catch(error => {
                console.error('Error:', error);
                showPopup('Failed to fetch question. Please try again.', false);
            });
    }
    function displayQuestion(question) {
        questionText.textContent = question.questionText;
        optionsContainer.innerHTML = '';
        question.options.forEach((option, index) => {
            const radioBtn = document.createElement('div');
            radioBtn.className = 'form-check';
            radioBtn.innerHTML = `
                <input class="form-check-input" type="radio" name="answer" id="option${index}" value="${index}">
                <label class="form-check-label" for="option${index}">${option}</label>
            `;
            optionsContainer.appendChild(radioBtn);
        });
        submitButton.disabled = false;
        questionModal.style.display = 'block';
    }

    function updateCell(cell, symbol) {
        cell.textContent = symbol;
        cell.classList.remove('selected');
    }

    function updateGameState(updatedGame) {
        currentPlayer = updatedGame.currentPlayer.symbol;
        document.querySelector('h2').textContent = `Current Player: ${updatedGame.currentPlayer.name}`;

        updatedGame.board.forEach((value, index) => {
            cells[index].textContent = value;
        });

        if (updatedGame.winner) {
            showPopup(updatedGame.winner === 'Tie' ? "It's a tie!" : `${updatedGame.winner} wins!`);
            document.querySelector('.game-container').style.display = 'none';
                        const winnerMessage = document.createElement('div');
                        winnerMessage.innerHTML = `
                            <h2>${updatedGame.winner === 'Tie' ? "It's a Tie!" : `Winner: ${updatedGame.winner}`}</h2>
                            <a href="/game/new" class="btn btn-primary mt-3">Start New Game</a>
                        `;
                        document.querySelector('.container').appendChild(winnerMessage);
        }
    }

    cells.forEach(cell => {
        cell.addEventListener('click', function() {
            selectCell(this);
        });
    });

    if (gameForm) {
        gameForm.addEventListener('submit', function(event) {
            event.preventDefault();

            const selectedAnswer = document.querySelector('input[name="answer"]:checked');
            if (!selectedAnswer) {
                showPopup('Please select an answer before submitting.', false);
                return;
            }

            const selectedAnswerValue = parseInt(selectedAnswer.value);

            fetch(gameForm.action, {
                method: 'POST',
                body: new FormData(gameForm)
            }).then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error('Network response was not ok.');
            }).then(result => {
                console.log('API Response:', result);
                if (result.isCorrect) {
                    console.log('Answer is correct');
                    showPopup('Correct!', true);
                } else {
                    console.log('Answer is incorrect');
                    showPopup('Not Correct!', false);
                }
                updateGameState(result.game);
                questionModal.style.display = 'none';
            }).catch(error => {
                console.error('Error:', error);
                showPopup('An error occurred. Please try again.', false);
            });
        });
    }

    window.onclick = function(event) {
        if (event.target == questionModal) {
            questionModal.style.display = "none";
        }
    }
});