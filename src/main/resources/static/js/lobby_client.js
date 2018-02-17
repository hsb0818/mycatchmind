let LOBBY_PROTOCOL = {
    ROOM_CREATE : 0,
    ROOMINFO_UPDATE : 1,
};

let sock = new SockJS("http://" + Config.SERVER_IP + ":9208/khwebgame-lobby");
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
                    [Config.SESS_ROOM_UID] : dataMap[Config.SESS_ROOM_UID],
                    [Config.SESS_ROOM_NAME] : dataMap[Config.SESS_ROOM_NAME]
                })
                .done((data) => {
                    alert('successed to send roomUid');
                    window.location.replace("http://" + Config.SERVER_IP + ":9208/room/in/" + dataMap[Config.SESS_ROOM_NAME]);
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
                $room.append('<h3>name : ' + room[Config.SESS_ROOM_NAME] + '</h3>');
                $room.append('<h4>count : ' + room['roomCount'] + '</h4>');

                let $btnJoin = $('<button>Join</button>');
                $btnJoin.click((e) => {
                    console.log();

                    $.post('/room/session', {
                            [Config.SESS_ROOM_UID] : room[Config.SESS_ROOM_UID],
                            [Config.SESS_ROOM_NAME] : room[Config.SESS_ROOM_NAME],
                        })
                        .done((data) => {
                            alert('successed to send roomUid');
                            window.location.replace("http://" + Config.SERVER_IP + ":9208/room/in/" + room[Config.SESS_ROOM_NAME]);
                        })
                        .fail(() => {
                            alert('failed to send roomUid');
                    });
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