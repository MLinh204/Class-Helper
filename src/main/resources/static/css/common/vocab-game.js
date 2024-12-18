const studentSelection = document.getElementById("student-selection");
const vocabGroupSelection = document.getElementById("vocab-group-selection");
const gameDashboard = document.getElementById("game-dashboard");
const gameplay = document.getElementById("gameplay");
const gameEnd = document.getElementById("game-end");
const vocabModal = new bootstrap.Modal(document.getElementById("vocab-modal"));
const alertModal = new bootstrap.Modal(document.getElementById("alert-modal"));

let selectedStudentId = null;
let selectedVocabGroupId = null;
let useHint = false;
const correctColor = "#b5d9a6";
const incorrectColor = "#ff4949";
const basicColor = "#d1ecf1";

document.addEventListener("DOMContentLoaded", () => {
    const students = document.getElementById("students-list");
    fetch("students/api/all")
        .then(response => response.json())
        .then(data => {
            data.students.forEach(student => {
                const card = document.createElement("div");
                card.className = "col-md-4 game-card";
                card.innerHTML = `
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${student.name}</h5>
                            <button class="btn btn-primary" onclick="selectStudent(${student.id})">Select</button>
                        </div>
                    </div>`;
                students.appendChild(card);
            });
        })
        .catch(error => console.error("Error fetching students:", error));

    const vocabGroups = document.getElementById("vocab-groups-list");
    fetch("vocab-group/api/all")
        .then(response => response.json())
        .then(data => {
            // Directly iterate over `data` since it's an array
            data.forEach(vocabGroup => {
                const card = document.createElement("div");
                card.className = "col-md-4 game-card";
                card.innerHTML = `
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${vocabGroup.vocabGroupName}</h5>
                            <button class="btn btn-primary" onclick="selectVocabGroup(${vocabGroup.id})">Select</button>
                        </div>
                    </div>`;
                vocabGroups.appendChild(card);
            });
        })
        .catch(error => console.error("Error fetching vocab groups:", error));

    // Functions for navigation
    window.selectStudent = (studentId) => {
        selectedStudentId = studentId;
        console.log("Selected Student ID: ", selectedStudentId);
        studentSelection.classList.add("d-none");
        vocabGroupSelection.classList.remove("d-none");
    };

    window.selectVocabGroup = (groupId) => {
        selectedVocabGroupId = groupId;
        console.log("Selected Vocab Group ID: ", selectedVocabGroupId);
        vocabGroupSelection.classList.add("d-none");
        gameDashboard.classList.remove("d-none");
    };

    document.getElementById("start-game").addEventListener("click", () => {
        if (!selectedStudentId || !selectedVocabGroupId) {
            showAlert("Please select a student and a vocab group before starting the game.", incorrectColor);
            return;
        }

        // Initialize game
        fetch(`/vocab-game/init?studentId=${selectedStudentId}&vocabGroupId=${selectedVocabGroupId}`, {
            method: "POST",
        })
        .then(response => {
            if (!response.ok) throw new Error("Failed to initialize game");
            return response.text();
        })
        .then(gameId => {
            console.log("Game initialized. ID:", gameId);

            // Store the game ID for later use
            localStorage.setItem("gameId", gameId);

            // Show the gameplay screen
            document.getElementById("gameplay").classList.remove("d-none");
            document.getElementById("game-dashboard").classList.add("d-none");
        })
        .catch(error => console.error("Error initializing game:", error));
    });

   document.getElementById("get-vocab").addEventListener("click", () => {
       const gameId = localStorage.getItem("gameId"); // Retrieve stored gameId

       if (!gameId) {
           showAlert("Game is not initialized. Please restart the game.", incorrectColor);
           return;
       }

       // Fetch next vocab
       fetch(`/vocab-game/${gameId}/next-vocab`)
           .then(response => {
                   if (!response.ok) {
                       return response.text().then(errorMessage => {
                           if (response.status === 400) {
                               console.warn("Game over:", errorMessage);
                               displayGameEnd(); // Show game end modal
                           }
                           throw new Error(errorMessage);
                       });
                   }
                   return response.json();
           })
           .then(vocab => {
                console.log("Next Vocab:", vocab);
                // Display the vocabulary word in the modal
                document.getElementById("vocab-word").innerText = vocab.word || "N/A";
                vocabModal.show();
           })
           .catch(error => console.error("Error fetching next vocab:", error));
   });

    document.getElementById("correct-btn").addEventListener("click", () => {
        submitAnswer(true, useHint);
        showAlert("Answer submitted: Correct!", correctColor);
        resetHint();
    });

    document.getElementById("incorrect-btn").addEventListener("click", () => {
        submitAnswer(false, useHint);
        showAlert("Answer submitted: Incorrect", incorrectColor);
        resetHint();
    });
    document.getElementById("hint-btn").addEventListener("click", () => {
        useHint = true;
        showAlert("Hint used! Proceed with your answer.", basicColor);
    });
    document.getElementById("return").addEventListener("click", () => {
        const gameId = localStorage.getItem("gameId");
        if (!gameId) {
           showAlert("Game is not initialized. Please restart the game.", incorrectColor);
           return;
       }
        if (!selectedStudentId) {
            showAlert("Student not found!", incorrectColor);
            return;
        }
        const studentId = selectedStudentId;
        fetch(`/vocab-game/${gameId}/game-end/${studentId}`, {
            method: "POST",
        })
            .then(response => {
                if (!response.ok) throw new Error("Failed to end game", incorrect-color);
                return response.text();
            })
            .then(() => {
                window.location.href = "/vocab-game";
            })
            .catch(error => {
                console.error("Error ending game:", error)
                showAlert("An error occurred. Please try again.", incorrectColor);
            });
    });
    function displayGameEnd() {
        document.getElementById("gameplay").classList.add("d-none");
        document.getElementById("game-end").classList.remove("d-none");

        const gameId = localStorage.getItem("gameId");

        fetch(`/vocab-game/game-session/${gameId}`)
            .then(response => response.json())
            .then(data => {
                const correctAnswers = data.correctAnswers || 0;
                const incorrectAnswers = data.incorrectAnswers || 0;
                const totalPoints = data.totalPoints || 0;

                document.getElementById("correct-answers").innerText = correctAnswers;
                document.getElementById("incorrect-answers").innerText = incorrectAnswers;
                document.getElementById("total-points").innerText = totalPoints;
            })
            .catch(error => {
                        console.error('Error fetching game session data:', error);
                        showAlert('Failed to retrieve game session data.', incorrectColor);
                    });

            showAlert("Game Over! No more vocabulary words left.", basicColor);
    }

    function submitAnswer(isCorrect, useHint) {
        const gameId = localStorage.getItem("gameId");
        if(!gameId){
         showAlert("Game is not initialized. Please restart the game.", incorrectColor);
         return;
         }
         fetch(`/vocab-game/${gameId}/submit-answer?isCorrect=${isCorrect}&useHint=${useHint}`, {
            method: "POST",
         })
         .then(response => {
             if (!response.ok) throw new Error("Failed to submit answer");
             return response.text();
         })
         .then(() => {
                 vocabModal.hide();
                 showAlert(`Answer submitted: ${isCorrect ? "Correct" : "Incorrect"}`, isCorrect ? correctColor : incorrectColor);
             })
             .catch(error => console.error("Error submitting answer:", error));
        }
        function resetHint() {
            useHint = false;
        }
        function showAlert(message, color) {
                console.log("showAlert called with message:", message, "and color:", color);
                document.getElementById("alert-message").innerText = message;
                document.querySelector("#alert-modal .modal-content").style.backgroundColor = color;
                alertModal.show();
            }
});
