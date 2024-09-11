document.addEventListener('DOMContentLoaded', function() {
    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');
    let mode = 'draw';
    let isDrawing = false;
    let startX, startY;
    let lines = [''];
    let currentLine = 0;
    let cursorPosition = 0;
    let fontSize = 14;
    let fontFamily = 'Arial';
    let lineHeight = fontSize * 1.2;
    let padding = 20;
    let maxWidth = canvas.width - padding * 2;

    function startAction(e) {
        isDrawing = true;
        [startX, startY] = [e.offsetX, e.offsetY];
        if (mode === 'draw') {
            ctx.beginPath();
            ctx.moveTo(startX, startY);
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

    function renderText() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.font = `${fontSize}px ${fontFamily}`;
        ctx.fillStyle = 'black';
        lines.forEach((line, index) => {
            ctx.fillText(line, padding, padding + (index + 1) * lineHeight);
        });

        // Render cursor
        if (mode === 'type') {
            let cursorX = padding + ctx.measureText(lines[currentLine].substring(0, cursorPosition)).width;
            ctx.beginPath();
            ctx.moveTo(cursorX, padding + currentLine * lineHeight + 2);
            ctx.lineTo(cursorX, padding + (currentLine + 1) * lineHeight + 2);
            ctx.strokeStyle = 'black';
            ctx.stroke();
        }
    }

     function typeOnCanvas(key) {
            if (mode !== 'type') return;
            if (key === 'Enter') {
                lines.splice(currentLine + 1, 0, lines[currentLine].substring(cursorPosition));
                lines[currentLine] = lines[currentLine].substring(0, cursorPosition);
                currentLine++;
                cursorPosition = 0;
            } else {
                lines[currentLine] = lines[currentLine].substring(0, cursorPosition) + key + lines[currentLine].substring(cursorPosition);
                cursorPosition++;
            }
            renderText();
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
                if (cursorPosition > 0) {
                    lines[currentLine] = lines[currentLine].substring(0, cursorPosition - 1) + lines[currentLine].substring(cursorPosition);
                    cursorPosition--;
                } else if (currentLine > 0) {
                    cursorPosition = lines[currentLine - 1].length;
                    lines[currentLine - 1] += lines[currentLine];
                    lines.splice(currentLine, 1);
                    currentLine--;
                }
            } else if (e.key === 'ArrowLeft') {
                if (cursorPosition > 0) {
                    cursorPosition--;
                } else if (currentLine > 0) {
                    currentLine--;
                    cursorPosition = lines[currentLine].length;
                }
            } else if (e.key === 'ArrowRight') {
                if (cursorPosition < lines[currentLine].length) {
                    cursorPosition++;
                } else if (currentLine < lines.length - 1) {
                    currentLine++;
                    cursorPosition = 0;
                }
            } else if (e.key === 'ArrowUp' && currentLine > 0) {
                currentLine--;
                cursorPosition = Math.min(cursorPosition, lines[currentLine].length);
            } else if (e.key === 'ArrowDown' && currentLine < lines.length - 1) {
                currentLine++;
                cursorPosition = Math.min(cursorPosition, lines[currentLine].length);
            } else if (e.key === 'Enter'){
                typeOnCanvas('Enter')
            } else if (e.key.length === 1) {
                typeOnCanvas(e.key);
            }
            renderText();
            e.preventDefault();
        }
    });

    document.getElementById('drawBtn').addEventListener('click', function() {
        mode = 'draw';
        ctx.strokeStyle = '#000';
        ctx.lineWidth = 2;
    });

    document.getElementById('typeBtn').addEventListener('click', function() {
        mode = 'type';
        renderText();
    });

    document.getElementById('eraseBtn').addEventListener('click', function() {
        mode = 'erase';
    });

    document.getElementById('clearBtn').addEventListener('click', function() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        lines = [''];
        currentLine = 0;
        cursorPosition = 0;
    });

    // Initial render
    renderText();
});