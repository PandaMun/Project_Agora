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
var isScreen = false;

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
        case 'receiveSystemComment':
            alert(parsedMessage.comment)
            break;
        case 'terminateDebate':
            alert('토론이 종료되었습니다.');
            leaveRoom();
            break
        default:
            console.error('Unrecognized message', parsedMessage);
    }
}

function register() {
    name = document.getElementById('name').value;
    let title = document.getElementById('title').value;
    let debateId = document.getElementById('debateId').value;
    position = document.getElementById('position').value;
    let roomType = document.getElementById('roomType').value;
    let time = document.getElementById('time').value;

    if (this.position === '사회자') {
        document.getElementById("buttons").style.display = '';
    }

    sendMessage({
        id: 'createRoom',
        debateId: debateId,
        title: title,
        roomType: roomType,
        time: time
    })

    document.getElementById('room-header').innerText = title;
    document.getElementById('join').style.display = 'none';
    document.getElementById('room').style.display = 'block';

    var message = {
        id: 'joinRoom',
        userName: name,
        debateId: debateId,
        title: title,
        position: position,
    }
    sendMessage(message);
}

function onNewParticipant(request) {
    receiveVideo(request.name, request.position, request.isScreen);
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

    if (isScreen) {
        console.log('share screen:', name, position)
        var participant = new Participant(name, position, true);
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
        var participant = new Participant(name, position, msg.isScreen);
        participants[name] = participant;
        var video = participant.getVideoElement();

        var options = {
            localVideo: video,
            mediaConstraints: constraints,
            onicecandidate: participant.onIceCandidate.bind(participant)
        }

        if (position === '청중') {
            participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
                function (error) {
                    if (error) {
                        return console.error(error);
                    }
                    this.generateOffer(participant.offerToReceiveVideo.bind(participant));
                });
        } else {
            participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options,
                function (error) {
                    if (error) {
                        return console.error(error);
                    }
                    this.generateOffer(participant.offerToReceiveVideo.bind(participant));
                });
        }

        msg.data.forEach(m => (receiveVideo(m.name, m.position, m.isScreen)));
    }

}

function start() {
    let debateId = document.getElementById('debateId').value;

    sendMessage({
        id: 'startSpeaking',
        debateId: debateId
    });
}

function stop() {
    let debateId = document.getElementById('debateId').value;

    sendMessage({
        id: 'pauseSpeaking',
        debateId: debateId
    });
}

function shareScreen() {
    name = document.getElementById('name').value;
    let debateId = document.getElementById('debateId').value;
    let title = document.getElementById('title').value;
    isScreen = true;

    sendMessage({
        id: 'leaveRoom'
    });
    for (var key in participants) {
        if (participants[key].name !== name) {
            var partVideo = document.getElementById("video-" + participants[key].name);
            console.log('leave', participants[key].name);
            partVideo.parentElement.remove();
        }
    }
    delete participants[name];
    document.getElementById("video-" + name).parentElement.remove();

    var message = {
        id: 'shareScreen',
        userName: name,
        debateId: debateId,
        title: title,
        position: position,
    }
    // name = 'screen_' + name;
    sendMessage(message);
    document.getElementById("button-share-on").style.display = "none";
    document.getElementById("button-share-off").style.display = "";
}

function stopShareScreen() {
    name = document.getElementById('name').value;
    let debateId = document.getElementById('debateId').value;
    let title = document.getElementById('title').value;
    isScreen = false;

    sendMessage({
        id: 'leaveRoom'
    });
    for (let key in participants) {
        if (participants[key].name !== name) {
            let partVideo = document.getElementById("video-" + participants[key].name);
            console.log('leave', participants[key].name);
            partVideo.parentElement.remove();
        }
    }
    delete participants[name];
    document.getElementById(name).remove();
    let video = document.getElementById("video-" + name);
    video.srcObject.getTracks().forEach(track => track.stop());
    video.remove();


    var message = {
        id: 'joinRoom',
        userName: name,
        debateId: debateId,
        title: title,
        position: position,
    }
    // name = 'screen_' + name;
    sendMessage(message);
    document.getElementById("button-share-on").style.display = "";
    document.getElementById("button-share-off").style.display = "none";
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

function receiveVideo(name, position, isScreen) {
    console.log(name, position)
    var sender = name;
    var participant = new Participant(sender, position, isScreen);
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
}

function onParticipantLeft(request) {
    console.log('Participant ' + request.name + ' left');
    var participant = participants[request.name];
    participant.dispose();
    document.getElementById('video-' + request.name).remove();
    delete participants[request.name];
}

function sendMessage(message) {
    var jsonMessage = JSON.stringify(message);
    console.log('Sending message: ' + jsonMessage);
    ws.send(jsonMessage);
}

function videoOnOff() {
    if (participants[name].rtcPeer.videoEnabled) {
        // 끌때
        participants[name].rtcPeer.videoEnabled = false;
        document.getElementById("vidOn").style.display = "";
        document.getElementById("vidOff").style.display = "none";
    } else {
        participants[name].rtcPeer.videoEnabled = true;
        document.getElementById("vidOn").style.display = "none";
        document.getElementById("vidOff").style.display = "";
    }
}

function audioOnOff() {
    if (participants[name].rtcPeer.audioEnabled) {
        participants[name].rtcPeer.audioEnabled = false;
        document.getElementById("audOn").style.display = "";
        document.getElementById("audOff").style.display = "none";
    } else {
        participants[name].rtcPeer.audioEnabled = true;
        document.getElementById("audOn").style.display = "none";
        document.getElementById("audOff").style.display = "";
    }
}

function sendSystemComment() {
    let debateId = document.getElementById('debateId').value;

    sendMessage({
        id: 'sendSystemComment',
        debateId: debateId,
        comment: 'test',
    })
}

function terminateDebate() {
    let debateId = document.getElementById('debateId').value;

    sendMessage({
        id: 'terminateDebate',
        debateId: debateId
    })
}