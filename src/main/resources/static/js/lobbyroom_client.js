var stompClient = null;

function connect() {
    var socket = new SockJS('/khwebgame');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/khcli-lobby/roomlist', (roomList) => {

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