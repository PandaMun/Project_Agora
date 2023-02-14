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
import kurentoUtils from "kurento-utils";

const PARTICIPANT_MAIN_CLASS = 'participant main';
const PARTICIPANT_CLASS = 'participant';

var ws ;
var participants = {};
var userName ;

function connect() {
	ws = new WebSocket('wss://localhost:8443/groupcall');
	participants = {};
	ws.onmessage = function (message) {
		var parsedMessage = JSON.parse(message.data);
		console.info('Received message: ' + message.data);

		switch (parsedMessage.id) {
			case 'existingParticipants':
				onExistingParticipants(parsedMessage);
				break;
			case 'newParticipantArrived':
				onNewParticipant(parsedMessage);
				break;
			case 'participantLeft':
				onParticipantLeft(parsedMessage);
				break;
			case 'receiveVideoAnswer':
				receiveVideoResponse(parsedMessage);
				break;
			case 'iceCandidate':
				participants[parsedMessage.userName].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
					if (error) {
						console.error("Error adding candidate: " + error);
						return;
					}
				});
				break;
			default:
				console.error('Unrecognized message', parsedMessage);
		}
	}
}
function onNewParticipant(request) {
	receiveVideo(request.userName);
}

function receiveVideoResponse(result) {
	participants[result.userName].rtcPeer.processAnswer(result.sdpAnswer, function (error) {
		if (error) return console.error(error);
	});
}

function receiveVideo(sender) {
	var participant = new Participant(sender);
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
	console.log('Participant ' + request.userName + ' left');
	var participant = participants[request.userName];
	participant.dispose();
	delete participants[request.userName];
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
	if (msg.data === []) {
		console.log('data is null')
	} else {
		console.log(userName + " registered in room " + msg.data.title);

	}
	var participant = new Participant(userName);
	participants[userName] = participant;
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
	console.log('리시브비디오직전~')
	msg.data.forEach(receiveVideo);
}

class Participant {
	constructor(userName) {

		this.userName = userName;
		var container = document.createElement('div');
		container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
		container.id = userName;
		var span = document.createElement('span');
		var video = document.createElement('video');
		var rtcPeer;

		container.appendChild(video);
		container.appendChild(span);
		container.onclick = switchContainerClass;
		document.getElementById('participants').appendChild(container);

		span.appendChild(document.createTextNode(userName));

		video.id = 'video-' + userName;
		video.autoplay = true;
		video.controls = false;


		this.getElement = function () {
			return container;
		}

		this.getVideoElement = function () {
			return video;
		}

		function switchContainerClass() {
			if (container.className === PARTICIPANT_CLASS) {
				var elements = Array.prototype.slice.call(document.getElementsByClassName(PARTICIPANT_MAIN_CLASS));
				elements.forEach(function (item) {
					item.className = PARTICIPANT_CLASS;
				});

				container.className = PARTICIPANT_MAIN_CLASS;
			} else {
				container.className = PARTICIPANT_CLASS;
			}
		}

		function isPresentMainParticipant() {
			return ((document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)).length != 0);
		}

		this.offerToReceiveVideo = function (error, offerSdp, wp) {
			if (error) return console.error("sdp offer error")
			console.log('Invoking SDP offer callback function');
			var message = {
				id: "receiveVideoFrom",
				sender: userName,
				sdpOffer: offerSdp
			};
			sendMessage(message);
		}


		this.onIceCandidate = function (candidate, wp) {
			console.log("Local candidate" + JSON.stringify(candidate));

			var message = {
				id: 'onIceCandidate',
				candidate: candidate,
				userName: userName
			};
			sendMessage(message);
		}

		Object.defineProperty(this, 'rtcPeer', {writable: true});

		this.dispose = function () {
			console.log('Disposing participant ' + this.userName);
			this.rtcPeer.dispose();
			container.parentNode.removeChild(container);
		};
	}
}

function registerRoom(data) {
	document.getElementById('room-header').innerText = 'ROOM' + data.title;
	document.getElementById('join').style.display = 'none';
	document.getElementById('room').style.display = 'block';

	userName = data.userName;

	let creation_message = {
		id: 'createRoom',
		debateId: data.debateId,
		title: data.title,
		roomType: data.roomType,
		time: data.time
	}
	sendMessage(creation_message)

	let join_message = {
		id: 'joinRoom',
		debateId: data.debateId,
		userName : data.userName,
		position : data.position
	}
	sendMessage(join_message);
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

function muteAudio(userName){
	console.log(participants)
	participants[userName].rtcPeer.videoEnabled = false;
}

function leaveRoom() {
	sendMessage({
		id: 'leaveRoom'
	});

	for (var key in participants) {
		participants[key].dispose();
	}

	// TODO
	document.getElementById('join').style.display = 'block';
	document.getElementById('room').style.display = 'none';

	ws.close();
}

function sendMessage(message) {
	var jsonMessage = JSON.stringify(message);
	console.log('Sending message: ' + jsonMessage);
	ws.send(jsonMessage);
}
const room = {connect, registerRoom, leaveRoom, muteAudio};

export default room;