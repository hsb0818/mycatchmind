const Game = {
    isStart : false
};

Game.Play = () => {
    console.log('game start')
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
    $('#wPaint').wPaint({
        path: '/wpaint/',
        theme:           'standard classic', // set theme
        autoScaleImage:  true,               // auto scale images to size of canvas (fg and bg)
        autoCenterImage: true,               // auto center images (fg and bg, default is left/top corner)
        menuHandle:      true,               // setting to false will means menus cannot be dragged around
        menuOrientation: 'horizontal',       // menu alignment (horizontal,vertical)
        menuOffsetLeft:  5,                  // left offset of primary menu
        menuOffsetTop:   5,                  // top offset of primary menu
        bg:              '#eeeeee',               // set bg on init
        image:           null,               // set image on init
        imageStretch:    false,              // stretch smaller images to full canvans dimensions
        onShapeDown:     null,               // callback for draw down event
        onShapeMove:     null,               // callback for draw move event
        onShapeUp:       null,                // callback for draw up event
    });

    window.requestAnimationFrame(loop);

    Game.Play();
});