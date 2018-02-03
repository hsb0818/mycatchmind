var stompClient = null;

function connect() {
    var socket = new SockJS('/khwebgame');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);

        console.log('Get Roomlist');
        stompClient.send("/khserver/user/connection");
        stompClient.send("/khserver/roomlist", {});
        stompClient.subscribe('/khcli-lobby/roomlist', (roomList) => {
            console.log(roomList.body);
            if (roomList === null)
                return;

            let dataArr = JSON.parse(roomList.body);
            for (let room of dataArr) {
                let $room = $('<div></div>');
                $room.append('<h3>' + room.name + '</h3>');
                $room.append('<p>' + room.count + '</p>');

                let $btnEnter = $room.append('<button>Enter</button>');
                $btnEnter.click(() => {
                    $.ajax({
                        type:'post',
                        url:'/'
                    });
                });

                $('#rooms').append($room);
            }
        }, (message) => {
            //disconnect
        });

        stompClient.subscribe('/khcli-lobby/createroom', (room) => {
            console.log(room);
            alert('Room has been created');
            let data = JSON.parse(room.body);
            window.location.replace('/user/room?name=' + data.name);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

$(document).ready(() => {
    connect();

    $('#create-room').click(() => {
        let name = prompt("Please enter your room name", "room1");
        if (name != null)
        {
            stompClient.send("/khserver/createroom", {}, JSON.stringify({
                'name': name
            }));
        }
    });
});