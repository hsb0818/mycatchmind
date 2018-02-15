let ROOM_PROTOCOL = {
    JOIN : 0,
    CHAT : 1,
    READY : 2,
};

let sock = new SockJS("http://" + Config.SERVER_IP + ":9208/khwebgame-room");
sock.onopen = function() {
    console.log('open');
    sock.send(JSON.stringify({
        [Config.PROTOCOL_PREFIX] : ROOM_PROTOCOL.JOIN
    }));
};

sock.onmessage = function(e) {
    console.log('message', e.data);
    let dataMap = JSON.parse(e.data);
    if (!dataMap[Config.PROTOCOL_SUC]) {
        console.log('[suc] receive is failed');
        return;
    }

    console.log("-------" + dataMap[Config.PROTOCOL_PREFIX]);
    switch (dataMap[Config.PROTOCOL_PREFIX]) {
        case ROOM_PROTOCOL.JOIN: {
            let $user = $('<div></div>');
            let $nameOutter = $('<h3></h3>');
            $nameOutter.attr('id', 'just-user');
            $nameOutter.text('user : ');

            let $name = $('<strong></strong>');
            $name.attr('user-id', dataMap['joinUserID']);
            $name.text(dataMap['joinUserName']);

            let $ready = $('<h1></h1>');
            $ready.attr('id', 'ready-' + dataMap['joinUserID']);
            $ready.css('display', 'none');
            $ready.text('Ready!');

            $nameOutter.append($name);
            $nameOutter.append($ready);
            $user.append($nameOutter);

            $('#user').append($user);
            break;
        }

        case ROOM_PROTOCOL.CHAT: {
            chatEnter(dataMap['chat']);
            break;
        }

        case ROOM_PROTOCOL.READY: {
            console.log('[cli recv] ready');
            let $txtReady = $('#ready-' + dataMap['userId']);
            if (parseInt(dataMap['display']) === 1) {
                $txtReady.show();
            }
            else
                $txtReady.hide();

            if (parseInt(dataMap['readyToStart']) === 1) {
                let count = 6;
                let timer = setInterval(() => {
                    if (count < 1) {
                        clearInterval(timer);
                        window.location.replace("http://" + Config.SERVER_IP + ":9208/game/in/" + roomName);
                    }
                    else {
                        count--;
                        chatEnter("start game after " + count.toString() + " sec", "red");
                    }
                }, 1000);
            }

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
            [Config.PROTOCOL_PREFIX] : ROOM_PROTOCOL.CHAT,
            chat: $chatInput.val()
        }));

        chatEnter($chatInput.val());
        $chatInput.val('');
    });

    $('#btn-ready').click(() => {
        let $txtReady = $('#ready-' + myId);
        $txtReady.toggle();

        let show = 0;
        if ($txtReady.css('display') !== 'none') {
            show = 1;
        }

        sock.send(JSON.stringify({
            [Config.PROTOCOL_PREFIX] : ROOM_PROTOCOL.READY,
            display : show
        }));
    });
});

function chatEnter(input, color) {
    if (color === null)
        color = "black";

    let $chatWin = $('#chat-window');
    let $chat = $('<p></p>');
    $chat.addClass('chat-bottom');
    $chat.css("color", color);
    $chat.text(input);
    $chatWin.append($chat);
}