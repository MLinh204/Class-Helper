const slidingImages = document.getElementById('slidingImages');
const imageContainer = document.getElementById('imageContainer');
const randomList = document.getElementById('randomList');
let animationId;
let randomStudents = [];

document.getElementById('addRandomStudent').addEventListener('click', function () {
    fetch('/students/api/all')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const { students, base64Photos } = data;
            const studentList = document.getElementById('studentList');
            students.forEach((student, index) => {
               const photoSrc = student.photo ? `data:image/jpeg;base64,${student.photo}` :
                                       (student.base64Photo ? `data:image/jpeg;base64,${student.base64Photo}` : '/uploads/default-profile.jpg');
                const studentElement = document.createElement('div');
                studentElement.className = 'col-md-4 mb-3';

                studentElement.innerHTML = `
                    <div class="card">
                        <img src="${photoSrc}" class="card-img-top" alt="${student.name}" style="height:300px;">
                        <div class="card-body">
                            <h5 class="card-title">${student.name}</h5>
                            <button class="btn btn-primary btn-sm add-student" data-id="${student.id}">Add</button>
                        </div>
                    </div>
                `;
                studentList.appendChild(studentElement);
            });
            new bootstrap.Modal(document.getElementById('addStudentModal')).show();
        })
        .catch(error => console.error('Error fetching students:', error));
});

document.getElementById('studentList').addEventListener('click', function(e) {
    if (e.target.classList.contains('add-student')) {
        const studentId = e.target.getAttribute('data-id');
        fetch(`/students/api/details/${studentId}`)
            .then(response => response.json())
            .then(data => {
                const student = data.student;
                const base64Photo = data.base64Photo;
                student.base64Photo = base64Photo;
                if (!randomStudents.some(s => s.id === student.id)) {
                    randomStudents.push(student);
                    console.log('Student added:', student);
                    updateRandomList();
                }
            });
    }
});

randomList.addEventListener('click', function(e) {
    if (e.target && e.target.classList.contains('remove-student')) {
        const studentId = e.target.getAttribute('data-id');
        console.log('Attempting to remove student with ID:', studentId);
        randomStudents = randomStudents.filter(student => student.id !== parseInt(studentId));
        updateRandomList();
    }
});

function updateRandomList() {
    console.log('Updating random list. Current students:', randomStudents);
    randomList.innerHTML = '';
    slidingImages.innerHTML = '';
    randomStudents.forEach(student => {
        const base64Photo = student.base64Photo; // Assuming base64Photo is part of each student object
        const photoSrc = base64Photo ? `data:image/jpeg;base64,${base64Photo}` : '/uploads/default-profile.jpg'; // Fallback to a default image
        const studentElement = document.createElement('div');
        studentElement.className = 'd-inline-block m-2 position-relative';
        studentElement.innerHTML = `
            <img src="${photoSrc}" class="student-image" alt="${student.name}">
                        <p>${student.name}</p>
                        <button class="btn btn-danger btn-sm position-absolute top-0 end-0 remove-student" data-id="${student.id}">-</button>
        `;
        randomList.appendChild(studentElement);

        const imageElement = document.createElement('img');
        imageElement.src = photoSrc;
        imageElement.className = 'student-image';
        imageElement.setAttribute('data-id', student.id);
        imageElement.alt = student.name;
        slidingImages.appendChild(imageElement);
    });
    console.log('Random list updated. DOM elements:', randomList.children.length);
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

        if (selectedStudent.base64Photo) {
            document.getElementById('selectedStudentImage').src = `data:image/jpeg;base64,${selectedStudent.base64Photo}`;
        } else {
            document.getElementById('selectedStudentImage').src = `/uploads/${selectedStudent.profilePictureName}`;
        }
        document.getElementById('selectedStudentName').textContent = selectedStudent.name;
        new bootstrap.Modal(document.getElementById('resultModal')).show();

        console.log('Selected student:', selectedStudent);
        randomStudents = randomStudents.filter(student => student.id !== selectedStudent.id);
        console.log('Students after random selection:', randomStudents);
        updateRandomList();
    }, 5000);
});