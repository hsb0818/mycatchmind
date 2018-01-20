var phaser = new Phaser.Game(GConfig.SCREEN_WIDTH, GConfig.SCREEN_HEIGHT, Phaser.CANVAS, document.getElementById('game'))
phaser.state.add('Game', Game)
phaser.state.start('Game')