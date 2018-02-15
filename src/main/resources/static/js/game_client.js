let GAME_PROTOCOL = {
    GAMESTART : 0,
};

let sock = new SockJS("http://" + Config.SERVER_IP + ":9208/khwebgame-game");
sock.onopen = function() {
    console.log('open');

    sock.send(JSON.stringify({
        [Config.PROTOCOL_PREFIX] : GAME_PROTOCOL.GAMESTART
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

    switch (dataMap[Config.PROTOCOL_PREFIX])
    {
        case GAME_PROTOCOL.GAMESTART: {
            break;
        }
    }
};

sock.onclose = function() {
    console.log('close');
};

$(function () {
});