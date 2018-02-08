let LOBBY_PROTOCOL = {
    ROOM_JOIN : 0,
    ROOM_CREATE : 1,
    ROOMINFO_UPDATE : 2,
};

let sock = new SockJS("http://14.58.119.156:9208/khwebgame-lobby");
sock.onopen = function() {
    console.log('open');

    sock.send(JSON.stringify({
        [Config.PROTOCOL_PREFIX] : LOBBY_PROTOCOL.ROOMINFO_UPDATE
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
        case LOBBY_PROTOCOL.ROOM_CREATE: {
            console.log(dataMap[Config.SESS_ROOM_UID]);
            $.post('/room/session',
                {
                    [Config.SESS_ROOM_UID] : dataMap[Config.SESS_ROOM_UID]
                })
                .done((data) => {
                    alert('successed to send roomUid');
                    window.location.replace("http://14.58.119.156:9208/room/in/" + dataMap["roomName"]);
                })
                .fail(() => {
                    alert('failed to send roomUid');
                });
            break;
        }
        case LOBBY_PROTOCOL.ROOM_JOIN: {
            $.post('/room/session',
                {
                    [Config.SESS_ROOM_UID] : dataMap[Config.SESS_ROOM_UID]
                })
                .done((data) => {
                    alert('successed to send roomUid');
                    window.location.replace("http://14.58.119.156:9208/room/in/" + dataMap["roomName"]);
                })
                .fail(() => {
                    alert('failed to send roomUid');
                });
            break;
        }
        case LOBBY_PROTOCOL.ROOMINFO_UPDATE: {
            if (Object.keys(dataMap['rooms']).length === 0)
                break;

            if ($('#rooms-inner').length) {
                $('#rooms-inner').remove();
            }

            let $rooms = $('<div id="rooms-inner"></div>');
            for (const room of dataMap['rooms']) {
                let $room = $('<div id="room"></div>');
                $room.append('<h3>name : ' + room['roomName'] + '</h3>');
                $room.append('<h4>count : ' + room['roomCount'] + '</h4>');

                let $btnJoin = $('<button>Join</button>');
                $btnJoin.click((e) => {
                    sock.send(JSON.stringify({
                        [Config.PROTOCOL_PREFIX] : LOBBY_PROTOCOL.ROOM_JOIN,
                        uid: room[Config.SESS_ROOM_UID]
                    }))
                });

                $room.append($btnJoin);
                $rooms.append($room);
            }

            $('#rooms').append($rooms);
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