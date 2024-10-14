document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('editStudentForm').addEventListener('submit', function(e) {
        e.preventDefault();

        var form = this;
        var formData = new FormData(form);

        fetch(form.action, {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                // Success, close modal and refresh page
                window.location.reload();
            } else {
                // Handle validation errors
                throw new Error(data.message || 'An error occurred. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            var errorMessageElement = document.getElementById('editErrorMessage');
            errorMessageElement.textContent = error.message || 'An error occurred. Please try again.';
            errorMessageElement.style.display = 'block';

            // Scroll to the error message
            errorMessageElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
        });
    });
});