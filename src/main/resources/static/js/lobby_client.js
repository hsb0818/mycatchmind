let LOBBY_PROTOCOL = {
    ROOM_JOIN : 0,
    ROOM_CREATE : 1,
    ROOMINFO_UPDATE : 2,
};

let sock = new SockJS("http://localhost:9208/khwebgame-lobby");
sock.onopen = function() {
    console.log('open');

    sock.send(JSON.stringify({
        [Config.PROTOCOL_PREFIX] : LOBBY_PROTOCOL.ROOMINFO_UPDATE
    }));
};

sock.onmessage = function(e) {
    console.log('message', e.data);
    let dataMap = JSON.parse(e.data);
    if (dataMap[Config.PROTOCOL_SUC] == false) {
        console.log('[suc] receive is failed');
        return;
    }

    console.log("-------" + dataMap[Config.PROTOCOL_PREFIX]);

    switch (dataMap[Config.PROTOCOL_PREFIX])
    {
        case LOBBY_PROTOCOL.ROOM_CREATE: {
            $.post('/room/session',
                {
                    [Config.SESS_ROOM_UID] : dataMap[Config.SESS_ROOM_UID]
                })
                .done((data) => {
                    alert('successed to send roomUid');
                    window.location.replace("http://localhost:9208/room/in/" + dataMap["roomName"]);
                })
                .fail(() => {
                    alert('failed to send roomUid');
                });
            break;
        }
        case LOBBY_PROTOCOL.ROOM_JOIN: {
            break;
        }
        case LOBBY_PROTOCOL.ROOMINFO_UPDATE: {
            break;
        }
    }
};

sock.onclose = function() {
    console.log('close');
};

$(document).ready(() => {
    $('#create-room').click(() => {
        let name = prompt("Please enter your room name", "room1");
        if (name != null)
        {
            sock.send(JSON.stringify({
                [Config.PROTOCOL_PREFIX] : LOBBY_PROTOCOL.ROOM_CREATE,
                roomName : name
            }));
        }
    });
});