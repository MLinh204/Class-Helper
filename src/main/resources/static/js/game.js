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
            .catch(error => console.error('Error:', error));
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
            alert(updatedGame.winner === 'Tie' ? "It's a tie!" : `${updatedGame.winner} wins!`);
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
                   alert('Please select an answer before submitting.');
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
                         alert('Correct!');
                     } else {
                         console.log('Answer is incorrect');
                         alert('Not Correct!');
                     }
                   updateGameState(result.game);
                   questionModal.style.display = 'none';
               }).catch(error => {
                   console.error('Error:', error);
                   alert('An error occurred. Please try again.');
               });
           });
       }

    window.onclick = function(event) {
        if (event.target == questionModal) {
            questionModal.style.display = "none";
        }
    }
});