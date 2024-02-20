document.getElementById('formCreate').addEventListener('submit', function (event){
    event.preventDefault();

    var formData = new FormData(this);
    var jsonData = {};
    formData.forEach(function (value, key) {
        jsonData[key] = value;
    });

    fetch('/create', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(jsonData)
    })
        .then(response => {
            if (response.ok) {
                document.getElementById('description').value = '';
                document.getElementById('createdSelect').value = 'IN_PROGRESS';
                location.reload();
            } else {
                return response.text();
            }
        })
        .then(errorMessage => {
            if (errorMessage) {
                var errorDiv = document.createElement('div');
                errorDiv.textContent = errorMessage;
                errorDiv.style.color = 'red';
                document.getElementById('formCreate').appendChild(errorDiv);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
});





function editTask(id) {
    var descriptionSpan = document.getElementById('description_' + id);
    var descriptionInput = document.getElementById('descriptionInput_' + id);
    var statusSpan = document.getElementById('status_' + id);
    var statusSelect = document.getElementById('statusSelect_' + id);

    descriptionSpan.style.display = 'none';
    descriptionInput.value = descriptionSpan.innerText;
    descriptionInput.style.display = 'inline';

    statusSpan.style.display = 'none';
    statusSelect.value = statusSpan.innerText;
    statusSelect.style.display = 'inline';

    var editButton = document.querySelector('#editButton_' + id);
    editButton.innerText = 'Save';
    editButton.setAttribute('onclick', 'saveTask(' + id + ')');

    var deleteButton = document.querySelector('#deleteButton_' + id);
    deleteButton.style.display = 'none';
}

function deleteTask(id) {
    console.log(id);
    fetch('/delete/' + id, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert('Failed to delete task.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while deleting the task.');
        });
}

function saveTask(id) {
    var descriptionInput = document.getElementById('descriptionInput_' + id);
    var statusSelect = document.getElementById('statusSelect_' + id);

    var data = {
        id: id,
        description: descriptionInput.value,
        status: statusSelect.value
    };

    fetch('/update', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                response.text().then(errorMessage => {
                    alert(errorMessage);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while saving changes.');
        });
}