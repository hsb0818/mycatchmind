const GAME_PROTOCOL = {
    JOIN : 0,
    GAMESTART : 1,
    CHAT : 2,
    DRAW_UPDATE : 3,
    SELECT_STATE : 4,
    CORANSWER : 5,
    BREAK_GAME : 6,
};

const STATE_PROTOCOL = {
  CLEAR : 0,
};

GameClient = {
    drawer : null,
};

let sock = new SockJS("http://" + Config.SERVER_IP + ":9208/khwebgame-game");
sock.onopen = function() {
    console.log('open');

    sock.send(JSON.stringify({
        [Config.PROTOCOL_PREFIX] : GAME_PROTOCOL.JOIN
    }));
};

sock.onmessage = function(e) {
    console.log('message', e.data);
    let dataMap = JSON.parse(e.data);
    if (!dataMap[Config.PROTOCOL_SUC]) {
        console.log('[suc] receive is failed');
        return;
    }

    console.log(">>" + dataMap);
    switch (dataMap[Config.PROTOCOL_PREFIX])
    {
        case GAME_PROTOCOL.JOIN: {
            join($('#users-outter'), dataMap['users']);

            if (parseInt(dataMap['readyToStart']) === 1) {
                sock.send(JSON.stringify({
                    [Config.PROTOCOL_PREFIX] : GAME_PROTOCOL.GAMESTART,
                }));
            }

            break;
        }
        case GAME_PROTOCOL.GAMESTART: {
            gameStart(dataMap['drawerID'], dataMap['word']);
            break;
        }
        case GAME_PROTOCOL.CHAT: {
            chatEnter(dataMap['userName'], dataMap['chat']);
            break;
        }
        case GAME_PROTOCOL.DRAW_UPDATE: {
            drawUpdate(parseInt(dataMap['x']), parseInt(dataMap['y']), parseInt(dataMap['init']));
            break;
        }
        case GAME_PROTOCOL.SELECT_STATE: {
            const state = parseInt(dataMap['state']);
            switch (state) {
                case STATE_PROTOCOL.CLEAR: {
                    clearCanvas(Game.mainCanvasContext, Game.mainCanvas);
                    break;
                }
            }

            break;
        }
        case GAME_PROTOCOL.CORANSWER: {
            correctAnswer(dataMap['userName'], parseInt(dataMap['correct']), dataMap);
            break;
        }
        case GAME_PROTOCOL.BREAK_GAME: {
            alert('Other user has exited at this room!');
            window.location.replace("http://" + Config.SERVER_IP + ":9208/user/lobby");
            break;
        }
    }
};

sock.onclose = function() {
    console.log('close');
};

$(document).ready(() => {
    $('#chat-send').click(() => {
        let $chatInput = $('#chat-input');

        sock.send(JSON.stringify({
            [Config.PROTOCOL_PREFIX] : GAME_PROTOCOL.CHAT,
            chat: $chatInput.val()
        }));

        chatEnter(myName, $chatInput.val());
        $chatInput.val('');
    });

    let $sendAnswer = $('#send-answer');
    $sendAnswer.click((e) => {
        let word = prompt("Enter your answer!", "answer word");
        if (word !== null) {
            $sendAnswer.prop("disabled", true);
            setTimeout(() => {
                $sendAnswer.prop("disabled", false);
                }, 5000);

            sock.send(JSON.stringify({
                [Config.PROTOCOL_PREFIX] : GAME_PROTOCOL.CORANSWER,
                word : word
            }));
        }
    });

    $('#clear-canvas').click((e) => {
        if (myId === GameClient.drawer) {
            clearCanvas(Game.mainCanvasContext, Game.mainCanvas);

            sock.send(JSON.stringify({
                [Config.PROTOCOL_PREFIX]: GAME_PROTOCOL.SELECT_STATE,
                state: STATE_PROTOCOL.CLEAR,
            }));
        }
    });
});

function chatEnter(who, input, color) {
    if (color === null)
        color = "black";

    let $chatWin = $('#chat-window');
    let $chat = $('<p></p>');
    $chat.addClass('chat-bottom');
    $chat.css("color", color);
    $chat.text(who + ' : ' + input);
    $chatWin.append($chat);
}

function join($usersOutter, users) {
    let $users = $('#users');
    if ($users !== null)
        $users.remove();

    $users = $('<div id="users"></div>');
    for (const user of users) {
        let $user = $('<div></div>');
        $user.addClass('user');

        let $userName = $('<p></p>');
        $userName.addClass('user-name');
        $userName.text(user['userName']);

        $user.append($userName);
        $users.append($user);
    }

    $usersOutter.append($users);
}

function gameStart(drawerID, word) {
    GameClient.drawer = drawerID;
    $('.correct-answer').text(word);
    console.log(drawerID + ' == ' + myId);
    if (drawerID === myId) {
        alert('Your turn!');
    }
    else {
    }

    Game.Play();
}

function drawUpdate(x, y, init) {
    if (init === 1)
        move(x, y);
    else
        stroke(Game.mainCanvasContext, x, y);
}

function correctAnswer(userName, correct, newGameData) {
    let correctTxt = "오답!";
    if (correct === 1) {
        correctTxt = "정답!"
    }
    alert(userName + "님 " + correctTxt);

    if (correct === 1) {
        $('.correct-answer').text(newGameData['word']);
        console.log(newGameData['drawerID'] + ' == ' + myId);
        GameClient.drawer = newGameData['drawerID'];
        if (newGameData['drawerID'] === myId) {
            alert('New Game! Your turn!');
        }
        else
            alert('New Game!');

        clearCanvas(Game.mainCanvasContext, Game.mainCanvas);
    }
}