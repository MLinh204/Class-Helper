document.addEventListener('DOMContentLoaded', function() {
    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');
    let mode = 'draw';
    let isDrawing = false;
    let startX, startY;
    let text = '';
    let textPosition = { x: 0, y: 0 };

    function startAction(e) {
        isDrawing = true;
        [startX, startY] = [e.offsetX, e.offsetY];

        if (mode === 'draw') {
            ctx.beginPath();
            ctx.moveTo(startX, startY);
        } else if (mode === 'type') {
            textPosition = { x: startX, y: startY };
        }
    }

    function endAction() {
        isDrawing = false;
    }

    function draw(e) {
        if (!isDrawing || mode !== 'draw') return;
        ctx.lineTo(e.offsetX, e.offsetY);
        ctx.stroke();
    }

    function erase(e) {
        if (!isDrawing || mode !== 'erase') return;
        ctx.clearRect(e.offsetX - 15, e.offsetY - 15, 30, 30);
    }

    function typeOnCanvas(e) {
        if (mode !== 'type') return;
        text += e.key;
        ctx.font = '20px Arial';
        ctx.fillStyle = 'black';
        ctx.fillText(text, textPosition.x, textPosition.y);
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

    document.addEventListener('keydown', function(e) {
        if (mode === 'type') {
            if (e.key === 'Backspace') {
                text = text.slice(0, -1);
                // Clear the area and redraw the text
                ctx.clearRect(textPosition.x, textPosition.y - 20, ctx.measureText(text + e.key).width, 25);
                ctx.fillText(text, textPosition.x, textPosition.y);
            } else if (e.key.length === 1) {
                typeOnCanvas(e);
            }
        }
    });

    document.getElementById('drawBtn').addEventListener('click', function() {
        mode = 'draw';
        ctx.strokeStyle = '#000';
        ctx.lineWidth = 2;
    });

    document.getElementById('typeBtn').addEventListener('click', function() {
        mode = 'type';
        text = '';
    });

    document.getElementById('eraseBtn').addEventListener('click', function() {
        mode = 'erase';
    });

    document.getElementById('clearBtn').addEventListener('click', function() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        text = '';
    });
});