const Game = {
    isStart : false,
    mainCanvas : null,
    mainCanvasContext : null,
    previousMouseX : null,
    previousMouseY : null,
};

function getMousePosOnCanvas(canvas, evt) {
    let rect = canvas.getBoundingClientRect();
    if (evt.clientX !== undefined && evt.clientY !== undefined) {
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    }
}

function stroke(ctx, x, y) {
    ctx.globalCompositeOperation = "source-over";
    ctx.lineJoin = ctx.lineCap = "round";
    ctx.lineWidth = 7;
    ctx.globalAlpha = "0.2";  //NOTE ALWAYS SET TO 'TRANSPARENT' needs variable if you want to switch to solid.
    ctx.beginPath();
    ctx.moveTo(Game.previousMouseX, Game.previousMouseY);
    ctx.lineTo(x, y);
    ctx.closePath();
    ctx.stroke();

    ctx.globalAlpha = "1";
    ctx.lineWidth = 4;
    ctx.beginPath();
    ctx.moveTo(Game.previousMouseX, Game.previousMouseY);
    ctx.lineTo(x, y);
    ctx.closePath();
    ctx.stroke();

    move(x, y);
}

function move(mouseX, mouseY) {
    Game.previousMouseX = mouseX;
    Game.previousMouseY = mouseY;
}

function clearCanvas(ctx, canvas) {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

Game.Play = () => {
    let $mainCanvas = $('#main-canvas');
    Game.mainCanvas = $mainCanvas[0];
    Game.mainCanvasContext = Game.mainCanvas.getContext('2d');
    Game.mainCanvas.width = 700;
    Game.mainCanvas.height = 500;

    let isDrawing  = false;
    $mainCanvas.on("mousedown", function(e) {
        if (myId === GameClient.drawer) {
            isDrawing = true;
            let pos = getMousePosOnCanvas(Game.mainCanvas, e);
            move(pos.x, pos.y);

            sock.send(JSON.stringify({
                [Config.PROTOCOL_PREFIX]: GAME_PROTOCOL.DRAW_UPDATE,
                x: pos.x,
                y: pos.y,
                init : 1,
            }));
        }
    });

    $mainCanvas.on("mousemove", function(e) {
        if (myId === GameClient.drawer) {
            if(isDrawing) {
                let pos = getMousePosOnCanvas(Game.mainCanvas, e);
                stroke(Game.mainCanvasContext, pos.x, pos.y);

                sock.send(JSON.stringify({
                    [Config.PROTOCOL_PREFIX]: GAME_PROTOCOL.DRAW_UPDATE,
                    x: pos.x,
                    y: pos.y,
                    init : 0,
                }));
            }
        }
    });

    $mainCanvas.on("mouseup", function(e) {
        isDrawing = false;
    });

    console.log('game start');
    Game.isStart = true;
};

let fps_timer = 0;
let sps_timer = 0;
Game.update = (timestamp, deltaTime) => {
    if (Game.isStart !== true)
        return;

    const time = timestamp;
    if (time - fps_timer >= GConfig.FPS) {
        physicsUpdate(deltaTime);
        fps_timer = time;
    }
    else
        fps_timer += deltaTime;

    if (sps_timer >= GConfig.SPS) {
        serverUpdate(deltaTime);
        sps_timer -= GConfig.SPS;
    }
    else
        sps_timer += deltaTime;
};

function physicsUpdate(deltaTime) {
}

function serverUpdate(deltaTime) {
}

function loop(timestamp) {
    let deltaTime = timestamp - lastRender;

    Game.update(timestamp, deltaTime);

    lastRender = timestamp;
    window.requestAnimationFrame(loop)
}
let lastRender = 0;

$(document).ready(() => {
    window.requestAnimationFrame(loop);

    Game.Play();
});