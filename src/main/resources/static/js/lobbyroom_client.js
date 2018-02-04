let sock = new SockJS("http://localhost:9208/name");
sock.onopen = function() {
    console.log('open');
    sock.send(JSON.stringify({name : 'test'}));
};

sock.onmessage = function(e) {
    console.log('message', e.data);
    sock.close();
};

sock.onclose = function() {
    console.log('close');
};

$(document).ready(() => {

    $('#create-room').click(() => {
        let name = prompt("Please enter your room name", "room1");
        if (name != null)
        {
            sock.send(JSON.stringify(name));
        }
    });
});