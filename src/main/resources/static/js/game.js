const Game = {
    isStart : false
};

Game.Play = () => {
    console.log('game start')
    Game.isStart = true;
};

Game.init = () => {
  phaser.stage.backgroundColor = "#000000";
  phaser.stage.disableVisibilityChange = true;
};

Game.preload = () => {

};

Game.create = () => {
};

let fps_timer = 0;
let sps_timer = 0;
Game.update = () => {
    if (Game.isStart !== true)
        return;

    const time = Date.now();
    if (time - fps_timer >= GConfig.FPS) {
        PhysicsUpdate(phaser.time.elapsedMS);
        fps_timer = time;
    }
    else
        fps_timer += phaser.time.elapsedMS;

    if (sps_timer >= GConfig.SPS) {
        ServerUpdate(phaser.time.elapsedMS);
        sps_timer -= GConfig.SPS;
    }
    else
        sps_timer += phaser.time.elapsedMS;
};

Game.render = () => {
    if (Game.isStart !== true)
        return;
};

function PhysicsUpdate(deltaMS) {
}

function ServerUpdate(deltaMS) {
}