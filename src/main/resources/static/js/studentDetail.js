document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('editStudentForm').addEventListener('submit', function(e) {
        e.preventDefault();
        var form = this;
        var formData = new FormData(form);

        fetch(form.action, {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.success === false) {
                // Handle the error case
                if (data.message === "A student with this name already exists.") {
                    alert('That student name already exists. Please choose another name.');
                } else {
                    throw new Error(data.message || 'An error occurred. Please try again.');
                }
            } else {
                // Success case
                // Close the modal if it exists
                var modal = document.getElementById('editStudentModal');
                if (modal) {
                    modal.style.display = 'none';
                }
                // Redirect to the updated student's page
                if (data.id) {
                    window.location.href = '/students/' + data.id;
                } else {
                    // Fallback if id is not provided
                    window.location.href = '/students';
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
            var errorMessageElement = document.getElementById('editErrorMessage');
            errorMessageElement.textContent = error.message;
            errorMessageElement.style.display = 'block';

            // Scroll to the error message
            errorMessageElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
        });
    });
});