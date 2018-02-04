let ROOM_PROTOCOL = {
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
    /*
    switch (dataMap[Config.PROTOCOL_PREFIX])
    {
    }
    */
};

sock.onclose = function() {
    console.log('close');
};

$(document).ready(() => {
});