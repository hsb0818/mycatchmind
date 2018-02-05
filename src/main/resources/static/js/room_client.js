let ROOM_PROTOCOL = {
    CHAT : 0
};

let sock = new SockJS("http://localhost:9208/khwebgame-room");
sock.onopen = function() {
    console.log('open');
};

sock.onmessage = function(e) {
    console.log('message', e.data);
    let dataMap = JSON.parse(e.data);
    if (dataMap[Config.PROTOCOL_SUC] == false) {
        console.log('[suc] receive is failed');
        return;
    }

    console.log("-------" + dataMap[Config.PROTOCOL_PREFIX]);
    switch (dataMap[Config.PROTOCOL_PREFIX]) {
        case ROOM_PROTOCOL.CHAT: {
            chatEnter(dataMap['chat']);
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
});

function chatEnter(input) {
    let $chatWin = $('#chat-window');
    let $chat = $('<p></p>');
    $chat.addClass('chat-bottom');
    $chat.text(input);
    $chatWin.append($chat);
}