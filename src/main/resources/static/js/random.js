const slidingImages = document.getElementById('slidingImages');
const imageContainer = document.getElementById('imageContainer');
const randomList = document.getElementById('randomList');
let animationId;
let randomStudents = [];

document.getElementById('addRandomStudent').addEventListener('click', function() {
    fetch('/students/all')
        .then(response => response.json())
        .then(students => {
            const studentList = document.getElementById('studentList');
            studentList.innerHTML = '';
            students.forEach(student => {
                const studentElement = document.createElement('div');
                studentElement.className = 'col-md-4 mb-3';
                studentElement.innerHTML = `
                    <div class="card">
                        <img src="${student.profilePicture}" class="card-img-top" alt="${student.name}" style="height:300px;" >
                        <div class="card-body">
                            <h5 class="card-title">${student.name}</h5>
                            <button class="btn btn-primary btn-sm add-student" data-id="${student.id}">Add</button>
                        </div>
                    </div>
                `;
                studentList.appendChild(studentElement);
            });
            new bootstrap.Modal(document.getElementById('addStudentModal')).show();
        });
});

document.getElementById('studentList').addEventListener('click', function(e) {
    if (e.target.classList.contains('add-student')) {
        const studentId = e.target.getAttribute('data-id');
        fetch(`/students/${studentId}/details`)
            .then(response => response.json())
            .then(student => {
                if (!randomStudents.some(s => s.id === student.id)) {
                    randomStudents.push(student);
                    updateRandomList();
                }
            });
    }
});

function updateRandomList() {
    randomList.innerHTML = '';
    slidingImages.innerHTML = '';
    randomStudents.forEach(student => {
        const studentElement = document.createElement('div');
        studentElement.className = 'd-inline-block m-2';
        studentElement.innerHTML = `
            <img src="${student.profilePicture}" class="student-image" alt="${student.name}">
            <p>${student.name}</p>
        `;
        randomList.appendChild(studentElement);
        const imageElement = document.createElement('img');
        imageElement.src = student.profilePicture;
        imageElement.className = 'student-image';
        imageElement.setAttribute('data-id', student.id);
        imageElement.alt = student.name;
        slidingImages.appendChild(imageElement);
    });
}

function slideImages() {
    let position = 0;
    const speed = 2;

    function animate() {
        position -= speed;
        if (position <= -100) {
            position = 0;
            slidingImages.appendChild(slidingImages.firstElementChild);
        }
        slidingImages.style.transform = `translateX(${position}px)`;
        animationId = requestAnimationFrame(animate);
    }

    animate();
}

document.getElementById('getRandomStudent').addEventListener('click', function() {
    if (randomStudents.length === 0) {
        alert('Please add students to the random list first.');
        return;
    }

    slideImages();

    setTimeout(() => {
        cancelAnimationFrame(animationId);
        const randomIndex = Math.floor(Math.random() * randomStudents.length);
        const selectedStudent = randomStudents[randomIndex];
        const selectedImage = document.querySelector(`.student-image[data-id="${selectedStudent.id}"]`);
        const containerWidth = imageContainer.offsetWidth;
        const imageWidth = selectedImage.offsetWidth;
        const scrollPosition = selectedImage.offsetLeft - (containerWidth / 2) + (imageWidth / 2);
        slidingImages.style.transition = 'transform 0.5s ease';
        slidingImages.style.transform = `translateX(-${scrollPosition}px)`;

        document.getElementById('selectedStudentImage').src = selectedStudent.profilePicture;
        document.getElementById('selectedStudentName').textContent = selectedStudent.name;
        new bootstrap.Modal(document.getElementById('resultModal')).show();
    }, 5000);
});