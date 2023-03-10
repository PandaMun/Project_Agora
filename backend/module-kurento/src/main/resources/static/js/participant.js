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

const PARTICIPANT_MAIN_CLASS = 'participant main';
const PARTICIPANT_CLASS = 'participant';

/**
 * Creates a video element for a new participant
 *
 * @param {String} name - the name of the new participant, to be used as tag
 *                        name of the video element.
 *                        The tag of the new element will be 'video<name>'
 * @param {String} position - the name of the new participant, to be used as tag
 *                        name of the video element.
 *                        The tag of the new element will be 'video<name>'
 * @return
 */
function Participant(name, position, isScreen) {
    this.name = name;
    this.position = position;
    var container = document.createElement('div');
    // container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
    container.className = PARTICIPANT_CLASS;
    container.id = name;
    var span = document.createElement('span');
    var video = document.createElement('video');
    video.id = 'video-' + name;
    video.autoplay = true;
    video.controls = false;

    console.log(this)

    var rtcPeer;
    // container.onclick = switchContainerClass;
    console.log(this.position)
    if (this.position === '반대') {
        document.getElementById('participants-opp').appendChild(container);

    } else if (this.position === '찬성') {
        document.getElementById('participants-agree').appendChild(container);
    } else if (this.position === '사회자') {
        document.getElementById('moderator').appendChild(container);
    }

    container.appendChild(video);




    if (isScreen) {
        let alternated = document.createElement('div');
        alternated.innerText = '화면 공유를 진행 중입니다.'
        alternated.style.width = '300';
        alternated.style.height = '225';
        alternated.style.color = 'white';
        alternated.style.display = 'flex';
        alternated.style.justifyContent = 'center';
        alternated.style.alignItems = 'center';

        container.appendChild(alternated)
        document.getElementById('screen').appendChild(video);

    }

    container.appendChild(span);

    span.appendChild(document.createTextNode(name));

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
        console.log(name)
        var msg = {
            id: "receiveVideoFrom",
            sender: name,
            sdpOffer: offerSdp
        };
        sendMessage(msg);
    }


    this.onIceCandidate = function (candidate, wp) {
        console.log("Local candidate" + JSON.stringify(candidate));

        var message = {
            id: 'onIceCandidate',
            candidate: candidate,
            userName: name
        };
        sendMessage(message);
    }

    Object.defineProperty(this, 'rtcPeer', {writable: true});

    this.dispose = function () {
        console.log('Disposing participant ' + this.name);
        this.rtcPeer.dispose();
        container.parentNode.removeChild(container);
    };
}