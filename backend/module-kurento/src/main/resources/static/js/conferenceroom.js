/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

var ws = new WebSocket('wss://' + location.host + '/groupcall');
var participants = {};
var name;
var position;

window.onbeforeunload = function () {
    ws.close();
};

ws.onmessage = function (message) {
    var parsedMessage = JSON.parse(message.data);
    console.info('Received message: ' + message.data);

    switch (parsedMessage.id) {
        case 'existingParticipants':
            onExistingParticipants(parsedMessage);
            break;
        case 'newParticipantArrived':
            console.log('newArrived')
            onNewParticipant(parsedMessage);
            break;
        case 'participantLeft':
            onParticipantLeft(parsedMessage);
            break;
        case 'receiveVideoAnswer':
            console.log(participants)
            receiveVideoResponse(parsedMessage);
            break;
        case 'iceCandidate':
            console.log('iceCandidate', parsedMessage)
            participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
                if (error) {
                    console.error("Error adding candidate: " + error);
                    return;
                }
            });
            break;
        case 'timeRemaining':
            var time = parsedMessage.time;
            document.getElementById('timer').innerText = parseInt(time / 60) + ':' + time % 60
            break
        case 'pauseSpeaking':
            var time = parsedMessage.time;
            document.getElementById('timer').innerText = parseInt(time / 60) + ':' + time % 60
            break
        default:
            console.error('Unrecognized message', parsedMessage);
    }
}

function register() {
    name = document.getElementById('name').value;
    var room = document.getElementById('roomName').value;
    position = document.getElementById('position').value;

    document.getElementById('room-header').innerText = 'ROOM ' + room;
    document.getElementById('join').style.display = 'none';
    document.getElementById('room').style.display = 'block';

    var message = {
        id: 'joinRoom',
        userName: name,
        debateId: room,
        roomName: room,
        position: position,
    }
    sendMessage(message);
}

function onNewParticipant(request) {
    receiveVideo(request.name, request.position);
}

function receiveVideoResponse(result) {
    participants[result.name].rtcPeer.processAnswer(result.sdpAnswer, function (error) {
        if (error) return console.error(error);
    });
}


function callResponse(message) {
    if (message.response != 'accepted') {
        console.info('Call not accepted by peer. Closing call');
        stop();
    } else {
        webRtcPeer.processAnswer(message.sdpAnswer, function (error) {
            if (error) return console.error(error);
        });
    }
}

function onExistingParticipants(msg) {
    var constraints = {
        audio: true,
        video: {
            mandatory: {
                maxWidth: 320,
                maxFrameRate: 15,
                minFrameRate: 15
            }
        }
    };

    if (position === 'screen') {
        name = 'screen_' + name
        console.log('share screen:', name, position)
        var participant = new Participant(name, position);
        participants[name] = participant;
        var video = participant.getVideoElement();

        if (navigator.getDisplayMedia || navigator.mediaDevices.getDisplayMedia) {
            if (navigator.mediaDevices.getDisplayMedia) {
                navigator.mediaDevices
                    .getDisplayMedia({video: true, audio: true})
                    .then((stream) => {
                        video.srcObject = stream;
                        options = {
                            videoStream: stream,
                            mediaConstraints: constraints,
                            sendSource: "screen",
                            onicecandidate: participant.onIceCandidate.bind(participant),
                        };
                        participant.rtcPeer =
                            new kurentoUtils.WebRtcPeer.WebRtcPeerSendrecv(options,
                                function (error) {
                                    if (error) {
                                        return console.error(error);
                                    }
                                    this.generateOffer(participant.offerToReceiveVideo.bind(participant));
                                });
                        msg.data.forEach(m => {receiveVideo(m.name, m.position)});
                    });
            }
        }

    } else {
        console.log(name + " registered in room " + room);
        var participant = new Participant(name, position);
        participants[name] = participant;
        var video = participant.getVideoElement();

        var options = {
            localVideo: video,
            mediaConstraints: constraints,
            onicecandidate: participant.onIceCandidate.bind(participant)
        }
        participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options,
            function (error) {
                if (error) {
                    return console.error(error);
                }
                this.generateOffer(participant.offerToReceiveVideo.bind(participant));
            });

        msg.data.forEach(m => (receiveVideo(m.name, m.position)));
    }

}

function start() {
    var room = document.getElementById('roomName').value;

    sendMessage({
        id: 'startSpeaking',
        debateId: room
    });
}

function stop() {
    var room = document.getElementById('roomName').value;

    sendMessage({
        id: 'pauseSpeaking',
        debateId: room
    });
}

function shareScreen() {
    name = document.getElementById('name').value;
    var room = document.getElementById('roomName').value;
    position = 'screen'

    var message = {
        id: 'shareScreen',
        userName: name,
        debateId: room,
        roomName: room,
        position: position,
    }
    sendMessage(message);
}

function leaveRoom() {
    sendMessage({
        id: 'leaveRoom'
    });

    for (var key in participants) {
        participants[key].dispose();
    }

    document.getElementById('join').style.display = 'block';
    document.getElementById('room').style.display = 'none';

    ws.close();
}

function receiveVideo(name, position) {
    console.log(name, position)
    if (position === 'screen') {
        return;
    }
    var sender = name;
    var participant = new Participant(sender, position);
    participants[sender] = participant;
    var video = participant.getVideoElement();

    var options = {
        remoteVideo: video,
        onicecandidate: participant.onIceCandidate.bind(participant)
    }

    participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
        function (error) {
            if (error) {
                return console.error(error);
            }
            this.generateOffer(participant.offerToReceiveVideo.bind(participant));
        });
    ;
}

function onParticipantLeft(request) {
    console.log('Participant ' + request.name + ' left');
    var participant = participants[request.name];
    participant.dispose();
    delete participants[request.name];
}

function sendMessage(message) {
    var jsonMessage = JSON.stringify(message);
    console.log('Sending message: ' + jsonMessage);
    ws.send(jsonMessage);
}