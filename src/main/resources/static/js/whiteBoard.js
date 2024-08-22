document.addEventListener('DOMContentLoaded', function() {
    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');
    let mode = 'draw';
    let isDrawing = false;
    let startX, startY;
    let textBoxes = [];

    function startAction(e) {
        isDrawing = true;
        [startX, startY] = [e.clientX, e.clientY];

        if (mode === 'draw') {
            [startX, startY] = [e.offsetX, e.offsetY];
            ctx.beginPath();
            ctx.stroke
            ctx.moveTo(startX, startY);
        }
    }

    function endAction(e) {
        isDrawing = false;

        if (mode === 'type') {
            createTextBox(e.clientX, e.clientY);
        }
    }

    function draw(e) {
        [startX, startY] = [e.offsetX, e.offsetY];
        if (!isDrawing || mode !== 'draw') return;
        ctx.lineTo(e.offsetX, e.offsetY);
        ctx.stroke();
    }

    function erase(e) {
        if (!isDrawing || mode !== 'erase') return;
        ctx.clearRect(e.offsetX - 15, e.offsetY - 15, 30, 30);
    }

    function createTextBox(endX, endY) {
        const textBox = document.createElement('textarea');
        textBox.style.position = 'absolute';
        textBox.style.left = `${Math.min(startX, endX)}px`;
        textBox.style.top = `${Math.min(startY, endY)}px`;
        textBox.style.width = `${Math.abs(endX - startX)}px`;
        textBox.style.height = `${Math.abs(endY - startY)}px`;
        textBox.style.border = '1px solid #000';
        textBox.style.backgroundColor = 'rgba(255, 255, 255, 0.7)';
        textBox.style.zIndex = '1000';
        document.body.appendChild(textBox);
        textBoxes.push(textBox);
    }

    canvas.addEventListener('mousedown', startAction);
    canvas.addEventListener('mouseup', endAction);
    canvas.addEventListener('mousemove', function(e) {
        if (mode === 'draw') {
            draw(e);
        } else if (mode === 'erase') {
            erase(e);
        }
    });

    document.getElementById('drawBtn').addEventListener('click', function() {
        mode = 'draw';
        ctx.strokeStyle = '#000';
        ctx.lineWidth = 2;
    });

    document.getElementById('typeBtn').addEventListener('click', function() {
        mode = 'type';
    });

    document.getElementById('eraseBtn').addEventListener('click', function() {
        mode = 'erase';
    });

    document.getElementById('clearBtn').addEventListener('click', function() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        textBoxes.forEach(box => box.remove());
        textBoxes = [];
    });
});